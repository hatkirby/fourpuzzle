/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.database.Music;
import com.fourisland.fourpuzzle.database.Sound;
import com.fourisland.fourpuzzle.database.Transitions;
import com.fourisland.fourpuzzle.database.Vocabulary;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import com.fourisland.fourpuzzle.util.PauseTimer;
import com.fourisland.fourpuzzle.window.ChoiceWindow;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 *
 * @author hatkirby
 */
public class TitleScreenGameState implements GameState {
    
    ChoiceWindow choices;
    
    public void initalize()
    {
        /* Play the Database-specified Title Screen music, which the client can
         * change */
        Audio.playMusic(Database.getMusic(Music.Title));
        
        /* Create the choice window, whose options are also taken from the
         * Database. Then tell Display to render it and KeyboardInput to send
         * keyboard events to it */
        choices = new ChoiceWindow.Builder(Arrays.asList(Database.getVocab(Vocabulary.NewGame), Database.getVocab(Vocabulary.LoadGame), Database.getVocab(Vocabulary.EndGame)), ChoiceWindow.ChoiceWindowLocation.BottomLeft)
                .center(true)
                .build();
        Display.registerRenderable(choices);
        KeyboardInput.registerInputable(choices);
    }
    
    public void deinitalize()
    {
        // Stop the music because the title screen is closing
        Audio.stopMusic();
        
        // Also tell Display and KeyboardInput to forget about our ChoiceWindow
        Display.unregisterRenderable(choices);
        KeyboardInput.unregisterInputable(choices);
    }

    PauseTimer pt = new PauseTimer(0);
    public void processInput(KeyInput key)
    {
        if (key.isActionDown())
        {
            /* If the player presses the action key, play the selection sound
             * and act upon the choice they selected */
            Audio.playSound(Database.getSound(Sound.Selection));

            if (choices.getSelected().equals(Database.getVocab(Vocabulary.NewGame)))
            {
                /* If the player starts a new game, set the save data to a blank
                 * instance */
                Game.setSaveFile(new SaveFile());

                // Then transition to the map where the game starts
                try {
                    Display.transition(Database.getTransition(Transitions.Generic), new MapViewGameState("TestMap", 1, 2), true);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } else if (choices.getSelected().equals(Database.getVocab(Vocabulary.LoadGame)))
            {
                // Do nothing, yet
            } else if (choices.getSelected().equals(Database.getVocab(Vocabulary.EndGame)))
            {
                // End the game, but transition out before doing so
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Display.transition(Database.getTransition(Transitions.Generic).getOutTransition());
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        System.exit(0);
                    }
                }).start();
            }
        } else if (pt.isElapsed())
        {
            if (key.getKey() == KeyEvent.VK_UP)
            {
                /* Tell ChoiceWindow that the player wants to move up, then
                 * wait a second before allowing any more vertical movement so
                 * that the player has a chance to let go of the up key */
                choices.moveUp();
                pt.setTimer(1);
            } else if (key.getKey() == KeyEvent.VK_DOWN)
            {
                /* Tell ChoiceWindow that the player wants to move down, then
                 * wait a second before allowing any more vertical movement so
                 * that the player has a chance to let go of the down key */
                choices.moveDown();
                pt.setTimer(1);
            }
        }
    }

    public void doGameCycle()
    {
        // Do nothing, yet
    }

    public void render(Graphics2D g)
    {
        // Display the title screen picture
        g.drawImage(ObjectLoader.getImage("Picture", "Title"), 0, 0, null);
    }

}

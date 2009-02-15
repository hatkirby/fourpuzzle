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
        Audio.playMusic(Database.getMusic(Music.Title));
        
        choices = new ChoiceWindow(Arrays.asList(Database.getVocab(Vocabulary.NewGame), Database.getVocab(Vocabulary.LoadGame), Database.getVocab(Vocabulary.EndGame)), true, ChoiceWindow.ChoiceWindowLocation.BottomLeft);
        Display.registerRenderable(choices);
    }
    
    public void deinitalize()
    {
        Audio.stopMusic();
        
        Display.unregisterRenderable(choices);
    }

    PauseTimer pt = new PauseTimer(0);
    public void processInput(KeyInput key)
    {
        if (pt.isElapsed())
        {
            if (key.getKey() == KeyEvent.VK_ENTER)
            {
                Audio.playSound(Database.getSound(Sound.Selection));
                
                if (choices.getSelected().equals(Database.getVocab(Vocabulary.NewGame)))
                {
                    Game.setSaveFile(new SaveFile());

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Display.transition(Database.getTransition(Transitions.TitleExit));
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }

                            Game.setGameState(new MapViewGameState("TestMap", 1, 2));

                            try {
                                Display.transition(Database.getTransition(Transitions.TitleToMap));
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }).start();
                } else if (choices.getSelected().equals(Database.getVocab(Vocabulary.LoadGame)))
                {
                    // Do nothing, yet
                } else if (choices.getSelected().equals(Database.getVocab(Vocabulary.EndGame)))
                {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Display.transition(Database.getTransition(Transitions.TitleExit));
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            
                            System.exit(0);
                        }
                    }).start();
                }
            } else if (key.getKey() == KeyEvent.VK_UP)
            {
                choices.moveUp();
                
                pt.setTimer(1);
            } else if (key.getKey() == KeyEvent.VK_DOWN)
            {
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
        g.drawImage(ObjectLoader.getImage("Picture", "Title"), 0, 0, null);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.database.Music;
import com.fourisland.fourpuzzle.database.Sound;
import com.fourisland.fourpuzzle.database.Vocabulary;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.transition.SquareTransition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
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
    int wx, wy;
    
    public void initalize()
    {
        Audio.playMusic(Database.getMusic(Music.Title));
        
        choices = new ChoiceWindow(Arrays.asList(Database.getVocab(Vocabulary.NewGame), Database.getVocab(Vocabulary.LoadGame), Database.getVocab(Vocabulary.EndGame)), true);
        wx = (Game.WIDTH/5)-(choices.getWidth()/2);
        wy = (Game.HEIGHT/4*3)-(choices.getHeight()/2);
    }
    
    public void deinitalize()
    {
        Audio.stopMusic();
    }

    PauseTimer pt = new PauseTimer(0);
    public void processInput()
    {
        if (pt.isElapsed())
        {
            if (Game.getKey().getKeyCode() == KeyEvent.VK_ENTER)
            {
                Audio.playSound(Database.getSound(Sound.Selection));
                
                if (choices.getSelected().equals(Database.getVocab(Vocabulary.NewGame)))
                {
                    Game.setSaveFile(new SaveFile());

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Display.transition(new SquareTransition(TransitionDirection.Out));
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }

                            Game.setGameState(new MapViewGameState("TestMap", 1, 2));

                            try {
                                Display.transition(new SquareTransition(TransitionDirection.In));
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
                                Display.transition(new SquareTransition(TransitionDirection.Out));
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            
                            System.exit(0);
                        }
                    }).start();
                }
            } else if (Game.getKey().getKeyCode() == KeyEvent.VK_UP)
            {
                choices.moveUp();
                
                pt.setTimer(2);
            } else if (Game.getKey().getKeyCode() == KeyEvent.VK_DOWN)
            {
                choices.moveDown();
                
                pt.setTimer(2);
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
        choices.render(g, wx, wy);
    }

}

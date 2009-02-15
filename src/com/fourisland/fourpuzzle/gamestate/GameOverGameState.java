/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.SaveFile;
import com.fourisland.fourpuzzle.database.Music;
import com.fourisland.fourpuzzle.database.Transitions;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class GameOverGameState implements GameState {
    
    public void initalize()
    {
        Audio.playMusic(Database.getMusic(Music.GameOver));
    }
    
    public void deinitalize()
    {
        Audio.stopMusic();
    }

    public void processInput(KeyInput key)
    {
        if ((key.getKey() == KeyEvent.VK_ENTER) || (key.getKey() == KeyEvent.VK_SPACE))
        {
            Game.setSaveFile(new SaveFile());
            
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Display.transition(Database.getTransition(Transitions.GameOverToTitle));
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    
                    Game.setGameState(new TitleScreenGameState());
                    
                    try {
                        Display.transition(Database.getTransition(Transitions.TitleEnter));
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }
    }

    public void doGameCycle()
    {
        // Do nothing
    }

    public void render(Graphics2D g)
    {
        g.drawImage(ObjectLoader.getImage("Picture", "GameOver"), 0, 0, null);
    }

}

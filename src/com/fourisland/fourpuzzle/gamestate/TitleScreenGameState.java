/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.transition.SquareTransition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class TitleScreenGameState implements GameState {
    
    public void initalize()
    {
        Audio.playMusic(Database.getMusic("Title"));
    }
    
    public void deinitalize()
    {
        Audio.stopMusic();
    }

    public void processInput()
    {
        if (Game.getKey().getKeyCode() == KeyEvent.VK_ENTER)
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

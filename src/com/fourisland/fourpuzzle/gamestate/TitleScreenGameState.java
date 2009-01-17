/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class TitleScreenGameState implements GameState {
    
    public void initalize() throws Exception
    {
        Audio.playMusic("Opening");
    }
    
    public void deinitalize() throws Exception
    {
        Audio.stopMusic();
    }

    public void processInput() throws Exception {
        if (Game.getKey().getKeyCode() == KeyEvent.VK_ENTER)
        {
            Game.setSaveFile(new SaveFile());
            Game.setGameState(new MapViewGameState("TestMap", 1, 2));
            //Game.setGameState(new SquareTransition(this, new MapViewGameState("TestMap", 0, 0)));
            //Game.setGameState(new TransitionGameState(this, this));
            //Game.setGameState(new GameOverGameState());
        }
    }

    public void doGameCycle() throws Exception {
        // Do nothing, yet
    }

    public void render(Graphics2D g) throws Exception {
        g.drawImage(ObjectLoader.getImage("Picture", "Title"), 0, 0, null);
    }

}

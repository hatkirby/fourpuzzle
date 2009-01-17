/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.SaveFile;
import com.fourisland.fourpuzzle.transition.SquareTransition;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class GameOverGameState implements GameState {
    
    public void initalize() throws Exception
    {
        Audio.playMusic("GameOver");
    }
    
    public void deinitalize() throws Exception
    {
        Audio.stopMusic();
    }

    public void processInput() throws Exception {
        if ((Game.getKey().getKeyCode() == KeyEvent.VK_ENTER) || (Game.getKey().getKeyCode() == KeyEvent.VK_SPACE))
        {
            Game.setSaveFile(new SaveFile());
            //Display.transition(SquareTransition.class, this, new TitleScreenGameState());
            Display.transition(new SquareTransition(true), new Runnable() {
                public void run() {
                    try {
                        Game.setGameState(new TitleScreenGameState());
                    } catch (Exception ex) {
                        Logger.getLogger(GameOverGameState.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    public void doGameCycle() throws Exception {
        // Do nothing
    }

    public void render(Graphics2D g) throws Exception {
        g.drawImage(ObjectLoader.getImage("Picture", "GameOver"), 0, 0, null);
    }

}

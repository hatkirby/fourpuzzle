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

/**
 *
 * @author hatkirby
 */
public class GameOverGameState implements GameState {
    
    public void initalize()
    {
        // Play the Database-specifed Game Over music
        Audio.playMusic(Database.getMusic(Music.GameOver));
    }
    
    public void deinitalize()
    {
        // Stop the music
        Audio.stopMusic();
    }

    public void processInput(KeyInput key)
    {
        if (key.isActionDown())
        {
            /* When the user presses the action key to exit the game over
             * screen, clear the save data and transition back to the title
             * screen.
             * 
             * NOTE: Clearing the save data may not actually be necessary here
             * because TitleScreenGameState clears the save data before starting
             * a new file */
            Game.setSaveFile(new SaveFile());
            
            try {
                Display.transition(Database.getTransition(Transitions.Generic), new TitleScreenGameState(), true);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void doGameCycle()
    {
        // Do nothing
    }

    public void render(Graphics2D g)
    {
        // Display the Game Over picture
        g.drawImage(ObjectLoader.getImage("Picture", "GameOver"), 0, 0, null);
    }

}

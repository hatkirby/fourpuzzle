/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import com.fourisland.fourpuzzle.gamestate.GameState;

/**
 *
 * @author hatkirby
 */
public class Game {
    
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int FPS = (1000 / 20); // 20 fps

    private static SaveFile saveFile;
    public static SaveFile getSaveFile()
    {
        return saveFile;
    }
    public static void setSaveFile(SaveFile saveFile)
    {
        Game.saveFile = saveFile;
    }
    
    private static GameState gameState;
    public static GameState getGameState()
    {
        return gameState;
    }
    public static void setGameState(GameState gameState)
    {
        if (Game.gameState != null)
        {
            Game.gameState.deinitalize();
        }
        
        Game.gameState = gameState;
        Game.gameState.initalize();
    }
    
    public static HeroEvent getHeroEvent()
    {
        return getSaveFile().getHero();
    }
    
}

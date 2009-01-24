/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import com.fourisland.fourpuzzle.gamestate.GameState;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class Game {
    
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;

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
    public static void setGameState(GameState gameState) throws Exception
    {
        if (Game.gameState != null)
        {
            Game.gameState.deinitalize();
        }
        
        Game.gameState = gameState;
        Game.gameState.initalize();
    }
    
    private static KeyEvent key;
    public static KeyEvent getKey()
    {
        return key;
    }
    public static void setKey(KeyEvent key)
    {
        Game.key = key;
    }
    
    public static HeroEvent getHeroEvent()
    {
        return getSaveFile().getHero();
    }
    
}
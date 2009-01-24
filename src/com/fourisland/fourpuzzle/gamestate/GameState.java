/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import java.awt.Graphics2D;

/**
 *
 * @author hatkirby
 */
public interface GameState {
    
    public void initalize();
    public void deinitalize();
    
    public void processInput();
    public void doGameCycle();
    public void render(Graphics2D g);
    
}

/*
    TitleScreen
    MapView
    Battle
    GameOver
    Menu
    LoadFile
    SaveFile
    Transition
*/
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
    
    public void initalize() throws Exception;
    public void deinitalize() throws Exception;
    
    public void processInput() throws Exception;
    public void doGameCycle() throws Exception;
    public void render(Graphics2D g) throws Exception;
    
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
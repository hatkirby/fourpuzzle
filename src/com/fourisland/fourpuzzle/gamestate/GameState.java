/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate;

import com.fourisland.fourpuzzle.util.Inputable;
import com.fourisland.fourpuzzle.util.Renderable;

/**
 *
 * @author hatkirby
 */
public interface GameState extends Renderable, Inputable {
    
    public void initalize();
    public void deinitalize();
    
    public void doGameCycle();
    
}
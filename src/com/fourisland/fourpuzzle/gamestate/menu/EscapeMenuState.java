/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.menu;

import com.fourisland.fourpuzzle.util.Renderable;
import com.fourisland.fourpuzzle.util.Inputable;

/**
 *
 * @author hatkirby
 */
public interface EscapeMenuState extends Renderable, Inputable {
    
    public void initalize(MenuGameState mgs);
    public void deinitalize();
    public void tick();

}

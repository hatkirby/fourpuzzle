/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.menu;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.window.Window;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class SaveEMS implements EscapeMenuState {
    
    MenuGameState parent;
    BufferedImage cacheBase;

    public void initalize(MenuGameState mgs)
    {
        parent = mgs;
        cacheBase = Window.Default.getImage(Game.WIDTH-Window.Default.getFullWidth(0), Window.Default.getFullHeight(Game.HEIGHT)/3+Window.Default.getFullHeight(0));
    }

    public void deinitalize()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void tick()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void render(Graphics2D g)
    {
        g.drawImage(cacheBase, 0, 0, null);
    }

    public void processInput(KeyInput key)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}

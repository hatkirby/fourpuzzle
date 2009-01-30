/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.viewpoint;

/**
 *
 * @author hatkirby
 */
public class FixedViewpoint implements Viewpoint {
    
    private int x;
    private int y;
    
    public FixedViewpoint(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.viewpoint;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.util.Interval;

/**
 *
 * @author hatkirby
 */
public class MovingViewpoint implements Viewpoint {
    
    private double x;
    private double y;
    private int sx;
    private int sy;
    private int dx;
    private int dy;
    private double speed;
    private int xdist;
    private int ydist;
    private Runnable callback;
    private Interval in;
    
    public MovingViewpoint(int sx, int sy, int dx, int dy, Runnable callback)
    {
        this(sx, sy, dx, dy, callback, 1000);
    }
    
    public MovingViewpoint(int sx, int sy, int dx, int dy, Runnable callback, int length)
    {
        this.x = sx;
        this.y = sy;
        this.sx = sx;
        this.sy = sy;
        this.dx = dx;
        this.dy = dy;
        this.speed = length / Game.FPS;
        this.xdist = dx - sx;
        this.ydist = dy - sy;
        this.callback = callback;
        this.in = Interval.createMillisInterval((int) speed);
    }
    
    private void refresh()
    {
        x += (xdist / speed);
        y += (ydist / speed);
        
        if (((sx < dx) && (x > dx)) || ((sx > dx) && (x < dx)))
        {
            x = dx;
        }
        
        if (((sy < dy) && (y > dy)) || ((sy > dy) && (y < dy)))
        {
            y = dy;
        }
        
        if ((x == dx) && (y == dy))
        {
            callback.run();
        }
    }

    public int getX()
    {
        if (in.isElapsed())
        {
            refresh();
        }
        
        return (int) x;
    }

    public int getY()
    {
        if (in.isElapsed())
        {
            refresh();
        }
        
        return (int) y;
    }
    
}

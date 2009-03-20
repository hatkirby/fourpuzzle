/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.viewpoint;

import com.fourisland.fourpuzzle.util.Interval;

/**
 *
 * @author hatkirby
 */
public class ShakingViewpoint implements Viewpoint {
    
    int sx;
    int length;
    int x;
    int y;
    ShakeSpeed speed;
    Interval in;
    Runnable callback;
    boolean back = false;
    long start;
    public ShakingViewpoint(int x, int y, ShakeSpeed speed, int length, Runnable callback)
    {
        this.sx = x;
        this.length = length;
        this.x = x;
        this.y = y;
        this.in = Interval.createMillisInterval(1000F / speed.getSpeed());
        this.speed = speed;
        this.callback = callback;
        this.start = System.currentTimeMillis();
    }
    
    private void refresh()
    {
        if (back)
        {
            x -= speed.getSpeed();
            
            if (x < sx - 16)
            {
                back = false;
            }
        } else {
            x += speed.getSpeed();
            
            if (x > sx + 16)
            {
                back = true;
            }
        }
        
        if (start + length <= System.currentTimeMillis())
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
        
        return x;
    }

    public int getY()
    {
        return y;
    }
    
    public static enum ShakeSpeed
    {
        Slow(16),
        Medium(24),
        Fast(32);
        
        private int speed;
        private ShakeSpeed(int speed)
        {
            this.speed = speed;
        }
        
        public int getSpeed()
        {
            return speed;
        }
    }

}

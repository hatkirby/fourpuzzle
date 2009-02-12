/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import com.fourisland.fourpuzzle.Game;

/**
 *
 * @author hatkirby
 */
public class Interval {
    
    private int wait;
    private Interval(int wait)
    {
        this.wait = wait;
    }
    
    public static Interval createTickInterval(int ticks)
    {
        return createMillisInterval(Game.FPS*ticks);
    }
    
    public static Interval createMillisInterval(int millis)
    {
        return new Interval(millis*1000000);
    }
    
    private long last = System.nanoTime();
    public boolean isElapsed()
    {
        if (last+wait < System.nanoTime())
        {
            last = System.nanoTime();
            
            return true;
        }
        
        return false;
    }

}

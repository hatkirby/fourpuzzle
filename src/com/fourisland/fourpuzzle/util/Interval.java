/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.PuzzleApplication;

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
    
    public static Interval createTickInterval(float ticks)
    {
        return createMillisInterval(Game.FPS * ticks);
    }
    
    public static Interval createMillisInterval(float millis)
    {
        return new Interval((int) (millis*1000000));
    }
    
    private long last = System.nanoTime();
    public boolean isElapsed()
    {
        if (Interval.getDebugSpeed())
        {
            return true;
        }
        
        if (last+wait < System.nanoTime())
        {
            last = System.nanoTime();
            
            return true;
        }
        
        return false;
    }
    
    private static boolean debugSpeed = false;
    public static void setDebugSpeed(boolean debugSpeed)
    {
        Interval.debugSpeed = debugSpeed;
    }
    
    public static boolean getDebugSpeed()
    {
        return debugSpeed;
    }

}

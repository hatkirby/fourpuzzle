/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyboardInput;
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
        if (PuzzleApplication.debugSpeed)
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

}

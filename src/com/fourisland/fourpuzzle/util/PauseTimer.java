/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

/**
 *
 * @author hatkirby
 */
public class PauseTimer {
    
    private int ticks;
    public PauseTimer(int ticks)
    {
        this.ticks = ticks;
    }
    
    Interval in = Interval.createTickInterval(1);
    public boolean isElapsed()
    {
        if (Interval.getDebugSpeed())
        {
            return true;
        }
        
        if (in.isElapsed())
        {
            ticks--;
        }
        
        if (ticks <= 0)
        {
            return true;
        }
        
        return false;
    }
    
    public void setTimer(int ticks)
    {
        this.ticks = ticks;
    }
    
    public int getTimer()
    {
        if (in.isElapsed())
        {
            if (ticks > 0)
            {
                ticks--;
            }
        }
        
        return ticks;
    }

}

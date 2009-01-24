/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import java.awt.Graphics2D;

/**
 *
 * @author hatkirby
 */
public abstract class Transition {
    
    private boolean way;
    protected void setDirection(boolean from)
    {
        if ((from) && !(isFromSupported()))
        {
            throw new TransitionUnsupportedException(this.getClass().getSimpleName(), "From");
        } else if ((!from) && !(isToSupported()))
        {
            throw new TransitionUnsupportedException(this.getClass().getSimpleName(), "To");
        } else {
            way = from;
        }
    }
    
    public boolean getDirection()
    {
        return way;
    }

    public boolean isFromSupported()
    {
        return true;
    }
    
    public boolean isToSupported()
    {
        return true;
    }
    
    public abstract void render(Graphics2D g);
    
    private boolean running = true;
    public boolean isRunning()
    {
        return running;
    }
    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
}

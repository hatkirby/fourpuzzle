/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author hatkirby
 */
public class SquareTransition extends Transition {
    
    private int tick;
    public SquareTransition(boolean from) throws TransitionUnsupportedException
    {
        setDirection(from);
        
        if (from)
        {
            tick = 160;
        } else {
            tick = 0;
        }
    }
    
    public void render(Graphics2D g)
    {
        if (((!getDirection()) && (tick == 0)) || ((getDirection()) && (tick == 160)))
        {
            setRunning(false);
            return;
        }
        
        if (getDirection())
        {
            tick+=8;
        } else {
            tick-=8;
        }
        
        g.setBackground(Color.BLACK);
        g.fillRect(0, 0, 320, 240);
        g.setClip(160-tick, 140-tick, tick*2, tick*2-40);
    }

}

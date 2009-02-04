/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class SquareTransition implements MultidirectionalTransition {
    
    private int tick;
    private TransitionDirection direction;
    public SquareTransition(TransitionDirection direction)
    {
        this.direction = direction;
        
        if (direction == TransitionDirection.Out)
        {
            tick = 160;
        } else {
            tick = 0;
        }
    }
    
    public boolean render(Graphics2D g)
    {
        if (((direction == TransitionDirection.Out) && (tick == 0)) || ((direction == TransitionDirection.In) && (tick == 160)))
        {
            return true;
        }
        
        if (direction == TransitionDirection.In)
        {
            tick+=8;
        } else {
            tick-=8;    
        }
        
        g.setBackground(Color.BLACK);
        g.fillRect(0, 0, 320, 240);
        g.setClip(160-tick, 140-tick, tick*2, tick*2-40);
        
        return false;
    }

    public TransitionDirection getDirection()
    {
        return direction;
    }
    
    private BufferedImage preTransition;
    public void setPreTransition(BufferedImage preTransition)
    {
        this.preTransition = preTransition;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import com.fourisland.fourpuzzle.Game;
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
        g.drawImage(preTransition, 0, 0, null);
        
        if (direction == TransitionDirection.In)
        {
            tick+=8;
            
            g.drawImage(postTransition, 160-tick, 140-tick, tick*2+(160-tick), tick*2-40+(140-tick), 160-tick, 140-tick, tick*2+(160-tick), tick*2-40+(140-tick), null);
        } else {
            tick-=8;
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Game.WIDTH, 160-tick);
            g.fillRect(0, 0, 160-tick, Game.HEIGHT);
            g.fillRect(tick*2+(160-tick), 0, Game.WIDTH-(tick*2), Game.HEIGHT);
            g.fillRect(0, tick*2-40+(140-tick), Game.WIDTH, Game.HEIGHT-(tick*2-40));
        }
        
        if (((direction == TransitionDirection.Out) && (tick == 0)) || ((direction == TransitionDirection.In) && (tick == 160)))
        {
            return true;
        }
        
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
    
    private BufferedImage postTransition;
    public void setPostTransition(BufferedImage postTransition)
    {
        this.postTransition = postTransition;
    }

    public Transition copy()
    {
        return new SquareTransition(direction);
    }

}

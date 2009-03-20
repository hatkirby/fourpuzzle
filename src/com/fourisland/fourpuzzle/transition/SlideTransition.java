/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class SlideTransition implements MultidirectionalTransition {

    TransitionDirection direction;
    Direction d;
    public SlideTransition(TransitionDirection direction, Direction d)
    {
        this.direction = direction;
        this.d = d;
        
        if (d == Direction.North)
        {
            max = -(Game.HEIGHT);
            way = SlideDirection.UpOrDown;
        } else if (d == Direction.West)
        {
            max = -(Game.WIDTH);
            way = SlideDirection.SideToSide;
        } else if (d == Direction.South)
        {
            max = Game.HEIGHT;
            way = SlideDirection.UpOrDown;
        } else if (d == Direction.East)
        {
            max = Game.WIDTH;
            way = SlideDirection.SideToSide;
        }
        
        wait = max / (Game.FPS/2);
    }
    
    public TransitionDirection getDirection()
    {
        return direction;
    }

    int tick;
    int wait;
    int max;
    SlideDirection way;
    public boolean render(Graphics2D g)
    {   
        if (max > 0)
        {
            tick = Math.min(tick + wait, max);
        } else {
            tick = Math.max(tick + wait, max);
        }
        
        if (direction == TransitionDirection.Out)
        {
            g.setColor(Color.BLACK);
            
            if (way == SlideDirection.SideToSide)
            {
                g.drawImage(preTransition, tick, 0, null);
                
                if (max > 0)
                {
                    g.fillRect(0, 0, tick, Game.HEIGHT);
                } else {
                    g.fillRect(Game.WIDTH+tick, 0, -tick, Game.HEIGHT);
                }
            } else {
                g.drawImage(preTransition, 0, tick, null);
                
                if (max > 0)
                {
                    g.fillRect(0, 0, Game.WIDTH, tick);
                } else {
                    g.fillRect(0, Game.HEIGHT+tick, Game.WIDTH, -tick);
                }
            }
        } else if (direction == TransitionDirection.In)
        {
            if (way == SlideDirection.SideToSide)
            {
                g.drawImage(preTransition, tick, 0, null);
                
                if (max > 0)
                {
                    g.drawImage(postTransition, tick - Game.WIDTH, 0, null);
                } else {
                    g.drawImage(postTransition, Game.WIDTH + tick, 0, null);
                }
            } else {
                g.drawImage(preTransition, 0, tick, null);
                
                if (max > 0)
                {
                    g.drawImage(postTransition, 0, tick - Game.HEIGHT, null);
                } else {
                    g.drawImage(postTransition, 0, Game.HEIGHT + tick, null);
                }
            }
        }
        
        if (tick == max)
        {
            return true;
        }
        
        return false;
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
    
    private enum SlideDirection
    {
        SideToSide,
        UpOrDown
    }

    public Transition copy()
    {
        return new SlideTransition(direction, d);
    }

}

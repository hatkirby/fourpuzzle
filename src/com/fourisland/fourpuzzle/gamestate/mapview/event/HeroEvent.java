/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Layer;
import java.awt.Graphics;
import java.awt.Point;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.GameCharacter;
import com.fourisland.fourpuzzle.gamestate.mapview.CharSet;
import com.fourisland.fourpuzzle.NoCharactersInPartyException;

/**
 *
 * @author hatkirby
 */
public class HeroEvent implements Event {
    
    public HeroEvent()
    {
        location = new Point();
    }
    
    public String getLabel()
    {
        return "Hero";
    }
    
    private Point location;
    public Point getLocation()
    {
        return location;
    }
    public void setLocation(Point location)
    {
        this.location = location;
    }
    public void setLocation(int x, int y)
    {
        location.setLocation(x, y);
    }
    
    public void render(Graphics g) throws Exception
    {
        int x = (location.x * 16) - 4;
        int y = (location.y * 16) - 16;
        
        if (moving)
        {
            if (moveDirection == Direction.North)
            {
                y -= (4 - moveTimer) * 4;
            } else if (moveDirection == Direction.West)
            {
                x -= (4 - moveTimer) * 4;
            } else if (moveDirection == Direction.South)
            {
                y += (4 - moveTimer) * 4;
            } else if (moveDirection == Direction.East)
            {
                x += (4 - moveTimer) * 4;
            }
        }
        
        try
        {
            GameCharacter toDraw = Game.getSaveFile().getParty().getLeader();
            if (!toDraw.getGraphic().equals("blank"))
            {
                g.drawImage(CharSet.getCharSet(toDraw.getGraphic()).getImage(toDraw.getGraphicOffset(), direction, animationStep), x, y, null);
            }
        } catch (NoCharactersInPartyException ex)
        {
        }
    }
    
    
    private boolean moving = false;
    public boolean isMoving()
    {
        return moving;
    }
    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }
    
    private int moveTimer;
    private Direction moveDirection;
    public void startMoving(Direction toMove) throws Exception
    {
        setDirection(toMove);
        setAnimationStep(2);
        moveTimer = 4;
        moving = true;
        moveDirection = toMove;
    }
    public void processMoving() throws Exception
    {
        if (moving)
        {
            moveTimer--;
            if (moveTimer == 2)
            {
                setAnimationStep(0);
            } else if (moveTimer == 0)
            {
                setAnimationStep(1);
                moving = false;
                
                if (moveDirection == Direction.North)
                {
                    setLocation(getLocation().x,getLocation().y-1);
                } else if (moveDirection == Direction.West)
                {
                    setLocation(getLocation().x-1,getLocation().y);
                } else if (moveDirection == Direction.South)
                {
                    setLocation(getLocation().x,getLocation().y+1);
                } else if (moveDirection == Direction.East)
                {
                    setLocation(getLocation().x+1,getLocation().y);
                }
            }
        }
    }
    
    private Direction direction = Direction.South;
    public Direction getDirection()
    {
        return direction;
    }
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    private int animationStep = 1;
    public int getAnimationStep()
    {
        return animationStep;
    }
    public void setAnimationStep(int animationStep)
    {
        this.animationStep = animationStep;
    }

    public Layer getLayer() throws Exception
    {
        return Layer.Middle;
    }

}
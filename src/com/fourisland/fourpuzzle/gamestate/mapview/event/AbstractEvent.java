/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.util.Functions;
import java.awt.Point;

/**
 *
 * @author hatkirby
 */
public abstract class AbstractEvent implements Event {
    
    private Point location = new Point();
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
    
    private boolean moving = false;
    public boolean isMoving()
    {
        return moving;
    }
    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }
    
    /* TODO Remove the moveDirection field. As direction itself is
     * always the same as moveDirection when moveDirection is needed,
     * it will do fine without having to complicated access modifiers
     */
    
    protected int moveTimer;
    protected Direction moveDirection;
    public void startMoving(Direction toMove)
    {
        setDirection(toMove);
        
        if (!getParentMap().checkForCollision(this, toMove))
        {
            setAnimationStep(2);
            moveTimer = 4;
            setMoving(true);
            moveDirection = toMove;
        }
    }
    
    public void processMoving()
    {
        if (isMoving())
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
    
    public boolean isOccupyingSpace(int x, int y)
    {
        if (getLocation().equals(new Point(x,y)))
        {
            return true;
        }
        
        if (Functions.isMovingTo(this, x, y))
        {
            return true;
        }
        
        return false;
    }
    
    private Map parentMap = null;
    public Map getParentMap()
    {
        if (parentMap == null)
        {
            throw new NullPointerException();
        }
        
        return parentMap;
    }
    public void setParentMap(Map parentMap)
    {
        this.parentMap = parentMap;
    }
}

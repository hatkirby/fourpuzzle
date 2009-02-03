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
    
    private int moveTimer;
    public boolean startMoving(Direction toMove)
    {
        if (isMoving())
        {
            return false;
        }
        
        setDirection(toMove);
        
        if (getDirection() != toMove)
        {
            return false;
        }
        
        if (!getParentMap().checkForCollision(this, toMove))
        {
            setAnimationStep(2);
            moveTimer = 4;
            setMoving(true);
            
            return true;
        } else {
            return false;
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
                
                if (getDirection() == Direction.North)
                {
                    setLocation(getLocation().x,getLocation().y-1);
                } else if (getDirection() == Direction.West)
                {
                    setLocation(getLocation().x-1,getLocation().y);
                } else if (getDirection() == Direction.South)
                {
                    setLocation(getLocation().x,getLocation().y+1);
                } else if (getDirection() == Direction.East)
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
    
    public int getRenderX()
    {
        return (getLocation().x * 16) - 4 + getMovingX();
    }
    
    public int getRenderY()
    {
        return (getLocation().y * 16) - 16 + getMovingY();
    }
    
    public int getMovingX()
    {
        if (isMoving())
        {
            if (getDirection() == Direction.West)
            {
                return -((4 - moveTimer) * 4);
            } else if (getDirection() == Direction.East)
            {
                return (4 - moveTimer) * 4;
            }
        }
        
        return 0;
    }
    
    public int getMovingY()
    {
        if (isMoving())
        {
            if (getDirection() == Direction.North)
            {
                return -((4 - moveTimer) * 4);
            } else if (getDirection() == Direction.South)
            {
                return (4 - moveTimer) * 4;
            }
        }
        
        return 0;
    }
}

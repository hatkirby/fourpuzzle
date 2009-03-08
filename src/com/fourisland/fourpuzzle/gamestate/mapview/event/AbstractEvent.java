/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.util.Functions;
import com.fourisland.fourpuzzle.util.Interval;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
            moveTimer = getMoveSpeed().getSpeed();
            setMoving(true);
            
            return true;
        } else {
            return false;
        }
    }
    
    Interval in = Interval.createTickInterval(0.5F);
    public void processMoving()
    {
        if (isMoving())
        {
            if (in.isElapsed())
            {
                moveTimer--;
                if (moveTimer <= 0)
                {
                    setAnimationStep(1);
                    setMoving(false);
                    setLocation(getDirection().to(getLocation()));
                } else if (moveTimer <= (getMoveSpeed().getSpeed() / 2))
                {
                    setAnimationStep(0);    
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
    
    public List<Direction> getLegalMoves()
    {
        List<Direction> temp = new ArrayList<Direction>();
        for (Direction d : Direction.values())
        {
            if (!getParentMap().checkForCollision(this, d))
            {
                temp.add(d);
            }
        }
        
        return temp;
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
                return -(Math.round((getMoveSpeed().getSpeed() - moveTimer) * (16F / getMoveSpeed().getSpeed())));
            } else if (getDirection() == Direction.East)
            {
                return Math.round((getMoveSpeed().getSpeed() - moveTimer) * (16F / getMoveSpeed().getSpeed()));
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
                return -(Math.round((getMoveSpeed().getSpeed() - moveTimer) * (16F / getMoveSpeed().getSpeed())));
            } else if (getDirection() == Direction.South)
            {
                return Math.round((getMoveSpeed().getSpeed() - moveTimer) * (16F / getMoveSpeed().getSpeed()));
            }
        }
        
        return 0;
    }
    
    private MoveSpeed moveSpeed;
    public void setMoveSpeed(MoveSpeed moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }
    
    public MoveSpeed getMoveSpeed()
    {
        return moveSpeed;
    }
}

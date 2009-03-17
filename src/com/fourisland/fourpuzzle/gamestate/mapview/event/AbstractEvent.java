/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
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
        /* If the event is already moving (sometimes it manages to slip through
         * the other filters), simply return without doing anything */
        if (isMoving())
        {
            return false;
        }
        
        /* Attempt to turn in the correct direction and then check if it was
         * done. It could fail in certain cases which would mean the event
         * shouldn't move, for instance, if a LayerEvent's AnimationType was
         * FixedGraphic.
         * 
         * There is a slight problem with this, however. Currently, if the
         * AnimationType is FixedGraphic, but the event is already facing the
         * correct direction, the event will move anyway, despite being fixed */
        setDirection(toMove);
        if (getDirection() != toMove)
        {
            return false;
        }
        
        /* Make sure that there are no present obstructions on the map in the
         * specified direction */
        if (!getParentMap().checkForCollision(this, toMove))
        {
            // Start the stepping animation
            setAnimationStep(2);
            
            // Ask the event's MoveSpeed for the length of the animation
            moveTimer = getMoveSpeed().getSpeed();
            
            // Set the moving flag
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
            // Movement should be processed every half tick
            if (in.isElapsed())
            {
                // Decrement the move timer
                moveTimer--;
                
                if (moveTimer <= 0)
                {
                    /* If movement has finished, stop the animation and unset
                     * the moving flag */
                    setAnimationStep(1);
                    setMoving(false);
                    
                    // Move the event to the correct location
                    setLocation(getDirection().to(getLocation()));
                } else if (moveTimer <= (getMoveSpeed().getSpeed() / 2))
                {
                    // If movement is half-complete, advance its animation
                    setAnimationStep(0);
                }
            }
        }
    }
    
    public boolean isOccupyingSpace(int x, int y)
    {
        // Check if the event occupies the given location
        if (getLocation().equals(new Point(x,y)))
        {
            return true;
        }
        
        /* Because a moving event technically occupies two locations, we also
         * need to check if the given location is where the event is moving to
         * (if it's moving at all) */
        if (isMoving())
        {
            Point loc = getDirection().to(getLocation());
            if ((loc.x == x) && (loc.y == y))
            {
                return true;
            }
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
    
    private MoveFrequency freq;
    public void setFrequency(MoveFrequency freq)
    {
        this.freq = freq;
    }
    
    public MoveFrequency getFrequency()
    {
        return freq;
    }
}

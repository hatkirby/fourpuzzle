/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Layer;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.util.Functions;

/**
 *
 * @author hatkirby
 */
public class LayerEvent implements Event {
    
    /** Create a new Event instance
     * 
     * @param x The horizontal location of the Event on the Map
     * @param y The vertical location of the Event on the Map
     */
    public LayerEvent(int x, int y)
    {
        location = new Point(x,y);
        events = new ArrayList<PossibleEvent>();
        label = "Unlabelled";
    }
    
    /** Create a new Event instance
     * 
     * @param x The horizontal location of the Event on the Map
     * @param y The vertical location of the Event on the Map
     * @param label An identifying label for the Event
     */
    public LayerEvent(int x, int y, String label)
    {
        this(x,y);
        this.label = label;
    }
    
    private String label;
    public String getLabel()
    {
        return label;
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
    
    private ArrayList<PossibleEvent> events;
    public void addEvent(PossibleEvent pe)
    {
        events.add(pe);
    }
    
    private PossibleEvent getPossibleEvent()
    {
        int i;
        for (i=(events.size()-1);i>-1;i--)
        {
            boolean good = true;
            int j;
            for (j=0;j<events.get(i).preconditions();j++)
            {
                good = (good ? events.get(i).getPrecondition(j).match() : false);
            }
            
            if (good)
            {
                return events.get(i);
            }
        }
        
        return new PossibleEvent();
    }
    
    public void render(Graphics g)
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
        
        PossibleEvent toDraw = getPossibleEvent();
        if (!toDraw.getGraphic().equals("blank"))
        {
            g.drawImage(toDraw.getImage(), x, y, null);
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
    public void startMoving(Map map)
    {
        Direction toMove = getPossibleEvent().getMovement().nextMovement();
        
        if (toMove != null)
        {
            if (!map.checkForCollision(getLocation().x, getLocation().y, toMove))
            {
                startMoving(toMove);
            }
        }
    }
    public void startMoving(Direction toMove)
    {
        getPossibleEvent().setDirection(toMove);
        getPossibleEvent().setAnimationStep(2);
        moveTimer = 4;
        moving = true;
        moveDirection = toMove;
    }
    public void processMoving()
    {
        if (moving)
        {
            moveTimer--;
            if (moveTimer == 2)
            {
                getPossibleEvent().setAnimationStep(0);
            } else if (moveTimer == 0)
            {
                getPossibleEvent().setAnimationStep(1);
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
    
    public Direction getDirection()
    {
        return getPossibleEvent().getDirection();
    }
    public void setDirection(Direction direction)
    {
        getPossibleEvent().setDirection(direction);
    }

    public Layer getLayer()
    {
        return getPossibleEvent().getLayer();
    }
    
    public EventCallTime getCalltime()
    {
        return getPossibleEvent().getCalltime();
    }
    
    public EventCall getCallback()
    {
        return getPossibleEvent().getCallback();
    }

    public void setLabel(String string) {
        this.label = string;
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
    
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.ImmutableEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * FollowMovementType allows an Event to continually walk toward a specified
 * Event.
 *
 * @author hatkirby
 */
public class FollowMovementType implements MovementType {
    
    String name = null;
    Event event = null;
    Point lastLoc = new Point();
    Deque<Direction> moves = new ArrayDeque<Direction>();
    List<Point> attempts = new ArrayList<Point>();
    
    public FollowMovementType() {}
    
    public FollowMovementType(String name)
    {
        this.name = name;
    }
    
    private boolean search(ImmutableEvent ev)
    {
        for (Direction d : Direction.values())
        {
            Point loc = d.to(ev.getLocation());
            if (lastLoc.equals(loc))
            {
                return true;
            }
        }
        
        List<Direction> ds = ev.getLegalMoves();
        List<Direction> tempd = new ArrayList<Direction>();
        
        if (lastLoc.x < ev.getLocation().x)
        {
            tempd.add(Direction.West);
        } else if (lastLoc.x > ev.getLocation().x)
        {
            tempd.add(Direction.East);
        } else {
            if (!ds.contains(Direction.North) || !ds.contains(Direction.South))
            {
                tempd.add(Direction.West);
                tempd.add(Direction.East);
            }
        }
        
        if (lastLoc.y < ev.getLocation().y)
        {
            tempd.add(Direction.North);
        } else if (lastLoc.y > ev.getLocation().y)
        {
            tempd.add(Direction.South);
        } else {
            if (!ds.contains(Direction.West) || !ds.contains(Direction.East))
            {
                tempd.add(Direction.North);
                tempd.add(Direction.South);
            }
        }
        
        tempd.retainAll(ds);
        Collections.shuffle(tempd);
        
        for (Direction d : tempd)
        {
            Point loc = d.to(ev.getLocation());
            
            if (attempts.contains(loc))
            {
                continue;
            }
            
            Event temp = new LayerEvent(loc.x, loc.y);
            temp.setParentMap(ev.getParentMap());
            attempts.add(loc);
            
            if (search(new ImmutableEvent(temp)))
            {
                moves.push(d);
                return true;
            }
        }
        
        return false;
    }
    
    public Direction nextMovement(ImmutableEvent ev)
    {
        if (event == null)
        {
            if (name == null)
            {
                event = Game.getHeroEvent();
            } else {
                event = ev.getParentMap().getEvent(name);
            }
        } else if ((name != null) && !(event.getParentMap().equals(ev.getParentMap())))
        {
            event = ev.getParentMap().getEvent(name);
        }
        
        if (!event.getLocation().equals(lastLoc))
        {
            lastLoc.setLocation(event.getLocation());
        
            moves.clear();
            attempts.clear();
        
            search(ev);
        }
        
        if (!moves.isEmpty())
        {
            return moves.pop();
        }
        
        return null;
    }

}
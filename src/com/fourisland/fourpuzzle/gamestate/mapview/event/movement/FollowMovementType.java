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
        /* Iterate over all of the directions and check if moving in that
         * direction would place the event on the destination event. If so, the
         * correct path has been aquired and thus we can return */
        for (Direction d : Direction.values())
        {
            Point loc = d.to(ev.getLocation());
            if (lastLoc.equals(loc))
            {
                return true;
            }
        }
        
        /* Calculate the directions to attempt and the order in which to do so
         * based on proximity to the destination event */
        
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
        
        // Remove calculated directions that aren't legal movements
        tempd.retainAll(ds);
        
        // Randomize directions so movement is more fluid
        Collections.shuffle(tempd);
        
        // Iterate over the suggested directions 
        for (Direction d : tempd)
        {
            /* If the position in the suggested direction has already been
             * covered, try the next direction */
            Point loc = d.to(ev.getLocation());
            if (attempts.contains(loc))
            {
                continue;
            }
            
            /* Create a dummy event and use it to search from the position in
             * the suggested direction */
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.PossibleEvent;

/**
 *
 * @author hatkirby
 */
public class Functions {

    public static boolean canTurn(PossibleEvent ev)
    {
        switch (ev.getAnimation())
        {
            case CommonWithoutStepping: return true;
            case CommonWithStepping: return true;
            case WithoutStepping: return true;
            case FixedGraphic: return false;
            case TurnLeft: return false;
            case TurnRight: return false;
        }
        
        return false;
    }
    
    public static boolean isFacing(Event ev1, Event ev2)
    {
        if ((ev1.getDirection() == Direction.North) && (ev2.getLocation().x == ev1.getLocation().x) && (ev2.getLocation().y == (ev1.getLocation().y - 1)))
        {
            return true;
        } else if ((ev1.getDirection() == Direction.West) && (ev2.getLocation().x == (ev1.getLocation().x - 1)) && (ev2.getLocation().y == ev1.getLocation().y))
        {
            return true;
        } else if ((ev1.getDirection() == Direction.South) && (ev2.getLocation().x == ev1.getLocation().x) && (ev2.getLocation().y == (ev1.getLocation().y + 1)))
        {
            return true;
        } else if ((ev1.getDirection() == Direction.East) && (ev2.getLocation().x == (ev1.getLocation().x + 1)) && (ev2.getLocation().y == ev1.getLocation().y))
        {
            return true;
        }
        
        return false;
    }

    public static boolean isMovingTo(Event ev, int x, int y)
    {        
        if (ev.isMoving() == false)
        {
            return false;
        }
        
        if ((ev.getDirection() == Direction.North) && ((ev.getLocation().y-1) == y) && (ev.getLocation().x == x))
        {
            return true;
        }
        
        if ((ev.getDirection() == Direction.West) && (ev.getLocation().y == y) && ((ev.getLocation().x-1) == x))
        {
            return true;
        }
        
        if ((ev.getDirection() == Direction.South) && ((ev.getLocation().y+1) == y) && (ev.getLocation().x == x))
        {
            return true;
        }
        
        if ((ev.getDirection() == Direction.East) && (ev.getLocation().y == y) && ((ev.getLocation().x+1) == x))
        {
            return true;
        }
        
        return false;
    }
    
    public static Direction oppositeDirection(Direction dir)
    {
        switch (dir)
        {
            case North: return Direction.South;
            case West: return Direction.East;
            case South: return Direction.North;
            case East: return Direction.West;
        }
        
        return null;
    }

}

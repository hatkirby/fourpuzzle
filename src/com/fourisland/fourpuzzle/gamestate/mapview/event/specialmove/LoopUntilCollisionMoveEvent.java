/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import java.awt.Point;

/**
 *
 * @author hatkirby
 */
public class LoopUntilCollisionMoveEvent implements MoveEvent {
    
    private Point loc;
    private MoveEvent[] moves;
    
    public LoopUntilCollisionMoveEvent(MoveEvent[] moves)
    {
        this.moves = moves;
    }

    public void doAction(Event ev)
    {
        loc = new Point();
        
        while ((loc == null) || (!loc.equals(ev.getLocation())))
        {
            loc.setLocation(ev.getLocation());
            
            for (MoveEvent move : moves)
            {
                move.doAction(ev);
            }
        }
    }

}

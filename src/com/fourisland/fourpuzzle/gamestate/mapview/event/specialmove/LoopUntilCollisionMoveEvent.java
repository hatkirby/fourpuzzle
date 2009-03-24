/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import java.awt.Point;
import java.util.Arrays;

/**
 * LoopUntilCollisionMoveEvent takes an array of <b>MoveEvent</b>s and repeatedly
 * executes them until the event in question collides into something. This is
 * done by comparing the location of the event before and after the MoveEvents execute.
 * Because of this, MoveEvent arrays that do not move the event will not block the
 * game.
 * 
 * @author hatkirby
 */
public class LoopUntilCollisionMoveEvent implements MoveEvent {
    
    private Point loc;
    private MoveEvent[] moves;
    
    public LoopUntilCollisionMoveEvent(MoveEvent[] moves)
    {
        this.moves = Arrays.copyOf(moves, moves.length);
    }

    public void doAction(Event ev)
    {
        loc = new Point();
        
        try {
            while ((loc == null) || (!loc.equals(ev.getLocation())))
            {
                loc.setLocation(ev.getLocation());

                for (MoveEvent move : moves)
                {
                    move.doAction(ev);

                    if (Thread.currentThread().isInterrupted())
                    {
                        throw new InterruptedException();
                    }
                }
            }
        } catch (InterruptedException ex) {
            /* The special event has been cancelled; Propogate the interrupt to
             * the MoveEventThread */
        }
    }

}

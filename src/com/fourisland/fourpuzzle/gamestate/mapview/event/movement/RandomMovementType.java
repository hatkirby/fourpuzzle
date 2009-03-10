/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.ImmutableEvent;
import com.fourisland.fourpuzzle.util.Interval;
import java.util.List;
import java.util.Random;

/**
 * RandomMovementType moves the event in random directions.
 *
 * @author hatkirby
 */
public class RandomMovementType implements MovementType {

    Interval in = Interval.createTickInterval(10);
    public Direction nextMovement(ImmutableEvent ev)
    {
        if (in.isElapsed())
        {
            List<Direction> moves = ev.getLegalMoves();
            Random r = new Random();
            int ra = r.nextInt(moves.size());
            
            if (ra != moves.size())
            {
                return moves.get(ra);
            }
        }
        
        return null;
    }

}

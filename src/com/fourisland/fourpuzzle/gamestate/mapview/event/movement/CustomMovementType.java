/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.ImmutableEvent;

/**
 * CustomMovementEvent takes an array of Directions and directions the event
 * to move in each of them, one by one, returning to the top of the list when
 * done.
 *
 * @author hatkirby
 */
public class CustomMovementType implements MovementType {

    private Direction[] moves;
    private int step = 0;
    
    public CustomMovementType(Direction[] moves)
    {
        this.moves = moves;
    }
    
    public Direction nextMovement(ImmutableEvent ev)
    {
        if (step >= moves.length)
        {
            step = 0;
        }

        return moves[step++];
    }

}

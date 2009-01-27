/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;

/**
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
    
    public Direction nextMovement()
    {        
        if (step >= moves.length)
        {
            step = 0;
        }

        return moves[step++];
    }

}

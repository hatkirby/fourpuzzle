/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;
import java.util.Random;

/**
 * RandomMovementType moves the event in random directions.
 *
 * @author hatkirby
 */
public class RandomMovementType implements MovementType {

    public Direction nextMovement()
    {
        Random r = new Random();
        int ra = r.nextInt(1000);
        Direction toMove = null;
        boolean letsMove = false;

        if (ra < 25)
        {
            toMove = Direction.North;
            letsMove = true;
        } else if (ra < 50)
        {
            toMove = Direction.West;
            letsMove = true;
        } else if (ra < 75)
        {
            toMove = Direction.South;
            letsMove = true;
        } else if (ra < 100)
        {
            toMove = Direction.East;
            letsMove = true;
        }

        if (letsMove)
        {
            return toMove;
        }
        
        return null;
    }

}

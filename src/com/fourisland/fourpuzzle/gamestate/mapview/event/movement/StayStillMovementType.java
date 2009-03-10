/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.ImmutableEvent;

/**
 * StayStillMovementType keeps the event stationary.
 *
 * @author hatkirby
 */
public class StayStillMovementType implements MovementType {

    public Direction nextMovement(ImmutableEvent ev)
    {
        return null; // Do nothing, stay still
    }
    
}

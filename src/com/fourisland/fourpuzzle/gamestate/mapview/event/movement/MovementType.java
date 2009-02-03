/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;

/**
 * A MovementType is an object that specifies the type of AI a non-hero event
 * will have. Every tick when said event is not moving, MovementType's
 * <b>nextMovement()</b> function will be invoked which should return the next
 * direction for the event to move into.
 *
 * @author hatkirby
 */
public interface MovementType {
    
    public Direction nextMovement();
    
}
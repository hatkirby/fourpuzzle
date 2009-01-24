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
public class StayStillMovementType implements MovementType {

    public Direction startMoving()
    {
        return null; // Do nothing, stay still
    }
    
}

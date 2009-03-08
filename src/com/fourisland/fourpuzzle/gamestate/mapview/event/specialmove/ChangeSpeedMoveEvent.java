/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.MoveSpeed;

/**
 * ChangeSpeedMoveEvent changes the walk speed of an event; in other words, it
 * changes how fast an event moves from one space to the next.
 *
 * @author hatkirby
 */
public class ChangeSpeedMoveEvent implements MoveEvent {
    
    private MoveSpeed moveSpeed;
    public ChangeSpeedMoveEvent(MoveSpeed moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }

    public void doAction(Event ev)
    {
        ev.setMoveSpeed(moveSpeed);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;

/**
 *
 * @author hatkirby
 */
public class StepMoveEvent implements MoveEvent {

    Direction direction;
    public StepMoveEvent(Direction direction)
    {
        this.direction = direction;
    }
    
    public void doAction(Event ev) throws Exception
    {
        ev.startMoving(direction);

        while (ev.isMoving())
        {
            Thread.sleep(2);
        }
    }

}
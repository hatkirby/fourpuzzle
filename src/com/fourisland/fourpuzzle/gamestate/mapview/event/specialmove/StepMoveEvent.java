/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void doAction(Event ev)
    {
        ev.startMoving(direction);

        while (ev.isMoving())
        {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(StepMoveEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

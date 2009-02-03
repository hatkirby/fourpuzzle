/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;

/**
 * FaceMoveEvent turns the selected Event in the chosen direction.
 * 
 * @author hatkirby
 */
public class FaceMoveEvent implements MoveEvent {

    Direction direction;
    public FaceMoveEvent(Direction direction)
    {
        this.direction = direction;
    }
    
    public void doAction(Event ev)
    {
        ev.setDirection(direction);
    }

}

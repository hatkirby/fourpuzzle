
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;

/**
 * A MoveEvent is an object that tells the <b>MoveEvent()</b> SpecialEvent
 * what to do.
 * 
 * @author hatkirby
 */
public interface MoveEvent {

    public void doAction(Event ev);
    
}

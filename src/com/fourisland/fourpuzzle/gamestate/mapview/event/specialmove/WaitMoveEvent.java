/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;

/**
 *
 * @author hatkirby
 */
public class WaitMoveEvent implements MoveEvent {

    int wait;
    public WaitMoveEvent(int wait)
    {
        this.wait = wait;
    }
    
    public void doAction(Event ev) throws Exception
    {
        Thread.sleep(wait);
    }

}

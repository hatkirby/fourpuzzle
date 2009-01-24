/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void doAction(Event ev)
    {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitMoveEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

/**
 *
 * @author hatkirby
 */
public abstract class EventCall extends SpecialEvent implements Runnable {
    
    public static EventCall getEmptyEventCall()
    {
        return new EventCall() {
            @Override
            public void run() {
            }
        };
    }

    public abstract void run();
    
}

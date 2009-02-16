/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import java.util.concurrent.Future;

/**
 *
 * @author hatkirby
 */
public abstract class EventCall extends SpecialEvent {
    
    public static EventCall getEmptyEventCall()
    {
        return new EventCall() {
            @Override
            public void run() {
            }
        };
    }
    
    public abstract void run();

    private Future isRunning = null;
    public void activate(EventCallTime calltime)
    {
        if ((isRunning == null) || (isRunning.isDone()))
        {
            if (calltime == EventCallTime.ParallelProcess)
            {
                isRunning = EventHandler.runParallel(this);
            } else {
                isRunning = EventHandler.runEvent(this);
            }
        }
    }
    
}

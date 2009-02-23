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
    
    public abstract void run() throws InterruptedException;

    private Future eventThread = null;
    public void activate(EventCallTime calltime)
    {
        if ((eventThread == null) || (eventThread.isDone()))
        {
            if (calltime == EventCallTime.ParallelProcess)
            {
                eventThread = EventHandler.runParallel(this);
            } else {
                eventThread = EventHandler.runEvent(this);
            }
        }
    }
    
    public void cancel()
    {
        if ((eventThread != null) && (!eventThread.isDone()))
        {
            eventThread.cancel(true);
        }
    }
    
}

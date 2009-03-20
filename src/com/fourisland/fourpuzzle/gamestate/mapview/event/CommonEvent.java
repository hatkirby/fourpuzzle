/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.gamestate.mapview.event.precondition.Precondition;
import java.util.ArrayList;

/**
 *
 * @author hatkirby
 */
public class CommonEvent {

    private ArrayList<Precondition> preconditions;
    private EventCallTime calltime;
    private EventCall callback;
    
    public CommonEvent()
    {
        calltime = EventCallTime.ParallelProcess;
        callback = EventCall.getEmptyEventCall();
        
        preconditions = new ArrayList<Precondition>();
    }
    
    public void addPrecondition(Precondition precondition)
    {
        preconditions.add(precondition);
    }
    
    public Precondition getPrecondition(int i)
    {
        return preconditions.get(i);
    }
    
    public int preconditions()
    {
        return preconditions.size();
    }

    public EventCall getCallback()
    {
        return callback;
    }
    
    public void setCallback(EventCall callback)
    {
        this.callback = callback;
    }

    public EventCallTime getCalltime()
    {
        return calltime;
    }

    public void setCalltime(EventCallTime calltime)
    {
        this.calltime = calltime;
    }
    
}

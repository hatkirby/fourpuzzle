/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;

/**
 * ChangeFrequencyMoveEvent changes the walk frequency of an event; in other
 * words, it changes how how often this event will initiate movement. The event
 * must be a LayerEvent and the frequency must be larger than or equal to 1.
 * 
 * @author hatkirby
 */
public class ChangeFrequencyMoveEvent implements MoveEvent {

    private int freq;
    public ChangeFrequencyMoveEvent(int freq)
    {
        this.freq = freq;
    }

    public void doAction(Event ev)
    {
        if (!(ev instanceof LayerEvent))
        {
            throw new IllegalArgumentException("");
        }
        
        ((LayerEvent) ev).setFrequency(freq);
    }

}

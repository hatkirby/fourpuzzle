/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author hatkirby
 */
public class MidiParser {
    
    private Sequence seq;
    private Track currentTrack;
    public MidiParser(Sequence seq)
    {
        this.seq = seq;
    }
    
    public Sequence parse() throws InvalidMidiDataException
    {
        Sequence temp = temp = new Sequence(seq.getDivisionType(), seq.getResolution());
        
        for (Track t : seq.getTracks())
        {
            parseTrack(t, temp.createTrack());
        }
        
        return temp;
    }
    
    private void parseTrack(Track t, Track nt)
    {
        currentTrack = nt;
        
        for (int i = 0; i < t.size(); i++)
        {
            MidiEvent mi = t.get(i);
            
            parseEvent(mi.getMessage(), mi.getTick());
        }
    }
    
    private void parseEvent(MidiMessage mm, long tick)
    {
        if (mm instanceof ShortMessage)
        {
            parseShortMessage((ShortMessage) mm,tick);
        } else {
            if (foundFirstReal)
            {
                currentTrack.add(new MidiEvent(mm, tick-firstReal));
            } else {
                currentTrack.add(new MidiEvent(mm, 0));
            }
        }
    }
    
    private long firstReal = 0;
    private boolean foundFirstReal = false;
    private void parseShortMessage(ShortMessage mm, long tick)
    {
        if ((mm.getCommand() == ShortMessage.NOTE_ON) || (mm.getCommand() == ShortMessage.NOTE_OFF))
        {
            if (!foundFirstReal)
            {
                if (mm.getData2() != 0)
                {
                    foundFirstReal = true;
                    firstReal = tick;
                } else {
                    return;
                }
            }   
        }
        
        if (foundFirstReal)
        {
            currentTrack.add(new MidiEvent(mm, tick-firstReal));
        } else {
            currentTrack.add(new MidiEvent(mm, 0));
        }
    }

}

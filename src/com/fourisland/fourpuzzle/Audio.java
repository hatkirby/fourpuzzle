/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

/**
 *
 * @author hatkirby
 */
public class Audio {

    private static Sequencer seq;
    
    public static void init()
    {
        try {
            seq = MidiSystem.getSequencer();
            seq.open();

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    if (seq.isRunning()) {
                        seq.stop();
                    }

                    seq.close();
                }
            }));
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void playMusic(String file)
    {
        playMusic(file, true, 1F);
    }
    
    public static void playMusic(String file, boolean loop)
    {
        playMusic(file, loop, 1F);
    }
    
    public static void playMusic(String file, boolean loop, float speed)
    {
        try {
            seq.setSequence(ObjectLoader.getMusic(file));

            if (loop) {
                seq.setLoopCount(seq.LOOP_CONTINUOUSLY);
            } else {
                seq.setLoopCount(0);
            }

            seq.setTempoFactor(speed);

            seq.start();
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void stopMusic()
    {
        if (seq != null)
        {
            seq.stop();
        }
    }
    
}


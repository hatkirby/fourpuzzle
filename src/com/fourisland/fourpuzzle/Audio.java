/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.Clip;

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
            /* TODO Because of the frequent MIDI unavailability, when
             * a MIDI sequencer is unavailable, instead of breaking down,
             * the application should display a message that something
             * went wrong with the sound and that they should attempt
             * the game again.
             */
            
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
        Sequence s = ObjectLoader.getMusic(file);
        
        if ((seq.getSequence() != null) && (seq.getSequence().equals(s)))
        {
            return;
        }
        
        try {
            seq.setSequence(s);
            seq.setTickPosition(0);

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
            
            try {
                seq.setSequence((Sequence) null);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static Executor soundExecutor = Executors.newCachedThreadPool();
    public static void playSound(String file)
    {
        final Clip temp = ObjectLoader.getSound(file);
        temp.start();

        soundExecutor.execute(new Runnable() {
            public void run() {
                temp.drain();
                temp.stop();
            }
        });
    }
    
}


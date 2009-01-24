/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 *
 * @author hatkirby
 */
public class Audio {

    private static Sequencer seq;
    
    public static void init() throws Exception
    {
        seq = MidiSystem.getSequencer();
        seq.open();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (seq.isRunning())
                {
                    seq.stop();
                }

                seq.close();
            }
        }));
    }
    
    public static void playMusic(String file) throws Exception
    {
        playMusic(file, true, 1F);
    }
    
    public static void playMusic(String file, boolean loop) throws Exception
    {
        playMusic(file, loop, 1F);
    }
    
    public static void playMusic(String file, boolean loop, float speed) throws Exception
    {
        seq.setSequence(ObjectLoader.getMusic(file));

        if (loop)
        {
            seq.setLoopCount(seq.LOOP_CONTINUOUSLY);
        } else {
            seq.setLoopCount(0);
        }
        
        seq.setTempoFactor(speed);
        
        seq.start();
    }
    
    public static void stopMusic() throws Exception
    {
        if (seq == null)
        {
            init();
        }
        
        seq.stop();
    }
    
}


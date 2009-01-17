/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class MoveEventThread implements Runnable {
    
    public static volatile CountDownLatch moveEventWait = new CountDownLatch(0);
    public static volatile int countMoveEventThreads = 0;
    
    Event ev;
    MoveEvent[] actions;
    
    public MoveEventThread(Event ev, MoveEvent[] actions)
    {
        this.ev = ev;
        this.actions = actions;
    }

    public void run() {
        MoveEventThread.countMoveEventThreads++;
        moveEventWait = new CountDownLatch(countMoveEventThreads);
        
        int i=0;
        for (i=0;i<actions.length;i++)
        {
            try {
                actions[i].doAction(ev);
            } catch (Exception ex) {
                Logger.getLogger(MoveEventThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        MoveEventThread.countMoveEventThreads--;
        moveEventWait.countDown();
    }

}

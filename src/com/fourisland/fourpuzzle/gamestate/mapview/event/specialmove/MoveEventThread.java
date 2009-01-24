/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class MoveEventThread implements Runnable {
    
    public static volatile CountDownLatch moveEventWait = new CountDownLatch(0);
    public static volatile int countMoveEventThreads = 0;
    public static volatile List<Event> events = new Vector<Event>();
    
    Event ev;
    MoveEvent[] actions;
    
    public MoveEventThread(Event ev, MoveEvent[] actions)
    {
        this.ev = ev;
        this.actions = actions;
    }

    public void run()
    {
        events.add(ev);
        
        MoveEventThread.countMoveEventThreads++;
        moveEventWait = new CountDownLatch(countMoveEventThreads);
        
        for (MoveEvent action : actions)
        {
            try {
                action.doAction(ev);
            } catch (Exception ex) {
                Logger.getLogger(MoveEventThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        events.remove(ev);
        
        MoveEventThread.countMoveEventThreads--;
        moveEventWait.countDown();
    }
    
    public static boolean isHeroMoving()
    {
        return (events.contains(Game.getHeroEvent()));
    }
    
    public static boolean isOtherMoving(LayerEvent event)
    {
        return (events.contains(event));
    }

}
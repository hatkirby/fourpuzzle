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
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class MoveEventThread implements Runnable {
        
    private static volatile List<Event> events = new Vector<Event>();
    private static volatile Semaphore moveEventWait = new Semaphore(100);
    
    private Event ev;
    private MoveEvent[] actions;
    
    public MoveEventThread(Event ev, MoveEvent[] actions)
    {
        this.ev = ev;
        this.actions = actions;
    }

    public void run()
    {
        try {
            moveEventWait.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(MoveEventThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (ev.isMoving())
        {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(MoveEventThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        events.add(ev);
        
        for (MoveEvent action : actions)
        {
            action.doAction(ev);
        }

        events.remove(ev);
        moveEventWait.release();
    }
    
    /* TODO Rename the two following methods (isHeroMoving and isOtherMoving)
     * to isHeroActive and isOtherActive respectively.
     */
    
    public static boolean isHeroMoving()
    {
        return (events.contains(Game.getHeroEvent()));
    }
    
    public static boolean isOtherMoving(LayerEvent event)
    {
        return (events.contains(event));
    }
    
    public static void moveAll()
    {
        try {
            moveEventWait.acquire(100);
            moveEventWait.release(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(MoveEventThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

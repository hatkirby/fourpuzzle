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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * @author hatkirby
 */
public class MoveEventThread implements Runnable {
    
    private static Executor moveEventExecutor = Executors.newCachedThreadPool();

    private static volatile List<Event> events = new Vector<Event>();
    private static volatile Semaphore moveEventWait = new Semaphore(100);
    
    private Event ev;
    private MoveEvent[] actions;
    
    public MoveEventThread(Event ev, MoveEvent[] actions)
    {
        this.ev = ev;
        this.actions = actions;
    }
    
    public void start()
    {
        moveEventExecutor.execute(this);
    }

    public void run()
    {
        try {
            moveEventWait.acquire();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        while (ev.isMoving())
        {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
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
    
    public static void moveAll() throws InterruptedException
    {
        moveEventWait.acquire(100);
        moveEventWait.release(100);
    }
    
    public static boolean isHeroActive()
    {
        return (events.contains(Game.getHeroEvent()));
    }
    
    public static boolean isOtherActive(LayerEvent event)
    {
        return (events.contains(event));
    }

}

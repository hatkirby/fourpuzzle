/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

/**
 *
 * @author hatkirby
 */
public class MoveEventThread implements Runnable {
    
    private static ExecutorService moveEventExecutor = Executors.newCachedThreadPool();

    private static volatile List<Event> events = new Vector<Event>();
    private static volatile Semaphore moveEventWait = new Semaphore(100);
    private static volatile List<Future> eventThreads = new ArrayList<Future>();
    
    private Event ev;
    private MoveEvent[] actions;
    
    public MoveEventThread(Event ev, MoveEvent[] actions)
    {
        this.ev = ev;
        this.actions = actions;
    }
    
    public void start()
    {
        for (Future f : eventThreads)
        {
            if (f.isDone())
            {
                eventThreads.remove(f);
            }
        }
        
        eventThreads.add(moveEventExecutor.submit(this));
    }

    public void run()
    {
        try {
            moveEventWait.acquire();
        } catch (InterruptedException ex) {
            return;
        }
        
        while (ev.isMoving())
        {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                return;
            }
        }
        
        events.add(ev);
        
        try
        {
            for (MoveEvent action : actions)
            {
                action.doAction(ev);

                if (Thread.currentThread().isInterrupted())
                {
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException ex)
        {
            /* Swallow the interrupt because execution will drop to the finally
             * and then the method will end anyway */
        } finally {
            events.remove(ev);
            moveEventWait.release();
        }
    }
    
    public static void moveAll() throws InterruptedException
    {
        try
        {
            moveEventWait.acquire(100);
        } catch (InterruptedException ex)
        {
            for (Future f : eventThreads)
            {
                f.cancel(true);
            }
            
            throw ex;
        } finally {
            moveEventWait.release(100);
        }
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 *
 * @author hatkirby
 */
public class EventHandler {
    
    private static ExecutorService eventExecutorService = Executors.newSingleThreadExecutor();
    private static ExecutorService parallelExecutorService = Executors.newCachedThreadPool();
    private static Future eventAction = null;
    
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run()
            {
                eventExecutorService.shutdownNow();
                parallelExecutorService.shutdownNow();
            }
        }));
    }

    public static Future runEvent(EventCall callback)
    {
        eventAction = eventExecutorService.submit(callback);
        return eventAction;
    }
    
    public static Future runParallel(EventCall callback)
    {
        return parallelExecutorService.submit(callback);
    }
    
    public static boolean isRunningEvent()
    {
        if (eventAction == null)
        {
            return false;
        }
        
        return (!eventAction.isDone());
    }
    
}

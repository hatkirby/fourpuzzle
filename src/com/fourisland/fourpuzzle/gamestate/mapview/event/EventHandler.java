/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.AutomaticViewpoint;
import com.fourisland.fourpuzzle.util.ResourceNotFoundException;
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
        eventAction = eventExecutorService.submit(eventThread(callback));
        return eventAction;
    }
    
    public static Future runParallel(EventCall callback)
    {
        return parallelExecutorService.submit(eventThread(callback));
    }
    
    private static Runnable eventThread(final EventCall callback)
    {
        return new Runnable() {
            public void run()
            {
                try
                {
                    callback.run();
                } catch (InterruptedException ex) {
                    /* Swallow the interrupt, as the interruption probably
                     * indicates that the event should be cancelled
                     * 
                     * Also reset the viewpoint in case the viewpoint was
                     * fixed during the thread */
                    
                    new SpecialEvent().ResetViewpoint();
                } catch (ResourceNotFoundException ex)
                {
                    PuzzleApplication.reportError(ex);
                }
            }
        };
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

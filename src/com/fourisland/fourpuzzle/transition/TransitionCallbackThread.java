/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import com.fourisland.fourpuzzle.Display;

/**
 *
 * @author hatkirby
 */
public class TransitionCallbackThread implements Runnable {
    
    private Runnable callback;
    public TransitionCallbackThread(Runnable callback)
    {
        this.callback = callback;
    }

    public void run()
    {
        while (Display.getTransition().isRunning())
        {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        
        //Display.setEnabled(false);

        callback.run();
    }

}

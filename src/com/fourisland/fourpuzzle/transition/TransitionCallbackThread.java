/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import com.fourisland.fourpuzzle.Display;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                Logger.getLogger(TransitionCallbackThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Display.setEnabled(false);

        callback.run();
    }

}

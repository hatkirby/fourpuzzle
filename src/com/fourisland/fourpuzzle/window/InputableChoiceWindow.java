/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.KeyInput;
import java.util.List;
import com.fourisland.fourpuzzle.util.Inputable;
import com.fourisland.fourpuzzle.util.PauseTimer;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class InputableChoiceWindow extends ChoiceWindow implements Inputable {
    
    Boolean hasInput = false;
    PauseTimer pt = new PauseTimer(0);
    
    public InputableChoiceWindow(List<String> choices, boolean center, ChoiceWindowLocation cwl)
    {
        super(choices, center, cwl);
    }

    public void processInput(KeyInput key)
    {
        if (key.getKey() == KeyEvent.VK_UP)
        {
            if (pt.isElapsed())
            {
                moveUp();
                pt.setTimer(1);
            }
        } else if (key.getKey() == KeyEvent.VK_DOWN)
        {
            if (pt.isElapsed())
            {
                moveDown();
                pt.setTimer(1);
            }
        } else if (key.isActionDown())
        {
            synchronized (hasInput)
            {
                hasInput = true;
            }
        }
    }
    
    public boolean hasInput()
    {
        synchronized (hasInput)
        {
            return hasInput;
        }
    }

}

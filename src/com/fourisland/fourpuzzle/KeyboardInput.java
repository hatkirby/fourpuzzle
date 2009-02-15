/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.util.Inputable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author hatkirby
 */
public class KeyboardInput {
    
    private static CopyOnWriteArrayList<Inputable> inputables = new CopyOnWriteArrayList<Inputable>();
    private static KeyInput key = new KeyInput();
    
    public static synchronized void registerInputable(Inputable inputable)
    {
        inputables.add(inputable);
    }
    
    public static synchronized void unregisterInputable(Inputable inputable)
    {
        inputables.remove(inputable);
    }
    
    public static void processInput()
    {
        Game.getGameState().processInput(key);
        
        for (Inputable inputable : inputables)
        {
            inputable.processInput(key);
        }
    }
    
    static synchronized KeyInput getKey()
    {
        return key;
    }

}
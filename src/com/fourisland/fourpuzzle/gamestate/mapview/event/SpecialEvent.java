/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEventThread;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class SpecialEvent {
    
    /* TODO Create a manager for SpecialEvent action threads that
     * controls when they are executed and allows MapViewGameState
     * to poll it to see if it is currently managing any action
     * threads. If it is, MapViewGameState should be able to prevent
     * certain actions from occuring (unless the action thread is
     * ParallelProcess) such as keyboard input.
     */

    /**
     * Display a message on the screen.
     * 
     * Usually used for dialogue. If SetFace() is
     * 
     * used prior to this, the face set is displayed
     * on the left side.
     * 
     * Display of the message area can be modified using
     * MessageDisplaySettings().
     * 
     * This function also automatically splits your
     * message up into blocks that will fit on
     * the screen (breaks at spaces). If there are too
     * many words, they will be held and displayed in
     * the message area after the prior message has
     * been read.
     * 
     * @param message The message to display
     */
    public void DisplayMessage(String message)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Sets the face used when displaying a message
     * 
     * See DisplayMessage() for more info
     * 
     * @param faceSet The name of the FaceSet to use
     * @param face The number of the face in the FaceSet
     *             to use. The faces are numbered
     *             horizontally.
     */
    public void SetFace(String faceSet, int face)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * Clears the face used when displaying a message
     * 
     * See DisplayMessage() for more info
     */
    public void EraseFace()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * Sets a Switch to a [boolean] value
     * 
     * @param switchID The Switch to set
     * @param value The value to set the Switch to
     */
    public void SetSwitch(String switchID, boolean value)
    {
        Game.getSaveFile().getSwitches().put(switchID, value);
    }
    
    /**
     * Toggles a Switch's [boolean] value
     * 
     * @param switchID The Switch to toggle
     */
    public void ToggleSwitch(String switchID)
    {
        if (Game.getSaveFile().getSwitches().containsKey(switchID))
        {
            Game.getSaveFile().getSwitches().put(switchID, !Game.getSaveFile().getSwitches().get(switchID));
        } else {
            Game.getSaveFile().getSwitches().put(switchID, true);
        }
    }
    
    /**
     * Performs actions on the hero
     * 
     * @param actions An array of MoveEvents to perform on the hero
     */
    public void MoveEvent(MoveEvent[] actions)
    {
        new Thread(new MoveEventThread(Game.getHeroEvent(), actions)).start();
    }
    
    /**
     * Performs actions on an event
     * 
     * @param actions An array of MoveEvents to perform on the event
     * @param label The label of the event to act upon
     */
    public void MoveEvent(MoveEvent[] actions, String label)
    {
        new Thread(new MoveEventThread(((MapViewGameState) Game.getGameState()).getCurrentMap().getEvent(label), actions)).start();
    }
    
    /**
     * Waits until all previously called MoveEvent()s have finished
     */
    public void MoveEventWait()
    {
        MoveEventThread.moveAll();
    }
    
    /**
     * Triggers the Game Over sequence
     */
    public void GameOver()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * Returns the player to the Title Screen
     */
    public void TitleScreen()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * Moves the player to a different map
     * 
     * @param map The name of the map to move to
     * @param x The X position on the map to move to
     * @param y The Y position on the map to move to
     */
    public void Teleport(String map, int x, int y)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * Waits for a specified interval
     * 
     * @param wait The time to wait in milliseconds
     */
    public void Wait(int wait)
    {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException ex) {
            Logger.getLogger(SpecialEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

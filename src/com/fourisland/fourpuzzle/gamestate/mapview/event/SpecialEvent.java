/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEventThread;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.AutomaticViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.FixedViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.MovingViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.Viewpoint;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class SpecialEvent {
    
    protected static MapViewGameState mapView = null;
    public static void setMapView(MapViewGameState mapView)
    {
        SpecialEvent.mapView = mapView;
    }

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
    
    /**
     * Fixes the viewpoint in the current position
     */
    public void FixViewpoint()
    {
        Viewpoint viewpoint = mapView.getViewpoint();
        mapView.setViewpoint(new FixedViewpoint(viewpoint.getX(),viewpoint.getY()));
    }
    
    /**
     * Pans the viewpoint the the specified tile location
     * 
     * @param x The x coordinate of the tile in the top-left corner to pan to
     * @param y The y coordinate of the tile in the top-left corner to pan to
     * @param length How long (in milliseconds) it will take to pan
     * @param block If true, the game will wait for the pan to complete
     *              before executing any more commands
     */
    public void PanViewpoint(final int x, final int y, int length, final boolean block)
    {
        Viewpoint viewpoint = mapView.getViewpoint();
        final CountDownLatch blocker;
        
        if (block)
        {
            blocker = new CountDownLatch(1);
        } else {
            blocker = null;
        }
            
        mapView.setViewpoint(new MovingViewpoint(viewpoint.getX(), viewpoint.getY(), x*16, y*16, new Runnable() {
            public void run()
            {
                mapView.setViewpoint(new FixedViewpoint(x*16,y*16));
                
                if (block)
                {
                    blocker.countDown();
                }
            }
        }, length));
        
        if (block)
        {
            try {
                blocker.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(SpecialEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Resets the viewpoint from whatever state is is currently in
     */
    public void ResetViewpoint()
    {
        Viewpoint viewpoint = mapView.getViewpoint();
        AutomaticViewpoint dest = new AutomaticViewpoint(mapView.getCurrentMap());
        
        mapView.setViewpoint(new MovingViewpoint(viewpoint.getX(), viewpoint.getY(), dest.getX(), dest.getY(), new Runnable() {
            public void run() {
                mapView.setViewpoint(new AutomaticViewpoint(mapView.getCurrentMap()));
            }
        }));
    }
    
}

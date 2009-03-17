/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.database.Transitions;
import com.fourisland.fourpuzzle.gamestate.GameOverGameState;
import com.fourisland.fourpuzzle.gamestate.TitleScreenGameState;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEventThread;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.AutomaticViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.FixedViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.MovingViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.ShakingViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.ShakingViewpoint.ShakeSpeed;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.Viewpoint;
import com.fourisland.fourpuzzle.transition.InTransition;
import com.fourisland.fourpuzzle.transition.OutTransition;
import com.fourisland.fourpuzzle.window.MessageWindow;
import java.util.concurrent.CountDownLatch;

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
    
    private String faceSet = "";
    private int face = 0;

    /**
     * Display a message on the screen.
     * 
     * <p>Usually used for dialogue. If SetFace() is used prior to this, the face
     * set is displayed on the left side.</p>
     * 
     * <p>Display of the message area can be modified using
     * MessageDisplaySettings().</p>
     * 
     * <p>This function also automatically splits your message up into blocks that
     * will fit on the screen (breaks at spaces). If there are too many words,
     * they will be held and displayed in the message area after the prior
     * message has been read.</p>
     * 
     * <p>Message Escapes can be used to preform specific actions during text
     * display. For instance, <code>\C[number]</code> changes the text color to
     * the color on the System Graphic represented by "number".</p>
     * 
     * @param message The message to display
     * @throws InterruptedException 
     */
    public void DisplayMessage(String message) throws InterruptedException
    {
        MessageWindow mw;
        
        if (faceSet.equals(""))
        {
            mw = new MessageWindow(message);
        } else {
            mw = new MessageWindow(message, faceSet, face);
        }
        
        Display.registerRenderable(mw);
        KeyboardInput.registerInputable(mw);

        mw.waitForCompletion();
        
        Display.unregisterRenderable(mw);
        KeyboardInput.unregisterInputable(mw);
    }

    /**
     * Sets the face used when displaying a message
     * 
     * <p>See DisplayMessage() for more info</p>
     * 
     * @param faceSet The name of the FaceSet to use
     * @param face The number of the face in the FaceSet to use. The faces are
     * numbered horizontally as in the top-left is 0, the one to the right is 1
     * and so on.
     */
    public void SetFace(String faceSet, int face)
    {
        this.faceSet = faceSet;
        this.face = face;
    }
    
    /**
     * Clears the face used when displaying a message
     * 
     * <p>See DisplayMessage() for more info</p>
     */
    public void EraseFace()
    {
        faceSet = "";
        face = 0;
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
        new MoveEventThread(Game.getHeroEvent(), actions).start();
    }
    
    /**
     * Performs actions on an event
     * 
     * @param actions An array of MoveEvents to perform on the event
     * @param label The label of the event to act upon
     */
    public void MoveEvent(MoveEvent[] actions, String label)
    {
        new MoveEventThread(mapView.getCurrentMap().getEvent(label), actions).start();
    }
    
    /**
     * Waits until all previously called MoveEvent()s have finished
     * 
     * @throws InterruptedException
     */
    public void MoveEventWait() throws InterruptedException
    {
        MoveEventThread.moveAll();
    }
    
    /**
     * Triggers the Game Over sequence
     * 
     * @throws InterruptedException
     */
    public void GameOver() throws InterruptedException
    {
        Audio.stopMusic();
        
        Display.transition(Database.getTransition(Transitions.Generic), new GameOverGameState(), false);
    }
    
    /**
     * Returns the player to the Title Screen
     * 
     * @throws InterruptedException
     */
    public void TitleScreen() throws InterruptedException
    {
        Audio.stopMusic();

        Display.transition(Database.getTransition(Transitions.Generic), new TitleScreenGameState(), false);
    }
    
    private boolean startedTransition = false;
    
    /**
     * Displays a transition from the current map to emptiness
     * 
     * <p>If this method is executed before Teleport(), Teleport() will not use
     * the database-default out transition and instead immeditatly jump to the
     * new map. It will also not use the database-default in transition which
     * requires you to also execute EndTransition().</p>
     * 
     * @param trans The transition to use
     * @throws InterruptedException 
     */
    public void StartTransition(OutTransition trans) throws InterruptedException
    {
        if (!startedTransition)
        {
            startedTransition = true;

            Display.transition(trans);
        }
    }
    
    /**
     * Moves the player to a different map
     * 
     * <p>If StartTransition() is executed prior to this method, then this will
     * not preform the database-default transitions, which requires that
     * EndTransition() is executed after this method.</p>
     * 
     * @param map The name of the map to move to
     * @param x The X position on the map to move to
     * @param y The Y position on the map to move to
     * @throws InterruptedException 
     */
    public void Teleport(String map, int x, int y) throws InterruptedException
    {
        if (!startedTransition)
        {
            Display.transition(Database.getTransition(Transitions.Map), new MapViewGameState(map, x, y), false);
        } else {
            Game.setGameState(new MapViewGameState(map, x, y));
        }
    }
    
    /**
     * Displays a transition from the emptiness to the new map
     * 
     * <p>This method is only required if you called StartTransition() before
     * Teleport(), in which case it will display the transition. Otherwise,
     * this action will do nothing.</p>
     * 
     * @param trans
     * @throws InterruptedException 
     */
    public void EndTransition(InTransition trans) throws InterruptedException
    {
        if (startedTransition)
        {
            Display.transition(trans);
            
            startedTransition = false;
        }
    }
    
    /**
     * Waits for a specified interval
     * 
     * @param wait The time to wait in milliseconds
     * @throws InterruptedException
     */
    public void Wait(int wait) throws InterruptedException
    {
        Thread.sleep(wait);
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
     * @param block If true, the game will wait for the pan to complete before
     * executing any more commands
     * @throws InterruptedException
     */
    public void PanViewpoint(final int x, final int y, int length, final boolean block) throws InterruptedException
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
                if (block)
                {
                    blocker.countDown();
                } else {
                    mapView.setViewpoint(new FixedViewpoint(x*16,y*16));
                }
            }
        }, length));
        
        if (block)
        {
            try
            {
                blocker.await();
            } catch (InterruptedException ex)
            {
                throw ex;
            } finally {
                mapView.setViewpoint(new FixedViewpoint(x*16,y*16));
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
    
    /**
     * Starts playing a Music file
     * 
     * @param filename The name of the Music file to play
     * @param loop Whether or not you want this Music to loop
     * @param speed The Tempo Factor. If this is 1, the Music will play at
     * normal speed. If this is 2, the Music will play at twice the normal
     * speed, and so on.
     */
    public void PlayMusic(String filename, boolean loop, float speed)
    {
        Audio.playMusic(filename, loop, speed);
    }
    
    /**
     * Stops playing the currently playing Music file
     */
    public void StopMusic()
    {
        Audio.stopMusic();
    }
    
    /**
     * Ends the currently executing event thread
     * 
     * @throws InterruptedException
     */
    public void StopThread() throws InterruptedException
    {
        throw new InterruptedException();
    }
    
    /**
     * Shake the screen like an earthquake
     * 
     * @param speed How fast the screen should shake
     * @param length The amount of time (in milliseconds) the shaking should
     * last
     * @param block If true, the game will wait for the shaking to complete
     * before executing any more commands
     * @throws java.lang.InterruptedException
     */
    public void ShakeScreen(ShakeSpeed speed, int length, final boolean block) throws InterruptedException
    {
        Viewpoint viewpoint = mapView.getViewpoint();
        final CountDownLatch blocker;
        
        if (block)
        {
            blocker = new CountDownLatch(1);
        } else {
            blocker = null;
        }
            
        mapView.setViewpoint(new ShakingViewpoint(viewpoint.getX(), viewpoint.getY(), speed, length, new Runnable() {
            public void run()
            {
                if (block)
                {
                    blocker.countDown();
                } else {
                    ResetViewpoint();
                }
            }
        }));
        
        if (block)
        {
            try
            {
                blocker.await();
            } catch (InterruptedException ex)
            {
                throw ex;
            } finally {
                ResetViewpoint();
            }
        }
    }
    
}

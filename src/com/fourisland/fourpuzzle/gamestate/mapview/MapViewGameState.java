/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.gamestate.*;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.database.Transitions;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventCallTime;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventHandler;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventList;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.SpecialEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEventThread;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.AutomaticViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.Viewpoint;
import com.fourisland.fourpuzzle.gamestate.menu.MenuGameState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class MapViewGameState implements GameState {
    
    public boolean debugWalkthrough = false;
    Map currentMap;
    Viewpoint currentViewpoint = null;
    
    public MapViewGameState(String map, int x, int y)
    {
        // Load the specified map into memory
        setCurrentMap(map);
        
        // Place the Hero at the specified location
        Game.getSaveFile().getHero().setLocation(x, y);
        
        // Create a new viewpoint for the map
        currentViewpoint = new AutomaticViewpoint(currentMap);
        
        // Tell SpecialEvent about the new map so it can access it
        SpecialEvent.setMapView(this);
    }
        
    public void initalize()
    {
        /* Depending on the specified music type, either play music, stop the
         * music or let the already playing music continue */
        switch (currentMap.getMusicType())
        {
            case NoMusic: Audio.stopMusic(); break;
            case NoChange: break;
            case Specified: Audio.playMusic(currentMap.getMusic()); break;
        }
    }
    
    public void deinitalize()
    {
        // Do nothing, yet
    }

    public void processInput(KeyInput key)
    {
        // Store the hero event in a local variable as it is used often
        HeroEvent hero = Game.getSaveFile().getHero();

        /* If debug mode is enabled and the control key is held down, set the
         * walkthrough flag so the Hero can walk through stuff */
        if (key.isCtrlDown() && !debugWalkthrough)
        {
            if (PuzzleApplication.getInstance().getContext().getResourceMap().getBoolean("debugMode"))
            {
                debugWalkthrough = true;
            }
        } else {
            debugWalkthrough = false;
        }

        /* If the hero is not moving or the center of a MoveEvent action and no
         * blocking special events are running, check the user input */
        if (!hero.isMoving() && !MoveEventThread.isHeroActive() && !EventHandler.isRunningEvent())
        {
            Direction toMove = null;
            Boolean letsMove = false;

            // Translate the key input into the appropriate direction
            switch (key.getKey())
            {
                case KeyEvent.VK_UP:
                    toMove = Direction.North;
                    letsMove = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    toMove = Direction.East;
                    letsMove = true;
                    break;
                case KeyEvent.VK_DOWN:
                    toMove = Direction.South;
                    letsMove = true;
                    break;
                case KeyEvent.VK_LEFT:
                    toMove = Direction.West;
                    letsMove = true;
                    break;
            }

            // If a movement key was indeed pressed, process it
            if (letsMove)
            {
                // Try to move the hero in the specified direction
                if (!hero.startMoving(toMove))
                {
                    /* If the hero is blocked in that direction, check to see
                     * if a middle-layer OnHeroTouch event is the blocker, if
                     * so, execute it */
                    for (LayerEvent ev : currentMap.getEvents())
                    {
                        if (ev.getCalltime() == EventCallTime.OnHeroTouch)
                        {
                            if (ev.getLayer() == Layer.Middle)
                            {
                                if (hero.getDirection().to(hero.getLocation()).equals(ev.getLocation()))
                                {
                                    ev.getCallback().activate(ev.getCalltime());
                                }
                            }
                        }
                    }
                }
            }

            /* If the player presses the action key, check if either of the two
             * PushKey conditions are available */
            if (key.isActionDown())
            {
                for (LayerEvent ev : currentMap.getEvents())
                {
                    if (ev.getCalltime() == EventCallTime.PushKey)
                    {
                        if (ev.getLayer() == Layer.Middle)
                        {
                            /* If the event is middle-layered and the hero is
                             * facing it, execute it */
                            if (hero.getDirection().to(hero.getLocation()).equals(ev.getLocation()))
                            {
                                ev.setDirection(hero.getDirection().opposite());
                                ev.getCallback().activate(ev.getCalltime());
                            }
                        } else {
                            /* If the event is not middle-layered and the hero
                             * is on it, execute it */
                            if (ev.getLocation().equals(hero.getLocation()))
                            {
                                ev.getCallback().activate(ev.getCalltime());
                            }
                        }
                    }
                }
            }

            // If the player presses the escape key, open the menu
            if (key.getKey() == KeyEvent.VK_ESCAPE)
            {
                try {
                    Display.transition(Database.getTransition(Transitions.Generic), new MenuGameState(this), true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MapViewGameState.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (EventHandler.isRunningEvent())
        {
            /* If debug mode is enabled and F11 is pressed, cancel any running
             * events */
            if ((key.getKey() == KeyEvent.VK_F11) && (PuzzleApplication.getInstance().getContext().getResourceMap().getBoolean("debugMode")))
            {
                for (LayerEvent ev : currentMap.getEvents())
                {
                    ev.getCallback().cancel();
                }
            }
        }
    }
    
    public void doGameCycle()
    {
        // Store the hero event in a local variable as it is used often
        HeroEvent hero = Game.getSaveFile().getHero();
        if (hero.isMoving())
        {
            // If the player is in the process of moving, continue it
            hero.processMoving();
            
            /* If the player has just finished moving, check for a non
             * middle-layered OnHeroTouch on the Hero and execute it */
            if (!hero.isMoving())
            {
                for (LayerEvent ev : currentMap.getEvents())
                {
                    if (ev.getCalltime() == EventCallTime.OnHeroTouch)
                    {
                        if (ev.getLayer() != Layer.Middle)
                        {
                            if (hero.getLocation().equals(ev.getLocation()))
                            {
                                ev.getCallback().activate(ev.getCalltime());
                            }
                        }
                    }
                }
            }
        }

        for (LayerEvent ev : currentMap.getEvents())
        {
            if (!ev.isMoving())
            {
                /* If one of the map's layer events aren't moving or being
                 * processed by a MoveEvent action and no blocking special
                 * events are running, start it moving in the direction provided
                 * by its MovementType */
                if (!MoveEventThread.isOtherActive(ev))
                {
                    if (!EventHandler.isRunningEvent())
                    {
                        ev.startMoving();
                    }
                }
            } else {
                // If the event IS moving, process the movement
                ev.processMoving();
            }
            
            if (ev.getCalltime() == EventCallTime.ParallelProcess)
            {
                // If the event is a ParallelProcess, execute it
                ev.getCallback().activate(ev.getCalltime());
            }
        }
    }

    public void render(Graphics2D g)
    {
        // Ask the current viewpoint where to render from
        int x = currentViewpoint.getX();
        int y = currentViewpoint.getY();
        
        /* Fill the background with black so specialized viewpoints that go off
         * the screen such as ShakingViewpoint render properly */
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        
        // Render the lower layer of the map
        g.drawImage(currentMap.renderLower(), 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);

        // Render each lower and middle layered event onto a seperate canvas
        BufferedImage eventLayer = Display.createCanvas(currentMap.getSize().width*16, currentMap.getSize().height*16);
        Graphics2D g2 = eventLayer.createGraphics();
        EventList events = currentMap.getEvents();
        
        for (LayerEvent event : events)
        {
            if (event.getLayer() != Layer.Above)
            {
                event.render(g2);
            }
        }
        
        // Render the hero event onto the event canvas
        Game.getHeroEvent().render(g2);

        // Render each above layered event onto the event canvas
        for (LayerEvent event : events)
        {
            if (event.getLayer() == Layer.Above)
            {
                event.render(g2);
            }
        }

        // Render the event canvas
        g.drawImage(eventLayer, 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);
        
        // Render the upper layer of the map
        g.drawImage(currentMap.renderUpper(), 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);
    }
    
    public void setCurrentMap(String mapName)
    {
        // Tell the save data what map is currently loaded
        Game.getSaveFile().setCurrentMap(mapName);
        
        // Load the specified map from the database
        currentMap = Database.getMap(mapName);
    }
    
    public Map getCurrentMap()
    {
        return currentMap;
    }
    
    public Viewpoint getViewpoint()
    {
        return currentViewpoint;
    }
    
    public void setViewpoint(Viewpoint viewpoint)
    {
        currentViewpoint = viewpoint;
    }

}

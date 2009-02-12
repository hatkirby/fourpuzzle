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
import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventCallTime;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventHandler;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventList;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.SpecialEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEventThread;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.AutomaticViewpoint;
import com.fourisland.fourpuzzle.gamestate.mapview.viewpoint.Viewpoint;
import com.fourisland.fourpuzzle.util.Functions;
import com.fourisland.fourpuzzle.util.ResourceNotFoundException;
import com.fourisland.fourpuzzle.window.MessageWindow;
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
    boolean processInput = true;
    Map currentMap;
    Viewpoint currentViewpoint = null;
    
    public MapViewGameState(String map, int x, int y)
    {
        setCurrentMap(map);
        Game.getSaveFile().getHero().setLocation(x, y);
        currentViewpoint = new AutomaticViewpoint(currentMap);
        SpecialEvent.setMapView(this);
    }
        
    public void initalize()
    {
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

    public void processInput()
    {
        if (processInput)
        {
            HeroEvent hero = Game.getSaveFile().getHero();

            if (Game.getKey().isControlDown() && !debugWalkthrough)
            {
                if (PuzzleApplication.INSTANCE.getContext().getResourceMap().getBoolean("debugMode"))
                {
                    debugWalkthrough = true;
                }
            } else {
                debugWalkthrough = false;
            }

            if (!hero.isMoving() && !MoveEventThread.isHeroActive() && !EventHandler.isRunningEvent())
            {
                Direction toMove = null;
                Boolean letsMove = false;

                switch (Game.getKey().getKeyCode())
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

                if (letsMove)
                {
                    if (!hero.startMoving(toMove))
                    {
                        for (LayerEvent ev : currentMap.getEvents())
                        {
                            if (ev.getCalltime() == EventCallTime.OnHeroTouch)
                            {
                                if (ev.getLayer() == Layer.Middle)
                                {
                                    if (Functions.isFacing(hero, ev))
                                    {
                                        ev.getCallback().activate(ev.getCalltime());
                                    }
                                }
                            }
                        }
                    }
                }

                if ((Game.getKey().getKeyCode() == KeyEvent.VK_ENTER) || (Game.getKey().getKeyCode() == KeyEvent.VK_SPACE))
                {
                    for (LayerEvent ev : currentMap.getEvents())
                    {
                        if (ev.getCalltime() == EventCallTime.PushKey)
                        {
                            if (ev.getLayer() == Layer.Middle)
                            {
                                if (Functions.isFacing(hero, ev))
                                {
                                    ev.setDirection(hero.getDirection().opposite());
                                    ev.getCallback().activate(ev.getCalltime());
                                }
                            } else {
                                if (ev.getLocation().equals(hero.getLocation()))
                                {
                                    ev.getCallback().activate(ev.getCalltime());
                                }
                            }
                        }
                    }

                    Game.getKey().setKeyCode(KeyEvent.VK_UNDEFINED);
                }
            }
        }
    }
    
    public void doGameCycle()
    {
        HeroEvent hero = Game.getSaveFile().getHero();
        if (hero.isMoving())
        {
            hero.processMoving();
            
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
                if (!MoveEventThread.isOtherActive(ev))
                {
                    if (!EventHandler.isRunningEvent())
                    {
                        ev.startMoving();
                    }
                }
            } else {
                ev.processMoving();
            }
            
            if (ev.getCalltime() == EventCallTime.ParallelProcess)
            {
                ev.getCallback().activate(ev.getCalltime());
            }
        }
    }

    public void render(Graphics2D g)
    {   
        int x = currentViewpoint.getX();
        int y = currentViewpoint.getY();
        
        g.drawImage(currentMap.renderLower(), 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);

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
        
        Game.getHeroEvent().render(g2);

        for (LayerEvent event : events)
        {
            if (event.getLayer() == Layer.Above)
            {
                event.render(g2);
            }
        }

        g.drawImage(eventLayer, 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);
        g.drawImage(currentMap.renderUpper(), 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);
        
        if (mw != null)
        {
            mw.render(g);
        }
    }
    
    public void initCurrentMap(String mapName)
    {
        try {
            Class mapClass = Class.forName(PuzzleApplication.INSTANCE.getGamePackage() + ".gamedata.map." + mapName);
            Object mapObject = mapClass.newInstance();
            Map map = (Map) mapObject;
            map.initalize();
            currentMap = map;
        } catch (InstantiationException ex) {
            Logger.getLogger(MapViewGameState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MapViewGameState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            throw new ResourceNotFoundException("Map", mapName);
        }
    }
    public void setCurrentMap(String mapName)
    {
        Game.getSaveFile().setCurrentMap(mapName);
        initCurrentMap(mapName);
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
    
    volatile MessageWindow mw = null;
    public void displayMessage(String message)
    {
        mw = new MessageWindow(message);
    }

}

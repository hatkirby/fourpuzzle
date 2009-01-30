/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.gamestate.*;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventCallTime;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventHandler;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventList;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.specialmove.MoveEventThread;
import com.fourisland.fourpuzzle.util.Functions;
import com.fourisland.fourpuzzle.util.ResourceNotFoundException;
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
    
    public MapViewGameState(String map, int x, int y)
    {
        //currentMap = ObjectLoader.getMap(map);
        setCurrentMap(map);
        Game.getSaveFile().getHero().setLocation(x, y);
    }
        
    public void initalize()
    {
        //if (!currentMap.getMusic().equals(""))
        {
          //  Audio.playMusic(currentMap.getMusic());
        }
    }
    
    public void deinitalize()
    {
        //if (!currentMap.getMusic().equals(""))
        {
            Audio.stopMusic();
        }
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

            if (!hero.isMoving() && !MoveEventThread.isHeroMoving() && !EventHandler.isRunningEvent())
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
                    hero.startMoving(toMove);
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
                                    ev.setDirection(Functions.oppositeDirection(hero.getDirection()));
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
        }

        for (LayerEvent ev : currentMap.getEvents())
        {
            if (!ev.isMoving())
            {
                if (!MoveEventThread.isOtherMoving(ev))
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
        /* TODO Fix viewpoint scrolling code. Currently, when the Hero moves
         * South or East across the scroll barrier, it warps reality a little,
         * while the other two directions scroll fine.
         */
        
        int x,y;
        HeroEvent hero = Game.getHeroEvent();
        if (hero.getLocation().x > 10)
        {
            if (hero.getLocation().x < (currentMap.getSize().width - 9))
            {
                x = (hero.getLocation().x - 10) * 16;
                x += hero.getMovingX();
            } else {
                x = (currentMap.getSize().width - 20) * 16;
            }
        } else {
            x = 0;
        }
        
        if (hero.getLocation().y > 7)
        {
            if (hero.getLocation().y < (currentMap.getSize().height - 7))
            {
                y = (hero.getLocation().y - 7) * 16;
                y += hero.getMovingY();
            } else {
                y = (currentMap.getSize().height - 15) * 16;
            }
        } else {
            y = 0;
        }
        
        g.drawImage(currentMap.renderLower(), 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);

        BufferedImage eventLayer = new BufferedImage(currentMap.getSize().width*16, currentMap.getSize().height*16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = eventLayer.createGraphics();
        hero.render(g2);

        EventList events = currentMap.getEvents();
        for (LayerEvent event : events)
        {
            event.render(g2);
        }

        g.drawImage(eventLayer, 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);
        g.drawImage(currentMap.renderUpper(), 0, 0, Game.WIDTH, Game.HEIGHT, x, y, x+Game.WIDTH, y+Game.HEIGHT, null);
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

}

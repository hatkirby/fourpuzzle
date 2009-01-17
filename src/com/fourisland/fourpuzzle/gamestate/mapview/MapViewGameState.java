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
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author hatkirby
 */
public class MapViewGameState implements GameState {
    
    boolean debugWalkthrough = false;
    boolean processInput = true;
    Map currentMap;
    
    public MapViewGameState(String map, int x, int y) throws Exception
    {
        //currentMap = ObjectLoader.getMap(map);
        setCurrentMap(map);
        Game.getSaveFile().getHero().setLocation(x, y);
    }
        
    public void initalize() throws Exception
    {
        //if (!currentMap.getMusic().equals(""))
        {
          //  Audio.playMusic(currentMap.getMusic());
        }
    }
    
    public void deinitalize() throws Exception
    {
        //if (!currentMap.getMusic().equals(""))
        {
            Audio.stopMusic();
        }
    }

    public void processInput() throws Exception
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

            if (!hero.isMoving())
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
                    if (debugWalkthrough || (!currentMap.checkForCollision(hero.getLocation().x, hero.getLocation().y, toMove)))
                    {
                        hero.startMoving(toMove);
                    } else {
                        hero.setDirection(toMove);
                    }
                }

                if ((Game.getKey().getKeyCode() == KeyEvent.VK_ENTER) || (Game.getKey().getKeyCode() == KeyEvent.VK_SPACE))
                {
                    int i=0;
                    for (i=0;i<currentMap.getEvents().size();i++)
                    {
                        LayerEvent ev = currentMap.getEvents().get(i);

                        if (ev.getCalltime() == EventCallTime.PushKey)
                        {
                            if ((hero.getDirection() == Direction.North) && (ev.getLocation().x == hero.getLocation().x) && (ev.getLocation().y == (hero.getLocation().y - 1)))
                            {
                                ev.setDirection(Direction.South);
                                ev.getCallback().run();
                            } else if ((hero.getDirection() == Direction.West) && (ev.getLocation().x == (hero.getLocation().x - 1)) && (ev.getLocation().y == hero.getLocation().y))
                            {
                                ev.setDirection(Direction.East);
                                ev.getCallback().run();
                            } else if ((hero.getDirection() == Direction.South) && (ev.getLocation().x == hero.getLocation().x) && (ev.getLocation().y == (hero.getLocation().y + 1)))
                            {
                                ev.setDirection(Direction.North);
                                ev.getCallback().run();
                            } else if ((hero.getDirection() == Direction.East) && (ev.getLocation().x == (hero.getLocation().x + 1)) && (ev.getLocation().y == hero.getLocation().y))
                            {
                                ev.setDirection(Direction.West);
                                ev.getCallback().run();
                            }
                        }
                    }

                    Game.getKey().setKeyCode(KeyEvent.VK_UNDEFINED);
                }
            }
        }
    }
    
    public void doGameCycle() throws Exception
    {
        HeroEvent hero = Game.getSaveFile().getHero();
        if (hero.isMoving())
        {
            hero.processMoving();
        }

        int i=0;
        for (i=0;i<currentMap.getEvents().size();i++)
        {
            if (!currentMap.getEvents().get(i).isMoving())
            {
                currentMap.getEvents().get(i).startMoving(currentMap);
            } else {
                currentMap.getEvents().get(i).processMoving();
            }
        }
    }

    public void render(Graphics2D g) throws Exception
    {
        ChipSet chipSet = ChipSet.getChipSet(currentMap.getChipSet());
        int i,x,y;
        for (i=0;i<currentMap.getMapData().size();i++)
        {
            for (y=0;y<currentMap.getSize().height;y++)
            {
                for (x=0;x<currentMap.getSize().width;x++)
                {
                    int tile = currentMap.getMapData().get(i).get(x+(y*currentMap.getSize().width));
                    if (chipSet.getChipSetData().get(tile).getLayer() != Layer.Above)
                    {
                        g.drawImage(chipSet.getImage(tile), x*16, y*16, null);
                    }
                }
            }
        }
/*
        MapViewer mv = new MapViewer(currentMap, new com.alienfactory.javamappy.viewer.render.J2SE14Renderer(currentMap), Game.WIDTH, Game.HEIGHT);
        mv.setBlockX(Game.getSaveFile().getHero().getLocation().x);
        mv.setBlockY(Game.getSaveFile().getHero().getLocation().y);
        mv.setPixelX((4 - Game.getSaveFile().getHero().getMoveTimer()) * 4);
        mv.draw(g, true);*/
        Game.getSaveFile().getHero().render(g);

        ArrayList<LayerEvent> events = currentMap.getEvents();
        for (i=0;i<events.size();i++)
        {
            events.get(i).render(g);
        }

        for (i=0;i<currentMap.getMapData().size();i++)
        {
            for (y=0;y<currentMap.getSize().height;y++)
            {
                for (x=0;x<currentMap.getSize().width;x++)
                {
                    int tile = currentMap.getMapData().get(i).get(x+(y*currentMap.getSize().width));
                    if (chipSet.getChipSetData().get(tile).getLayer() == Layer.Above)
                    {
                        g.drawImage(chipSet.getImage(tile), x*16, y*16, null);
                    }
                }
            }
        }
    }
    
    public void initCurrentMap(String mapName) throws Exception
    {
        Class mapClass = Class.forName(PuzzleApplication.INSTANCE.getGamePackage() + ".gamedata.map." + mapName);
        Object mapObject = mapClass.newInstance();
        Map map = (Map) mapObject;
        map.initalize();
        currentMap = map;
    }
    public void setCurrentMap(String mapName) throws Exception
    {
        Game.getSaveFile().setCurrentMap(mapName);
        initCurrentMap(mapName);
    }
    public Map getCurrentMap()
    {
        return currentMap;
    }

}

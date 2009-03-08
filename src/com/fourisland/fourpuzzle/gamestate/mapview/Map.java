/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.gamestate.mapview.event.Event;
import com.fourisland.fourpuzzle.gamestate.mapview.event.EventList;
import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author hatkirby
 */
public class Map {
    
    /**
     * Creates a new Map
     * 
     * @param width The width of the Map in tiles
     * @param height The height of the Map in tiles
     * @param chipSet The name of the ChipSet to use
     * @param music The name of the Music file to play in the background
     */
    public Map(int width, int height, String chipSet, String music)
    {
        setSize(new Dimension(width, height));
        setChipSet(chipSet);
        setMusic(music);
    }
    
    /**
     * Creates a new Map
     * 
     * @param width The width of the Map in tiles
     * @param height The height of the Map in tiles
     * @param chipSet The name of the ChipSet to use
     * @param musicType The non-Specified Music mode to use
     * 
     * @throws IllegalArgumentException if MapMusicType.Specified is passed in
     * musicType. This constructor is for MapMusicType.NoMusic or
     * MapMusicType.NoChange. If you wish to specify a Music file, use the other
     * constructor (int, int, String, String)
     */
    public Map(int width, int height, String chipSet, MapMusicType musicType)
    {
        if (musicType == MapMusicType.Specified)
        {
            throw new IllegalArgumentException("MapMusicType.Specified is not a valid value for musicType. If you wish to specify a music type, use the other constructor (int, int, String, String)");
        }
        
        setSize(new Dimension(width, height));
        setChipSet(chipSet);
        setMusicType(musicType);
    }
    
    public Map copy()
    {
        Map temp;
        if (getMusicType() == MapMusicType.Specified)
        {
            temp = new Map(getSize().width, getSize().height, getChipSet(), getMusic());
        } else {
            temp = new Map(getSize().width, getSize().height, getChipSet(), getMusicType());
        }
        
        temp.mapData = new Vector<HashMap<Integer,Integer>>(getMapData());
        temp.events = getEvents().copy(this);
        
        return temp;
    }
    
    private Dimension size;
    public Dimension getSize()
    {
        return size;
    }
    private void setSize(Dimension size)
    {
        if ((size.width < 20) || (size.height < 15))
        {
            this.size = new Dimension(20,15);
        } else {
            this.size = size;
        }
    }
    
    private EventList events = new EventList(this);
    public EventList getEvents()
    {
        return events;
    }
    public LayerEvent getEvent(String event)
    {
        for (LayerEvent ev : events)
        {
            if (ev.getLabel().equals(event))
            {
                return ev;
            }
        }
        
        return null;
    }
    
    public boolean checkForCollision(Event ev, Direction toMove)
    {
        int x = ev.getLocation().x;
        int y = ev.getLocation().y;

        // Check if the event is trying to move over the map boundaries
        if ((toMove == Direction.North) && (y == 0))
        {
            return true;
        } else if ((toMove == Direction.West) && (x == 0))
        {
            return true;
        } else if ((toMove == Direction.South) && (y == (size.height - 1)))
        {
            return true;
        } else if ((toMove == Direction.East) && (x == (size.width - 1)))
        {
            return true;
        }
        
        /* If the event is the hero and walkthrough is enabled, bypass the rest
         * of the collision-checking */
        if ((ev instanceof HeroEvent) && (((MapViewGameState) Game.getGameState()).debugWalkthrough))
        {
            return false;
        }
        
        // Check for layer events in the specified direction
        if ((toMove == Direction.North) && (checkForEventCollision(x, y-1)))
        {
            return true;
        }
        
        if ((toMove == Direction.West) && (checkForEventCollision(x-1, y)))
        {
            return true;
        }
        
        if ((toMove == Direction.South) && (checkForEventCollision(x, y+1)))
        {
            return true;
        }
        
        if ((toMove == Direction.East) && (checkForEventCollision(x+1, y)))
        {
            return true;
        }
        
        // Check for obstructions on the map itself in the specified direction
        ChipSet cSI = ChipSet.getChipSet(chipSet);
        HashMap<Integer,ChipSetData> cSID = cSI.getChipSetData();
        for (HashMap<Integer,Integer> mapArea : getMapData())
        {
            if ((toMove == Direction.North) && (!cSID.get(mapArea.get(x+((y-1)*getSize().width))).isEnableSouth()))
            {
                return true;
            } else if ((toMove == Direction.West) && (!cSID.get(mapArea.get(x-1+(y*getSize().width))).isEnableEast()))
            {
                return true;
            } else if ((toMove == Direction.South) && (!cSID.get(mapArea.get(x+((y+1)*getSize().width))).isEnableNorth()))
            {
                return true;
            } else if ((toMove == Direction.East) && (!cSID.get(mapArea.get(x+1+(y*getSize().width))).isEnableWest()))
            {
                return true;
            }

            if ((toMove == Direction.North) && (!cSID.get(mapArea.get(x+(y*getSize().width))).isEnableNorth()))
            {
                return true;
            } else if ((toMove == Direction.West) && (!cSID.get(mapArea.get(x+(y*getSize().width))).isEnableWest()))
            {
                return true;
            } else if ((toMove == Direction.South) && (!cSID.get(mapArea.get(x+(y*getSize().width))).isEnableSouth()))
            {
                return true;
            } else if ((toMove == Direction.East) && (!cSID.get(mapArea.get(x+(y*getSize().width))).isEnableEast()))
            {
                return true;
            }    
        }
        
        return false;
    }
    
    private boolean checkForEventCollision(int x, int y)
    {
        for (LayerEvent ev : events)
        {
            if (ev.getLayer() == Layer.Middle)
            {
                if (ev.isOccupyingSpace(x, y))
                {
                    return true;
                }
            }
        }

        if (Game.getHeroEvent().isOccupyingSpace(x, y))
        {
            return true;
        }
       
        return false;
    }

    private String chipSet;
    public String getChipSet() {
        return chipSet;
    }
    public void setChipSet(String chipSet) {
        this.chipSet = chipSet;
    }

    private List<HashMap<Integer,Integer>> mapData = new Vector<HashMap<Integer,Integer>>();
    public List<HashMap<Integer, Integer>> getMapData() {
        return mapData;
    }
    
    private String music;
    public String getMusic()
    {
        return music;
    }

    private void setMusic(String music)
    {
        this.music = music;
        this.musicType = MapMusicType.Specified;
    }
    
    private MapMusicType musicType = MapMusicType.NoChange;
    public MapMusicType getMusicType()
    {
        return musicType;
    }
    
    private void setMusicType(MapMusicType musicType)
    {
        this.musicType = musicType;
    }
    
    BufferedImage lowerLayer = null;
    public BufferedImage renderLower()
    {
        if (lowerLayer == null)
        {
            lowerLayer = Display.createCanvas(size.width*16, size.height*16);
            Graphics2D g = lowerLayer.createGraphics();
            ChipSet chipSetObj = ChipSet.getChipSet(chipSet);
            int i,x,y;
            for (i=0;i<mapData.size();i++)
            {
                for (y=0;y<size.height;y++)
                {
                    for (x=0;x<size.width;x++)
                    {
                        int tile = mapData.get(i).get(x+(y*size.width));
                        if (chipSetObj.getChipSetData().get(tile).getLayer() != Layer.Above)
                        {
                            g.drawImage(chipSetObj.getImage(tile), x*16, y*16, null);
                        }
                    }
                }
            }
        }
        
        return lowerLayer;
        
    }
    
    BufferedImage upperLayer = null;
    public BufferedImage renderUpper()
    {
        if (upperLayer == null)
        {
            upperLayer = Display.createCanvas(size.width*16, size.height*16);
            Graphics2D g = upperLayer.createGraphics();
            ChipSet chipSetObj = ChipSet.getChipSet(chipSet);
            int i,x,y;
            for (i=0;i<mapData.size();i++)
            {
                for (y=0;y<size.height;y++)
                {
                    for (x=0;x<size.width;x++)
                    {
                        int tile = mapData.get(i).get(x+(y*size.width));
                        if (chipSetObj.getChipSetData().get(tile).getLayer() == Layer.Above)
                        {
                            g.drawImage(chipSetObj.getImage(tile), x*16, y*16, null);
                        }
                    }
                }
            }
        }
        
        return upperLayer;
    }
    
}

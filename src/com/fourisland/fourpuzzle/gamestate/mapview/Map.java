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
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author hatkirby
 */
public abstract class Map {

    public abstract void initalize();
    
    protected void initalize(Dimension size)
    {
        setSize(size);
        mapData = new Vector<HashMap<Integer,Integer>>();
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
        
        if ((ev instanceof HeroEvent) && (((MapViewGameState) Game.getGameState()).debugWalkthrough))
        {
            return false;
        }
        
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

    private Vector<HashMap<Integer,Integer>> mapData;
    public Vector<HashMap<Integer, Integer>> getMapData() {
        return mapData;
    }
    
    private String music;
    public String getMusic()
    {
        return music;
    }
    public void setMusic(String music)
    {
        this.music = music;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.gamestate.mapview.event.LayerEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author hatkirby
 */
public abstract class Map {

    public abstract void initalize() throws Exception;
    
    public void initalize(Dimension size)
    {
        setSize(size);
        mapData = new Vector<HashMap<Integer,Integer>>();
    }
    
    private Dimension size;
    public Dimension getSize()
    {
        return size;
    }
    public void setSize(Dimension size)
    {
        if ((size.width < 20) || (size.height < 15))
        {
            this.size = new Dimension(20,15);
        } else {
            this.size = size;
        }
    }
    
    private ArrayList<LayerEvent> events = new ArrayList<LayerEvent>();
    public ArrayList<LayerEvent> getEvents()
    {
        return events;
    }
    public LayerEvent getEvent(String event)
    {
        int i=0;
        for (i=0;i<events.size();i++)
        {
            if (events.get(i).getLabel().equals(event))
            {
                return events.get(i);
            }
        }
        
        return null;
    }

    public boolean checkForCollision(int x, int y, Direction toMove) throws Exception
    {
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
        
        int i=0;
        for (i=0;i<events.size();i++)
        {
            if (events.get(i).getLayer() == Layer.Middle)
            {
                if ((events.get(i).getLocation().y == (y - 1)) && (events.get(i).getLocation().x == x) && (toMove == Direction.North))
                {
                    return true;
                }
                
                if ((events.get(i).getLocation().x == (x - 1)) && (events.get(i).getLocation().y == y) && (toMove == Direction.West))
                {
                    return true;
                }
                
                if ((events.get(i).getLocation().y == (y + 1)) && (events.get(i).getLocation().x == x) && (toMove == Direction.South))
                {
                    return true;
                }
                
                if ((events.get(i).getLocation().x == (x + 1)) && (events.get(i).getLocation().y == y) && (toMove == Direction.East))
                {
                    return true;
                }
            }
        }
        
        if ((Game.getHeroEvent().getLocation().y == (y - 1)) && (Game.getHeroEvent().getLocation().x == x) && (toMove == Direction.North))
        {
            return true;
        }

        if ((Game.getHeroEvent().getLocation().x == (x - 1)) && (Game.getHeroEvent().getLocation().y == y) && (toMove == Direction.West))
        {
            return true;
        }

        if ((Game.getHeroEvent().getLocation().y == (y + 1)) && (Game.getHeroEvent().getLocation().x == x) && (toMove == Direction.South))
        {
            return true;
        }

        if ((Game.getHeroEvent().getLocation().x == (x + 1)) && (Game.getHeroEvent().getLocation().y == y) && (toMove == Direction.East))
        {
            return true;
        }
        
        ChipSet cSI = ChipSet.getChipSet(chipSet);
        HashMap<Integer,ChipSetData> cSID = cSI.getChipSetData();
        for (i=0;i<getMapData().size();i++)
        {
            if ((toMove == Direction.North) && (!cSID.get(mapData.get(i).get(x+((y-1)*getSize().width))).isEnableSouth()))
            {
                return true;
            } else if ((toMove == Direction.West) && (!cSID.get(mapData.get(i).get(x-1+(y*getSize().width))).isEnableEast()))
            {
                return true;
            } else if ((toMove == Direction.South) && (!cSID.get(mapData.get(i).get(x+((y+1)*getSize().width))).isEnableNorth()))
            {
                return true;
            } else if ((toMove == Direction.East) && (!cSID.get(mapData.get(i).get(x+1+(y*getSize().width))).isEnableWest()))
            {
                return true;
            }

            if ((toMove == Direction.North) && (!cSID.get(mapData.get(i).get(x+(y*getSize().width))).isEnableNorth()))
            {
                return true;
            } else if ((toMove == Direction.West) && (!cSID.get(mapData.get(i).get(x+(y*getSize().width))).isEnableWest()))
            {
                return true;
            } else if ((toMove == Direction.South) && (!cSID.get(mapData.get(i).get(x+(y*getSize().width))).isEnableSouth()))
            {
                return true;
            } else if ((toMove == Direction.East) && (!cSID.get(mapData.get(i).get(x+(y*getSize().width))).isEnableEast()))
            {
                return true;
            }    
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

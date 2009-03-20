/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import java.util.Vector;

/**
 *
 * @author hatkirby
 */
public class EventList extends Vector<LayerEvent> {
    
    private static final long serialVersionUID = 765438545;
    
    public EventList(Map parentMap)
    {
        setParentMap(parentMap);
    }
    
    public EventList copy(Map parentMap)
    {
        EventList temp = new EventList(parentMap);
        
        for (LayerEvent ev : this)
        {
            temp.add(ev.copy());
        }
        
        return temp;
    }
    
    @Override
    public boolean add(LayerEvent o)
    {
        if (o.getLabel().equals("Unlabelled"))
        {
            o.setLabel("Event" + (this.size()+1));
        } else {
            if (    (o.getLabel().equals("Hero")) ||
                    (o.getLabel().equals("Unlabelled")) ||
                    (o.getLabel().equals("")))
            {
                return false;
            }
            
            for (LayerEvent ev : this)
            {
                if (ev.getLabel().equals(o.getLabel()))
                {
                    return false;
                }
            }
        }
        
        if (parentMap != null)
        {
            o.setParentMap(parentMap);
        }
        
        return super.add(o);
    }
    
    private transient Map parentMap = null;
    public Map getParentMap()
    {
        return parentMap;
    }
    public void setParentMap(Map parentMap)
    {
        this.parentMap = parentMap;
        
        for (LayerEvent ev : this)
        {
            ev.setParentMap(parentMap);
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + (this.parentMap != null ? this.parentMap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        
        if (getClass() != obj.getClass())
        {
            return false;
        }
        
        final EventList other = (EventList) obj;
        if (this.parentMap != other.parentMap && (this.parentMap == null || !this.parentMap.equals(other.parentMap)))
        {
            return false;
        }
        
        return true;
    }

}

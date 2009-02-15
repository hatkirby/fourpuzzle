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
    
    private Map parentMap = null;
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

}

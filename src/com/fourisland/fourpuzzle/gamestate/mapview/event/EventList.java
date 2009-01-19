/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import java.util.Vector;

/**
 *
 * @author hatkirby
 */
public class EventList extends Vector<LayerEvent> {
    
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
            
            for (Event ev : this)
            {
                if (ev.getLabel().equals(o.getLabel()))
                {
                    return false;
                }
            }
        }
        
        return super.add(o);
    }

}

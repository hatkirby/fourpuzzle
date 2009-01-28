/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Layer;
import java.awt.Graphics;
import java.util.ArrayList;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.BlankEventGraphic;

/**
 *
 * @author hatkirby
 */
public class LayerEvent extends AbstractEvent implements Event {
    
    /** Create a new Event instance
     * 
     * @param x The horizontal location of the Event on the Map
     * @param y The vertical location of the Event on the Map
     */
    public LayerEvent(int x, int y)
    {
        setLocation(x,y);
        events = new ArrayList<PossibleEvent>();
        label = "Unlabelled";
    }
    
    /** Create a new Event instance
     * 
     * @param x The horizontal location of the Event on the Map
     * @param y The vertical location of the Event on the Map
     * @param label An identifying label for the Event
     */
    public LayerEvent(int x, int y, String label)
    {
        this(x,y);
        this.label = label;
    }
    
    private String label;
    public String getLabel()
    {
        return label;
    }
    
    private ArrayList<PossibleEvent> events;
    public void addEvent(PossibleEvent pe)
    {
        events.add(pe);
    }
    
    private PossibleEvent getPossibleEvent()
    {
        int i;
        for (i=(events.size()-1);i>-1;i--)
        {
            boolean good = true;
            int j;
            for (j=0;j<events.get(i).preconditions();j++)
            {
                good = (good ? events.get(i).getPrecondition(j).match() : false);
            }
            
            if (good)
            {
                return events.get(i);
            }
        }
        
        return new PossibleEvent();
    }
    
    public void render(Graphics g)
    {
        PossibleEvent toDraw = getPossibleEvent();
        
        /* TODO Replace below condition with an instanceof check to
         * see if toDraw.getGraphic() is an instance of BlankEventGraphic.
         * The current way requires BlankEventGraphic() to be instantated
         * many times for no reason. */
        
        if (!toDraw.getGraphic().equals(new BlankEventGraphic()))
        {
            g.drawImage(toDraw.getImage(), getRenderX(), getRenderY(), null);
        }
    }
    
    public void startMoving()
    {
        Direction toMove = getPossibleEvent().getMovement().nextMovement();
        
        if (toMove != null)
        {
            startMoving(toMove);
        }
    }
    
    public Direction getDirection()
    {
        return getPossibleEvent().getDirection();
    }
    public void setDirection(Direction direction)
    {
        getPossibleEvent().setDirection(direction);
    }

    public Layer getLayer()
    {
        return getPossibleEvent().getLayer();
    }
    
    public EventCallTime getCalltime()
    {
        return getPossibleEvent().getCalltime();
    }
    
    public EventCall getCallback()
    {
        return getPossibleEvent().getCallback();
    }

    public void setLabel(String string) {
        this.label = string;
    }

    public void setAnimationStep(int animStep)
    {
        getPossibleEvent().setAnimationStep(animStep);
    }
    
    public int getAnimationStep()
    {
        return getPossibleEvent().getAnimationStep();
    }
    
}
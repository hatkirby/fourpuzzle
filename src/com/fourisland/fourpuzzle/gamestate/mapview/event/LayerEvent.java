/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Layer;
import java.awt.Graphics;
import java.util.ArrayList;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.BlankEventGraphic;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.MoveableEventGraphic;
import com.fourisland.fourpuzzle.gamestate.mapview.event.precondition.Precondition;

/**
 *
 * @author hatkirby
 */
public class LayerEvent extends AbstractEvent implements Event {
    
    /**
     * Create a new Event instance
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
    
    /**
     * Create a new Event instance
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
    
    public LayerEvent copy()
    {
        LayerEvent temp = new LayerEvent(getLocation().x, getLocation().y, getLabel());
        
        for (PossibleEvent pe : events)
        {
            temp.addEvent(pe.copy());
        }
        
        return temp;
    }
    
    private String label;
    public String getLabel()
    {
        return label;
    }
    
    private ArrayList<PossibleEvent> events;
    public void addEvent(PossibleEvent pe)
    {
        events.add(0, pe);
    }
    
    private PossibleEvent getPossibleEvent()
    {
        for (PossibleEvent event : events)
        {
            boolean good = true;
            for (Precondition pre : event.getPreconditions())
            {
                good = (good ? pre.match() : false);
            }
            
            if (good)
            {
                return event;
            }
        }
        
        return new PossibleEvent();
    }
    
    public void render(Graphics g)
    {
        PossibleEvent toDraw = getPossibleEvent();
        if (!(toDraw.getGraphic() instanceof BlankEventGraphic))
        {
            g.drawImage(toDraw.getGraphic().getImage(), getRenderX(), getRenderY(), null);
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
    
    @Override
    public boolean startMoving(Direction toMove)
    {
        if (!(getPossibleEvent().getGraphic() instanceof MoveableEventGraphic))
        {
            return false;
        }
        
        return super.startMoving(toMove);
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

    public void setLabel(String string)
    {
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
    
    @Override
    public void setMoving(boolean moving)
    {
        super.setMoving(moving);
        getPossibleEvent().setMoving(moving);
    }
    
}
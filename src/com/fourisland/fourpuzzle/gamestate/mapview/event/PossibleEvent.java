/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.gamestate.mapview.event.precondition.Precondition;
import java.util.ArrayList;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.BlankEventGraphic;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.EventGraphic;
import com.fourisland.fourpuzzle.gamestate.mapview.event.movement.MovementType;
import com.fourisland.fourpuzzle.gamestate.mapview.event.movement.StayStillMovementType;

/**
 *
 * @author hatkirby
 */
public class PossibleEvent {

    private EventGraphic graphic;
    private Layer layer;
    private AnimationType animation;
    private MovementType movement;
    private EventCallTime calltime;
    private EventCall callback;
    
    private ArrayList<Precondition> preconditions = new ArrayList<Precondition>();
    private Direction direction = Direction.South;
    private int animationStep = 1;
    
    PossibleEvent()
    {
        this(new Builder());
    }
    
    public static class Builder
    {
        private EventGraphic graphic = new BlankEventGraphic();
        private Layer layer = Layer.Below;
        private AnimationType animation = AnimationType.CommonWithoutStepping;
        private MovementType movement = new StayStillMovementType();
        private EventCallTime calltime = EventCallTime.PushKey;
        private EventCall callback = EventCall.getEmptyEventCall();
        
        public Builder graphic(EventGraphic graphic)
        {
            this.graphic = graphic;
            return this;
        }
        
        public Builder layer(Layer layer)
        {
            this.layer = layer;
            return this;
        }
        
        public Builder animation(AnimationType animation)
        {
            this.animation = animation;
            return this;
        }
        
        public Builder movement(MovementType movement)
        {
            this.movement = movement;
            return this;
        }
        
        public Builder calltime(EventCallTime calltime)
        {
            this.calltime = calltime;
            return this;
        }
        
        public Builder callback(EventCall callback)
        {
            this.callback = callback;
            return this;
        }
        
        public PossibleEvent build()
        {
            return new PossibleEvent(this);
        }
    }
    
    private PossibleEvent(Builder builder)
    {
        graphic = builder.graphic;
        layer = builder.layer;
        animation = builder.animation;
        movement = builder.movement;
        calltime = builder.calltime;
        callback = builder.callback;
    }

    private boolean aSLC = false;
    public EventGraphic getGraphic()
    {
        if (animation.isAlwaysStepping())
        {
            if (aSLC)
            {
                aSLC = false;
                
                if (animationStep == 0)
                {
                    setAnimationStep(2);
                } else {
                    setAnimationStep(animationStep-1);
                }
            } else {
                aSLC = true;
            }
        }
        return graphic;
    }

    public Layer getLayer()
    {
        return layer;
    }

    public AnimationType getAnimation()
    {
        return animation;
    }

    public MovementType getMovement()
    {
        return movement;
    }

    Direction getDirection()
    {
        return direction;
    }

    void setDirection(Direction direction)
    {
        if (animation.canTurn())
        {
            this.direction = direction;
            graphic.setDirection(direction);
        }
    }

    int getAnimationStep()
    {
        return animationStep;
    }

    void setAnimationStep(int animationStep)
    {
        if (animation.canStep())
        {
            this.animationStep = animationStep;
            graphic.setAnimationStep(animationStep);
        }
    }
    
    /**
     * Add a precondition to this PossibleEvent
     * 
     * PossibleEvents are different versions of a single LayerEvent. A
     * LayerEvent may have many PossibleEvents, or none at all. The way it
     * determines which PossibleEvent is current is that each PossibleEvent
     * (possibly) has a set of <b>Precondition</b>s, objects that describe
     * certain situations. If a PossibleEvent's Preconditions are all fulfilled,
     * it is chosen as the active one. If there are more than one PossibleEvents
     * with completely fulfilled Preconditions (that includes having no
     * Preconditions at all), the later one is the one chosen as current.
     * 
     * @param precondition The Precondition to add to the list
     */
    public void addPrecondition(Precondition precondition)
    {
        preconditions.add(precondition);
    }
    
    public ArrayList<Precondition> getPreconditions()
    {
        return preconditions;
    }

    public EventCall getCallback()
    {
        return callback;
    }

    public EventCallTime getCalltime()
    {
        return calltime;
    }
    
}
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
    private MoveSpeed moveSpeed;
    private int freq;
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
        private MoveSpeed moveSpeed = MoveSpeed.Normal;
        private int freq = 1;
        private EventCallTime calltime = EventCallTime.PushKey;
        private EventCall callback = EventCall.getEmptyEventCall();
        
        /**
         * Set the graphic that this event will display
         * 
         * @param graphic The graphic this event will display
         */
        public Builder graphic(EventGraphic graphic)
        {
            this.graphic = graphic;
            return this;
        }
        
        /**
         * Set the layer that this event will be on
         * 
         * @param layer The layer (Above, Middle, Below) this event will be on
         */
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
        
        public Builder speed(MoveSpeed moveSpeed)
        {
            this.moveSpeed = moveSpeed;
            return this;
        }
        
        /**
         * Set how often this event will initiate movement
         * 
         * @param freq How many ticks, minus one, that the event must wait
         * before it can start moving. This number must be greater than or equal
         * to one. For no pause, use 1.
         */
        public Builder freq(int freq)
        {
            this.freq = freq;
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
        setMoveSpeed(builder.moveSpeed);
        setFrequency(builder.freq);
        calltime = builder.calltime;
        callback = builder.callback;
    }
    
    public PossibleEvent copy()
    {
        PossibleEvent temp =  new Builder()
                .graphic(graphic)
                .layer(layer)
                .animation(animation)
                .movement(movement)
                .speed(moveSpeed)
                .freq(freq)
                .calltime(calltime)
                .callback(callback)
                .build();
        
        temp.getPreconditions().addAll(preconditions);
        
        return temp;
    }

    public EventGraphic getGraphic()
    {
        animation.tick(this);
        
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
    
    void setMoveSpeed(MoveSpeed moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }
    
    public MoveSpeed getMoveSpeed()
    {
        return moveSpeed;
    }
    
    void setFrequency(int freq)
    {
        if (freq < 1)
        {
            throw new IllegalArgumentException("Frequency must be greater than or equal to one");
        }
        
        this.freq = freq;
    }
    
    public int getFrequency()
    {
        return freq;
    }

    private boolean moving = false;
    void setMoving(boolean moving)
    {
        this.moving = moving;
    }
    
    Direction getDirection()
    {
        return direction;
    }

    void setDirection(Direction direction)
    {
        if (animation.canTurn() && !moving)
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
     * <p>PossibleEvents are different versions of a single LayerEvent. A
     * LayerEvent may have many PossibleEvents, or none at all. The way it
     * determines which PossibleEvent is current is that each PossibleEvent
     * (possibly) has a set of <b>Precondition</b>s, objects that describe
     * certain situations. If a PossibleEvent's Preconditions are all fulfilled,
     * it is chosen as the active one. If there are more than one PossibleEvents
     * with completely fulfilled Preconditions (that includes having no
     * Preconditions at all), the later one is the one chosen as current.</p>
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
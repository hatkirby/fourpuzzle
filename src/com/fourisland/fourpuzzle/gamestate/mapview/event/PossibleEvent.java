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
import java.awt.image.BufferedImage;

/**
 *w
 * @author hatkirby
 */
public class PossibleEvent {

    private EventGraphic graphic;
    private Layer layer;
    private AnimationType animation;
    private MovementType movement;
    private EventCallTime calltime;
    private EventCall callback;
    private ArrayList<Precondition> preconditions;
    
    private Direction direction;
    private int animationStep;
    
    public PossibleEvent()
    {   
        graphic = new BlankEventGraphic();
        layer = Layer.Below;
        animation = AnimationType.CommonWithStepping;
        movement = new StayStillMovementType();
        calltime = EventCallTime.PushKey;
        callback = EventCall.getEmptyEventCall();
        preconditions = new ArrayList<Precondition>();
        
        direction = Direction.South;
        animationStep = 1;
    }
        
    public BufferedImage getImage()
    {        
        return graphic.getImage();
    }

    private boolean aSLC = false;
    public EventGraphic getGraphic() {
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

    public void setGraphic(EventGraphic graphic) {
        this.graphic = graphic;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public AnimationType getAnimation() {
        return animation;
    }

    public void setAnimation(AnimationType animation) {
        this.animation = animation;
    }

    public MovementType getMovement() {
        return movement;
    }

    public void setMovement(MovementType movement) {
        this.movement = movement;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        if (animation.canTurn())
        {
            this.direction = direction;
            graphic.setDirection(direction);
        }
    }

    public int getAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(int animationStep) {
        if (animation.canStep())
        {
            this.animationStep = animationStep;
            graphic.setAnimationStep(animationStep);
        }
    }
    
    public void addPrecondition(Precondition precondition)
    {
        preconditions.add(precondition);
    }
    
    public Precondition getPrecondition(int i)
    {
        return preconditions.get(i);
    }
    
    public int preconditions()
    {
        return preconditions.size();
    }

    public EventCall getCallback()
    {
        return callback;
    }
    
    public void setCallback(EventCall callback)
    {
        this.callback = callback;
    }

    public EventCallTime getCalltime() {
        return calltime;
    }

    public void setCalltime(EventCallTime calltime) {
        this.calltime = calltime;
    }
    
}

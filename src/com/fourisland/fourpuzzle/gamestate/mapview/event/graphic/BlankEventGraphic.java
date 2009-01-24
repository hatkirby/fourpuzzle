/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.graphic;

import com.fourisland.fourpuzzle.Direction;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class BlankEventGraphic implements EventGraphic {
    
    private Direction direction;
    private int animationStep;

    public BufferedImage getImage()
    {
        return null;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(int animationStep) {
        this.animationStep = animationStep;
    }

}

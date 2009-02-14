/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.graphic;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.gamestate.mapview.CharSet;
import java.awt.image.BufferedImage;

/**
 * CharSetEventGraphic specifes an Event with an image from a <b>CharSet</b>.
 *
 * @author hatkirby
 */
public class CharSetEventGraphic implements MoveableEventGraphic {
    
    private Direction direction = Direction.South;
    private int animationStep = 1;
    private String graphic;
    private int graphicOffset;
    
    public CharSetEventGraphic(String graphic, int graphicOffset)
    {
        this.graphic = graphic;
        this.graphicOffset = graphicOffset;
    }

    public BufferedImage getImage()
    {
        return CharSet.getCharSet(getGraphic()).getImage(getGraphicOffset(), getDirection(), getAnimationStep());
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    public int getGraphicOffset() {
        return graphicOffset;
    }

    public void setGraphicOffset(int graphicOffset) {
        this.graphicOffset = graphicOffset;
    }

    public int getAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(int animationStep) {
        this.animationStep = animationStep;
    }

}

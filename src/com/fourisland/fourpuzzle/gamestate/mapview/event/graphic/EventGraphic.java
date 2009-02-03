/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.graphic;

import com.fourisland.fourpuzzle.Direction;
import java.awt.image.BufferedImage;

/**
 * An EventGraphic specifies the image to be displayed on the map for an Event.
 *
 * @author hatkirby
 */
public interface EventGraphic {
    
    public BufferedImage getImage();
    
    public Direction getDirection();
    public void setDirection(Direction direction);
    
    public int getAnimationStep();
    public void setAnimationStep(int animationStep);

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Layer;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author hatkirby
 */
public interface Event {
    
    public String getLabel();

    public Point getLocation();

    public void setLocation(Point location);
    public void setLocation(int x, int y);
    
    public void render(Graphics g) throws Exception;
    
    public Direction getDirection() throws Exception;
    public void setDirection(Direction direction) throws Exception;

    public boolean isMoving();
    public void startMoving(Direction direction) throws Exception;
    
    public Layer getLayer() throws Exception;
}

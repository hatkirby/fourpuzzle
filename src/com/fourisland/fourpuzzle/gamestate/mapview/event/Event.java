/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public interface Event {
    
    public String getLabel();

    public Point getLocation();

    public void setLocation(Point location);
    public void setLocation(int x, int y);
    
    public void render(Graphics g);
    public int getRenderX();
    public int getRenderY();
    
    public int getMovingX();
    public int getMovingY();
    
    public Direction getDirection();
    public void setDirection(Direction direction);

    public boolean isMoving();
    public boolean startMoving(Direction direction);
    
    public Layer getLayer();
    
    public boolean isOccupyingSpace(int x, int y);
    public List<Direction> getLegalMoves();
    
    public void setAnimationStep(int animStep);
    public int getAnimationStep();
    
    public void setParentMap(Map parentMap);
    public Map getParentMap();
    
    public void setMoveSpeed(MoveSpeed moveSpeed);
    public MoveSpeed getMoveSpeed();
}

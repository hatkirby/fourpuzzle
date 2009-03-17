/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Layer;
import java.awt.Graphics;
import java.awt.Point;
import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.database.GameCharacter;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.BlankEventGraphic;

/**
 *
 * @author hatkirby
 */
public class HeroEvent extends AbstractEvent implements Event {
    
    public HeroEvent()
    {
        setLocation(new Point());
        setMoveSpeed(MoveSpeed.Faster);
    }
    
    public String getLabel()
    {
        return "Hero";
    }
    
    public void render(Graphics g)
    {
        GameCharacter toDraw = Game.getSaveFile().getParty().getLeader();
        if (!(toDraw.getGraphic() instanceof BlankEventGraphic))
        {
            toDraw.getGraphic().setDirection(direction);
            toDraw.getGraphic().setAnimationStep(animationStep);
            g.drawImage(toDraw.getGraphic().getImage(), getRenderX(), getRenderY(), null);
        }
    }
    
    private Direction direction = Direction.South;
    public Direction getDirection()
    {
        return direction;
    }
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    private int animationStep = 1;
    public int getAnimationStep()
    {
        return animationStep;
    }
    public void setAnimationStep(int animationStep)
    {
        this.animationStep = animationStep;
    }

    public Layer getLayer()
    {
        return Layer.Middle;
    }
    
    @Override
    public Map getParentMap()
    {
        return ((MapViewGameState) Game.getGameState()).getCurrentMap();
    }

}

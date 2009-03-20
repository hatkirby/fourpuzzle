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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author hatkirby
 */
public class HeroEvent extends AbstractEvent implements Event, Serializable {
    
    private static final long serialVersionUID = 402340890;
    
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
    
    /**
     * Serialize this HeroEvent instance
     * 
     * When serializing a HeroEvent, the only information that is really
     * necessary is the Hero's location and direction, because all other
     * important information is serialized by SaveFile.
     * 
     * @serialData The Hero's location (as a Point object) is emitted, followed
     * by the a Direction object representing the Hero's direction.
     */
    private void writeObject(ObjectOutputStream s) throws IOException
    {
        s.writeObject(getLocation());
        s.writeObject(getDirection());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        setLocation((Point) s.readObject());
        setDirection((Direction) s.readObject());
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import java.awt.Point;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public final class ImmutableEvent
{
    final Event ev;
    public ImmutableEvent(Event ev)
    {
        this.ev = ev;
    }

    public String getLabel()
    {
        return new String(ev.getLabel());
    }

    public Point getLocation()
    {
        return new Point(ev.getLocation());
    }

    public Direction getDirection()
    {
        return ev.getDirection();
    }

    public boolean isMoving()
    {
        return ev.isMoving();
    }

    public Layer getLayer()
    {
        return ev.getLayer();
    }

    public boolean isOccupyingSpace(int x, int y)
    {
        return ev.isOccupyingSpace(x, y);
    }

    public List<Direction> getLegalMoves()
    {
        return ev.getLegalMoves();
    }

    public int getAnimationStep()
    {
        return ev.getAnimationStep();
    }

    public Map getParentMap()
    {
        return ev.getParentMap();
    }

    public MoveSpeed getMoveSpeed()
    {
        return ev.getMoveSpeed();
    }

}

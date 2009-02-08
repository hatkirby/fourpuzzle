/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.viewpoint;

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import java.awt.Point;

/**
 *
 * @author hatkirby
 */
public class AutomaticViewpoint implements Viewpoint {
    
    private Map map;
    private Point heroLoc = new Point();
    private Point viewpoint;
    
    public AutomaticViewpoint(Map map)
    {
        this.map = map;
        
        refresh();
    }
    
    private void refresh()
    {
        int x,y;
        
        HeroEvent hero = Game.getHeroEvent();
        heroLoc.setLocation(hero.getLocation());
        
        Point endLoc = new Point(hero.getLocation());
        if (hero.isMoving())
        {
            switch (hero.getDirection())
            {
                case North: endLoc.translate(0, -1); break;
                case West: endLoc.translate(-1, 0); break;
                case South: endLoc.translate(0, 1); break;
                case East: endLoc.translate(1, 0); break;
            }
        }
        
        if (Math.max(endLoc.x,heroLoc.x) > 10)
        {
            if (Math.max(endLoc.x,heroLoc.x) < (map.getSize().width - 9))
            {
                x = (heroLoc.x - 10) * 16;
                x += hero.getMovingX();
            } else {
                x = (map.getSize().width - 20) * 16;
            }
        } else {
            x = 0;
        }
        
        if (Math.max(endLoc.y,heroLoc.y) > 7)
        {
            if (Math.max(endLoc.y,heroLoc.y) < (map.getSize().height - 7))
            {
                y = (heroLoc.y - 7) * 16;
                y += hero.getMovingY();
            } else {
                y = (map.getSize().height - 15) * 16;
            }
        } else {
            y = 0;
        }
        
        viewpoint = new Point(x,y);
    }

    public int getX()
    {
        if (!Game.getHeroEvent().getLocation().equals(heroLoc) || Game.getHeroEvent().isMoving())
        {
            refresh();
        }
        
        return viewpoint.x;
    }

    public int getY()
    {
        if (!Game.getHeroEvent().getLocation().equals(heroLoc) || Game.getHeroEvent().isMoving())
        {
            refresh();
        }
        
        return viewpoint.y;
    }

}

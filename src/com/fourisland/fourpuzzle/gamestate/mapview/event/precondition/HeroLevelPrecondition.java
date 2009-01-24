/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

import com.fourisland.fourpuzzle.Game;

/**
 *
 * @author hatkirby
 */
public class HeroLevelPrecondition implements Precondition {

    private String heroName;
    private int level;
    
    public HeroLevelPrecondition(String heroName, int level)
    {
        this.heroName = heroName;
        this.level = level;
    }
    
    public boolean match()
    {
        return (Game.getSaveFile().getParty().exists(heroName) && (Game.getSaveFile().getParty().get(heroName).getLevel() == level));
    }
    
}

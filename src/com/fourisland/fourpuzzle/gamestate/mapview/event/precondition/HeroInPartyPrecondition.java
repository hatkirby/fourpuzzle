/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

import com.fourisland.fourpuzzle.Game;

/**
 * HeroInPartyPrecondition checks to see if a specific character is in the
 * party.
 *
 * @author hatkirby
 */
public class HeroInPartyPrecondition implements Precondition {

    private String heroName;
    
    public HeroInPartyPrecondition(String heroName)
    {
        this.heroName = heroName;
    }
    
    public boolean match()
    {
        return Game.getSaveFile().getParty().exists(heroName);
    }
    
}

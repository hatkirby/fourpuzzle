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
public class SwitchPrecondition implements Precondition {
    
    private String switchID;
    
    public SwitchPrecondition(String switchID)
    {
        this.switchID = switchID;
    }
    
    public boolean match() 
    {
        return (Game.getSaveFile().getSwitches().containsKey(switchID) && Game.getSaveFile().getSwitches().get(switchID));
    }

}

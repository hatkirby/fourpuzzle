/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

import com.fourisland.fourpuzzle.util.Comparison;
import com.fourisland.fourpuzzle.Game;

/**
 *
 * @author hatkirby
 */
public class VariableNumberPrecondition implements Precondition {
    
    private String variableID;
    private Comparison comparison;
    private int number;
    
    public VariableNumberPrecondition(String variableID, Comparison comparison, int number)
    {
        this.variableID = variableID;
        this.comparison = comparison;
        this.number = number;
    }
    
    public boolean match() {
        if (Game.getSaveFile().getVariables().containsKey(variableID))
        {
            int n1 = Game.getSaveFile().getVariables().get(variableID);
            int n2 = number;
            
            switch (comparison)
            {
                case Less: return (n1 < n2);
                case Greater: return (n1 > n2);
                case Equal: return (n1 == n2);
            }
        }
        
        return false;
    }

}

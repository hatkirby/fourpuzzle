/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

import com.fourisland.fourpuzzle.util.Comparison;
import com.fourisland.fourpuzzle.Game;

/**
 * VariableVariablePrecondition compares two variables together with a specified
 * Comparison.
 *
 * @author hatkirby
 */
public class VariableVariablePrecondition implements Precondition {
    
    private String variableID;
    private Comparison comparison;
    private String variableID2;
    
    public VariableVariablePrecondition(String variableID, Comparison comparison, String variableID2)
    {
        this.variableID = variableID;
        this.comparison = comparison;
        this.variableID2 = variableID2;
    }
    
    public boolean match()
    {
        if (Game.getSaveFile().getVariables().containsKey(variableID))
        {
            if (Game.getSaveFile().getVariables().containsKey(variableID2))
            {
                int n1 = Game.getSaveFile().getVariables().get(variableID);
                int n2 = Game.getSaveFile().getVariables().get(variableID2);

                return comparison.compare(n1, n2);
            }
        }
        
        return false;
    }

}

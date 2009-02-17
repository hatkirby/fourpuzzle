/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

import com.fourisland.fourpuzzle.util.Comparison;
import com.fourisland.fourpuzzle.Game;

/**
 * VariableNumberPrecondition compares a the value of a variable and a number
 * together with a specified Comparison.
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
    
    public boolean match()
    {
        if (Game.getSaveFile().getVariables().containsKey(variableID))
        {
            int n1 = Game.getSaveFile().getVariables().get(variableID);
            int n2 = number;

            return comparison.compare(n1, n2);
        }
        
        return false;
    }

}

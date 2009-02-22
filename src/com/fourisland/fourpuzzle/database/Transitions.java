/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

import com.fourisland.fourpuzzle.transition.FadeTransition;
import com.fourisland.fourpuzzle.transition.SquareTransition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
import com.fourisland.fourpuzzle.transition.TransitionPair;

/**
 *
 * @author hatkirby
 */
public enum Transitions {
    
    Generic(new TransitionPair(new FadeTransition(TransitionDirection.Out), new FadeTransition(TransitionDirection.In))),
    Map(new TransitionPair(new SquareTransition(TransitionDirection.Out), new SquareTransition(TransitionDirection.In)));
    
    private TransitionPair trans;
    private Transitions(TransitionPair trans)
    {
        this.trans = trans;
    }
    
    TransitionPair getValue()
    {
        return trans;
    }
    
    void setValue(TransitionPair trans)
    {
        this.trans = trans;
    }
    
}

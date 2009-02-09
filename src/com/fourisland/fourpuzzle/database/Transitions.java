/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

import com.fourisland.fourpuzzle.transition.InTransition;
import com.fourisland.fourpuzzle.transition.MultidirectionalTransition;
import com.fourisland.fourpuzzle.transition.OutTransition;
import com.fourisland.fourpuzzle.transition.SquareTransition;
import com.fourisland.fourpuzzle.transition.Transition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
import com.fourisland.fourpuzzle.transition.TransitionUnsupportedException;

/**
 *
 * @author hatkirby
 */
public enum Transitions {
    MapExit(TransitionDirection.Out, new SquareTransition(TransitionDirection.Out)),
    MapEnter(TransitionDirection.In, new SquareTransition(TransitionDirection.In));
    
    private final TransitionDirection dir;
    private Transition trans;
    private Transitions(TransitionDirection dir, Transition trans)
    {
        this.dir = dir;
        
        if (isTransitionSupported(dir, trans))
        {
            this.trans = trans;
        } else {
            throw new TransitionUnsupportedException(trans.getClass().getName(), dir);
        }
    }
    
    Transition getValue()
    {
        return trans;
    }
    
    void setValue(Transition trans)
    {
        if (isTransitionSupported(dir, trans))
        {
            this.trans = trans;
        } else {
            throw new TransitionUnsupportedException(trans.getClass().getName(), dir);
        }
    }
    
    private boolean isTransitionSupported(TransitionDirection dir, Transition trans)
    {
        if (trans instanceof MultidirectionalTransition)
        {
            return (((MultidirectionalTransition) trans).getDirection() == dir);
        } else if (trans instanceof OutTransition)
        {
            return (dir == TransitionDirection.Out);
        } else if (trans instanceof InTransition)
        {
            return (dir == TransitionDirection.In);
        }
        
        return false;
    }
}

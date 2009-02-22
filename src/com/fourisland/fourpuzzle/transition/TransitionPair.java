/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

/**
 *
 * @author hatkirby
 */
public class TransitionPair {
    
    private OutTransition out;
    private InTransition in;
    
    public TransitionPair(OutTransition out, InTransition in)
    {
        this.out = out;
        this.in = in;
    }
    
    public OutTransition getOutTransition()
    {
        return out;
    }
    
    public InTransition getInTransition()
    {
        return in;
    }
    
    public TransitionPair copy()
    {
        return new TransitionPair((OutTransition) out.copy(), (InTransition) in.copy());
    }

}

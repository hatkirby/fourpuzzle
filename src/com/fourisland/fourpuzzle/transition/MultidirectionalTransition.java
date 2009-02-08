/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

/**
 *
 * @author hatkirby
 */
public interface MultidirectionalTransition extends OutTransition, InTransition {
    
    public TransitionDirection getDirection();

}

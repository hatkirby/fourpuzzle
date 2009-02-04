/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

/**
 *
 * @author hatkirby
 */
public class TransitionUnsupportedException extends RuntimeException {

    private String className;
    private TransitionDirection direction;
    
    public TransitionUnsupportedException(String className, TransitionDirection direction)
    {
        this.className = className;
        this.direction = direction;
    }
    
    @Override
    public String getMessage()
    {
        return "Transition \"" + className + "\" does not support the " + direction.toString() + " direction";
    }
    
}

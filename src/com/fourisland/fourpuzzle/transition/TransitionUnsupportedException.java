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

    public TransitionUnsupportedException(String className, String direction)
    {
        super("Transition \"" + className + "\" does not support the " + direction + " direction");
    }
    
}

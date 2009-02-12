/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public interface Transition {

    /**
     * Render the transition to the display
     * 
     * @param g The graphics device to render the transition to
     * @return If the transition has completed, true. Otherwise false.
     */
    public boolean render(Graphics2D g);
    
    public void setPreTransition(BufferedImage preTransition);
    
    /**
     * Create another Transition with the same properties
     * 
     * <p>This function is used in the Database where default transitions are
     * stored to be used in certain circumstances. When these transitions are
     * needed, this function is called on them to create a copy of the
     * Transition with the same parameters. Essentially, this function should
     * return a new Transition of the same type constructed with the same
     * parameters as the Transition this function is being called on.</p>
     * 
     * @return A copy of the specified Transition
     */
    public Transition copy();
}
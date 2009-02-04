/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import java.awt.Graphics2D;

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
    
}
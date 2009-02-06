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
public class DoNotEraseTransition implements OutTransition {
    
    public boolean render(Graphics2D g)
    {
        return true;
    }

    public void setPreTransition(BufferedImage preTransition)
    {
        // Do nothing
    }

}

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
        g.drawImage(preTransition, 0, 0, null);
        
        return true;
    }

    private BufferedImage preTransition;
    public void setPreTransition(BufferedImage preTransition)
    {
        this.preTransition = preTransition;
    }
    
    public Transition copy()
    {
        return new DoNotEraseTransition();
    }

}

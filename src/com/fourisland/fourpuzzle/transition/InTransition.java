/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public interface InTransition extends Transition {
    
    public void setPreTransition(BufferedImage preTransition);

}

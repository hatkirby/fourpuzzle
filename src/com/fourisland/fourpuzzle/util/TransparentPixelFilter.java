/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import java.awt.image.RGBImageFilter;

/**
 *
 * @author hatkirby
 */
public class TransparentPixelFilter extends RGBImageFilter {

    private int trans;
    public TransparentPixelFilter(int i)
    {
        trans = i;
        
        canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int x, int y, int rgb)
    {
        if (rgb == trans)
        {
            return 0;
        } else {
            return rgb;
        }
    }

}

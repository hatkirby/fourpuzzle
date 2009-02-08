/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.*;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class CharSet {
    
    private BufferedImage charSetImage;
    
    private CharSet()
    {
    }
    
    public BufferedImage getImage(int offset, Direction direction, int step)
    {
        int sx = ((offset % 4) * 72) + (step * 24);
        int sy = ((offset / 4) * 128) + (direction.ordinal() * 32);
     
        return charSetImage.getSubimage(sx, sy, 24, 32);
    }

    public static CharSet getCharSet(String charSet)
    {
        CharSet temp = new CharSet();
        temp.charSetImage = ObjectLoader.getImage("CharSet", charSet);
        return temp;
    }

}

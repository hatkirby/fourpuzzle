/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class FaceSet {
    
    private BufferedImage faceSetImage;
    
    private FaceSet()
    {
    }
    
    public BufferedImage getImage(int offset)
    {
        int sx = (offset % 4) * 48;
        int sy = (offset / 4) * 48;
     
        return faceSetImage.getSubimage(sx, sy, 48, 48);
    }

    public static FaceSet getFaceSet(String faceSet)
    {
        FaceSet temp = new FaceSet();
        temp.faceSetImage = ObjectLoader.getImage("FaceSet", faceSet);
        return temp;
    }

}

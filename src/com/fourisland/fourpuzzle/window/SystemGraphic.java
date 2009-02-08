/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class SystemGraphic {
    
    private static BufferedImage systemGraphic;
    private static String filename = "System";
    public static void setGraphic(String filename)
    {
        SystemGraphic.filename = filename;
    }
    
    public static void initalize()
    {
        systemGraphic = ObjectLoader.getImage("Picture", filename);
    }
    
    public static BufferedImage getMessageBackground()
    {
        return systemGraphic.getSubimage(0, 0, 32, 32);
    }
    
    public static BufferedImage getSelectionBackground()
    {
        return systemGraphic.getSubimage(79, 15, 1, 1);
    }
    
    public static BufferedImage getChoiceArea(Rectangle sca)
    {
        return systemGraphic.getSubimage(sca.x, sca.y, sca.width, sca.height);
    }
    
    public static BufferedImage getTextColor()
    {
        return systemGraphic.getSubimage(0, 48, 16, 16);
    }
    
    public static Color getTransparentColor()
    {
        return new Color(systemGraphic.getRGB(159, 0));
    }

}

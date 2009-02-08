/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.Color;
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
    
    public static BufferedImage getChoiceArea(SystemChoiceArea sca)
    {
        return systemGraphic.getSubimage(sca.getX(), sca.getY(), sca.getWidth(), sca.getHeight());
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import com.fourisland.fourpuzzle.util.TransparentPixelFilter;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;

/**
 *
 * @author hatkirby
 */
public class SystemGraphic {
    
    private static BufferedImage systemGraphic = null;
    private static String filename = "System";
    public static void setGraphic(String filename)
    {
        SystemGraphic.filename = filename;
    }
    
    public static void initalize()
    {
        BufferedImage temp = ObjectLoader.getImage("Picture", filename);
        systemGraphic = Display.createCanvas(160, 80);
        
        systemGraphic.createGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(temp.getSource(), new TransparentPixelFilter(temp.getRGB(159, 0)))),0,0,null);
    }
    
    public static BufferedImage getMessageBackground()
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(0, 0, 32, 32);
    }
    
    public static BufferedImage getSelectionBackground(boolean isFlashing)
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage((isFlashing ? 96 : 64) + 15, 15, 1, 1);
    }
    
    public static BufferedImage getChoiceArea(Rectangle sca)
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(sca.x, sca.y, sca.width, sca.height);
    }
    
    public static BufferedImage getTextColor(int color)
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        if (color < 10)
        {
            return systemGraphic.getSubimage(color*16, 48, 16, 16);
        } else if (color < 20)
        {
            return systemGraphic.getSubimage((color-10)*16, 64, 16, 16);
        } else {
            throw new IllegalArgumentException("Color must be in the range of 0 to 19");
        }
    }
    
    public static BufferedImage getUpArrow()
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(43, 10, 10, 6);
    }
    
    public static BufferedImage getDownArrow()
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(43, 18, 10, 6);
    }

}

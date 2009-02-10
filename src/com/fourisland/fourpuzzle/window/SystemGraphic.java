/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.util.ObjectLoader;
import com.fourisland.fourpuzzle.util.TransparentPixelFilter;
import com.fourisland.fourpuzzle.window.Window.SystemArea;
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
        systemGraphic = new BufferedImage(160, 80, BufferedImage.TYPE_INT_ARGB);
        
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
    
    public static BufferedImage getSelectionBackground()
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(Window.Selector.getX(SystemArea.TOP_LEFT)+15, 15, 1, 1);
    }
    
    public static BufferedImage getChoiceArea(Rectangle sca)
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(sca.x, sca.y, sca.width, sca.height);
    }
    
    public static BufferedImage getTextColor()
    {
        if (systemGraphic == null)
        {
            initalize();
        }
        
        return systemGraphic.getSubimage(0, 48, 16, 16);
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

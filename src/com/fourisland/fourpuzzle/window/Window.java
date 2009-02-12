/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.util.Interval;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public enum Window
{
    Default(32)
    {
        protected BufferedImage getBackground()
        {
            return SystemGraphic.getMessageBackground();
        }
    },
    Selector(64)
    {
        Interval in = Interval.createTickInterval(4);
        boolean isFlashing = false;
        
        @Override
        protected int getX(SystemArea sa)
        {
            if (in.isElapsed())
            {
                isFlashing = !isFlashing;
            }
            
            if (isFlashing)
            {
                return (super.getX(sa)+32);
            } else {
                return super.getX(sa);
            }
        }
        
        protected BufferedImage getBackground()
        {
            if (in.isElapsed())
            {
                isFlashing = !isFlashing;
            }
            
            return SystemGraphic.getSelectionBackground(isFlashing);
        }
    };
    
    protected enum SystemArea
    {
        TOP(15, 0, 1, 8),
        TOP_RIGHT(24, 0, 8, 8),
        RIGHT(24, 15, 8, 1),
        BOTTOM_RIGHT(24, 24, 8, 8),
        BOTTOM(15, 24, 1, 8),
        BOTTOM_LEFT(0, 24, 8, 8),
        LEFT(0, 15, 8, 1),
        TOP_LEFT(0, 0, 8, 8);

        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private SystemArea(int x, int y, int width, int height)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    
    private int x;
    private Window(int x)
    {
        this.x = x;
    }
    
    protected int getX(SystemArea sa)
    {
        return x+sa.x;
    }
    
    protected int getY(SystemArea sa)
    {
        return sa.y;
    }
    
    protected int getWidth(SystemArea sa)
    {
        return sa.width;
    }
    
    protected int getHeight(SystemArea sa)
    {
        return sa.height;
    }
    
    private Rectangle getBounds(SystemArea sa)
    {
        return new Rectangle(getX(sa), getY(sa), getWidth(sa), getHeight(sa));
    }
    
    public int getFullWidth(int width)
    {
        return getWidth(SystemArea.TOP_LEFT) + getWidth(SystemArea.TOP_RIGHT) + width;
    }
    
    public int getFullHeight(int height)
    {
        return getHeight(SystemArea.TOP_LEFT) + getHeight(SystemArea.BOTTOM_LEFT) + height;
    }
    
    public int getLeftX()
    {
        return getWidth(SystemArea.TOP_LEFT);
    }
    
    public int getTopY()
    {
        return getHeight(SystemArea.TOP_LEFT);
    }
    
    protected abstract BufferedImage getBackground();
    
    public BufferedImage getImage(int width, int height)
    {
        BufferedImage temp = Display.createCanvas(getFullWidth(width), getFullHeight(height));
        Graphics2D g = temp.createGraphics();
        
        g.drawImage(getBackground(), 3, 3, getFullWidth(width)-6, getFullHeight(height)-6, null);
        
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.TOP_LEFT)), 0, 0, null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.TOP)), getWidth(SystemArea.TOP_LEFT), 0, width, getHeight(SystemArea.TOP), null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.TOP_RIGHT)), getWidth(SystemArea.TOP_LEFT)+width, 0, null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.LEFT)), 0, getHeight(SystemArea.TOP_LEFT), getWidth(SystemArea.LEFT),height, null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.BOTTOM_LEFT)), 0, getHeight(SystemArea.TOP_LEFT)+height, null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.BOTTOM)), getWidth(SystemArea.BOTTOM_LEFT), (height+getHeight(SystemArea.TOP_LEFT)+getHeight(SystemArea.BOTTOM_LEFT))-getHeight(SystemArea.BOTTOM), width, getHeight(SystemArea.BOTTOM), null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.BOTTOM_RIGHT)), getWidth(SystemArea.BOTTOM_RIGHT)+width, getHeight(SystemArea.TOP_RIGHT)+height, null);
        g.drawImage(SystemGraphic.getChoiceArea(getBounds(SystemArea.RIGHT)), (width+getWidth(SystemArea.TOP_LEFT)+getWidth(SystemArea.TOP_RIGHT))-getWidth(SystemArea.RIGHT), getHeight(SystemArea.TOP_RIGHT), getWidth(SystemArea.RIGHT), height, null);
        
        return temp;
    }
}
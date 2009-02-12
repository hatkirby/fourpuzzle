/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.database.Sound;
import com.fourisland.fourpuzzle.util.Renderable;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public class ChoiceWindow implements Renderable {
    
    private static final int SPACER = 4;
    
    private List<String> choices;
    int numChoices;
    boolean center;
    private int width;
    private int height;
    BufferedImage cacheBase;
    int x;
    int y;
    public ChoiceWindow(List<String> choices, boolean center, ChoiceWindowLocation cwl)
    {
        this.choices = choices;
        numChoices = choices.size();
        this.center = center;
        
        Graphics2D g3 = Display.createCanvas(1, 1).createGraphics();
        Display.setFont(g3);
        
        for (String choice : choices)
        {
            int l = g3.getFontMetrics().stringWidth(choice);
            if (l > getWidth())
            {
                width = l;
            }
            
            height += g3.getFontMetrics().getHeight() + SPACER;
        }
        
        cacheBase = Window.Default.getImage(width, height);
        
        x = cwl.getX(width);
        y = cwl.getY(height);
    }

    public void render(Graphics2D g2)
    {
        Display.setFont(g2);
        
        g2.drawImage(cacheBase, x, y, null);
        
        int fh = g2.getFontMetrics().getHeight();
        int ty = Window.Default.getTopY()+fh+y;
        for (String choice : choices)
        {
            int fw = g2.getFontMetrics().stringWidth(choice);
            int tx = Window.Default.getLeftX()+x;
            
            if (center)
            {
                tx += ((width/2)-(fw/2));
            }
            
            if (getSelected().equals(choice))
            {
                g2.drawImage(Window.Selector.getImage(fw-Window.Selector.getLeftX(), fh-Window.Selector.getTopY()), tx-SPACER, ty-fh-SPACER, null);
            }

            g2.setPaint(new TexturePaint(SystemGraphic.getTextColor(), new Rectangle(tx, ty, fw, fh)));
            g2.drawString(choice, tx, ty);
            
            ty+=(SPACER+g2.getFontMetrics().getHeight());
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    int selected = 0;
    public void moveUp()
    {
        if (selected > 0)
        {
            Audio.playSound(Database.getSound(Sound.MoveCursor));
        
            selected--;
        }
    }
    
    public void moveDown()
    {
        if (selected < (choices.size()-1))
        {
            Audio.playSound(Database.getSound(Sound.MoveCursor));
        
            selected++;
        }
    }
    
    public String getSelected()
    {
        return choices.get(selected);
    }
    
    public static enum ChoiceWindowLocation
    {
        BottomLeft
        {
            public int getX(int width)
            {
                return (Game.WIDTH/5)-(width/2);
            }
        },
        BottomCenter
        {
            public int getX(int width)
            {
                return (Game.WIDTH/2)-(width/2);
            }
        },
        BottomRight
        {
            public int getX(int width)
            {
                return (Game.WIDTH/5*4)-(width/2);
            }    
        };
        
        public abstract int getX(int width);
        
        public int getY(int height)
        {
            return (Game.HEIGHT/4*3)-(height/2);
        }
    }

}
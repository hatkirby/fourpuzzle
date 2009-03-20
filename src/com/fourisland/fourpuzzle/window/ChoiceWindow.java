/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.database.Sound;
import com.fourisland.fourpuzzle.util.Inputable;
import com.fourisland.fourpuzzle.util.PauseTimer;
import com.fourisland.fourpuzzle.util.Renderable;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public class ChoiceWindow implements Renderable, Inputable {
    
    private static final int SPACER = 4;

    public static class Builder
    {
        List<String> choices;
        ChoiceWindowLocation location;
        boolean center = false;
        int width = 0;
        
        public Builder(List<String> choices, ChoiceWindowLocation location)
        {
            this.choices = choices;
            this.location = location;
        }
        
        public Builder center(boolean center)
        {
            this.center = center;
            return this;
        }
        
        public Builder width(int width)
        {
            this.width = width;
            return this;
        }
        
        public ChoiceWindow build()
        {
            return new ChoiceWindow(this);
        }
    }
    
    private List<String> choices;
    boolean center;
    private int width;
    private int height;
    BufferedImage cacheBase;
    int x;
    int y;
    private ChoiceWindow(Builder builder)
    {
        this.choices = builder.choices;
        this.center = builder.center;
        
        for (String choice : choices)
        {
            if (builder.width == 0)
            {
                int l = Display.getFontMetrics().stringWidth(choice);
                if (l > getWidth())
                {
                    width = l;
                }
            }

            height += Display.getFontMetrics().getHeight() + SPACER;
        }
        
        if (builder.width != 0)
        {
            width = builder.width;
        }
        
        cacheBase = Window.Default.getImage(width, height);
        
        x = builder.location.getX(width);
        y = builder.location.getY(height);
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

            g2.setPaint(new TexturePaint(SystemGraphic.getTextColor(0), new Rectangle(tx, ty, fw, fh)));
            g2.drawString(choice, tx, ty);
            
            ty+=(SPACER+fh);
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
        AbsoluteTopLeft
        {
            public int getX(int width)
            {
                return 0;
            }
            
            @Override
            public int getY(int height)
            {
                return 0;
            }
        },
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

    Object hasInputLock = new Object();
    Boolean hasInput = false;
    PauseTimer pt = new PauseTimer(0);
    public void processInput(KeyInput key)
    {
        if (key.getKey() == KeyEvent.VK_UP)
        {
            if (pt.isElapsed())
            {
                moveUp();
                pt.setTimer(1);
            }
        } else if (key.getKey() == KeyEvent.VK_DOWN)
        {
            if (pt.isElapsed())
            {
                moveDown();
                pt.setTimer(1);
            }
        } else if (key.isActionDown())
        {
            synchronized (hasInputLock)
            {
                hasInput = true;
            }
        }
    }
    
    public boolean hasInput()
    {
        synchronized (hasInputLock)
        {
            return hasInput;
        }
    }

}
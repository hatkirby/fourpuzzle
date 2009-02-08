/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Audio;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public class ChoiceWindow {
    
    private final int SPACER = 4;
    
    private List<String> choices;
    int numChoices;
    boolean center;
    public ChoiceWindow(List<String> choices, boolean center)
    {
        this.choices = choices;
        numChoices = choices.size();
        this.center = center;
        
        createGraphic(new BufferedImage(Window.Default.getFullWidth(width), Window.Default.getFullHeight(height), BufferedImage.TYPE_INT_ARGB).createGraphics());
    }
    
    private int width;
    private int height;
    BufferedImage cacheBase;
    private void createGraphic(Graphics2D g3)
    {
        for (String choice : choices)
        {
            int l = g3.getFontMetrics().stringWidth(choice);
            if (l > getWidth())
            {
                width = l;
            }
            
            height += g3.getFontMetrics().getHeight() + SPACER;
        }

        width += SPACER*2;
        height -= SPACER;
        
        cacheBase = new BufferedImage(Window.Default.getFullWidth(width), Window.Default.getFullHeight(height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = cacheBase.createGraphics();
        
        g2.drawImage(SystemGraphic.getMessageBackground(), 1, 1, Window.Default.getFullWidth(width)-2, Window.Default.getFullHeight(height)-2, null);
        g2.drawImage(Window.Default.getImage(width, height), 0, 0, null);
    }

    public void render(Graphics2D g2, int x, int y)
    {
        g2.drawImage(cacheBase, x, y, null);
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        
        int fh = g2.getFontMetrics().getHeight();
        int ty = Window.Default.getTopY()+fh-SPACER+y;
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
                g2.drawImage(SystemGraphic.getSelectionBackground(), tx-1, ty-fh+3, fw+SPACER-2, fh+SPACER-2, null);
                g2.drawImage(Window.Selector.getImage(fw-Window.Selector.getLeftX(), fh-Window.Selector.getTopY()), tx-SPACER, ty-fh, null);
            }
            
            g2.setPaint(new TexturePaint(SystemGraphic.getTextColor(), new Rectangle(tx, ty, fw, fh)));
            g2.drawString(choice, tx, ty);
            
            ty+=(SPACER+g2.getFontMetrics().getHeight());
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
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
            Audio.playSound("Cursor1");
        
            selected--;
        }
    }
    
    public void moveDown()
    {
        if (selected < (choices.size()-1))
        {
            Audio.playSound("Cursor1");
        
            selected++;
        }
    }
    
    public String getSelected()
    {
        return choices.get(selected);
    }

}

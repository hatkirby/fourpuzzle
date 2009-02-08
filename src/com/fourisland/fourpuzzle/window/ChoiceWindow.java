/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.util.TransparentPixelFilter;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.util.List;
import static com.fourisland.fourpuzzle.window.SystemChoiceArea.*;

/* TODO Find a more elegant way to implement window, it looks terrible now */

/**
 *
 * @author hatkirby
 */
public class ChoiceWindow {
    
    private final int SPACER = 4;
    
    private List<String> choices;
    int numChoices;
    public ChoiceWindow(List<String> choices)
    {
        this.choices = choices;
        numChoices = choices.size();
        
        createGraphic(new BufferedImage(TopLeft.getWidth()+getWidth()+TopRight.getWidth(), TopLeft.getHeight()+getHeight()+BottomLeft.getHeight(), BufferedImage.TYPE_INT_ARGB).createGraphics());
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
        
        BufferedImage temp = new BufferedImage(TopLeft.getWidth()+getWidth()+TopRight.getWidth(), TopLeft.getHeight()+getHeight()+BottomLeft.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = temp.createGraphics();

        g.drawImage(SystemGraphic.getChoiceArea(TopLeft), 0, 0, null);
        g.drawImage(SystemGraphic.getChoiceArea(Top), TopLeft.getWidth(), 0, getWidth(),Top.getHeight(), null);
        g.drawImage(SystemGraphic.getChoiceArea(TopRight), TopLeft.getWidth()+getWidth(), 0, null);
        g.drawImage(SystemGraphic.getChoiceArea(Left), 0, TopLeft.getHeight(), Left.getWidth(),getHeight(), null);
        g.drawImage(SystemGraphic.getChoiceArea(BottomLeft), 0, TopLeft.getHeight()+getHeight(), null);
        g.drawImage(SystemGraphic.getChoiceArea(Bottom), BottomLeft.getWidth(), (getHeight()+TopLeft.getHeight()+BottomLeft.getHeight())-Bottom.getHeight(), getWidth(),Bottom.getHeight(), null);
        g.drawImage(SystemGraphic.getChoiceArea(BottomRight), BottomRight.getWidth()+getWidth(), TopRight.getHeight()+getHeight(), null);
        g.drawImage(SystemGraphic.getChoiceArea(Right), (getWidth()+TopLeft.getWidth()+TopRight.getWidth())-Right.getWidth(), TopRight.getHeight(), Right.getWidth(),getHeight(), null);

        cacheBase = new BufferedImage(TopLeft.getWidth()+getWidth()+TopRight.getWidth(), TopLeft.getHeight()+getHeight()+BottomLeft.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = cacheBase.createGraphics();
        
        g2.drawImage(SystemGraphic.getMessageBackground(), 1, 1, TopLeft.getWidth()+getWidth()+TopRight.getWidth()-2, TopLeft.getHeight()+getHeight()+BottomLeft.getHeight()-2, null);
        g2.drawImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(temp.getSource(), new TransparentPixelFilter(-25600))), 0, 0, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        
        int tx = TopLeft.getWidth();
        int ty = TopLeft.getHeight()+g2.getFontMetrics().getHeight()-SPACER;
        for (String choice : choices)
        {
            /* TODO The following code paints the text onto the window. However,
             * when it paints the lowercase 'y', the tail is white, not the
             * correct gradient. */
            
            g2.setPaint(new TexturePaint(SystemGraphic.getTextColor(), new Rectangle(tx, ty, g2.getFontMetrics().stringWidth(choice),g2.getFontMetrics().getHeight())));
            g2.drawString(choice, tx, ty);
            
            ty+=(SPACER+g2.getFontMetrics().getHeight());
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
    }

    public void render(Graphics2D g2, int x, int y)
    {
        g2.drawImage(cacheBase, x, y, null);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

}

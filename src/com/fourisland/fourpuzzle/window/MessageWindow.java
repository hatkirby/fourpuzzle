/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Game;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public class MessageWindow {
    
    private static final int SPACER = 4;
    private static final int HEIGHT = 4*(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFontMetrics().getHeight()+SPACER);
    
    private List<String> messages;
    int width;
    BufferedImage cacheBase;
    public MessageWindow(String message)
    {
        width = Game.WIDTH - Window.Default.getFullWidth(0);
        messages = new ArrayList<String>();
        
        initalizeMessages(message, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics());
        
        cacheBase = new BufferedImage(Game.WIDTH, Window.Default.getFullHeight(HEIGHT), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = cacheBase.createGraphics();
        
        g2.drawImage(SystemGraphic.getMessageBackground(), 1, 1, Game.WIDTH-2, Window.Default.getFullHeight(HEIGHT)-2, null);
        g2.drawImage(Window.Default.getImage(width, HEIGHT), 0, 0, null);
    }
    
    private void initalizeMessages(String message, Graphics2D g)
    {
        setFont(g);
        
        String temp = message;
        int len = 0;
        while (!temp.isEmpty())
        {
            while ((g.getFontMetrics().stringWidth(temp.substring(0, len)) < (width - SPACER)) && (len < temp.length()))
            {
                len++;
            }

            if (len != temp.length())
            {
                while ((!temp.substring(len, len+1).equals(" ")) && (len > 0))
                {
                    len--;
                }
            }
            
            messages.add(temp.substring(0, len));
            
            if (len != temp.length())
            {
                temp = temp.substring(len+1);
            } else {
                temp = "";
            }
            
            len = 0;
        }
    }
    
    public void render(Graphics2D g2)
    {
        int y = MessageWindowLocation.Bottom.getY();
        
        g2.drawImage(cacheBase, 0, y, null);

        setFont(g2);
        
        int fh = g2.getFontMetrics().getHeight();
        int ty = Window.Default.getTopY()+fh-(SPACER/2)+y;
        int msgs = Math.min(messages.size(), 4);
        for (int i=0;i<msgs;i++)
        {
            String message = messages.get(i);
            int fw = g2.getFontMetrics().stringWidth(message);
            int tx = Window.Default.getLeftX();
            
            g2.setPaint(new TexturePaint(SystemGraphic.getTextColor(), new Rectangle(tx, ty, fw, fh)));
            g2.drawString(message, tx, ty);
            
            ty+=(SPACER+g2.getFontMetrics().getHeight());
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
    }
    
    public static enum MessageWindowLocation
    {
        Top(0),
        Middle((Game.HEIGHT/2)-(Window.Default.getFullHeight(MessageWindow.HEIGHT)/2)),
        Bottom(Game.HEIGHT-Window.Default.getFullHeight(MessageWindow.HEIGHT));
        
        private int y;
        private MessageWindowLocation(int y)
        {
            this.y = y;
        }
        
        public int getY()
        {
            return y;
        }
    }
    
    public static void setFont(Graphics2D g)
    {
        try {
            g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("/usr/share/fonts/truetype/ttf-dejavu/DejaVuSansMono.ttf")));
        } catch (FontFormatException ex) {
            Logger.getLogger(MessageWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MessageWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 10));        
    }

}

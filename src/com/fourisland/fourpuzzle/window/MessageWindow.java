/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.gamestate.mapview.FaceSet;
import com.fourisland.fourpuzzle.util.Interval;
import com.fourisland.fourpuzzle.util.Renderable;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author hatkirby
 */
public class MessageWindow implements Renderable {
    
    private static final int SPACER = 4;
    private static final int HEIGHT = (4*(Display.getFontMetrics().getHeight()+SPACER));
    
    String message;
    private volatile List<String> messages;
    int width;
    BufferedImage cacheBase;
    int num = 0;
    int upTo = 0;
    boolean bounceArrow = false;
    Interval in = Interval.createTickInterval(4);
    private MessageWindow(String message)
    {
        this.message = message;
        width = Game.WIDTH - Window.Default.getFullWidth(0);

        cacheBase = Window.Default.getImage(width, HEIGHT);
    }
    
    boolean hasFace = false;
    BufferedImage face;
    private MessageWindow(String message, String faceSet, int face)
    {
        this(message);
        
        this.face = FaceSet.getFaceSet(faceSet).getImage(face);
        hasFace = true;
    }
    
    private static void displayMessage(final MessageWindow mw) throws InterruptedException
    {
        final CountDownLatch cdl = new CountDownLatch(1);
        
        Display.registerRenderable(mw);
        
        KeyAdapter ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_SPACE))
                {
                    if (mw.pushEnter())
                    {
                        cdl.countDown();
                    }
                }

                Game.setKey(null);
            }
        };
        
        PuzzleApplication.gameFrame.addKeyListener(ka);
        
        cdl.await();
        
        PuzzleApplication.gameFrame.removeKeyListener(ka);
        Display.unregisterRenderable(mw);
    }
    
    public static void displayMessage(String message) throws InterruptedException
    {
        displayMessage(new MessageWindow(message));
    }
    
    public static void displayMessage(String message, String faceSet, int face) throws InterruptedException
    {
        displayMessage(new MessageWindow(message, faceSet, face));
    }
    
    private void initalizeMessages(String message, Graphics2D g)
    {
        messages = new ArrayList<String>();
        
        Display.setFont(g);
        
        int length = width - SPACER;
        if (hasFace)
        {
            length -= (48 + (SPACER*3));
        }
        
        String temp = message;
        int len = 0;
        while (!temp.isEmpty())
        {
            while ((g.getFontMetrics().stringWidth(temp.substring(0, len)) < length) && (len < temp.length()))
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
        
        setLength();
    }
    
    private void setLength()
    {
        num = 0;
        
        for (int i=0;i<Math.min(messages.size(),4);i++)
        {
            num += messages.get(i).length();
        }
    }
    
    public void render(Graphics2D g2)
    {
        if (messages == null)
        {
            initalizeMessages(message, g2);
        }
        
        int y = MessageWindowLocation.Bottom.getY();

        Display.setFont(g2);
        
        g2.drawImage(cacheBase, 0, y, null);
        
        int fw = g2.getFontMetrics().stringWidth(message);
        int fh = g2.getFontMetrics().getHeight();
        int tx = Window.Default.getLeftX();
        int ty = Window.Default.getTopY()+fh-(SPACER/2)+y;
        int msgs = Math.min(messages.size(), 4);
        int toPrint = upTo;
        
        if (hasFace)
        {
            g2.drawImage(face, tx+SPACER, ty-fh+SPACER, null);

            tx += 48 + (SPACER*2);
        }
        
        for (int i=0;i<msgs;i++)
        {
            String message = messages.get(i);
            
            g2.setPaint(new TexturePaint(SystemGraphic.getTextColor(), new Rectangle(tx, ty, fw, fh)));
            g2.drawString(message.substring(0, Math.min(toPrint, message.length())), tx, ty);
            
            ty+=(SPACER+fh);
            
            toPrint -= Math.min(toPrint, message.length());
        }
        
        if (upTo < num)
        {
            upTo+=3;
        } else {
            g2.drawImage(SystemGraphic.getDownArrow(), (Window.Default.getFullWidth(width)/2)-5, y+HEIGHT+SPACER+(bounceArrow ? 1 : 0), null);
            
            if (in.isElapsed())
            {
                bounceArrow = !bounceArrow;
            }
        }
    }
    
    private synchronized boolean pushEnter()
    {
        if (upTo >= num)
        {
            int msgs = messages.size();
            for (int i=0;i<Math.min(4, msgs);i++)
            {
                messages.remove(0);
            }
            
            if (messages.size() > 0)
            {
                upTo = 0;
                setLength();
            } else {
                return true;
            }
        }
        
        return false;
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
}

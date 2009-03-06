/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.gamestate.mapview.FaceSet;
import com.fourisland.fourpuzzle.util.Inputable;
import com.fourisland.fourpuzzle.util.Interval;
import com.fourisland.fourpuzzle.util.Renderable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author hatkirby
 */
public class MessageWindow implements Renderable, Inputable {
    
    private static final int SPACER = 4;
    private static final int HEIGHT = (4*(Display.getFontMetrics().getHeight()+SPACER));
    
    TextRenderer tr;
    int width;
    BufferedImage cacheBase;
    int upTo = 0;
    boolean bounceArrow = false;
    Interval in = Interval.createTickInterval(4);
    public MessageWindow(String message)
    {
        width = Game.WIDTH - Window.Default.getFullWidth(0);
        cacheBase = Window.Default.getImage(width, HEIGHT);
        
        tr = new TextRenderer(width);
        tr.setEscapes(true);
        tr.initalizeText(message);
    }
    
    boolean hasFace = false;
    BufferedImage face;
    public MessageWindow(String message, String faceSet, int face)
    {
        width = Game.WIDTH - Window.Default.getFullWidth(0);
        cacheBase = Window.Default.getImage(width, HEIGHT);
        
        this.face = FaceSet.getFaceSet(faceSet).getImage(face);
        hasFace = true;
        
        tr = new TextRenderer(width);
        tr.setEscapes(true);
        tr.setIndent(48 + (SPACER*2));
        tr.initalizeText(message);
    }
    
    public void render(Graphics2D g2)
    {
        int y = MessageWindowLocation.Bottom.getY();
        
        g2.drawImage(cacheBase, 0, y, null);
        
        int fh = g2.getFontMetrics().getHeight();
        int ty = Window.Default.getTopY()+fh-(SPACER/2)+y;
        
        if (hasFace)
        {
            g2.drawImage(face, Window.Default.getLeftX()+SPACER, ty-fh+SPACER, null);
        }
        
        g2.drawImage(tr.render(upTo), Window.Default.getLeftX(), Window.Default.getTopY()+y, null);
        
        if (tr.isCascadingDone())
        {
            g2.drawImage(SystemGraphic.getDownArrow(), (Window.Default.getFullWidth(width)/2)-5, y+HEIGHT+SPACER+(bounceArrow ? 1 : 0), null);
            
            if (in.isElapsed())
            {
                bounceArrow = !bounceArrow;
            }
        }
    }
    
    CountDownLatch cdl = new CountDownLatch(1);
    public void waitForCompletion() throws InterruptedException
    {
        cdl.await();
    }
    
    public void processInput(KeyInput key)
    {
        if (key.isActionDown())
        {
            if (tr.isCascadingDone())
            {
                int msgs = tr.numLines();
                if (upTo >= (msgs-4))
                {
                    cdl.countDown();
                } else {
                    upTo += 4;

                    if (upTo > msgs)
                    {
                        upTo = msgs;
                    }
                }
            }

            key.letGo();
        }
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

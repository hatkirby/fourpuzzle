/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.gamestate.GameState;
import com.fourisland.fourpuzzle.transition.InTransition;
import com.fourisland.fourpuzzle.transition.MultidirectionalTransition;
import com.fourisland.fourpuzzle.transition.OutTransition;
import com.fourisland.fourpuzzle.transition.Transition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
import com.fourisland.fourpuzzle.transition.TransitionPair;
import com.fourisland.fourpuzzle.transition.TransitionUnsupportedException;
import com.fourisland.fourpuzzle.util.Renderable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author hatkirby
 */
public class Display {
    
    private static int tileAnimationFrame = 0;
    private static List<Renderable> renderables = new CopyOnWriteArrayList<Renderable>();

    public static void render(Component gameFrame)
    {
        VolatileImage vImg = gameFrame.createVolatileImage(Game.WIDTH, Game.HEIGHT);
        render(gameFrame, vImg);

        Image img = null;
        do
        {
            int returnCode = vImg.validate(gameFrame.getGraphicsConfiguration());
            if (returnCode == VolatileImage.IMAGE_RESTORED)
            {
                render(gameFrame, vImg);
            } else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE)
            {
                vImg = gameFrame.createVolatileImage(Game.WIDTH, Game.HEIGHT);
                render(gameFrame, vImg);
            }

            img = vImg;
        } while (vImg.contentsLost());

        Rectangle renderArea = getRenderArea(gameFrame);
        gameFrame.getGraphics().setColor(Color.BLACK);
        gameFrame.getGraphics().fillRect(0, 0, renderArea.x, gameFrame.getHeight());
        gameFrame.getGraphics().fillRect(0, 0, gameFrame.getWidth(), renderArea.y);
        gameFrame.getGraphics().fillRect(renderArea.x+renderArea.width, 0, gameFrame.getWidth()-(renderArea.x+renderArea.width), gameFrame.getHeight());
        gameFrame.getGraphics().fillRect(0, renderArea.y+renderArea.height, gameFrame.getWidth(), gameFrame.getHeight()-(renderArea.y+renderArea.height));
        gameFrame.getGraphics().drawImage(img, renderArea.x, renderArea.y, renderArea.width, renderArea.height, gameFrame);
        img.flush();
        Toolkit.getDefaultToolkit().sync();

        if (tileAnimationFrame == 15)
        {
            tileAnimationFrame = 0;
        } else {
            tileAnimationFrame++;
        }
    }

    private static void render(Component gameFrame, VolatileImage vImg)
    {
        if (vImg.validate(gameFrame.getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)
        {
            vImg = gameFrame.createVolatileImage(Game.WIDTH, Game.HEIGHT);
        }

        Graphics2D g = vImg.createGraphics();
        
        if (transitionRunning)
        {
            if (transition != null)
            {
                if (transition.render(g))
                {
                    if (startedTransition)
                    {
                        midTransition = vImg.getSnapshot();
                    } else {
                        midTransition = null;
                    }

                    transitionWait.countDown();
                }
            } else {
                g.drawImage(midTransition, 0, 0, null);
            }
        } else {
            render(g);
        }
        
        g.dispose();
    }
    
    private static void render(Graphics2D g)
    {
        Game.getGameState().render(g);
        
        for (Renderable r : renderables)
        {
            r.render(g);
        }
    }
    
    public static void registerRenderable(Renderable r)
    {
        renderables.add(r);
    }
    
    public static void unregisterRenderable(Renderable r)
    {
        renderables.remove(r);
    }
    
    private static boolean startedTransition = false;
    private static Transition transition;
    private static CountDownLatch transitionWait;
    private static boolean transitionRunning = false;
    private static BufferedImage midTransition = null;
    public static void transition(Transition transition) throws InterruptedException
    {
        if (transition instanceof MultidirectionalTransition)
        {
            MultidirectionalTransition temp = (MultidirectionalTransition) transition;
            
            if (startedTransition && (temp.getDirection() != TransitionDirection.In))
            {
                throw new TransitionUnsupportedException(transition.getClass().getName(), TransitionDirection.In);
            } else if (!startedTransition && (temp.getDirection() != TransitionDirection.Out))
            {
                throw new TransitionUnsupportedException(transition.getClass().getName(), TransitionDirection.Out);
            }
            
            if (temp.getDirection() == TransitionDirection.In)
            {
                temp.setPreTransition(midTransition);
                
                BufferedImage bImg = Display.createCanvas(Game.WIDTH, Game.HEIGHT);
                render(bImg.createGraphics());
                temp.setPostTransition(bImg);
            } else {
                BufferedImage bImg = Display.createCanvas(Game.WIDTH, Game.HEIGHT);
                render(bImg.createGraphics());
                temp.setPreTransition(bImg);
            }
        } else {
            if (startedTransition && !(transition instanceof InTransition))
            {
                throw new TransitionUnsupportedException(transition.getClass().getName(), TransitionDirection.In);
            } else if (!startedTransition && !(transition instanceof OutTransition))
            {
                throw new TransitionUnsupportedException(transition.getClass().getName(), TransitionDirection.Out);
            }
            
            if (transition instanceof InTransition)
            {
                transition.setPreTransition(midTransition);
                
                BufferedImage bImg = Display.createCanvas(Game.WIDTH, Game.HEIGHT);
                render(bImg.createGraphics());
                ((InTransition) transition).setPostTransition(bImg);
            } else {
                BufferedImage bImg = Display.createCanvas(Game.WIDTH, Game.HEIGHT);
                render(bImg.createGraphics());
                transition.setPreTransition(bImg);
            }
        }
        
        Display.transition = transition;
        startedTransition = !startedTransition;
        transitionRunning = true;
        
        transitionWait = new CountDownLatch(1);
        
        try {
            transitionWait.await();
        } catch (InterruptedException ex) {
            throw ex;
        } finally {
            Display.transition = null;

            if (!startedTransition)
            {
                transitionRunning = false;
            }
        }
    }
    
    private static Executor transitioner = Executors.newSingleThreadExecutor();
    public static void transition(final OutTransition out, final GameState gameState, final InTransition in, boolean thread) throws InterruptedException
    {
        if (thread)
        {
            Runnable transitionCall = new Runnable() {
                public void run()
                {
                    try {
                        Display.transition(out);
                        Game.setGameState(gameState);
                        Display.transition(in);
                    } catch (InterruptedException ex) {
                        return;
                    } catch (RuntimeException ex)
                    {
                        PuzzleApplication.reportError(ex);
                    } catch (Error ex)
                    {
                        PuzzleApplication.reportError(ex);
                    }
                }
            };
            
            transitioner.execute(transitionCall);
        } else {
            Display.transition(out);
            Game.setGameState(gameState);
            Display.transition(in);
        }
    }
    
    public static void transition(TransitionPair trans, GameState gameState, boolean thread) throws InterruptedException
    {
        transition(trans.getOutTransition(), gameState, trans.getInTransition(), thread);
    }
    
    public static boolean isTransitionRunning()
    {
        return transitionRunning;
    }
    
    private static Font theFont = null;
    private static void initalizeFont()
    {
        ResourceMap rm = PuzzleApplication.getInstance().getContext().getResourceMap();
        InputStream file = rm.getClassLoader().getResourceAsStream("com/fourisland/fourpuzzle/resources/RMG2000.ttf");

        try {
            theFont = Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (FontFormatException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        theFont = theFont.deriveFont(Font.PLAIN, 9);
    }
    
    public static void setFont(Graphics2D g)
    {
        if (theFont == null)
        {
            initalizeFont();
        }
        
        g.setFont(theFont);
    }
    
    public static Font getFont()
    {
        if (theFont == null)
        {
            initalizeFont();
        }
        
        return theFont;
    }
    
    public static BufferedImage createCanvas(int width, int height)
    {
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        return temp;
    }
    
    private static FontMetrics fontMetrics = null;
    public static FontMetrics getFontMetrics()
    {
        if (fontMetrics == null)
        {
            fontMetrics = createCanvas(1, 1).createGraphics().getFontMetrics(getFont());
        }
        
        return fontMetrics;
    }
    
    private static Component cacheFrame = null;
    private static Rectangle cachePoint = null;
    private static Rectangle getRenderArea(Component gameFrame)
    {
        if ((cacheFrame != null) && (cacheFrame == gameFrame.getParent()))
        {
            return cachePoint;
        }
        
        float wt = gameFrame.getWidth() / (float) Game.WIDTH;
        float ht = gameFrame.getHeight() / (float) Game.HEIGHT;
        int width = Math.round(Math.min(wt, ht) * Game.WIDTH);
        int height = Math.round(Math.min(wt, ht) * Game.HEIGHT);
        int x = (gameFrame.getWidth()/2)-(width/2);
        int y = (gameFrame.getHeight()/2)-(height/2);
        cacheFrame = gameFrame.getParent();
        cachePoint = new Rectangle(x, y, width, height);
        
        return cachePoint;
    }
    
    public int getTileAnimationFrame()
    {
        return tileAnimationFrame;
    }
    
}

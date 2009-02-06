/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.transition.InTransition;
import com.fourisland.fourpuzzle.transition.MultidirectionalTransition;
import com.fourisland.fourpuzzle.transition.OutTransition;
import com.fourisland.fourpuzzle.transition.Transition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
import com.fourisland.fourpuzzle.transition.TransitionUnsupportedException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.concurrent.CountDownLatch;
import javax.swing.JDialog;

/**
 *
 * @author hatkirby
 */
public class Display {
    
    public static int tileAnimationFrame = 0;

    public static void render(JDialog gameFrame)
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

        gameFrame.getContentPane().getGraphics().drawImage(img, 0, 0, gameFrame.getContentPane().getWidth(), gameFrame.getContentPane().getHeight(), gameFrame);
        img.flush();
        Toolkit.getDefaultToolkit().sync();

        if (tileAnimationFrame == 15)
        {
            tileAnimationFrame = 0;
        } else {
            tileAnimationFrame++;
        }
    }

    private static void render(JDialog gameFrame, VolatileImage vImg)
    {
        if (vImg.validate(gameFrame.getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)
        {
            vImg = gameFrame.createVolatileImage(Game.WIDTH, Game.HEIGHT);
        }

        Graphics2D g = vImg.createGraphics();
        
        if (transitionRunning)
        {
            Game.getGameState().render(g);

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
            Game.getGameState().render(g);
        }
        
        g.dispose();
    }
    
    private static boolean startedTransition = false;
    private static Transition transition;
    private static CountDownLatch transitionWait;
    private static boolean transitionRunning = false;
    private static BufferedImage midTransition = null;
    private static BufferedImage postTransition = null;
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
                
                postTransition = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Game.getGameState().render(postTransition.createGraphics());
                temp.setPostTransition(postTransition);
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
                ((InTransition) transition).setPreTransition(midTransition);
                
                postTransition = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Game.getGameState().render(postTransition.createGraphics());
                ((InTransition) transition).setPostTransition(postTransition);
            }
        }
        
        Display.transition = transition;
        startedTransition = !startedTransition;
        transitionRunning = true;
        
        transitionWait = new CountDownLatch(1);
        transitionWait.await();
        
        transition = null;
        
        if (!startedTransition)
        {
            transitionRunning = false;
        }
    }
    
    public static boolean isTransitionRunning()
    {
        return transitionRunning;
    }
    
}

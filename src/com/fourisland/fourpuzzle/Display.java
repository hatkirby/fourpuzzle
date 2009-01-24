/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.transition.Transition;
import com.fourisland.fourpuzzle.transition.TransitionCallbackThread;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.VolatileImage;
import javax.swing.JDialog;

/**
 *
 * @author hatkirby
 */
public class Display {
    
    public static int tileAnimationFrame = 0;

    public static void render(JDialog gameFrame)
    {
        if (enabled)
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
    }

    private static void render(JDialog gameFrame, VolatileImage vImg)
    {
        if (vImg.validate(gameFrame.getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)
        {
            vImg = gameFrame.createVolatileImage(Game.WIDTH, Game.HEIGHT);
        }

        Graphics2D g = vImg.createGraphics();
        
        if (transition != null)
        {
            transition.render(g);
        }
        
        Game.getGameState().render(g);
        g.dispose();
    }
    
    public static void transition(Transition transition, Runnable callback)
    {
        setTransition(transition);
        
        new Thread(new TransitionCallbackThread(callback)).start();
    }
    
    private static Transition transition;
    public static Transition getTransition()
    {
        return transition;
    }
    public static void setTransition(Transition transition)
    {
        Display.transition = transition;
    }
    
    private static boolean enabled = true;
    public static boolean isEnabled()
    {
        return enabled;
    }
    public static void setEnabled(boolean aEnabled)
    {
        enabled = aEnabled;
    }
    
}

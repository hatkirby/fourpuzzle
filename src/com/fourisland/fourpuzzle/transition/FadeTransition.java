/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.transition;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.util.PauseTimer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author hatkirby
 */
public class FadeTransition implements MultidirectionalTransition {
    
    private TransitionDirection direction;
    public FadeTransition(TransitionDirection direction)
    {
        this.direction = direction;
    }

    public TransitionDirection getDirection()
    {
        return direction;
    }

    private int ticks = 750 / Game.FPS;
    private PauseTimer pt = new PauseTimer(ticks);
    public boolean render(Graphics2D g)
    {
        BufferedImage temp = Display.createCanvas(Game.WIDTH, Game.HEIGHT);
        double alpha = 1.0 * pt.getTimer() / ticks * 0.75;

        for (int i=0;i<Game.WIDTH;i++)
        {
            for (int j=0;j<Game.HEIGHT;j++)
            {
                if (direction == TransitionDirection.Out)
                {
                    temp.setRGB(i, j, combine(preTransition.getRGB(i, j), Color.BLACK.getRGB(), alpha));
                } else if (direction == TransitionDirection.In)
                {
                    temp.setRGB(i, j, combine(preTransition.getRGB(i, j), postTransition.getRGB(i, j), alpha));
                }
            }
        }
        
        g.drawImage(temp, 0, 0, null);
        
        if (pt.isElapsed())
        {
            return true;
        }
        
        return false;
    }
    
    public static int combine(int c1, int c2, double alpha)
    {
        Color co1 = new Color(c1);
        Color co2 = new Color(c2);
        
        int r = (int) (alpha * co1.getRed() + (1 - alpha) * co2.getRed());
        int g = (int) (alpha * co1.getGreen() + (1 - alpha) * co2.getGreen());
        int b = (int) (alpha * co1.getBlue() + (1 - alpha) * co2.getBlue());

        return new Color(r, g, b).getRGB();
    }

    private BufferedImage preTransition;
    public void setPreTransition(BufferedImage preTransition)
    {
        this.preTransition = preTransition;
    }

    public Transition copy()
    {
        return new FadeTransition(direction);
    }

    private BufferedImage postTransition;
    public void setPostTransition(BufferedImage postTransition)
    {
        this.postTransition = postTransition;
    }

}

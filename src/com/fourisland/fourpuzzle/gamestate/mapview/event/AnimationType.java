/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.util.Interval;

/**
 *
 * @author hatkirby
 */
public enum AnimationType {
    /**
     * The default AnimationType, it allows the Event to turn and to animate
     * while it walks, but it only animates while it moves.
     */
    CommonWithoutStepping(true, true),
    /**
     * An AnimationType which allows the Event to turn and to animate. It will
     * animate at all times, even while stationary.
     */
    CommonWithStepping(true, true)
    {
        Interval in = Interval.createTickInterval(2);
        
        @Override
        public void tick(PossibleEvent pe)
        {
            if (in.isElapsed())
            {
                if (pe.getAnimationStep() == 0)
                {
                    pe.setAnimationStep(2);
                } else {
                    pe.setAnimationStep(pe.getAnimationStep()-1);
                }
            }
        }
    },
    /**
     * An AnimationType that allows the Event to turn, but not to animate.
     */
    WithoutStepping(true, false),
    /**
     * An AnimationType that does not allow the Event to turn or animate.
     */
    FixedGraphic(false, false);
    
    private boolean canTurn;
    private boolean canStep;
    private AnimationType(boolean canTurn, boolean canStep)
    {
        this.canTurn = canTurn;
        this.canStep = canStep;
    }
    
    public boolean canTurn()
    {
        return canTurn;
    }
    
    public boolean canStep()
    {
        return canStep;
    }
    
    public void tick(PossibleEvent pe)
    {
        // Do nothing
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

/**
 *
 * @author hatkirby
 */
public enum AnimationType {
    /**
     * The default AnimationType, it allows the Event to turn and to animate
     * while it walks, but it only animates while it moves.
     */
    CommonWithoutStepping(true, true, false),
    /**
     * An AnimationType which allows the Event to turn and to animate. It will
     * animate at all times, even while stationary.
     */
    CommonWithStepping(true, true, true),
    /**
     * An AnimationType that allows the Event to turn, but not to animate.
     */
    WithoutStepping(true, false, false),
    /**
     * An AnimationType that does not allow the Event to turn or animate.
     */
    FixedGraphic(false, false, false);
    
    private boolean canTurn;
    private boolean canStep;
    private boolean alwaysStepping;
    private AnimationType(boolean canTurn, boolean canStep, boolean alwaysStepping)
    {
        this.canTurn = canTurn;
        this.canStep = canStep;

        if (!canStep)
        {
            this.alwaysStepping = false;
        } else {
            this.alwaysStepping = alwaysStepping;
        }
    }
    
    public boolean canTurn()
    {
        return canTurn;
    }
    
    public boolean canStep()
    {
        return canStep;
    }
    
    public boolean isAlwaysStepping()
    {
        return alwaysStepping;
    }

}

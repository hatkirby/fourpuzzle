/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

/**
 *
 * @author hatkirby
 */
public enum MoveSpeed {
    Slower2(16),
    Slower(8),
    Normal(6),
    Faster(4),
    Faster2(2),
    Faster4(1);
    
    private int speed;
    private MoveSpeed(int speed)
    {
        this.speed = speed;
    }
    
    public int getSpeed()
    {
        return speed;
    }

}

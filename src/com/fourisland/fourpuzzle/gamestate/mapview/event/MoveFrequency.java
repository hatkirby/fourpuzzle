/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

/**
 *
 * @author hatkirby
 */
public enum MoveFrequency {
    
    EIGHT(1),
    SEVEN(2),
    SIX(3),
    FIVE(4),
    FOUR(5),
    THREE(6),
    TWO(7),
    ONE(8);
    
    private int freq;
    private MoveFrequency(int freq)
    {
        this.freq = freq;
    }
    
    public int getFrequency()
    {
        return freq;
    }

}

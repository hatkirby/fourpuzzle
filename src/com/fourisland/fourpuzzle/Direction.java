/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

/**
 *
 * @author hatkirby
 */
public enum Direction {
    North,
    East,
    South,
    West;
    
    /**
     * Returns the direction opposite from the current one
     * @return A Direction representing the opposite direction
     */
    public Direction oppositeDirection()
    {
        switch (this)
        {
            case North: return Direction.South;
            case West: return Direction.East;
            case South: return Direction.North;
            case East: return Direction.West;
        }
        
        return null;
    }
}

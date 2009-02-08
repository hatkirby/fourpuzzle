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
    North
    {
        public Direction opposite()
        {
            return Direction.South;
        }
        
        public Direction left()
        {
            return Direction.West;
        }
        
        public Direction right()
        {
            return Direction.East;
        }
    },
    East
    {
        public Direction opposite()
        {
            return Direction.West;
        }
        
        public Direction left()
        {
            return Direction.North;
        }
        
        public Direction right()
        {
            return Direction.South;
        }
    },
    South
    {
        public Direction opposite()
        {
            return Direction.North;
        }
        
        public Direction left()
        {
            return Direction.East;
        }
        
        public Direction right()
        {
            return Direction.West;
        }
    },
    West
    {
        public Direction opposite()
        {
            return Direction.East;
        }
        
        public Direction left()
        {
            return Direction.South;
        }
        
        public Direction right()
        {
            return Direction.North;
        }
    };
    
    /**
     * Returns the direction opposite from the current one
     * @return A Direction representing the wanted direction
     */
    public abstract Direction opposite();
    
    /**
     * Returns the direction counterclockwise from the current one
     * @return A Direction representing the wanted direction
     */
    public abstract Direction left();
    
    /**
     * Returns the direction clockwise from the current one
     * @return A Direction representing the wanted direction
     */
    public abstract Direction right();
}

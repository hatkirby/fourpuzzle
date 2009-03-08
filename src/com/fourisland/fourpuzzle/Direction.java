/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import java.awt.Point;

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
        
        public Point to(Point original)
        {
            Point temp = new Point(original);
            temp.translate(0, -1);
            
            return temp;
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
        
        public Point to(Point original)
        {
            Point temp = new Point(original);
            temp.translate(1, 0);
            
            return temp;
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
        
        public Point to(Point original)
        {
            Point temp = new Point(original);
            temp.translate(0, 1);
            
            return temp;
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
        
        public Point to(Point original)
        {
            Point temp = new Point(original);
            temp.translate(-1, 0);
            
            return temp;
        }
    };
    
    /**
     * Returns the direction opposite from the current one
     * 
     * @return A Direction representing the wanted direction
     */
    public abstract Direction opposite();
    
    /**
     * Returns the direction counterclockwise from the current one
     * 
     * @return A Direction representing the wanted direction
     */
    public abstract Direction left();
    
    /**
     * Returns the direction clockwise from the current one
     * 
     * @return A Direction representing the wanted direction
     */
    public abstract Direction right();
    
    /**
     * Returns a point one unit in the specified direction away from the
     * specified point
     * 
     * @param original The point to move away from
     * @return A Point representing a space one unit away from the original
     */
    public abstract Point to(Point original);
    
}

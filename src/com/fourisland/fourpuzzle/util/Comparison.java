/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

/**
 *
 * @author hatkirby
 */
public enum Comparison {
    /**
     * A comparision that returns true if param a is less than param b.
     */
    Less
    {
        public boolean compare(int a, int b)
        {
            return (a < b);
        }
    },
    /**
     * A comparision that returns true if param a is greater than param b.
     */
    Greater
    {
        public boolean compare(int a, int b)
        {
            return (a > b);
        }
    },
    /**
     * A comparision that returns true if param a is equal to param b.
     */
    Equal
    {
        public boolean compare(int a, int b)
        {
            return (a == b);
        }
    },
    /**
     * A comparision that returns true if param a is greater than or equal to
     * param b.
     */
    Above
    {
        public boolean compare(int a, int b)
        {
            return (a >= b);
        }
    },
    /**
     * A comparision that returns true if param a is less than or equal to
     * param b.
     */
    Below
    {
        public boolean compare(int a, int b)
        {
            return (a <= b);
        }
    },
    /**
     * A comparison that returns true if param a is not equal to param b.
     */
    Inequal
    {
        public boolean compare(int a, int b)
        {
            return (a != b);
        }
    };
    
    /**
     * Compares both parameters
     * 
     * @param a The first number to compare
     * @param b The second number to compare
     * @return true if <code>a COMPARE b</code> is true, otherwise false
     */
    public abstract boolean compare(int a, int b);

}

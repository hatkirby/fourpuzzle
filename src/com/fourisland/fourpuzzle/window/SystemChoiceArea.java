/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

/**
 *
 * @author hatkirby
 */
public enum SystemChoiceArea
{
    Top(47, 0, 1, 9),
    TopRight(56, 0, 8, 9),
    Right(56, 15, 8, 1),
    BottomRight(56, 25, 8, 9),
    Bottom(47, 24, 1, 9),
    BottomLeft(32, 24, 11, 9),
    Left(32, 15, 11, 1),
    TopLeft(32, 0, 8, 8),
    UpArrow(43, 9, 10, 6),
    DownArrow(43, 17, 10, 6);

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private SystemChoiceArea(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
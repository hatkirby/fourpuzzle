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
    Top(47, 0, 1, 4),
    TopRight(56, 0, 8, 8),
    Right(60, 15, 4, 1),
    BottomRight(56, 24, 8, 8),
    Bottom(47, 28, 1, 4),
    BottomLeft(32, 24, 8, 8),
    Left(32, 15, 4, 1),
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
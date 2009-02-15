/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class KeyInput
{
    private boolean ctrl = false;
    private boolean alt = false;
    private boolean shift = false;
    private int key = 0;
    public void keyInput(KeyEvent ev)
    {
        ctrl = ev.isControlDown();
        alt = ev.isAltDown();
        shift = ev.isShiftDown();
        key = ev.getKeyCode();
    }

    public void letGo()
    {
        ctrl = false;
        alt = false;
        shift = false;
        key = 0;
    }

    public boolean isCtrlDown()
    {
        return ctrl;
    }

    public boolean isAltDown()
    {
        return alt;
    }

    public boolean isShiftDown()
    {
        return shift;
    }

    public boolean isKeyDown()
    {
        return (key == 0 ? false : true);
    }

    public int getKey()
    {
        return key;
    }
}
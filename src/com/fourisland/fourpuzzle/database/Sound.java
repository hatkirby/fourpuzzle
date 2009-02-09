/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

/**
 *
 * @author hatkirby
 */
public enum Sound {
    MoveCursor("Cursor1"),
    Selection("Decision2");
    
    private String value;
    private Sound(String value)
    {
        this.value = value;
    }
    
    String getValue()
    {
        return value;
    }
    
    void setValue(String value)
    {
        this.value = value;
    }
}

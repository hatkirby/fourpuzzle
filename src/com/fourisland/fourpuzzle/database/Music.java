/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

/**
 *
 * @author hatkirby
 */
public enum Music {
    Title("Opening1"),
    GameOver("GameOver");
    
    private String value;
    private Music(String value)
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

/**
 *
 * @author hatkirby
 */
public enum Vocabulary {
    Title("Untitled Game"),
    NewGame("New Game"),
    LoadGame("Load Game"),
    EndGame("End Game");
    
    private String value;
    private Vocabulary(String value)
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.BlankEventGraphic;
import com.fourisland.fourpuzzle.gamestate.mapview.event.graphic.EventGraphic;

/**
 *
 * @author hatkirby
 */
public class GameCharacter {
    
    public GameCharacter(String name)
    {
        this.name = name;
    }
    
    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    private int level = 1;
    public int getLevel() {
        return level;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    private boolean inParty = false;
    public boolean isInParty()
    {
        return inParty;
    }
    public void setInParty(boolean inParty)
    {
        this.inParty = inParty;
    }
    
    private EventGraphic graphic = new BlankEventGraphic();
    public EventGraphic getGraphic()
    {
        return graphic;
    }
    public void setGraphic(EventGraphic graphic)
    {
        this.graphic = graphic;
    }
    
}

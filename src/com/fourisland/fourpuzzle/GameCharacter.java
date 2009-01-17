/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

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
    
    private String graphic = "blank";
    public String getGraphic()
    {
        return graphic;
    }
    public void setGraphic(String graphic)
    {
        this.graphic = graphic;
    }

    private int graphicOffset = 0;
    public int getGraphicOffset()
    {
        return graphicOffset;
    }
    public void setGraphicOffset(int graphicOffset)
    {
        this.graphicOffset = graphicOffset;
    }
    
}

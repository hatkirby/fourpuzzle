/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.gamestate.mapview.event.HeroEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author hatkirby
 */
public class SaveFile implements Serializable {
    
    /**
     * Creates a new SaveFile
     * @throws java.lang.CloneNotSupportedException
     */
    public SaveFile() throws CloneNotSupportedException
    {
        switches = new HashMap<String, Boolean>();
        party = GameCharacters.createParty();
        variables = new HashMap<String, Integer>();
        currentMap = new String();
        hero = new HeroEvent();
    }
    
    /**
     * Loads a SaveFile
     * @param file The ID of the SaveFile to load
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public SaveFile(int file) throws IOException, ClassNotFoundException
    {
        InputStream is = PuzzleApplication.INSTANCE.getContext().getLocalStorage().openInputFile("Save" + file + ".sav");
        ObjectInputStream ois = new ObjectInputStream(is);
        SaveFile temp = (SaveFile) ois.readObject();
        
        switches = temp.getSwitches();
        variables = temp.getVariables();
        party = temp.getParty();
        currentMap = temp.getCurrentMap();
        
        ois.close();
    }
    
    public void saveGame(int file) throws IOException
    {
        OutputStream os = PuzzleApplication.INSTANCE.getContext().getLocalStorage().openOutputFile("Save" + file + ".sav");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(this);
        oos.close();
    }

    private HashMap<String, Boolean> switches;
    public HashMap<String, Boolean> getSwitches() {
        return switches;
    }
    
    private GameCharacters party;
    public GameCharacters getParty() {
        return party;
    }
    
    private HashMap<String, Integer> variables;
    public HashMap<String, Integer> getVariables() {
        return variables;
    }
    
    private String currentMap;
    public String getCurrentMap()
    {
        return currentMap;
    }
    public void setCurrentMap(String currentMap)
    {
        this.currentMap = currentMap;
    }
    
    private HeroEvent hero;
    public HeroEvent getHero()
    {
        return hero;
    }
    public void setHero(HeroEvent hero)
    {
        this.hero = hero;
    }
}

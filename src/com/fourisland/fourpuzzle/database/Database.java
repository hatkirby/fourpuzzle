/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.database;

import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import com.fourisland.fourpuzzle.transition.Transition;
import java.util.HashMap;

/**
 *
 * @author hatkirby
 */
public class Database {
    
    public static String getVocab(Vocabulary key)
    {
        return key.getValue();
    }
    
    /**
     * Sets a Vocabulary definition
     * 
     * FourPuzzle uses pre-defined Vocabulary strings in many places such as
     * for the Title of the game, menu options and more. There are default
     * values for all of these definitions, but you can change them if you wish,
     * using this function.
     * 
     * @param key The Vocabulary to set
     * @param value The value to set the Vocabulary to
     */
    public static void setVocab(Vocabulary key, String value)
    {
        key.setValue(value);
    }
    
    /**
     * Adds a Hero to the party
     * 
     * When making a game, you need characters, at least one playable character.
     * You have to create your characters and use this function to add them to
     * the central list of playable characters, or your game will not work.
     * There are no default characters, so this is a must-do.
     * 
     * @param hero The Hero to add
     */
    public static void addHero(GameCharacter hero)
    {
        GameCharacters.getDefaultParty().add(hero);
    }
    
    public static GameCharacters createParty()
    {
        return GameCharacters.createParty();
    }
    
    public static String getMusic(Music key)
    {
        return key.getValue();
    }
    
    /**
     * Change a default Music value
     * 
     * In certain places of your game, such as the Title Screen and Game Over
     * screen, background music plays. You can tell FourPuzzle what Music to
     * play during these instances with this function. There are default values
     * for all instances, though.
     * 
     * @param key The Music instance you wish to change
     * @param value The name of the Music file you wish to change it to
     */
    public static void setMusic(Music key, String value)
    {
        key.setValue(value);
    }
    
    public static Transition getTransition(Transitions key)
    {
        return key.getValue().copy();
    }
    
    /**
     * Set a default Transition
     * 
     * In certain places, a Transition may be displayed that you did not
     * directly incur. These are default transitions, but they can be changed
     * if you wish by using this function.
     * 
     * Warning, all Transition instances have a required type of transition,
     * whether it be In or Out. If you provide the wrong type of Transition for
     * a certain instance, your game will not run.
     * 
     * @param key The transition to change
     * @param value The transition to change it to
     */
    public static void setTransition(Transitions key, Transition value)
    {
        key.setValue(value);
    }
    
    public static String getSound(Sound key)
    {
        return key.getValue();
    }
    
    /**
     * Change a default sound effect
     * 
     * Sound Effects are used in many places of the game. The default sound
     * effects for certain situations can be changed using this function.
     * 
     * @param key The Sound instance to change
     * @param value The name of the Sound file to change it to
     */
    public static void setSound(Sound key, String value)
    {
        key.setValue(value);
    }
    
    private static java.util.Map<String, Map> maps = new HashMap<String, Map>();
    public static void addMap(String key, Map value)
    {
        maps.put(key, value);
    }
    
    public static Map getMap(String key)
    {
        return maps.get(key);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.transition.SquareTransition;
import com.fourisland.fourpuzzle.transition.Transition;
import com.fourisland.fourpuzzle.transition.TransitionDirection;
import java.util.HashMap;

/**
 *
 * @author hatkirby
 */
public class Database {
    
    private static HashMap<String, String> vocabulary = new HashMap<String, String>();
    private static GameCharacters heros = GameCharacters.getDefaultParty();
    private static HashMap<String, String> music = new HashMap<String, String>();
    private static HashMap<String, Transition> transitions = new HashMap<String, Transition>();
    
    static {
        loadDefaultVocabulary();
        loadDefaultMusic();
        loadDefaultTransitions();
    }

    /* Vocabulary */

    private static void loadDefaultVocabulary()
    {
        /* Global */
        vocabulary.put("Title", "Untitled Game");
        
        /* TitleScreen */
        vocabulary.put("NewGame", "New Game");
        vocabulary.put("LoadGame", "Load Game");
        vocabulary.put("EndGame", "End");
    }
    
    public static String getVocab(String key)
    {
        return vocabulary.get(key);
    }
    
    public static void setVocab(String key, String value)
    {
        vocabulary.put(key, value);
    }
    
    /* Heros */
    
    public static void addHero(GameCharacter hero)
    {
        heros.add(hero);
    }
    
    public static GameCharacters createParty()
    {
        return GameCharacters.createParty();
    }
    
    /* Music */
    
    public static void loadDefaultMusic()
    {
        music.put("Title", "Opening");
        music.put("GameOver", "GameOver");
    }
    
    public static String getMusic(String key)
    {
        return music.get(key);
    }
    
    public static void setMusic(String key, String value)
    {
        music.put(key, value);
    }
    
    /* Transitions */
    
    public static void loadDefaultTransitions()
    {
        transitions.put("MapExit", new SquareTransition(TransitionDirection.Out));
        transitions.put("MapEnter", new SquareTransition(TransitionDirection.In));
    }
    
    public static Transition getTransition(String key)
    {
        return transitions.get(key).copy();
    }
    
    public static void setTransition(String key, Transition value)
    {
        transitions.put(key, value);
    }

}

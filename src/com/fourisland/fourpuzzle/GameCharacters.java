/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.gamestate.GameOverGameState;
import java.util.ArrayList;

/**
 *
 * @author hatkirby
 */
public class GameCharacters implements Cloneable {
    
    private GameCharacters()
    {
    }
    
    public static GameCharacters INSTANCE = new GameCharacters();

    public void add(GameCharacter e)
    {
        characters.add(e);
    }

    public GameCharacter getLeader() throws Exception
    {
        int i = 0;
        for (i=0;i<characters.size();i++)
        {
            if (characters.get(i).isInParty())
            {
                return characters.get(i);
            }
        }
        
        Game.setGameState(new GameOverGameState());
        throw new NoCharactersInPartyException();
    }
    public GameCharacters newInstance() throws CloneNotSupportedException
    {
        return (GameCharacters) clone();
    }

    private ArrayList<GameCharacter> characters = new ArrayList<GameCharacter>();
    
    public boolean exists(String heroName) {
        int i=0;
        for (i=0;i<characters.size();i++)
        {
            if (characters.get(i).getName().equals(heroName))
            {
                return true;
            }
        }
        
        return false;
    }

    public GameCharacter get(Integer get) {
        return characters.get(get);
    }

    public GameCharacter get(String heroName) throws NullPointerException {
        int i=0;
        for (i=0;i<characters.size();i++)
        {
            if (characters.get(i).getName().equals(heroName))
            {
                return characters.get(i);
            }
        }
        
        throw new NullPointerException("Could not find character \"" + heroName + "\"");
    }

}

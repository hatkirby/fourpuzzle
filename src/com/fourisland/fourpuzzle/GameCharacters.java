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
public class GameCharacters extends ArrayList<GameCharacter>
{
    private GameCharacters() {}
    
    private static GameCharacters INSTANCE = new GameCharacters();
    public static GameCharacters getDefaultParty()
    {
        return INSTANCE;
    }

    public static GameCharacters createParty()
    {
        GameCharacters temp = new GameCharacters();
        temp.addAll(INSTANCE);
        
        return temp;
    }

    public GameCharacter getLeader() throws Exception
    {
        for (GameCharacter chara : this)
        {
            if (chara.isInParty())
            {
                return chara;
            }
        }
        
        Game.setGameState(new GameOverGameState());
        return null;
    }
    
    public boolean exists(String heroName) {
        for (GameCharacter chara : this)
        {
            if (chara.getName().equals(heroName))
            {
                return true;
            }
        }
        
        return false;
    }

    public GameCharacter get(String heroName) throws NullPointerException {
        for (GameCharacter chara : this)
        {
            if (chara.getName().equals(heroName))
            {
                return chara;
            }
        }
        
        throw new NullPointerException("Could not find character \"" + heroName + "\"");
    }

}

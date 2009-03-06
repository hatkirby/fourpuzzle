/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.menu;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.KeyboardInput;
import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.database.Transitions;
import com.fourisland.fourpuzzle.database.Vocabulary;
import com.fourisland.fourpuzzle.gamestate.TitleScreenGameState;
import com.fourisland.fourpuzzle.window.ChoiceWindow;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 *
 * @author hatkirby
 */
public class MenuEMS implements EscapeMenuState {
    
    MenuGameState parent;
    ChoiceWindow cw;

    public void initalize(MenuGameState mgs)
    {
        parent = mgs;
        cw = new ChoiceWindow.Builder(Arrays.asList(Database.getVocab(Vocabulary.EscapeMenuItems), Database.getVocab(Vocabulary.EscapeMenuEquipment), Database.getVocab(Vocabulary.EscapeMenuMagic), Database.getVocab(Vocabulary.EscapeMenuSave), Database.getVocab(Vocabulary.EscapeMenuQuit)), ChoiceWindow.ChoiceWindowLocation.AbsoluteTopLeft)
                .width(Game.WIDTH/5)
                .build();
        Display.registerRenderable(cw);
        KeyboardInput.registerInputable(cw);
    }

    public void deinitalize()
    {
        Display.unregisterRenderable(cw);
        KeyboardInput.unregisterInputable(cw);
    }

    public void tick()
    {
        if (cw.hasInput())
        {
            String value = cw.getSelected();
            if (value.equals(Database.getVocab(Vocabulary.EscapeMenuQuit)))
            {
                try {
                    Display.transition(Database.getTransition(Transitions.Generic), new TitleScreenGameState(), true);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } else if (value.equals(Database.getVocab(Vocabulary.EscapeMenuSave)))
            {
                parent.setEMS(new SaveEMS());
            }
        }
    }

    public void render(Graphics2D g)
    {
        g.setBackground(Color.BLACK);
        g.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
    }

    public void processInput(KeyInput key)
    {
        if (key.getKey() == KeyEvent.VK_ESCAPE)
        {
            try {
                Display.transition(Database.getTransition(Transitions.Generic), parent.mapView, true);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

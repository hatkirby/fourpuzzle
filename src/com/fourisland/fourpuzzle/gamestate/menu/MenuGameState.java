/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.menu;

import com.fourisland.fourpuzzle.Audio;
import com.fourisland.fourpuzzle.gamestate.*;
import com.fourisland.fourpuzzle.KeyInput;
import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.database.Sound;
import com.fourisland.fourpuzzle.gamestate.mapview.MapViewGameState;
import java.awt.Graphics2D;

/**
 *
 * @author hatkirby
 */
public class MenuGameState implements GameState {
    
    EscapeMenuState ems;
    MapViewGameState mapView;

    public MenuGameState(MapViewGameState mapView)
    {
        this.mapView = mapView;
    }

    public void initalize()
    {
        Audio.playSound(Database.getSound(Sound.Selection));
        setEMS(new MenuEMS());
    }

    public void deinitalize()
    {
        this.ems.deinitalize();
    }

    public void doGameCycle()
    {
        ems.tick();
    }

    public void render(Graphics2D g)
    {
        ems.render(g);
    }

    public void processInput(KeyInput key)
    {
        ems.processInput(key);
    }
    
    public void setEMS(EscapeMenuState ems)
    {
        if (this.ems != null)
        {
            this.ems.deinitalize();
        }
        
        this.ems = ems;
        this.ems.initalize(this);
    }

}

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.SaveFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hatkirby
 */
public class SwitchPreconditionTest {

    String switchName = "TestSwitch";
    SwitchPrecondition sp;

    @Before
    public void setUp()
    {
        Game.setSaveFile(new SaveFile());
        sp = new SwitchPrecondition(switchName);
    }
    
    @Test
    public void testUnsetSwitch()
    {
        Game.getSaveFile().getSwitches().put(switchName, false);
        assertFalse(sp.match());
    }
    
    @Test
    public void testSetSwitch()
    {
        Game.getSaveFile().getSwitches().put(switchName, true);
        assertTrue(sp.match());
    }
    
}
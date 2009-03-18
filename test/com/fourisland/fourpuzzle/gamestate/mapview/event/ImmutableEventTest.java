/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event;

import com.fourisland.fourpuzzle.Direction;
import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.gamestate.mapview.Map;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hatkirby
 */
public class ImmutableEventTest {

    ImmutableEvent ie;
    Event ev;

    @Before
    public void setUp()
    {
        LayerEvent temp = new LayerEvent(1, 2, "Test");
        temp.addEvent(new PossibleEvent());
        
        ev = temp;
        ie = new ImmutableEvent(ev);
    }
    
    @Test
    public void testLiveness()
    {
        // First, modify the internal event
        ev.setAnimationStep(2);
        ev.setDirection(Direction.East);
        ev.setLocation(1, 3);
        ev.setMoveSpeed(MoveSpeed.Slower2);
        
        // Then, ensure that the immutable event reflects these changes
        assertEquals(ie.getAnimationStep(), 2);
        assertEquals(ie.getDirection(), Direction.East);
        assertEquals(ie.getLocation(), new Point(1, 3));
        assertEquals(ie.getMoveSpeed(), MoveSpeed.Slower2);
    }

}
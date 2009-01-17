/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import com.fourisland.fourpuzzle.gamestate.mapview.event.PossibleEvent;

/**
 *
 * @author hatkirby
 */
public class Functions {

    public static boolean canTurn(PossibleEvent ev) throws Exception
    {
        switch (ev.getAnimation())
        {
            case CommonWithoutStepping: return true;
            case CommonWithStepping: return true;
            case WithoutStepping: return true;
            case FixedGraphic: return false;
            case TurnLeft: return false;
            case TurnRight: return false;
        }
        
        return false;
    }

}

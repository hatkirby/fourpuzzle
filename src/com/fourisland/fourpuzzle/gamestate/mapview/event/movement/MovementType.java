/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.movement;

import com.fourisland.fourpuzzle.Direction;

/**
 *
 * @author hatkirby
 */
public interface MovementType {
    
    // TODO Rename the following method to getNextDirection
    public Direction startMoving();
    
}
    
/*
    CycleUpDown
    CycleLeftRight
    StepTowardHero
    StepAwayFromHero
*/
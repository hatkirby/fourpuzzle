/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview.event.precondition;

/**
 * A Precondition is an object that helps <b>LayerEvent</b> to determine which
 * of its (possibly many) <b>PossibleEvent</b>s is active. PossibleEvents can
 * have Preconditions attached to them and when all Preconditions are fulfilled
 * (determined by the <code>true</code> return value from <b>match()</b>) and
 * there are not fulfilled PossibleEvents later in the queue, said PossibleEvent
 * will be active.
 *
 * @author hatkirby
 */
public interface Precondition {

    public boolean match();
    
}

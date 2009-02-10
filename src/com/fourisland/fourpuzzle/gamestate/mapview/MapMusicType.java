/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

/**
 *
 * @author hatkirby
 */
public enum MapMusicType {
    /**
     * NoMusic represents the instance where no Music should be played
     */
    NoMusic,
    /**
     * NoChange represents the instance where the currently playing Music (or
     * silence) should be kept
     */
    NoChange,
    /**
     * Specified represents the instance where a specified Music file should be
     * played
     */
    Specified
}

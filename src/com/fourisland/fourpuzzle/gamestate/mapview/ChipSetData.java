/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.*;

/**
 *
 * @author hatkirby
 */
public class ChipSetData {
    
    private String terrain;
    private Layer layer;
    private boolean enableNorth;
    private boolean enableWest;
    private boolean enableSouth;
    private boolean enableEast;

    public ChipSetData(String terrain, Layer layer)
    {
        this.terrain = terrain;
        this.layer = layer;
        
        if (layer == Layer.Middle)
        {
            enableNorth = false;
            enableWest = false;
            enableSouth = false;
            enableEast = false;
        } else {
            enableNorth = true;
            enableWest = true;
            enableSouth = true;
            enableEast = true;
        }
    }
    
    public ChipSetData(String terrain, boolean enableNorth, boolean enableWest, boolean enableSouth, boolean enableEast)
    {
        this.terrain = terrain;
        
        if (!enableNorth && !enableWest && !enableSouth && !enableEast)
        {
            layer = Layer.Middle;
        } else {
            layer = Layer.Below;
        }
        
        this.enableNorth = enableNorth;
        this.enableWest = enableWest;
        this.enableSouth = enableSouth;
        this.enableEast = enableEast;
    }

    public String getTerrain() {
        return terrain;
    }

    public Layer getLayer() {
        return layer;
    }

    public boolean isEnableNorth() {
        return enableNorth;
    }

    public boolean isEnableWest() {
        return enableWest;
    }

    public boolean isEnableSouth() {
        return enableSouth;
    }

    public boolean isEnableEast() {
        return enableEast;
    }
    
}

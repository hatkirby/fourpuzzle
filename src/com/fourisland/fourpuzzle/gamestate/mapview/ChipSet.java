/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import com.fourisland.fourpuzzle.util.ResourceNotFoundException;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hatkirby
 */
public abstract class ChipSet {
    
    public abstract void initalize();

    private BufferedImage chipSetImage;
    public BufferedImage getImage(int offset)
    {
        int sx = (offset % 8) * 16;
        int sy = (offset / 8) * 16;
        
        return chipSetImage.getSubimage(sx, sy, 16, 16);
    }

    public static ChipSet getChipSet(String chipSet)
    {
        try {
            Class chipSetClass = Class.forName(PuzzleApplication.INSTANCE.getGamePackage() + ".gamedata.chipset." + chipSet);
            Object chipSetObject = chipSetClass.newInstance();
            ChipSet temp = (ChipSet) chipSetObject;
            temp.initalize();
            temp.chipSetImage = ObjectLoader.getImage("ChipSet", chipSet);

            return temp;
        } catch (InstantiationException ex) {
            Logger.getLogger(ChipSet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ChipSet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            throw new ResourceNotFoundException("ChipSetData", chipSet);
        }
        
        return null;
    }

    private HashMap<Integer,ChipSetData> chipSetData = new HashMap<Integer,ChipSetData>(); //162
    public HashMap<Integer,ChipSetData> getChipSetData()
    {
        return chipSetData;
    }

}

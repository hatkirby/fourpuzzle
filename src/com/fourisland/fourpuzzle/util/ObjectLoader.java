/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

import com.fourisland.fourpuzzle.PuzzleApplication;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author hatkirby
 */
public class ObjectLoader {
    
    private static HashMap<String,Object> objectCache = new HashMap<String,Object>();
    
    public static BufferedImage getImage(String type, String name)
    {
        if (!objectCache.containsKey(type + "/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = rm.getResourcesDir() + type.toLowerCase() + "/" + name + ".png";
            InputStream str = rm.getClassLoader().getResourceAsStream(filename);
            
            if (str == null)
            {
                throw new ResourceNotFoundException(type, name);
            }
            
            BufferedImage bImg = null;
            try {
                bImg = ImageIO.read(str);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            addToObjectCache(type,name,bImg);
        }
        
        return (BufferedImage) objectCache.get(type + "/" + name);
    }
    
    public static BufferedImage getImage(String type, String name, int transparencyColor)
    {
        if (!objectCache.containsKey(type + "/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = rm.getResourcesDir() + type.toLowerCase() + "/" + name + ".png";
            InputStream str = rm.getClassLoader().getResourceAsStream(filename);
            
            if (str == null)
            {
                throw new ResourceNotFoundException(type, name);
            }

            BufferedImage bImg = null;
            try {
                bImg = ImageIO.read(str);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            bImg.createGraphics().drawImage(bImg, 0, 0, new Color(transparencyColor, true), null);
            
            addToObjectCache(type,name,bImg);
        }
        
        return (BufferedImage) objectCache.get(type + "/" + name);
    }
    
    public static void addToObjectCache(String type, String name, Object object)
    {
        if (objectCache.size() >= 100)
        {
            objectCache.clear();
        }
        
        objectCache.put(type + "/" + name, object);
    }
    
    public static Sequence getMusic(String name)
    {
        if (!objectCache.containsKey("Music/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = rm.getResourcesDir() + "music/" + name + ".mid";
            InputStream str = rm.getClassLoader().getResourceAsStream(filename);
            if (str == null)
            {
                throw new ResourceNotFoundException("Music", name);
            }
            
            Sequence seq = null;
            try {
                seq = MidiSystem.getSequence(str);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            addToObjectCache("Music", name, seq);
        }
        
        return (Sequence) objectCache.get("Music/" + name);
    }
    
}

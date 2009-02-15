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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author hatkirby
 */
public class ObjectLoader {
    
    private static Map<String,BufferedImage> imageCache = new HashMap<String,BufferedImage>();
    private static Map<String,Sequence> musicCache = new HashMap<String,Sequence>();
    private static Map<String,Clip> soundCache = new HashMap<String,Clip>();
    
    static
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                for (Entry<String, Clip> o : soundCache.entrySet())
                {
                        o.getValue().close();
                }
            }
        }));
    }
    
    public static BufferedImage getImage(String type, String name)
    {
        if (!imageCache.containsKey(type + "/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = getFilename(type, name, "png");
            InputStream str = rm.getClassLoader().getResourceAsStream(filename);
            BufferedImage bImg = null;
            try {
                bImg = ImageIO.read(str);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            addToObjectCache(imageCache, type + "/" + name, bImg);
        }
        
        return imageCache.get(type + "/" + name);
    }
    
    public static BufferedImage getImage(String type, String name, int transparencyColor)
    {
        if (!imageCache.containsKey(type + "/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = getFilename(type, name, "png");
            InputStream str = rm.getClassLoader().getResourceAsStream(filename);
            BufferedImage bImg = null;
            try {
                bImg = ImageIO.read(str);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            bImg.createGraphics().drawImage(bImg, 0, 0, new Color(transparencyColor, true), null);

            addToObjectCache(imageCache, type + "/" + name, bImg);
        }
        
        return imageCache.get(type + "/" + name);
    }
    
    public static <T> void addToObjectCache(Map<String, T> cacheMap, String name, T object)
    {
        if (cacheMap.size() >= 100)
        {
            cacheMap.clear();
        }
        
        cacheMap.put(name, object);
    }
    
    public static Sequence getMusic(String name)
    {
        if (!musicCache.containsKey(name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = getFilename("Music", name, "mid");
            InputStream str = rm.getClassLoader().getResourceAsStream(filename);
            Sequence seq = null;
            try {
                seq = MidiSystem.getSequence(str);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            MidiParser mp = new MidiParser(seq);
            try {
                seq = mp.parse();
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            addToObjectCache(musicCache, name, seq);
        }
        
        return musicCache.get(name);
    }
    
    public static Clip getSound(String name)
    {
        if (!soundCache.containsKey("Sound/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = getFilename("Sound", name, "wav");
            InputStream soundFile = rm.getClassLoader().getResourceAsStream(filename);
            AudioInputStream ais = null;
            try {
                ais = AudioSystem.getAudioInputStream(soundFile);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            AudioFormat af = ais.getFormat();
            Clip line = null;
            DataLine.Info info = new DataLine.Info(Clip.class, af);
            try {
                line = (Clip) AudioSystem.getLine(info);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                line.open(ais);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ObjectLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            addToObjectCache(soundCache, name, line);
        }
        
        return soundCache.get(name);
    }
    
    public static String getFilename(String type, String name, String ex)
    {
        ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
        String f = rm.getResourcesDir() + type.toLowerCase() + "/" + name + "." + ex;
        URL fu = rm.getClassLoader().getResource(f);

        if (fu == null)
        {
            f = "com/fourisland/fourpuzzle/resources/" + type.toLowerCase() + "/" + name + "." + ex;
            fu = rm.getClassLoader().getResource(f);

            if (fu == null)
            {
                throw new ResourceNotFoundException(type, name);
            }
        }
        
        return f;
    }
    
}

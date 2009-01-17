/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

//import com.alienfactory.javamappy.loader.MapLoader;
import com.fourisland.fourpuzzle.PuzzleApplication;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author hatkirby
 */
public class ObjectLoader {
    
    private static HashMap<String,Object> objectCache = new HashMap<String,Object>();
    
    public static BufferedImage getImage(String type, String name) throws Exception
    {
        if (!objectCache.containsKey(type + "/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = rm.getResourcesDir() + type.toLowerCase() + "/" + name + ".png";
            BufferedImage bImg = ImageIO.read(rm.getClassLoader().getResourceAsStream(filename));
            
            addToObjectCache(type,name,bImg);
        }
        
        return (BufferedImage) objectCache.get(type + "/" + name);
    }
    
    public static BufferedImage getImage(String type, String name, int transparencyColor) throws Exception
    {
        if (!objectCache.containsKey(type + "/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = rm.getResourcesDir() + type + "/" + name + ".png";
            BufferedImage bImg = ImageIO.read(rm.getClassLoader().getResourceAsStream(filename));
            bImg = new BufferedImage(bImg.getWidth(), bImg.getHeight(), BufferedImage.TYPE_INT_RGB);
            Image image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(bImg.getSource(), new TransparentImageFilter(transparencyColor)));
            bImg.createGraphics().drawImage(image, 0, 0, null);            
            
            addToObjectCache(type,name,bImg);
        }
        
        return (BufferedImage) objectCache.get(type + "/" + name);
    }
    
    /*public static com.alienfactory.javamappy.Map getMap(String name) throws Exception
    {
        ResourceMap rm = PuzzleApplication.getInstance().getContext().getResourceManager().getResourceMap();
        String filename = rm.getResourcesDir() + "mapdata/" + name + ".fmp";
        
        //com.alienfactory.javamappy.Map map = MapLoader.loadMap(rm.getClassLoader().getResourceAsStream(filename));
        return map;
    }*/
    
    public static void addToObjectCache(String type, String name, Object object) throws Exception
    {
        if (objectCache.size() >= 100)
        {
            objectCache.clear();
        }
        
        objectCache.put(type + "/" + name, object);
    }
    
    public static Sequence getMusic(String name) throws Exception
    {
        if (!objectCache.containsKey("Music/" + name))
        {
            ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
            String filename = rm.getResourcesDir() + "music/" + name + ".mid";
            Sequence seq = MidiSystem.getSequence(rm.getClassLoader().getResourceAsStream(filename));
            
            addToObjectCache("Music", name, seq);
        }
        
        return (Sequence) objectCache.get("Music/" + name);
    }
    
}

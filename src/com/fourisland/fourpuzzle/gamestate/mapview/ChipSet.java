/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.gamestate.mapview;

import com.fourisland.fourpuzzle.Layer;
import com.fourisland.fourpuzzle.PuzzleApplication;
import com.fourisland.fourpuzzle.util.ObjectLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.jdesktop.application.ResourceMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hatkirby
 */
public class ChipSet {
    
    private ChipSet() {}

    private BufferedImage chipSetImage;
    public BufferedImage getImage(int offset)
    {
        int sx = (offset % 30) * 16;
        int sy = (offset / 30) * 16;
        
        return chipSetImage.getSubimage(sx, sy, 16, 16);
    }
    
    private HashMap<Integer,ChipSetData> chipSetData = new HashMap<Integer,ChipSetData>(); //162
    public HashMap<Integer,ChipSetData> getChipSetData()
    {
        return chipSetData;
    }

    public static void initalize()
    {
        ResourceMap rm = PuzzleApplication.INSTANCE.getContext().getResourceManager().getResourceMap();
        File folder = new File(new File(rm.getResourcesDir()).getParent() + "/gamedata/chipset/");
        URL path = rm.getClassLoader().getResource(folder.toString());
        File[] files = new File(path.getPath().replace("%20", " ")).listFiles();
        for (File cs : files)
        {
            try {
                SAXParserFactory.newInstance().newSAXParser().parse(cs, new ChipSetDefaultHandler());
            } catch (SAXException ex) {
                Logger.getLogger(ChipSet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChipSet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(ChipSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void initalize(String name, int trans, HashMap<Integer, ChipSetData> lower)
    {
        ChipSet temp = new ChipSet();
        temp.chipSetData = lower;
        temp.chipSetImage = ObjectLoader.getImage("ChipSet", name, trans);
        
        chipSets.put(name, temp);
    }
    
    private static HashMap<String, ChipSet> chipSets = new HashMap<String, ChipSet>();
    public static ChipSet getChipSet(String name)
    {
        return chipSets.get(name);
    }

}

class ChipSetDefaultHandler extends DefaultHandler {

    public ChipSetDefaultHandler() {
    }
    
    private String name;
    private int trans;
    
    private int curTile;
    private HashMap<Integer, ChipSetData> lower = new HashMap<Integer,ChipSetData>();
    
    private String terrain;
    private Layer layer;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("tileset"))
        {
            name = attributes.getValue("name");
        } else if (qName.equals("image"))
        {
            trans = Integer.decode("0x" + attributes.getValue("trans"));
        } else if (qName.equals("tile"))
        {
            curTile = Integer.decode(attributes.getValue("id"));
            lower.put(curTile, new ChipSetData("Grass", Layer.Below));
        } else if (qName.equals("property"))
        {
            if (attributes.getValue("name").equals("terrain"))
            {
                terrain = attributes.getValue("value");
            } else if (attributes.getValue("name").equals("layer"))
            {
                layer = Layer.valueOf(attributes.getValue("value"));
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("tile"))
        {
            lower.put(curTile, new ChipSetData(terrain, layer));
        }
    }

    @Override
    public void endDocument() throws SAXException
    {
        ChipSet.initalize(name, trans, lower);
    }

}
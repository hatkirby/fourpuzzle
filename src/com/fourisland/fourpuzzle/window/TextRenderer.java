/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import com.fourisland.fourpuzzle.Display;
import com.fourisland.fourpuzzle.Game;
import com.fourisland.fourpuzzle.util.PauseTimer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author hatkirby
 */
public class TextRenderer {
    
    private static final int SPACER = 4;
    
    private boolean initalized = false;
    private int width;
    private List<LineOfText> messages = new ArrayList<LineOfText>();
    
    public TextRenderer(int width)
    {
        this.width = width;
    }
    
    private boolean useEscapes = false;
    public void setEscapes(boolean use)
    {
        if (initalized == true)
        {
            throw new IllegalStateException("Already initalized");
        }
        
        useEscapes = use;
    }
    
    private int indent = 0;
    public void setIndent(int indent)
    {
        if (initalized == true)
        {
            throw new IllegalStateException("Already initalized");
        }
        
        this.indent = indent;
    }
    
    public void initalizeText(String message)
    {
        if (initalized == true)
        {
            throw new IllegalStateException("Already initalized");
        }
        
        int length = width - SPACER - indent;
        int len = 0;
        LineOfText temp = new LineOfText(message);
        
        if (useEscapes)
        {
            for (int i=0; i<temp.length(); i++)
            {
                if (temp.toString().substring(i, i+1).equals("\\"))
                {
                    for (MessageEscape escape : MessageEscape.values())
                    {
                        if (escape.match(temp.toString().substring(i)))
                        {
                            temp.addEscape(i, escape.getMatch(temp.toString().substring(i)));
                            temp = new LineOfText(escape.removeEscape(temp.toString()), temp.escapes);
                            
                            break;
                        }
                    }
                }
            }
            
            temp = new LineOfText(MessageEscape.removeEscapes(temp.toString()), temp.escapes);
        }
        
        while (!temp.toString().isEmpty())
        {
            while ((Display.getFontMetrics().stringWidth(temp.toString().substring(0, len)) < length) && (len < temp.length()))
            {            
                len++;
            }

            if (len != temp.toString().length())
            {
                while ((!temp.toString().substring(len, len+1).equals(" ")) && (len > 0))
                {
                    len--;
                }
            }
            
            messages.add(temp.part(0, len));
            
            if (len != temp.length())
            {
                temp = temp.part(len+1, temp.length());
            } else {
                break;
            }
            
            len = 0;
        }
        
        initalized = true;
    }
    
    int upTo = Integer.MIN_VALUE;
    int num = 0;
    int lastStart = Integer.MIN_VALUE+1;
    PauseTimer pt = new PauseTimer(0);
    public BufferedImage render(int start)
    {
        if (initalized == false)
        {
            throw new IllegalStateException("TextRenderer must be initalized prior to rendering");
        }
        
        if (lastStart != start)
        {
            num = 0;
            for (int i=start;i<Math.min(messages.size(),start+4);i++)
            {
                num += messages.get(i).length();
            }
            
            lastStart = start;
            upTo = 0;
        }
        
        BufferedImage temp = Display.createCanvas(width, Game.HEIGHT);
        Graphics2D g = temp.createGraphics();
        Display.setFont(g);
        
        int currentColor = 0;
        
        int fh = g.getFontMetrics().getHeight();
        int ty = fh-(SPACER/2);
        int toPrint = upTo;
        int end = Math.min(messages.size(), start+4);
        
        for (int i=start;i<end;i++)
        {
            LineOfText message = messages.get(i);
            int tx = indent;
            g.setPaint(new TexturePaint(SystemGraphic.getTextColor(currentColor), new Rectangle(0, ty, width, fh)));
            
            for (int j=0;j<Math.min(toPrint, message.length());j++)
            {
                boolean breakout = false;
                for (MessageEscapePair escape : message.getEscapes(j))
                {
                    switch (escape.getMessageEscape())
                    {
                        case Color: currentColor = Integer.decode(escape.getMatchResult().group(1));
                                    g.setPaint(new TexturePaint(SystemGraphic.getTextColor(currentColor), new Rectangle(0, ty, width, fh)));
                                    
                                    break;
                                    
                        case Pause: pt.setTimer(10);
                                    message.getEscapes(j).remove(escape);
                        
                                    breakout = true;
                                    break;
                    }
                    
                    if (breakout)
                    {
                        break;
                    }
                }
            
                g.drawString(message.toString().substring(j, j+1), tx, ty);
                
                tx+=(g.getFontMetrics().stringWidth(message.toString().substring(j, j+1)));
            }
            
            ty+=(SPACER+fh);
            
            toPrint -= Math.min(toPrint, message.length());
        }
        
        if (pt.isElapsed() && (upTo < num))
        {
            upTo+=3;
        }
        
        return temp;
    }
    
    public int numLines()
    {
        return messages.size();
    }
    
    public boolean isCascadingDone()
    {
        return (upTo >= num);
    }

}

class LineOfText implements CharSequence
{
    String line;
    Map<Integer, Vector<MessageEscapePair>> escapes = new HashMap<Integer, Vector<MessageEscapePair>>();
    public LineOfText(String line)
    {
        this.line = line;
    }

    public LineOfText(String line, Map<Integer, Vector<MessageEscapePair>> escapes)
    {
        this.line = line;
        
        for (int i=0; i<line.length(); i++)
        {
            if (escapes.containsKey(i))
            {
                for (MessageEscapePair escape : escapes.get(i))
                {
                    addEscape(i, escape);
                }
            }
        }
    }
    
    public void addEscape(int index, MessageEscapePair escape)
    {
        if (!escapes.containsKey(index))
        {
            escapes.put(index, new Vector<MessageEscapePair>());
        }
        
        escapes.get(index).add(escape);
    }
    
    public Vector<MessageEscapePair> getEscapes(int index)
    {
        if (!escapes.containsKey(index))
        {
            return new Vector<MessageEscapePair>();
        } else {
            return escapes.get(index);
        }
    }
    
    public LineOfText part(int start, int end)
    {
        LineOfText temp = new LineOfText(line.substring(start, end));
        
        for (int i=start; i<end; i++)
        {
            if (escapes.containsKey(i))
            {
                for (MessageEscapePair escape : escapes.get(i))
                {
                    temp.addEscape(i, escape);
                }
            }
        }
        
        return temp;
    }
    
    @Override
    public String toString()
    {
        return line;
    }
    
    public void setString(String message)
    {
        this.line = message;
    }

    public int length()
    {
        return line.length();
    }

    public char charAt(int index)
    {
        return line.charAt(index);
    }

    public CharSequence subSequence(int start, int end)
    {
        return line.subSequence(start, end);
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.window;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author hatkirby
 */
public enum MessageEscape
{
    Color("\\\\C\\[(\\d+)\\]"),
    Pause("\\\\P");

    private Pattern pattern;
    private MessageEscape(String pattern)
    {
        this.pattern = Pattern.compile(pattern);
    }

    public static String removeEscapes(String message)
    {
        for (MessageEscape escape : values())
        {
            message = escape.pattern.matcher(message).replaceAll("");
        }

        return message;
    }

    public boolean match(String substring)
    {
        Matcher m = pattern.matcher(substring);
        
        return (m.lookingAt() && (m.start() == 0));
    }
    
    public String removeEscape(String message)
    {
        return pattern.matcher(message).replaceFirst("");
    }

    MessageEscapePair getMatch(String substring)
    {
        Matcher m = pattern.matcher(substring);
        m.lookingAt();
        
        return new MessageEscapePair(this, m.toMatchResult());
    }
}

class MessageEscapePair
{
    MessageEscape escape;
    MatchResult match;
    
    public MessageEscapePair(MessageEscape escape, MatchResult match)
    {
        this.escape = escape;
        this.match = match;
    }
    
    public MessageEscape getMessageEscape()
    {
        return escape;
    }
    
    public MatchResult getMatchResult()
    {
        return match;
    }
}
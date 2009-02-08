/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.fourpuzzle.util;

/**
 *
 * @author hatkirby
 */
public class ResourceNotFoundException extends RuntimeException {
    
    String type;
    String name;

    public ResourceNotFoundException(String type, String name)
    {
        this.type = type;
        this.name = name;
    }
    
    @Override
    public String getMessage()
    {
        return type + " \"" + name + "\" could not be found";
    }

}

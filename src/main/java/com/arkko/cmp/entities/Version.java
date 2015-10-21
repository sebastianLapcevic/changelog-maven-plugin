package com.arkko.cmp.entities;

import java.util.List;

/**
 *
 * @author slapcevic
 */
public class Version {
    
    private String name;
    private List<Type> types;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the types
     */
    public List<Type> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(List<Type> types) {
        this.types = types;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arkko.cmp.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jquiros
 */
public class Tag {
    
    String name;
    String message;
    List<Type> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
    
    public void addTypes(Type type){
        if(types == null){
            types = new ArrayList();
        }
        types.add(type);
    }
    
}

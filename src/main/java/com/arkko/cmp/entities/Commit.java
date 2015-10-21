/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arkko.cmp.entities;

/**
 *
 * @author slapcevic
 */
public class Commit {

    private String subject;
    private String body;
    private String footer;
    private String scope;

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the footer
     */
    public String getFooter() {
        return footer;
    }

    /**
     * @param footer the footer to set
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
    
}

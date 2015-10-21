package com.arkko.cmp.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author slapcevic
 */
public class Type {
    private String code;
    private String name;
    private List<Commit> commits;
    
    public Type(){
        commits = new ArrayList();
    }
    public void addCommit(Commit commit){
        if(commits!=null){
            commits.add(commit);
        }
    }

    /**
     * @return the feature
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the feature to set
     */
    public void setCode(String code) {
        this.code = code;
        setName(code);
    }

    /**
     * @return the commits
     */
    public List<Commit> getCommits() {
        return commits;
    }

    /**
     * @param commits the commits to set
     */
    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param code the name to set
     */
    public void setName(String code) {
        switch (code) {
            case "feat":
                this.name="Feature";
                break;
            case "fix":
                this.name="Bug Fixes";
                break;
            case "docs":
                this.name="Documentation";
                break;
            case "style":
                this.name="Style";
                break;
            case "refactor":
                this.name="Refactor";
                break;
            case "test":
                this.name="Tests";
                break;
            case "chore":
                this.name="Chores";
                break;
        }
    }
    
    
}

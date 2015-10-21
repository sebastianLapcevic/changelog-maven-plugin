package com.arkko.cmp;

import com.arkko.cmp.entities.Commit;
import com.arkko.cmp.entities.Type;
import com.arkko.cmp.entities.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author slapcevic
 */
public class GitParser {

    private static final Logger log = Logger.getLogger(ChangelogGenerator.class.getName());
    private Type feat;
    private Type fix;
    private Type docs;
    private Type style;
    private Type refactor;
    private Type test;
    private Type chore;

    public GitParser() {
        cleanLists();
    }

    private void cleanLists() {
        feat = new Type();
        feat.setCode("feat");
        fix = new Type();
        fix.setCode("fix");
        docs = new Type();
        docs.setCode("docs");
        style = new Type();
        style.setCode("style");
        refactor = new Type();
        refactor.setCode("refator");
        test = new Type();
        test.setCode("test");
        chore = new Type();
        chore.setCode("chore");
    }

    public List<Version> parseCommits(List<String> commits) {
        List<Version> versions = new ArrayList();
        String r = "(?:version)(?:.*)\\:(.*)";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher;
        String versionData;
        List<String> versionCommits = new ArrayList();
        
        for (String commit : commits) {
            commit = commit.trim();
            matcher = pattern.matcher(commit);
            if (matcher.matches()) {
                versionData = matcher.group(1);
                if (!versionCommits.isEmpty()) {
                    if(versionData.isEmpty()){
                        versions.add(createVersion(versionCommits, "Changes"));
                    }else{
                        versions.add(createVersion(versionCommits, versionData));
                    }
                }
                versionCommits = new ArrayList();
            } else {
                versionCommits.add(commit);
            }
        }
        return versions;
    }

    private Version createVersion(List<String> commits, String versionName) {
        Version version = new Version();
        String r = "(feat|fix|docs|style|refactor|test|chore)\\((.*)\\)\\:(.*)(?:.*|\\n\\n)((?:\\s|.*)*)";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher;
        for (String commitData : commits) {
            Commit commit;
            matcher = pattern.matcher(commitData);
            if (matcher.matches()) {
                String typeName = matcher.group(1);
                String scope = matcher.group(2);
                String subject = matcher.group(3);
                String bodyFooter = matcher.group(4);
                commit = createCommit(scope, subject, bodyFooter);
                addToList(typeName, commit);
            }
        }
        List<Type> types = createTypes();
        cleanLists();
        
        version.setTypes(types);
        version.setName(versionName);
        return version;
    }

    private Commit createCommit(String scope, String subject, String bodyFooter) {
        Commit commit = new Commit();
        String body = "";
        String footer = "";
        if (!bodyFooter.isEmpty()) {
            String parts[] = bodyFooter.split("\\n\\n");
            body = parts[0];
            footer = parts[1];
        }
        commit.setSubject(subject);
        commit.setBody(body);
        commit.setFooter(footer);
        commit.setScope(scope);
        
        return commit;
    }

    private void addToList(String type, Commit commit) {
        switch (type) {
            case "feat":
                feat.addCommit(commit);
                break;
            case "fix":
                fix.addCommit(commit);
                break;
            case "docs":
                docs.addCommit(commit);
                break;
            case "style":
                style.addCommit(commit);
                break;
            case "refactor":
                refactor.addCommit(commit);
                break;
            case "test":
                test.addCommit(commit);
                break;
            case "chore":
                chore.addCommit(commit);
                break;
        }
    }

    private List<Type> createTypes() {
        List<Type> types = new ArrayList();
        types.add(feat);
        types.add(fix);
        types.add(docs);
        types.add(style);
        types.add(refactor);
        types.add(test);
        types.add(chore);
        return types;
    }
}

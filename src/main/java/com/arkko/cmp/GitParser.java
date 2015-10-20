package com.arkko.cmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private List<HashMap<String, String>> feat;
    private List<HashMap<String, String>> fix;
    private List<HashMap<String, String>> docs;
    private List<HashMap<String, String>> style;
    private List<HashMap<String, String>> refactor;
    private List<HashMap<String, String>> test;
    private List<HashMap<String, String>> chore;

    public GitParser() {
        cleanLists();
    }

    private void cleanLists() {
        feat = new ArrayList();
        fix = new ArrayList();
        docs = new ArrayList();
        style = new ArrayList();
        refactor = new ArrayList();
        test = new ArrayList();
        chore = new ArrayList();
    }

    public HashMap<String, HashMap<String, List<HashMap<String, String>>>> parseCommits(List<String> commits) {
        HashMap<String, HashMap<String, List<HashMap<String, String>>>> versions = new HashMap();
        String r = "(?:version)(?:.*)\\:(.*)";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher;
        String version;
        List<String> versionCommits = new ArrayList();
        
        for (String commit : commits) {
            commit = commit.trim();
            matcher = pattern.matcher(commit);
            if (matcher.matches()) {
                version = matcher.group(1);
                if (!versionCommits.isEmpty()) {
                    if(version.isEmpty()){
                        versions.put("Changes", createVersion(versionCommits));
                    }else{
                        versions.put(version, createVersion(versionCommits));
                    }
                }
                versionCommits = new ArrayList();
            } else {
                versionCommits.add(commit);
            }
        }
        return versions;
    }

    private HashMap<String, List<HashMap<String, String>>> createVersion(List<String> commits) {
        String r = "(feat|fix|docs|style|refactor|test|chore)\\((.*)\\)\\:(.+)(?:.|\\n\\n)((?:\\s|.*)*)";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher;
        for (String commit : commits) {
            HashMap<String, String> commitData;
            matcher = pattern.matcher(commit);
            if (matcher.matches()) {
                String type = matcher.group(1);
                String scope = matcher.group(2);
                String subject = matcher.group(3);
                String bodyFooter = matcher.group(4);
                commitData = createCommit(scope, subject, bodyFooter);
                addToList(type, commitData);
            }
        }
        HashMap<String, List<HashMap<String, String>>> version = createTypes();
        cleanLists();
        return version;
    }

    private HashMap<String, String> createCommit(String scope, String subject, String bodyFooter) {
        HashMap<String, String> commit = new HashMap();
        String body = "";
        String footer = "";
        if (!bodyFooter.isEmpty()) {
            String parts[] = bodyFooter.split("\\n\\n");
            body = parts[0];
            footer = parts[1];
        }
        commit.put("subject", subject);
        commit.put("body", body);
        commit.put("footer", footer);
        commit.put("scope", scope);
        return commit;
    }

    private void addToList(String type, HashMap<String, String> commitData) {
        switch (type) {
            case "feat":
                feat.add(commitData);
                break;
            case "fix":
                fix.add(commitData);
                break;
            case "docs":
                docs.add(commitData);
                break;
            case "style":
                style.add(commitData);
                break;
            case "refactor":
                refactor.add(commitData);
                break;
            case "test":
                test.add(commitData);
                break;
            case "chore":
                chore.add(commitData);
                break;
        }
    }

    private HashMap<String, List<HashMap<String, String>>> createTypes() {
        HashMap<String, List<HashMap<String, String>>> types = new HashMap();
        types.put("feat", feat);
        types.put("fix", fix);
        types.put("docs", docs);
        types.put("style", style);
        types.put("refactor", refactor);
        types.put("test", test);
        types.put("chore", chore);
        return types;
    }
}

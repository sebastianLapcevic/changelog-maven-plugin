package com.arkko.cmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.markdown4j.Markdown4jProcessor;

/**
 * @author slapcevic
 */
public class ChangelogGenerator {

    private static final Logger log = Logger.getLogger(ChangelogGenerator.class.getName());
    String markdown;

    public void generateChangelog(HashMap<String, HashMap<String, List<HashMap<String, String>>>> versions) throws ParserConfigurationException, TransformerException, IOException {
        log.info("Start");
        markdown = "# Changelog \n";
        for (Entry<String, HashMap<String, List<HashMap<String, String>>>> entry : versions.entrySet()) {
            String key = entry.getKey();
            HashMap value = entry.getValue();
            generateVersion(value, key);
        }
        generateFile();
        log.info("Done");
    }

    private void generateFile() throws IOException {
        log.log(Level.INFO, markdown);
        Markdown4jProcessor processor = new Markdown4jProcessor();
        String mdFile = processor.process(markdown);
        File file;
        FileOutputStream fop = null;
        try {
            file = new File("target/changelog.md");
            fop = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = mdFile.getBytes();
            fop.write(contentInBytes);
        } finally {
            if (fop != null) {
                fop.flush();
                fop.close();
            }
        }
    }

    private void generateVersion(HashMap<String, List<HashMap<String, String>>> commands, String versionName) {
        markdown = markdown + "> ## " + versionName + " \n";
        
        appendCommits(commands.get("feat"), "Features");
        appendCommits(commands.get("fix"), "Fixes");
        appendCommits(commands.get("docs"), "Documentation");
        appendCommits(commands.get("style"), "Style");
        appendCommits(commands.get("refactor"), "Refactor");
        appendCommits(commands.get("test"), "Tests");
        appendCommits(commands.get("chore"), "Chores");
    }

    private void appendCommits(List<HashMap<String, String>> commits, String commandName) {
        if (!commits.isEmpty()) {
            markdown = markdown + "> > ### "+commandName+" \n> \n";
            for (HashMap<String, String> commit : commits) {
                markdown = markdown + "> > > **" + commit.get("scope").trim() + ":**"+ commit.get("subject") + " \n> \n"; 
                if (!commit.get("body").isEmpty()) {
                    markdown = markdown + " " + commit.get("body").trim() + " \n";
                    markdown = markdown + " " + commit.get("footer").trim() + " \n";
                }
            }
        }
    }

}

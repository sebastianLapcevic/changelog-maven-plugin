package com.arkko.cmp;

import com.arkko.cmp.entities.Commit;
import com.arkko.cmp.entities.Type;
import com.arkko.cmp.entities.Version;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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

    public void generateChangelog(List<Version> versions) throws ParserConfigurationException, TransformerException, IOException {
        log.info("Start");
        markdown = "# Changelog \n";        
        
        for (Version v : versions) {
            String name = v.getName();
            List<Type> types = v.getTypes();
            appendVersion(types, name);
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

    private void appendVersion(List<Type> types, String versionName) {
        markdown = markdown + "> ## " + versionName + " \n";
        for(Type t: types){
            appendCommits(t.getCommits(), t.getName());
        }
    }

    private void appendCommits(List<Commit> commits, String commandName) {
        if (!commits.isEmpty()) {
            markdown = markdown + "> > ### " + commandName + " \n> \n";
            for (Commit commit : commits) {
                markdown = markdown + "> > > **" + commit.getScope().trim() + ":**" + commit.getSubject() + " \n> \n";
                if (!commit.getBody().isEmpty()) {
                    markdown = markdown + " " + commit.getBody().trim() + " \n";
                    markdown = markdown + " " + commit.getFooter().trim() + " \n";
                }
            }
        }
    }
}

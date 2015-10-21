package com.arkko.cmp;

import com.arkko.cmp.entities.Version;
import com.arkko.cmp.exceptions.ChangeLogException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 *
 * @author slapcevic
 */
@Mojo(name = "generate")
public class MainMojo extends AbstractMojo {

    private final Logger log = Logger.getLogger(MainMojo.class.getName());

    private final File repositoryPath = new File("");

    @Override
    public void execute() throws MojoExecutionException {
        GitLogReader reader = new GitLogReader();
        GitParser parser = new GitParser();
        ChangelogGenerator generator = new ChangelogGenerator();
        try {
            log.info("Start");
            List<String> logList = reader.readLog(repositoryPath.getAbsolutePath());
            //List<String> lines =  new ChangelogReader().readChangelog();
            List<Version> versions = parser.parseCommits(logList);
            generator.generateChangelog(versions);
            log.info("Done");
        } catch (ChangeLogException | NullPointerException | TransformerException | IOException | ParserConfigurationException e) {
            log.log(Level.SEVERE, e.getMessage());
            log.log(Level.SEVERE, e.getClass().toString());
        }
    }
}

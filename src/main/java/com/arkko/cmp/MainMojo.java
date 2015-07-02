package com.arkko.cmp;

import com.arkko.cmp.exceptions.ChangeLogException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *
 * @author slapcevic
 */

@Mojo( name = "generate")
public class MainMojo extends AbstractMojo{
    
    private final Log log = getLog();
    
    @Parameter(defaultValue = "${project.basedir}")
    private File repositoryPath;
 
    @Override
    public void execute() throws MojoExecutionException
    {
        log.info("TEST");        
        GitLogReader reader = new GitLogReader();
        
        try{
            log.info(repositoryPath.getAbsolutePath());
            ArrayList<String> logList = reader.readLog(repositoryPath.getAbsolutePath());
            for(String logMessage : logList){
                log.info(logMessage);
            }
        }catch (ChangeLogException | NullPointerException e){
            log.error(e.getMessage());
            log.error(e);
        }
    }
}
package com.arkko.cmp;

import com.arkko.cmp.exceptions.ChangeLogException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 *
 * @author slapcevic
 */
public class GitLogReader {
    public ArrayList<String> readLog(String repositoryPath) {
        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repo = builder.setGitDir(new File(repositoryPath + "\\.git")).setMustExist(true).build();
            Git git = new Git(repo);
            Iterable<RevCommit> log = git.log().call();
            ArrayList<String> logMessages = new ArrayList<>();
            for (RevCommit rev : log) {
                logMessages.add(rev.getFullMessage());
            }
            return logMessages;
        } catch (IOException | GitAPIException e) {
            throw new ChangeLogException(e.getMessage(), e);
        }
    }
}

package com.arkko.cmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author slapcevic
 */
public class ChangelogReader {

    public List<String> readChangelog() {
        List<String> lines = new ArrayList();
        Path file = Paths.get("target/changelog.md");
        try (InputStream in = Files.newInputStream(file); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                lines.add(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return lines;
    }
}

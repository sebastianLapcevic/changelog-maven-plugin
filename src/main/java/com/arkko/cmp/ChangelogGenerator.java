package com.arkko.cmp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author slapcevic
 */
public class ChangelogGenerator {

    private static final Logger log = Logger.getLogger(ChangelogGenerator.class.getName());
    
    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;
    Element rootElement;

    public void generateChangelog(HashMap<String, HashMap<String, List<HashMap<String, String>>>> versions) throws ParserConfigurationException, TransformerException {
        log.info("Start");
        
        this.docFactory = DocumentBuilderFactory.newInstance();
        this.docBuilder = docFactory.newDocumentBuilder();
        this.doc = docBuilder.newDocument();
        
        log.info("Getting versions: "+ versions);
        for (Entry<String, HashMap<String, List<HashMap<String, String>>>> entry : versions.entrySet()) {
            String key = entry.getKey();
            log.info("LLAVE: " + key);
            HashMap value = entry.getValue();
            generateVersion(value, key);
        }
        
        log.info("Creating transformer Factory");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("target/changelog.xml"));

        transformer.transform(source, result);
        log.info("Done");
    }

    private void generateVersion(HashMap<String, List<HashMap<String, String>>> commands, String versionName) {
        rootElement = doc.createElement(versionName);
        doc.appendChild(rootElement);

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
            Element command = doc.createElement(commandName);
            rootElement.appendChild(command);
            for (HashMap<String, String> commit : commits) {
                Element commitElement = doc.createElement("commit");

                Attr attr = doc.createAttribute("subject");
                attr.setValue(commit.get("subject"));
                commitElement.setAttributeNode(attr);
                attr = doc.createAttribute("body");
                attr.setValue(commit.get("body"));
                commitElement.setAttributeNode(attr);
                attr = doc.createAttribute("footer");
                attr.setValue(commit.get("footer"));
                commitElement.setAttributeNode(attr);
                attr = doc.createAttribute("scope");
                attr.setValue(commit.get("scope"));
                commitElement.setAttributeNode(attr);

                command.appendChild(commitElement);
            }
        }
    }

}

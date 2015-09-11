package com.arkko.cmp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author slapcevic
 */
public class ChangelogGenerator {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;
    Element rootElement;

    public void generateChangelog(List<HashMap<String, List<String>>> versions, List<String> versionNames) throws ParserConfigurationException, TransformerException {
        this.docBuilder = docFactory.newDocumentBuilder();
        this.docFactory = DocumentBuilderFactory.newInstance();
        this.doc = docBuilder.newDocument();

        int i = 0;
        for (HashMap<String, List<String>> version : versions) {
            generateVersion(version, versionNames.get(i));
            i++;
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("changelog.xml"));

        transformer.transform(source, result);
    }

    private void generateVersion(HashMap<String, List<String>> version, String versionName) {
        rootElement = doc.createElement(versionName);
        doc.appendChild(rootElement);

        appendCommits(version.get("feat"), "Features");
        appendCommits(version.get("fix"), "Fixes");
        appendCommits(version.get("docs"), "Documentation");
        appendCommits(version.get("style"), "Style");
        appendCommits(version.get("refactor"), "Refactor");
        appendCommits(version.get("test"), "Tests");
        appendCommits(version.get("chore"), "Chores");
    }

    private void appendCommits(List<String> commits, String elementName) {
        if (!commits.isEmpty()) {
            Element element = doc.createElement(elementName);
            rootElement.appendChild(element);
            for (String commit : commits) {
                element.appendChild(doc.createTextNode(commit));
            }
        }
    }

}

package Presentation.Views;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLParser {
    private static XMLParser instance;
    private DocumentBuilderFactory docBFactory;
    private DocumentBuilder docBuilder;
    private Document doc;

    private XMLParser(){
        //open file and stuff
        try{
            //set up document builder
            File file = new File("Resources/board.xml");
            docBFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBFactory.newDocumentBuilder();
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static XMLParser getInstance(){
        if(instance == null) {
            instance = new XMLParser();
        }
        return instance;
    }

    public void printInfo() {
        /* Print the root */
        System.out.println("Root: " + doc.getDocumentElement().getNodeName());

        NodeList nodeList = doc.getElementsByTagName("set");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Element: " + node.getNodeName());
        }
    }

}

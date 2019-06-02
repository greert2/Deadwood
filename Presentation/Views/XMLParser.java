package Presentation.Views;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLParser {
    private static XMLParser instanceForBoard;
    private static XMLParser instanceForCards;

    private DocumentBuilderFactory docBFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private static int setIx; //index for set, to be able to call nextSet()

    private static final int NUMBER_OF_SETS = 10;

    private XMLParser(boolean forBoard){
        setIx = 0;
        //open file and stuff
        String filePath;
        if(forBoard){
            filePath = "Resources/board.xml";
        }else{
            filePath = "Resources/cards.xml";
        }
        try{
            //set up document builder
            File file = new File(filePath);
            docBFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBFactory.newDocumentBuilder();
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static XMLParser getInstanceForBoard(){
        if(instanceForBoard == null) {
            instanceForBoard = new XMLParser(true);
        }
        return instanceForBoard;
    }

    public static XMLParser getInstanceForCards(){
        if(instanceForCards == null) {
            instanceForCards = new XMLParser(false);
        }
        return instanceForCards;
    }

    public void selectSet(String setName) {
        //changes the set index to that of the set with the passed name
        Node n;
        for(int i = 0; i < NUMBER_OF_SETS; i++) {
            n = doc.getElementsByTagName("set").item(i);
            Element element = (Element)n;
            if(element.getAttribute("name").equals(setName)){
                setIx = i;
                return;
            }
        }
    }

    public void selectScene(String sceneName) {
        //changes the set index to that of the SCENE with the passed name
        Node n;
        for(int i = 0; i < NUMBER_OF_SETS; i++) {
            n = doc.getElementsByTagName("card").item(i);
            Element element = (Element)n;
            if(element.getAttribute("name").equals(sceneName)){
                setIx = i;
                return;
            }
        }
    }


    public boolean nextSet(){
        if(setIx < NUMBER_OF_SETS) {
            setIx++;
            return true;
        }
        return false;
    }

    public int[] getSizes() {
        /* Return array of structure [x, y, h, w]  */
        int x=0, y=0, h=0, w=0;
        Node n = doc.getElementsByTagName("set").item(setIx);
        if(n instanceof Element) {
            Element docElement = (Element)n;
            Node areaNode = docElement.getElementsByTagName("area").item(0);
            Element areaElement = (Element)areaNode;
            x = Integer.parseInt(areaElement.getAttribute("x"));
            y = Integer.parseInt(areaElement.getAttribute("y"));
            h = Integer.parseInt(areaElement.getAttribute("h"));
            w = Integer.parseInt(areaElement.getAttribute("w"));
        }
        return new int[]{ x, y, h, w};
    }

    public String getSceneImagePath(String sceneName) {
        if(instanceForCards == null) {
            System.out.println("Not an XMLParser instance for cards.");
            return null;
        }
        NodeList nList = doc.getElementsByTagName("card");
        for (int i = 0; i < nList.getLength(); i++) {
            Element el = (org.w3c.dom.Element) nList.item(i);
            if (el.hasAttribute("name") && el.getAttribute("name").equals(sceneName)) {
                return "Resources/cards/" + el.getAttribute("img");
            }
        }
        return null;
    }



}

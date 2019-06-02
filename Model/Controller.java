package Model;

import Presentation.Views.DeadwoodFrame;
import Presentation.Views.IntroFrame;
import Presentation.Views.XMLParser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    private static Controller instance;
    private static IntroFrame introFrame;
    private static DeadwoodFrame deadwoodFrame;

    /* A map of set names to their scene card object */
    private HashMap<String, JLabel> roomSceneCardMap = new HashMap<String, JLabel>();

    private Controller() {}

    public static void main(String[] args) {
        introFrame = new IntroFrame();
        introFrame.setSize(300,270);
        introFrame.setResizable(false);
        introFrame.setVisible(true);
    }

    public void showMainFrame(){
        introFrame.setVisible(false);
        deadwoodFrame = DeadwoodFrame.getInstance();
        deadwoodFrame.setSize(1336, 920);
        deadwoodFrame.setResizable(false);

        /* Create Players */
        DeadwoodFrame.getInstance().createPlayerLabels();
        //displayPlayers();
        /* Create scenes */
        createSceneLabels();
        //XMLParser.getInstance().printInfo();
        this.flipSceneCard(((Set)Board.getInstance().getRooms().get(0)).getCurrScene().getSceneName());
        /* Show the main frame */
        deadwoodFrame.setVisible(true);
    }

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void displayPlayers(){
        ArrayList<Player> players = GameSystem.getInstance().getPlayerList();

        //deadwoodFrame.
        //for(int i = 0;)
    }

    public void createSceneLabels() {
        XMLParser boardParser = XMLParser.getInstanceForBoard();
        //XMLParser cardsParser = XMLParser.getInstanceForCards();
        /* Get scenes from the model */
        ArrayList<Room> rooms = Board.getInstance().getRooms();
        Scene scene;

        /* get the layeredPane and the sizes */
        JLayeredPane pane = DeadwoodFrame.getInstance().getLayeredPane();

        /* loop through rooms */
        for(int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);

            if(room instanceof Set) {
                boardParser.selectSet(room.getRoomName());
                int[] sizes = boardParser.getSizes(); //get area sizes of scene card
                scene = ((Set) room).getCurrScene();
                /* Create scene card label object */
                JLabel sceneCard = new JLabel();
                //ImageIcon img = new ImageIcon(cardsParser.getSceneImagePath(scene.getSceneName()));
                ImageIcon img = new ImageIcon(((new ImageIcon("Resources/cards/cardBack.png")).getImage()).getScaledInstance(205, 115, java.awt.Image.SCALE_SMOOTH));
                sceneCard.setIcon(img);
                sceneCard.setBounds(sizes[0], sizes[1], sizes[3], sizes[2]);
                sceneCard.setOpaque(true);
                //add component to view
                DeadwoodFrame.getInstance().addComponentToFrame(sceneCard, 1);
                //add component to hashmap for later manipulation
                System.out.println("Added scene " + scene.getSceneName());
                roomSceneCardMap.put(scene.getSceneName(), sceneCard);
            }

        }
    }



    public void flipSceneCard(String sceneName) {
        XMLParser cardsParser = XMLParser.getInstanceForCards();

        if(roomSceneCardMap.containsKey(sceneName)) {
            System.out.println("Scene " + sceneName + " flipped");
            cardsParser.selectScene(sceneName);
            String imgPath = cardsParser.getSceneImagePath(sceneName);
            ImageIcon img = new ImageIcon(imgPath);
            roomSceneCardMap.get(sceneName).setIcon(img);

            //create and add roles
        }
    }

    public static IntroFrame getIntroFrame() {
        return introFrame;
    }
}

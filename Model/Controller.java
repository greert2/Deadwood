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
    private HashMap<String, JLabel> roomSceneCardMap = new HashMap<String, JLabel>();//<sceneName, JLabel (scene img)>
    private HashMap<String, JLabel> playerMap = new HashMap<String, JLabel>(); //<playerColor, JLabel (player img)>
    //private HashMap<String, JButton> roomMap = new HashMap<String, JButton>(); //<roomName, room button>

    private Player currPlayer;

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
        //DeadwoodFrame.getInstance().createPlayerLabels();
        //displayPlayers();
        /* Create scenes */
        createSceneLabels();
        createPlayerLabels();
        //XMLParser.getInstance().printInfo();
        this.flipSceneCard(((Set)Board.getInstance().getRooms().get(0)).getCurrScene().getSceneName());

        //set currPlayer initially
        this.currPlayer = GameSystem.getInstance().getCurrPlayer();

        //ArrayList<Room> rooms = Board.getInstance().getRooms();
        //System.out.println(rooms.get(0).getRoomName());
        //rooms.get(0).setPlayerCoords(785,135);

        //displayPlayers();
        updateActivePlayerGUI();

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
        //players.get(0).moveRoom(Board.getInstance().getSpecificRoom("Main Street"));
        int[] coords;
        int offset = 0;
        for(int i = 0; i < players.size(); i++) {
            //get player's set, place player here based on number of players here
            if(players.get(i).getCurrentRoom() != null) {
                //get the current model player
                Player currPlayer = players.get(i);
                //get the player gui component
                JLabel playerGUIComponent = playerMap.get(currPlayer.getColor());
                //get coords
                coords = currPlayer.getCurrentRoom().getPlayerCoords();
                //move player gui component to room
                Icon icon = playerGUIComponent.getIcon();
                playerGUIComponent.setBounds(coords[0] + offset, coords[1], icon.getIconWidth(), icon.getIconHeight());
                offset += 20;
            }

            //get if player has a role, place here if true
        }
    }

    public void createPlayerLabels() {
        //get list of players from the model
        ArrayList<Player> players = GameSystem.getInstance().getPlayerList();

        int offset = 0;
        //create a label for each
        for(int i = 0; i < players.size(); i++) {
            JLabel p = new JLabel();
            //get info on player color and rank in order to select correct image
            Player currPlayer = players.get(i);
            String color = currPlayer.getColor().substring(0,1);
            String rank = Integer.toString(currPlayer.getRank());
            //example b1.png (color: blue, rank: 1)
            String image = "Resources/dice/" + color + rank + ".png";
            ImageIcon playerDiceIcon = new ImageIcon(((new ImageIcon(image)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
            p.setIcon(playerDiceIcon);
            p.setBounds(1000 + offset, 410, playerDiceIcon.getIconWidth(), playerDiceIcon.getIconHeight());
            p.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    DeadwoodFrame.getInstance().getPaneDeadwood().moveToFront(p);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    DeadwoodFrame.getInstance().getPaneDeadwood().moveToBack(p);
                }
            });
            DeadwoodFrame.getInstance().addComponentToFrame(p, 3);
            /* Add player component to map */
            playerMap.put(currPlayer.getColor(), p);
            offset += 20;
            //JOptionPane.showMessageDialog(this,  "This is a test of the JOptionPane!", "Success!", JOptionPane.INFORMATION_MESSAGE, playerDiceIcon);
            //996,414
        }
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

    public void endTurn() {
        GameSystem.getInstance().endTurn(); //ends current players turn
        this.currPlayer = GameSystem.getInstance().getCurrPlayer();
        //update active player gui
        updateActivePlayerGUI();
    }

    public void updateActivePlayerGUI() {
        DeadwoodFrame dFrame = DeadwoodFrame.getInstance();

        ImageIcon icon = (ImageIcon) playerMap.get(currPlayer.getColor()).getIcon();
        ImageIcon biggerIcon = new ImageIcon((icon.getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
        dFrame.getLabelCurrPlayerImg().setIcon(biggerIcon);

        dFrame.getLabelActivePlayerMoney().setText("$" + currPlayer.getMoney());
        dFrame.getLabelActivePlayerCredits().setText(currPlayer.getMoney() + " credits");
    }

    public boolean moveRoom(String roomName) {
        if(currPlayer.moveRoom(Board.getInstance().getSpecificRoom(roomName))) {
            //player has moved
            System.out.println("Player " + currPlayer.getColor() + " has moved to " + roomName);
            displayPlayers();
            return true;
        }
        JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),  "You cannot move here",
                "Failure!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        return false;
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

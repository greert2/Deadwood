package Model;

import Presentation.Views.DeadwoodFrame;
import Presentation.Views.IntroFrame;
import Presentation.Views.XMLParser;
import Presentation.Views.UpgradeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Controller {

    private static Controller instance;
    private static IntroFrame introFrame;
    private static DeadwoodFrame deadwoodFrame;
    private static UpgradeFrame upgradeFrame;

    /* A map of set names to their scene card object */
    private HashMap<String, JLabel> roomSceneCardMap = new HashMap<String, JLabel>();//<sceneName, JLabel (scene img)>
    private HashMap<String, JLabel> playerMap = new HashMap<String, JLabel>(); //<playerColor, JLabel (player img)>
    private HashMap<String, Queue<JLabel>> shotCounterMap = new HashMap<String, Queue<JLabel>>(); //<setName, queue of shots for set>
    private ArrayList<JLabel> roleLabels = new ArrayList<JLabel>();
    //private ArrayList<Role> allOffCardRoles = new ArrayList<Role>();
    //private HashMap<String, JLabel> offCardRoleMap = new HashMap<String, JLabel>(); //<roleName, label>
    //private HashMap<String, JButton> roomMap = new HashMap<String, JButton>(); //<roomName, room button>

    private int[][] costs = new int[][]{ {4,5},{10,10},{18,15},{28,20},{40,25}};

    private Player currPlayer;
    private boolean blockAction;

    private Controller() {
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
        createOffCardRoles();
        createShotCounters();

        /* Set number of days */
        GameSystem.getInstance().setTotalDays(3);


        //set currPlayer initially
        this.currPlayer = GameSystem.getInstance().getCurrPlayer();
        this.blockAction = false;

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
    // ADDED
    public void showUpgradeFrame() {
    	upgradeFrame = new UpgradeFrame();
    	upgradeFrame.setSize(380,250);
    	upgradeFrame.setResizable(false);
    	upgradeFrame.setVisible(true);
    }

    public void displayPlayers(){
        ArrayList<Player> players = GameSystem.getInstance().getPlayerList();
        //players.get(0).moveRoom(Board.getInstance().getSpecificRoom("Main Street"));
        int[] coords;
        int offset = 0;
        for(int i = 0; i < players.size(); i++) {
            //get player's set, place player here based on number of players here
            if(players.get(i).getCurrentRoom() != null && players.get(i).getRole() == null) {
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
        updateActivePlayerGUI();
    }

    public void updateRanks() {
        ArrayList<Player> players = GameSystem.getInstance().getPlayerList();

        for(int i = 0; i < players.size(); i++) {
            //get info on player color and rank in order to select correct image
            Player currPlayer = players.get(i);
            String color = currPlayer.getColor().substring(0, 1);
            String rank = Integer.toString(currPlayer.getRank());
            //example b1.png (color: blue, rank: 1)
            String image = "Resources/dice/" + color + rank + ".png";
            ImageIcon playerDiceIcon = new ImageIcon(((new ImageIcon(image)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
            playerMap.get(currPlayer.getColor()).setIcon(playerDiceIcon);
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
                sceneCard.setName(scene.getSceneName());
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

    public void createOffCardRoles() {
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
                ArrayList<String[]> info = boardParser.getSetRoleSizes();
                for(String[] role : info){
                    JLabel roleLabel = new JLabel();
                    roleLabel.setBounds(
                            Integer.parseInt(role[1]),
                            Integer.parseInt(role[2]),
                            Integer.parseInt(role[3]),
                            Integer.parseInt(role[4])
                    );
                    roleLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(GameSystem.getInstance().getCurrPlayer().takeRole(role[0])){
                                //player got the role! move them
                                JLabel currPlayerImg = playerMap.get(currPlayer.getColor());
                                Icon img = currPlayerImg.getIcon();
                                currPlayerImg.setBounds(
                                        Integer.parseInt(role[1])+4,
                                        Integer.parseInt(role[2])+4,
                                        img.getIconWidth(),
                                        img.getIconHeight()
                                );
                            }
                        }
                    });
                    //add role to the view
                    DeadwoodFrame.getInstance().addComponentToFrame(roleLabel, 2);
                }
            }

        }
    }

    public void createShotCounters() {
        XMLParser boardParser = XMLParser.getInstanceForBoard();
        //XMLParser cardsParser = XMLParser.getInstanceForCards();
        /* Get scenes from the model */
        ArrayList<Room> rooms = Board.getInstance().getRooms();

        /* get the sizes */
        /* loop through rooms */
        for(int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);

            if(room instanceof Set) {
                boardParser.selectSet(room.getRoomName());
                shotCounterMap.put(room.getRoomName(), new LinkedList<JLabel>());
                ArrayList<int[]> info = boardParser.getShotCounterSizes();
                for(int[] shot : info){
                    JLabel shotLabel = new JLabel();
                    ImageIcon img = new ImageIcon("Resources/shot.png");
                    shotLabel.setIcon(img);
                    shotLabel.setBounds(shot[0], shot[1], shot[2], shot[3]);
                    //add shot counter label to the hashmap's queue
                    shotCounterMap.get(room.getRoomName()).add(shotLabel);
                    //add role to the view
                    DeadwoodFrame.getInstance().addComponentToFrame(shotLabel, 2);
                }
            }

        }
    }

    public void endTurn() {
        GameSystem.getInstance().endTurn(); //ends current players turn
        this.currPlayer = GameSystem.getInstance().getCurrPlayer();
        this.blockAction = false;
        //update active player gui
        updateActivePlayerGUI();
    }

    public void updateActivePlayerGUI() {
        DeadwoodFrame dFrame = DeadwoodFrame.getInstance();

        ImageIcon icon = (ImageIcon) playerMap.get(currPlayer.getColor()).getIcon();
        ImageIcon biggerIcon = new ImageIcon((icon.getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
        dFrame.getLabelCurrPlayerImg().setIcon(biggerIcon);

        dFrame.getLabelActivePlayerMoney().setText("$" + currPlayer.getMoney());
        dFrame.getLabelActivePlayerCredits().setText(currPlayer.getCredits() + " credits");
        dFrame.getLabelActivePlayerRehearsals().setText(currPlayer.getRehearseChips() + " chips");
    }

    public boolean moveRoom(String roomName) {
        Room room = Board.getInstance().getSpecificRoom(roomName);
        boolean alreadyVisited = true; //set to true so we are more likely to not create double scene cards
        if(room instanceof Set){
            alreadyVisited = ((Set)room).getCurrScene().getVisited();
        }
        if(!blockAction && currPlayer.getRole() == null && currPlayer.moveRoom(room)) {
            //flip scene card and create roles (if needed)
            if(!alreadyVisited) { //!alreadyVisited implies it IS valid and not visited
                flipSceneCard(((Set)room).getCurrScene().getSceneName());
            }
            //player has moved
            System.out.println("Player " + currPlayer.getColor() + " has moved to " + roomName);
            displayPlayers();
            this.blockAction = true;
            return true;
        }else if(currPlayer.getRole() != null) {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You can't move if you are working on a role",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        }else if(blockAction){
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You have already moved or acted this turn",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        }else {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You cannot move here",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        }
        return false;
    }

    public void flipSceneCard(String sceneName) {
        XMLParser cardsParser = XMLParser.getInstanceForCards();

        if(roomSceneCardMap.containsKey(sceneName)) {
            System.out.println("Scene " + sceneName + " flipped");
            //flip scene
            cardsParser.selectScene(sceneName);
            String imgPath = cardsParser.getSceneImagePath(sceneName);
            ImageIcon img = new ImageIcon(imgPath);
            roomSceneCardMap.get(sceneName).setIcon(img);
            //
            if(currPlayer.getCurrentRoom() instanceof Set) {
                ((Set)currPlayer.getCurrentRoom()).resetShotCounters();
            }


            JLabel sceneCard = roomSceneCardMap.get(sceneName);
            Rectangle rect = sceneCard.getBounds();
            int cardX = (int)rect.getX();
            int cardY = (int)rect.getY();
            //create and add roles
            cardsParser.selectScene(sceneName);
            ArrayList<String[]> info = cardsParser.getCardRoleSizes();
            for(String[] role : info) {
                JLabel roleLabel = new JLabel();
                roleLabel.setBounds(
                        cardX + Integer.parseInt(role[1]) + 4,
                        cardY + Integer.parseInt(role[2]) + 4,
                        Integer.parseInt(role[3]),
                        Integer.parseInt(role[4])
                );
                roleLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (GameSystem.getInstance().getCurrPlayer().takeRole(role[0])) {
                            //player got the role! move them
                            JLabel currPlayerImg = playerMap.get(currPlayer.getColor());
                            Icon img = currPlayerImg.getIcon();
                            currPlayerImg.setBounds(
                                    cardX + Integer.parseInt(role[1]) + 4,
                                    cardY + Integer.parseInt(role[2]) + 4,
                                    img.getIconWidth(),
                                    img.getIconHeight()
                            );
                        }
                    }
                });
                //add role to the view
                DeadwoodFrame.getInstance().addComponentToFrame(roleLabel, 5);
                roleLabels.add(roleLabel);
                System.out.println("Added " + role[0]);
            }
        }
    }

    public static IntroFrame getIntroFrame() {
        return introFrame;
    }
    public static UpgradeFrame getUpgradeFrame() {
    	return upgradeFrame;
    }

    public HashMap<String, JLabel> getPlayerMap(){
        return playerMap;
    }

    public HashMap<String, Queue<JLabel>> getShotCounterMap() {
        return shotCounterMap;
    }

    public void removeShotCounter(String setName) {
        if(setName.equals("Trailer")){
            //the day just ended, so we can just ignore, the rest will handle it
        }else{
            JLabel shotCounter = this.shotCounterMap.get(setName).remove();
            shotCounter.setVisible(false);
            this.getShotCounterMap().get(setName).add(shotCounter);
            Room room = currPlayer.getCurrentRoom();
            if(((Set)room).getShotsLeft() == 0) {
                if(!((Set)room).getCurrScene().isWrapped()) {
                    ((Set) room).getCurrScene().wrap();
                }
                DeadwoodFrame.getInstance().removeComponentFromFrame(((Set) room).getCurrScene().getSceneName());
            }
        }
    }


    public boolean isBlockAction() {
        return blockAction;
    }

    public boolean blockAction() {
        return blockAction = true;
    }

    public Player getCurrPlayer(){
        return currPlayer;
    }

    public void resetBoard() {
        //remove all scene cards
        for(JLabel sceneCard : roomSceneCardMap.values()) {
            if(sceneCard != null)
                sceneCard.setVisible(false);
                deadwoodFrame.removeComponentFromFrame(sceneCard.getName());
        }
        //remove all on-card roles
        for(JLabel role : roleLabels) {
            role.setVisible(false);
        }
        //reset all shot counters
        for(Queue<JLabel> shotCounterQueue : shotCounterMap.values()) {
            for(JLabel shotCounter : shotCounterQueue) {
                shotCounter.setVisible(true);
            }
        }
        //only do the rest if there are days left!
        if(GameSystem.getInstance().getDaysLeft() >= 0) {
            createSceneLabels();
        }
        //move players to trailers (just redisplay)
        displayPlayers();

    }

    public void Upgrade(int rank, boolean credit) {
        int rankCostDollar = costs[rank - 2][0];
        int rankCostCredit = costs[rank - 2][1];
        if (!credit && currPlayer.getMoney() >= rankCostDollar) {
            currPlayer.updateRank(rank);
            currPlayer.payMoney(rankCostDollar);

            String color = currPlayer.getColor().substring(0, 1);
            String rankStr = Integer.toString(currPlayer.getRank());
            //example b1.png (color: blue, rank: 1)
            String image = "Resources/dice/" + color + rankStr + ".png";
            ImageIcon playerDiceIcon = new ImageIcon(((new ImageIcon(image)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

            playerMap.get(currPlayer.getColor()).setIcon(playerDiceIcon);
            System.out.println("PLAYER UPGRADED" + currPlayer.getRank());
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You are now rank: " + currPlayer.getRank(),
                    "Success!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        } else if (!credit && currPlayer.getCredits() < rankCostDollar) {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You do not have sufficient funds. ",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        }
        if (credit && currPlayer.getCredits() >= rankCostCredit) {
            currPlayer.updateRank(rank);
            currPlayer.payCredits(rankCostCredit);

            String color = currPlayer.getColor().substring(0, 1);
            String rankStr = Integer.toString(currPlayer.getRank());
            //example b1.png (color: blue, rank: 1)
            String image = "Resources/dice/" + color + rankStr + ".png";
            ImageIcon playerDiceIcon = new ImageIcon(((new ImageIcon(image)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

            playerMap.get(currPlayer.getColor()).setIcon(playerDiceIcon);
            System.out.println("PLAYER UPGRADED" + currPlayer.getRank());
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You are now rank: " + currPlayer.getRank(),
                    "Success!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        } else if (credit && currPlayer.getCredits() < rankCostCredit) {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), "You do not have sufficient funds. ",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE, playerMap.get(currPlayer.getColor()).getIcon());
        }
        updateActivePlayerGUI();
    }

}

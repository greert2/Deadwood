package Presentation.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;

import java.util.ArrayList;

import Presentation.Listeners.*;
import Model.*;

public class DeadwoodFrame extends JFrame {
    private static DeadwoodFrame instance;

    private JLabel labelGameBoard;
    private JLabel labelCard;
    private JLabel labelPlayer;
    private JLabel labelMenu;
    private JLabel labelCurrPlayerImg;
    private JLabel labelActivePlayer;

    private JButton buttonAct;
    private JButton buttonRehearse;
    private JButton buttonMove;

    private JButton buttonHotel;
    private JButton buttonChurch;
    private JButton buttonJail;
    private JButton buttonTrainStation;
    private JButton buttonGeneralStore;
    private JButton buttonCastingOffice;
    private JButton buttonRanch;
    private JButton buttonSecretHideout;
    private JButton buttonMainStreet;
    private JButton buttonSaloon;
    private JButton buttonBank;
    private JButton buttonTrailer;


    private JLayeredPane paneDeadwood;

    private ImageIcon iconGameBoard;

    private static final String DEADWOOD_TITLE = "Deadwood";
    private static final String GAME_BOARD_IMAGE = "Resources/board.jpg";
    private static final String CARD_IMAGE = "Resources/cards/01.png";
    private static final String DICE_IMAGE = "Resources/dice/r2.png";
    private static final String MENU_LABEL_TEXT = "Menu";
    private static final String ACT_BUTTON_TEXT = "Act";
    private static final String REHEARSE_BUTTON_TEXT = "Rehearse";
    private static final String MOVE_BUTTON_TEXT = "Move";
    private static final String ACTIVE_PLAYER_LABEL_TEXT = "Active Player";

    private ArrayList<JLabel> playerLabels;


    private DeadwoodFrame() {
        super(DEADWOOD_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeLabels();
        initializeButtons();
        initializeDeadwoodPane();
    }

    public static DeadwoodFrame getInstance() {
        if(instance == null) {
            instance = new DeadwoodFrame();
        }
        return instance;
    }


    private void initializeLabels() {
        setupGameBoardLabel();
        setupCardLabel();
        setupPlayerLabel();
        setupMenuLabel();
        setupActivePlayerLabel(); //picture
        setupCurrPlayerImgLabel(); //image
    }

    private void setupGameBoardLabel() {
        labelGameBoard = new JLabel();
        iconGameBoard = new ImageIcon(GAME_BOARD_IMAGE);
        labelGameBoard.setIcon(iconGameBoard);
        labelGameBoard.setBounds(0, 0, iconGameBoard.getIconWidth(), iconGameBoard.getIconHeight());
        setSize(iconGameBoard.getIconWidth() + 200, iconGameBoard.getIconHeight()); // Set the size of the GUI
    }

    private void setupCardLabel() {
        labelCard = new JLabel();
        ImageIcon cardIcon = new ImageIcon(CARD_IMAGE);
        labelCard.setIcon(cardIcon);
        labelCard.setBounds(20, 65, cardIcon.getIconWidth() + 2, cardIcon.getIconHeight());
        labelCard.setOpaque(true);
    }

    private void setupPlayerLabel() {
        labelPlayer = new JLabel();
        ImageIcon playerDiceIcon = new ImageIcon(DICE_IMAGE);
        labelPlayer.setIcon(playerDiceIcon);
        labelPlayer.setBounds(114, 227, playerDiceIcon.getIconWidth(), playerDiceIcon.getIconHeight());
    }

    //This is a view of the current player (in the right side panel)
    private void setupCurrPlayerImgLabel() {
        labelCurrPlayerImg = new JLabel();
        ImageIcon playerDiceIcon = new ImageIcon(DICE_IMAGE);
        labelCurrPlayerImg.setIcon(playerDiceIcon);
        labelCurrPlayerImg.setBounds(iconGameBoard.getIconWidth() + 45, 30, playerDiceIcon.getIconWidth(), playerDiceIcon.getIconHeight());
    }

    private void setupActivePlayerLabel() {
        labelActivePlayer = new JLabel(ACTIVE_PLAYER_LABEL_TEXT);
        labelActivePlayer.setBounds(iconGameBoard.getIconWidth() + 30, 0, 100, 20);
    }

    private void setupMenuLabel() {
        labelMenu = new JLabel(MENU_LABEL_TEXT);
        labelMenu.setBounds(iconGameBoard.getIconWidth() + 40, 160, 100, 20);
    }

    private void initializeButtons() {
        setupActButton();
        setupRehearseButton();
        setupMoveButton();
        setupRoomButtons();
    }

    private void setupActButton() {
        buttonAct = new JButton(ACT_BUTTON_TEXT);
        buttonAct.setBackground(Color.white);
        buttonAct.setBounds(iconGameBoard.getIconWidth() + 10, 190, 100, 20);
        buttonAct.addMouseListener(new ActButtonMouseListener());
        buttonAct.setOpaque(true);
    }

    private void setupRehearseButton() {
        buttonRehearse = new JButton(REHEARSE_BUTTON_TEXT);
        buttonRehearse.setBackground(Color.white);
        buttonRehearse.setBounds(iconGameBoard.getIconWidth() + 10, 220, 100, 20);
        buttonRehearse.addMouseListener(new RehearseButtonMouseListener());
    }

    private void setupMoveButton() {
        buttonMove = new JButton(MOVE_BUTTON_TEXT);
        buttonMove.setBackground(Color.white);
        buttonMove.setBounds(iconGameBoard.getIconWidth() + 10, 250, 100, 20);
        buttonMove.addMouseListener(new MoveButtonMouseListener());
        //buttonMove.setVisible(false);
    }

    private void setupRoomButtons() {
        buttonHotel = new JButton("HOTEL");
        buttonHotel.setOpaque(true);
        buttonHotel.setBackground(Color.orange);
        buttonHotel.setBounds(1025, 860, 100, 35);
//        buttonHotel.setVisible(false);

        buttonChurch = new JButton("CHURCH");
        buttonChurch.setOpaque(true);
        buttonChurch.setBackground(Color.orange);
        buttonChurch.setBounds(675, 855, 100, 35);

        buttonJail = new JButton("JAIL");
        buttonJail.setOpaque(true);
        buttonJail.setBackground(Color.orange);
        buttonJail.setBounds(315, 150, 100, 35);

        buttonTrainStation = new JButton("TRAIN STATION");
        buttonTrainStation.setOpaque(true);
        buttonTrainStation.setBackground(Color.orange);
        buttonTrainStation.setBounds(29, 190, 180, 40);

        buttonGeneralStore = new JButton("GENERAL STORE");
        buttonGeneralStore.setOpaque(true);
        buttonGeneralStore.setBackground(Color.orange);
        buttonGeneralStore.setBounds(378, 402, 185, 40);

        buttonCastingOffice = new JButton("CASTING OFFICE");
        buttonCastingOffice.setOpaque(true);
        buttonCastingOffice.setBackground(Color.orange);
        buttonCastingOffice.setBounds(22, 468, 185, 40);

        buttonSecretHideout = new JButton("SECRET HIDEOUT");
        buttonSecretHideout.setOpaque(true);
        buttonSecretHideout.setBackground(Color.orange);
        buttonSecretHideout.setBounds(26, 853, 200, 40);

        buttonRanch = new JButton("RANCH");
        buttonRanch.setOpaque(true);
        buttonRanch.setBackground(Color.orange);
        buttonRanch.setBounds(289, 597, 100, 40);

        buttonMainStreet = new JButton("MAIN STREET");
        buttonMainStreet.setOpaque(true);
        buttonMainStreet.setBackground(Color.orange);
        buttonMainStreet.setBounds(993, 148, 175, 40);

        buttonSaloon = new JButton("SALOON");
        buttonSaloon.setOpaque(true);
        buttonSaloon.setBackground(Color.orange);
        buttonSaloon.setBounds(686, 402, 100, 40);

        buttonTrailer = new JButton("TRAILERS");
        buttonTrailer.setOpaque(true);
        buttonTrailer.setBackground(Color.orange);
        buttonTrailer.setBounds(1033, 270, 120, 40);

        buttonBank = new JButton("BANK");
        buttonBank.setOpaque(true);
        buttonBank.setBackground(Color.orange);
        buttonBank.setBounds(688, 594, 100, 40);
    }

    private void initializeDeadwoodPane() {
        paneDeadwood = getLayeredPane();
        paneDeadwood.add(labelGameBoard, new Integer(0)); // Add the board to the lowest layer
        //paneDeadwood.add(labelCard, new Integer(1)); // Add the card to the lower layer
        //paneDeadwood.add(labelPlayer, new Integer(3));
        paneDeadwood.add(labelCurrPlayerImg, new Integer(2));
        paneDeadwood.add(labelActivePlayer, new Integer(2));
        paneDeadwood.add(labelMenu, new Integer(2));

        paneDeadwood.add(buttonAct, new Integer(2));
        paneDeadwood.add(buttonRehearse, new Integer(2));
        paneDeadwood.add(buttonMove, new Integer(2));

        paneDeadwood.add(buttonHotel, new Integer(2));
        paneDeadwood.add(buttonChurch, new Integer(2));
        paneDeadwood.add(buttonJail, new Integer(2));
        paneDeadwood.add(buttonTrainStation, new Integer(2));
        paneDeadwood.add(buttonGeneralStore, new Integer(2));
        paneDeadwood.add(buttonCastingOffice, new Integer(2));
        paneDeadwood.add(buttonSecretHideout, new Integer(2));
        paneDeadwood.add(buttonRanch, new Integer(2));
        paneDeadwood.add(buttonMainStreet, new Integer(2));
        paneDeadwood.add(buttonSaloon, new Integer(2));
        paneDeadwood.add(buttonTrailer, new Integer(2));
        paneDeadwood.add(buttonBank, new Integer(2));
    }

    public JLayeredPane getPaneDeadwood(){
        return paneDeadwood;
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
                    paneDeadwood.moveToFront(p);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    paneDeadwood.moveToBack(p);
                }
            });
            paneDeadwood.add(p, new Integer(3));
            offset += 20;
            //JOptionPane.showMessageDialog(this,  "This is a test of the JOptionPane!", "Success!", JOptionPane.INFORMATION_MESSAGE, playerDiceIcon);
            //996,414
        }
    }

    public void addComponentToFrame(Component comp, int layer) {
        paneDeadwood.add(comp, new Integer(layer));
    }
}

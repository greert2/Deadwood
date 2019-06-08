package Presentation.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    private JLabel labelActivePlayerMoney;
    private JLabel labelActivePlayerCredits;
    private JLabel labelActivePlayerRehearsals;

    private JButton buttonAct;
    private JButton buttonRehearse;
    private JButton buttonEnd;
    private JButton buttonUpgrade;

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

    //private Color bgColor = new Color(254,243,237);
    private Color bgColor = new Color(182,113,61);

    private JLayeredPane paneDeadwood;

    private ImageIcon iconGameBoard;

    private static final String DEADWOOD_TITLE = "Deadwood";
    private static final String GAME_BOARD_IMAGE = "Resources/board.jpg";
    private static final String CARD_IMAGE = "Resources/cards/01.png";
    private static final String DICE_IMAGE = "Resources/dice/r2.png";
    private static final String MENU_LABEL_TEXT = "Menu";
    private static final String ACT_BUTTON_TEXT = "Act";
    private static final String REHEARSE_BUTTON_TEXT = "Rehearse";
    private static final String UPGRADE_BUTTON_TEXT = "Upgrade";
    //private static final String MOVE_BUTTON_TEXT = "Move";
    private static final String ACTIVE_PLAYER_LABEL_TEXT = "Active Player";

    private ArrayList<JLabel> playerLabels;
    private HashMap<String, JButton> roomMap = new HashMap<String, JButton>(); //<roomName, room button>


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
        setupActivePlayerLabelMoney(); //money
        setupActivePlayerLabelCredits(); //credits
        setupActivePlayerLabelRehearsals(); //rehearsal chips
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
        labelCurrPlayerImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Player p = Controller.getInstance().getCurrPlayer();
                p.addCredits(100);
                p.addMoney(100);
                p.updateRank(6);
                //Controller.getInstance().resetBoard();
                System.out.println("TEST");
                Controller.getInstance().displayPlayers();
                Controller.getInstance().updateRanks();
                Controller.getInstance().updateActivePlayerGUI();
            }
        });

    }

    private void setupActivePlayerLabel() {
        labelActivePlayer = new JLabel(ACTIVE_PLAYER_LABEL_TEXT);
        labelActivePlayer.setBounds(iconGameBoard.getIconWidth() + 30, 0, 100, 20);
    }

    private void setupActivePlayerLabelMoney() {
        labelActivePlayerMoney = new JLabel("$0");
        labelActivePlayerMoney.setBounds(iconGameBoard.getIconWidth() + 20, 75, 100, 20);
    }

    private void setupActivePlayerLabelCredits() {
        labelActivePlayerCredits = new JLabel("0 credits");
        labelActivePlayerCredits.setBounds(iconGameBoard.getIconWidth() + 20, 95, 100, 20);
    }

    private void setupActivePlayerLabelRehearsals() {
        labelActivePlayerRehearsals = new JLabel("0 chips");
        labelActivePlayerRehearsals.setBounds(iconGameBoard.getIconWidth() + 20, 115, 100, 20);
    }

    private void setupMenuLabel() {
        labelMenu = new JLabel(MENU_LABEL_TEXT);
        labelMenu.setBounds(iconGameBoard.getIconWidth() + 40, 190, 100, 20);
    }

    private void initializeButtons() {
        setupActButton();
        setupRehearseButton();
        setupEndButton();
        setupRoomButtons();
        setupUpgradeButton();
    }

    private void setupActButton() {
        buttonAct = new JButton(ACT_BUTTON_TEXT);
        buttonAct.setBackground(Color.white);
        buttonAct.setBounds(iconGameBoard.getIconWidth() + 10, 220, 110, 20);
        buttonAct.addMouseListener(new ActButtonMouseListener());
        buttonAct.setOpaque(true);
    }

    private void setupRehearseButton() {
        buttonRehearse = new JButton(REHEARSE_BUTTON_TEXT);
        buttonRehearse.setBackground(Color.white);
        buttonRehearse.setBounds(iconGameBoard.getIconWidth() + 10, 250, 110, 20);
        buttonRehearse.addMouseListener(new RehearseButtonMouseListener());
    }

    private void setupEndButton() {
        buttonEnd = new JButton("End Turn");
        buttonEnd.setBackground(Color.white);
        buttonEnd.setBounds(iconGameBoard.getIconWidth() + 10, 310, 110, 20);
        buttonEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().endTurn();
            }
        });
    }
    private void setupUpgradeButton() {
    	buttonUpgrade = new JButton(UPGRADE_BUTTON_TEXT);
    	buttonUpgrade.setBackground(Color.white);
    	buttonUpgrade.setBounds(iconGameBoard.getIconWidth() + 10, 280, 110, 20);
    	buttonUpgrade.addMouseListener(new UpgradeButtonMouseListener());
    }

    private void setupRoomButtons() {
        buttonHotel = new JButton("HOTEL");
        buttonHotel.setOpaque(true);
        buttonHotel.setBackground(bgColor);
        buttonHotel.setBounds(1025, 860, 100, 35);
//        buttonHotel.setVisible(false);
        roomMap.put("Hotel", buttonHotel);
        buttonHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Hotel");
            }
        });

        buttonChurch = new JButton("CHURCH");
        buttonChurch.setOpaque(true);
        buttonChurch.setBackground(bgColor);
        buttonChurch.setBounds(675, 855, 100, 35);
        roomMap.put("Church", buttonChurch);
        buttonChurch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Church");
            }
        });

        buttonJail = new JButton("JAIL");
        buttonJail.setOpaque(true);
        buttonJail.setBackground(bgColor);
        buttonJail.setBounds(315, 150, 100, 35);
        roomMap.put("Jail", buttonJail);
        buttonJail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Jail");
            }
        });

        buttonTrainStation = new JButton("TRAIN STATION");
        buttonTrainStation.setOpaque(true);
        buttonTrainStation.setBackground(bgColor);
        buttonTrainStation.setBounds(29, 190, 180, 40);
        roomMap.put("Train Station", buttonTrainStation);
        buttonTrainStation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Train Station");
            }
        });

        buttonGeneralStore = new JButton("GENERAL STORE");
        buttonGeneralStore.setOpaque(true);
        buttonGeneralStore.setBackground(bgColor);
        buttonGeneralStore.setBounds(378, 402, 185, 40);
        roomMap.put("General Store", buttonGeneralStore);
        buttonGeneralStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("General Store");
            }
        });

        buttonCastingOffice = new JButton("CASTING OFFICE");
        buttonCastingOffice.setOpaque(true);
        buttonCastingOffice.setBackground(bgColor);
        buttonCastingOffice.setBounds(22, 468, 185, 40);
        roomMap.put("Casting Office", buttonCastingOffice);
        buttonCastingOffice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Casting Office");
            }
        });

        buttonSecretHideout = new JButton("SECRET HIDEOUT");
        buttonSecretHideout.setOpaque(true);
        buttonSecretHideout.setBackground(bgColor);
        buttonSecretHideout.setBounds(26, 853, 200, 40);
        roomMap.put("Secret Hideout", buttonSecretHideout);
        buttonSecretHideout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Secret Hideout");
            }
        });

        buttonRanch = new JButton("RANCH");
        buttonRanch.setOpaque(true);
        buttonRanch.setBackground(bgColor);
        buttonRanch.setBounds(289, 597, 100, 40);
        roomMap.put("Ranch", buttonRanch);
        buttonRanch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Ranch");
            }
        });

        buttonMainStreet = new JButton("MAIN STREET");
        buttonMainStreet.setOpaque(true);
        buttonMainStreet.setBackground(bgColor);
        buttonMainStreet.setBounds(993, 148, 175, 40);
        roomMap.put("Main Street", buttonMainStreet);
        buttonMainStreet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Main Street");
            }
        });

        buttonSaloon = new JButton("SALOON");
        buttonSaloon.setOpaque(true);
        buttonSaloon.setBackground(bgColor);
        buttonSaloon.setBounds(686, 402, 100, 40);
        roomMap.put("Saloon", buttonSaloon);
        buttonSaloon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Saloon");
            }
        });

        buttonTrailer = new JButton("TRAILERS");
        buttonTrailer.setOpaque(true);
        buttonTrailer.setBackground(bgColor);
        buttonTrailer.setBounds(1033, 270, 120, 40);
        roomMap.put("Trailer", buttonTrailer);
        buttonTrailer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Trailer");
            }
        });

        buttonBank = new JButton("BANK");
        buttonBank.setOpaque(true);
        buttonBank.setBackground(bgColor);
        buttonBank.setBounds(688, 594, 100, 40);
        roomMap.put("Bank", buttonBank);
        buttonBank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().moveRoom("Bank");
            }
        });
    }

    private void initializeDeadwoodPane() {
        paneDeadwood = getLayeredPane();
        this.getContentPane().setBackground(bgColor);
        paneDeadwood.add(labelGameBoard, new Integer(0)); // Add the board to the lowest layer
        //paneDeadwood.add(labelCard, new Integer(1)); // Add the card to the lower layer
        //paneDeadwood.add(labelPlayer, new Integer(3));
        paneDeadwood.add(labelCurrPlayerImg, new Integer(2));
        paneDeadwood.add(labelActivePlayer, new Integer(2));
        paneDeadwood.add(labelMenu, new Integer(2));
        paneDeadwood.add(labelActivePlayerMoney, 2);
        paneDeadwood.add(labelActivePlayerCredits, 2);
        paneDeadwood.add(labelActivePlayerRehearsals, 2);

        paneDeadwood.add(buttonAct, new Integer(2));
        paneDeadwood.add(buttonRehearse, new Integer(2));
        paneDeadwood.add(buttonEnd, new Integer(2));
        paneDeadwood.add(buttonUpgrade, new Integer(2));

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

    public JLabel getLabelCurrPlayerImg(){
        return labelCurrPlayerImg;
    }

    public JLabel getLabelActivePlayerMoney() {
        return labelActivePlayerMoney;
    }

    public JLabel getLabelActivePlayerCredits() {
        return labelActivePlayerCredits;
    }

    public JLabel getLabelActivePlayerRehearsals() {
        return labelActivePlayerRehearsals;
    }


    public void addComponentToFrame(Component comp, int layer) {
        paneDeadwood.add(comp, new Integer(layer));
    }

    public void removeComponentFromFrame(String sceneName) {
        Component[] comps = paneDeadwood.getComponentsInLayer(1);
        for(int i = 0; i < comps.length; i++) {
            try {
                if (comps[i].getName().equals(sceneName)) {
                    comps[i].setVisible(false);
                    paneDeadwood.remove(comps[i]);
                }
            }catch(Exception e) {
                System.out.println("Couldn't find component to remove");
            }
        }
    }
}
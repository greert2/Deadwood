package Presentation.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import Presentation.Listeners.*;

public class IntroFrame extends JFrame {

    private int playerCnt;

    private JLabel labelMenu;
    private JLabel labelDescription;
    private JSlider slider;
    private JButton buttonPlay;

    private JLayeredPane paneDeadwood;

    private static final String DEADWOOD_TITLE = "Deadwood";


    public IntroFrame() {
        super(DEADWOOD_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setupMenuLabel();
        setupDescriptionLabel();
        setupSlider();
        setupPlayButton();
        initializePane();
    }

    private void setupMenuLabel() {
        labelMenu = new JLabel("Welcome to Deadwood!");
        labelMenu.setBounds(70, 10, 200, 20);
    }

    private void setupDescriptionLabel() {
        labelDescription  = new JLabel("Please select the number of players");
        labelDescription.setBounds(40, 40, 250, 20);
    }

    private void setupSlider() {
        slider = new JSlider(JSlider.HORIZONTAL, 2, 8, 2);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setBounds(25, 110, 250, 40);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                playerCnt = ((JSlider)e.getSource()).getValue();
            }
        });
    }


    private void setupPlayButton() {
        buttonPlay = new JButton("Play");
        buttonPlay.setBackground(Color.white);
        buttonPlay.setBounds(100, 200, 100, 20);
        buttonPlay.addMouseListener(new PlayButtonMouseListener());
    }


    private void initializePane() {
        paneDeadwood = getLayeredPane();
        paneDeadwood.add(labelMenu, new Integer(1));
        paneDeadwood.add(labelDescription, new Integer(1));

        paneDeadwood.add(slider, new Integer(1));

        paneDeadwood.add(buttonPlay, new Integer(1));

    }

    public int getPlayerCnt() {
        return playerCnt;
    }
}

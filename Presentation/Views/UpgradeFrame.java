package Presentation.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import Presentation.Listeners.*;

public class UpgradeFrame extends JFrame {
	
	private int newRank;
	private String paymentType;
	
	private JLabel labelRank;
	private JLabel labelPayment;
	private JButton buttonGo;
	private JSlider slider;
	
	private JCheckBox creditsBox;
	private JCheckBox dollarsBox;
	
	private JLayeredPane paneUpgrade;
	
	private static final String UPGRADE_TITLE = "Upgrade";
	
	public UpgradeFrame() {
		super(UPGRADE_TITLE);
		setupRankLabel();
		setupPaymentLabel();
		setupSlider();
		setupUpgradeButton();
		setupCreditsBox();
		setupDollarsBox();
		initializePane();
	}
	public void setupRankLabel() {
		labelRank = new JLabel("Select rank to upgrade to");
		labelRank.setBounds(70, 10, 220, 20);
	}
	
	private void setupSlider() {
        slider = new JSlider(JSlider.HORIZONTAL, 2, 6, 2);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setBounds(50, 50, 250, 40);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                newRank = ((JSlider)e.getSource()).getValue();
            }
        });
    }
	private void setupPaymentLabel() {
        labelPayment  = new JLabel("Select to pay by credits or dollars.");
        labelPayment.setBounds(50, 120, 350, 20);
    }
	
	private void setupCreditsBox() {
		creditsBox = new JCheckBox("Credits");
		creditsBox.setBounds(100, 150, 200, 15);
		creditsBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(creditsBox.isSelected()) {
					paymentType = "credits";
				}
			}
		});
	}
	private void setupDollarsBox() {
		dollarsBox = new JCheckBox("Dollars");
		dollarsBox.setBounds(100, 165, 200, 15);
		dollarsBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(dollarsBox.isSelected()) {
					paymentType = "money";
				}
			}
		});
	}
	
	private void setupUpgradeButton() {
		buttonGo = new JButton("Go");
		buttonGo.setBackground(Color.white);
		buttonGo.setBounds(100, 210, 100, 20);
		buttonGo.addMouseListener(new GoButtonMouseListener());
    }
	
	private void initializePane() {
		paneUpgrade = getLayeredPane();
		paneUpgrade.add(labelRank, new Integer(1));
		paneUpgrade.add(labelPayment, new Integer(1));

		paneUpgrade.add(slider, new Integer(1));

		paneUpgrade.add(buttonGo, new Integer(1));
		paneUpgrade.add(creditsBox);
		paneUpgrade.add(dollarsBox);

	    }
	public int getNewRank() {
		return newRank;
	} 
	public String getPaymentType() {
		return paymentType;
	}
	
	
}
package Presentation.Views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.*;

import Model.Controller;
import Model.Player;
import Presentation.Listeners.*;

public class UpgradeFrame extends JFrame {
	
	private int newRank;
	private String paymentType;
	
	private JLabel labelRank;
	private JLabel labelPayment;
	private JButton buttonGo;
	private JSlider slider;

	private ButtonGroup buttonGroup;
	private JRadioButton creditsBox;
	private JRadioButton dollarsBox;
	
	private JLayeredPane paneUpgrade;

	private int[][] costs = new int[][]{ {4,5},{10,10},{18,15},{28,20},{40,25}};

	private int desiredRank = 2;
	
	private static final String UPGRADE_TITLE = "Upgrade";
	
	public UpgradeFrame() {
		super(UPGRADE_TITLE);
		setupRankLabel();
		setupPaymentLabel();
		setupSlider();
		setupUpgradeButton();
		setupCreditsBox();
		setupDollarsBox();
		setupButtonGroup();
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
				desiredRank = ((JSlider)e.getSource()).getValue();

			}
		});
    }
	private void setupPaymentLabel() {
        labelPayment  = new JLabel("Select to pay by credits or dollars.");
        labelPayment.setBounds(50, 120, 350, 20);
    }
	
	private void setupCreditsBox() {
		creditsBox = new JRadioButton("Credits");
		creditsBox.setBounds(100, 150, 200, 15);
		creditsBox.setSelected(true);
	}
	private void setupDollarsBox() {
		dollarsBox = new JRadioButton("Dollars");
		dollarsBox.setBounds(100, 170, 200, 15);
	}

	private void setupButtonGroup() {
		buttonGroup = new ButtonGroup();
		buttonGroup.add(creditsBox);
		buttonGroup.add(dollarsBox);
	}

	private void setupUpgradeButton() {
		buttonGo = new JButton("Go");
		buttonGo.setBackground(Color.white);
		buttonGo.setBounds(100, 210, 100, 20);

		buttonGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Controller.getInstance().Upgrade(desiredRank, creditsBox.isSelected());
			}
		});
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


	public int[][] getCosts() {
		return costs;
	}
}
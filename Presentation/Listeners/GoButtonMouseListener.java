package Presentation.Listeners;

import Model.Controller;
import Model.GameSystem;
import Model.Player;
import Model.CastingOffice;
import Presentation.Views.DeadwoodFrame;
import Presentation.Views.UpgradeFrame;


import javax.swing.*;
import java.awt.event.MouseEvent;

public class GoButtonMouseListener extends BaseDeadwoodMouseListener { 
    public void mouseClicked(MouseEvent e) {
    	Player currPlayer =  GameSystem.getInstance().getCurrPlayer();
        int newRank = Controller.getInstance().getUpgradeFrame().getNewRank() + 2;
        String paymentType = Controller.getInstance().getUpgradeFrame().getPaymentType();

        
        if(((CastingOffice)currPlayer.getCurrentRoom()).canUpgrade(currPlayer, newRank, paymentType)) {
        	((CastingOffice)currPlayer.getCurrentRoom()).selectUpgrade(currPlayer, paymentType + " " + Integer.toString(newRank));
        	
        	JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
        	"Upgraded!",
  	         "Success!", JOptionPane.INFORMATION_MESSAGE,
  	         Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }
        else {
        	JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
       			 "Upgrade Failed. Make sure you have enough credits/dollars and select rank above current rank.",
       	         "Failure!", JOptionPane.INFORMATION_MESSAGE,
       	         Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }
        
        Controller.getInstance().updateActivePlayerGUI();
        //Controller.getInstance().createPlayerLabels();
        Controller.getInstance().displayPlayers();
	}
}
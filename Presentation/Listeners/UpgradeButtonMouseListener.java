package Presentation.Listeners;

import Model.Controller;
import Model.GameSystem;
import Model.Player;
import Model.CastingOffice;
import Presentation.Views.DeadwoodFrame;
import Presentation.Views.UpgradeFrame;


import javax.swing.*;
import java.awt.event.MouseEvent;

public class UpgradeButtonMouseListener extends BaseDeadwoodMouseListener {
	public void mouseClicked(MouseEvent e) {
		Player currPlayer =  GameSystem.getInstance().getCurrPlayer();
		if (currPlayer.getCurrentRoom() instanceof CastingOffice) {
			Controller.getInstance().showUpgradeFrame();
		}
		else {
			JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
	                "You must be in the Casting Office to upgrade! ",
	                "Failure!", JOptionPane.INFORMATION_MESSAGE,
	                Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
		}
	}

	
}

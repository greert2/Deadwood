package Presentation.Listeners;

import Model.Controller;
import Model.GameSystem;
import Model.Player;
import Presentation.Views.DeadwoodFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class RehearseButtonMouseListener extends BaseDeadwoodMouseListener {

    public void mouseClicked(MouseEvent e) {
        Player currPlayer =  GameSystem.getInstance().getCurrPlayer();
        if(currPlayer.getRole() != null) {
            //player has role, so can rehearse
            currPlayer.rehearse();
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You have successfully rehearsed. You now have " + currPlayer.getRehearseChips() + " chips.",
                    "Success!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }else{
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You must have a role to rehearse! ",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }
    }
}

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
        Controller controller = Controller.getInstance();
        if(!controller.isBlockAction() && currPlayer.getRole() != null) {
            //player has role, so can rehearse
            currPlayer.rehearse();
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You have successfully rehearsed. You now have " + currPlayer.getRehearseChips() + " chips.",
                    "Success!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
            //player attempted to rehearse so block
            Controller.getInstance().blockAction();
        }else if(controller.isBlockAction()) {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You must wait for your next turn to rehearse.",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }else{
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You must have a role to rehearse! ",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }
    }
}

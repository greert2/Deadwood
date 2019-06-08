package Presentation.Listeners;

import Model.Controller;
import Model.GameSystem;
import Model.Player;
import Presentation.Views.DeadwoodFrame;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ActButtonMouseListener extends BaseDeadwoodMouseListener {
    private final String ACT_SELECT_MSG = "Acting is Selected\n";

    public void mouseClicked(MouseEvent e) {
        Player currPlayer =  GameSystem.getInstance().getCurrPlayer();
        Controller controller = Controller.getInstance();
        if(!controller.isBlockAction() && currPlayer.getRole() != null) {
            //player has role, so can act
            String output = currPlayer.act();
            if(output != null) {
                JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                        output,
                        "Acting", JOptionPane.INFORMATION_MESSAGE,
                        Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
            }else{
                JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                        output,
                        "Acting", JOptionPane.INFORMATION_MESSAGE,
                        Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
            }
            //player had role and attempted to act, so lets remove a shot counter
            //remove the first shot counter in this set's queue of shot counters (GUI)
            //Controller.getInstance().getShotCounterMap().get(currPlayer.getCurrentRoom()).remove().setVisible(false);
            Controller.getInstance().removeShotCounter(currPlayer.getCurrentRoom().getRoomName());
            //player attempted to act so block
            Controller.getInstance().blockAction();
        }else if(controller.isBlockAction()) {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You must wait for your next turn to act.",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }else {
            JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),
                    "You must have a role to act! ",
                    "Failure!", JOptionPane.INFORMATION_MESSAGE,
                    Controller.getInstance().getPlayerMap().get(currPlayer.getColor()).getIcon());
        }
        currPlayer.resetRehearseChips();
        Controller.getInstance().displayPlayers();
        Controller.getInstance().updateActivePlayerGUI();
    }
}

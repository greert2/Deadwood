package Presentation.Listeners;

import Presentation.Views.DeadwoodFrame;

import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;

public class ActButtonMouseListener extends BaseDeadwoodMouseListener {
    private final String ACT_SELECT_MSG = "Acting is Selected\n";

    public void mouseClicked(MouseEvent e) {
        System.out.println(ACT_SELECT_MSG);

        //JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(),  "This is a test of the JOptionPane!", "Success!", JOptionPane.INFORMATION_MESSAGE, playerDiceIcon);
    }
}

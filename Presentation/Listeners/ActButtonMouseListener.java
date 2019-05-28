package Presentation.Listeners;

import java.awt.event.MouseEvent;

public class ActButtonMouseListener extends BaseDeadwoodMouseListener {
    private final String ACT_SELECT_MSG = "Acting is Selected\n";

    public void mouseClicked(MouseEvent e) {
        System.out.println(ACT_SELECT_MSG);
    }
}

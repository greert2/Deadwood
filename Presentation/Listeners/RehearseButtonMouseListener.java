package Presentation.Listeners;

import java.awt.event.MouseEvent;

public class RehearseButtonMouseListener extends BaseDeadwoodMouseListener {
    private final String REHEARSE_SELECT_MSG = "Rehearse is Selected\n";

    public void mouseClicked(MouseEvent e) {
        System.out.println(REHEARSE_SELECT_MSG);
    }
}

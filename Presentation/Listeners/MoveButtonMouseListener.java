package Presentation.Listeners;

import java.awt.event.MouseEvent;

public class MoveButtonMouseListener extends BaseDeadwoodMouseListener {
    private final String MOVE_SELECT_MSG = "Move is Selected\n";

    public void mouseClicked(MouseEvent e) {
        System.out.println(MOVE_SELECT_MSG);
    }
}

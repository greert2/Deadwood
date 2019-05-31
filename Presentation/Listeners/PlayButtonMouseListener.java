package Presentation.Listeners;

import java.awt.event.MouseEvent;
import Model.Controller;

public class PlayButtonMouseListener extends BaseDeadwoodMouseListener {
    private final String ACT_SELECT_MSG = "Acting is Selected\n";

    public void mouseClicked(MouseEvent e) {
        Controller.getInstance().showMainFrame();
    }
}

package Presentation.Listeners;

import java.awt.event.MouseEvent;
import Model.Controller;
import Model.GameSystem;

public class PlayButtonMouseListener extends BaseDeadwoodMouseListener {

    public void mouseClicked(MouseEvent e) {
        int playerCnt = Controller.getInstance().getIntroFrame().getPlayerCnt();
        if(playerCnt == 0) {
            playerCnt = 2;
        }
        GameSystem.getInstance().startGameGUI(playerCnt);
        Controller.getInstance().showMainFrame();
    }
}

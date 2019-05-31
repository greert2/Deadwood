package Model;

import Presentation.Views.DeadwoodFrame;
import Presentation.Views.IntroFrame;
import Presentation.Views.XMLParser;

import java.util.ArrayList;

public class Controller {

    private static Controller instance;
    private static IntroFrame introFrame;
    private static DeadwoodFrame deadwoodFrame;

    private Controller() {}

    public static void main(String[] args) {
        introFrame = new IntroFrame();
        introFrame.setSize(300,270);
        introFrame.setResizable(false);
        introFrame.setVisible(true);
    }

    public void showMainFrame(){
        introFrame.setVisible(false);
        deadwoodFrame = DeadwoodFrame.getInstance();
        deadwoodFrame.setSize(1336, 920);
        deadwoodFrame.setResizable(false);
        deadwoodFrame.setVisible(true);

        /* Create Players */
        DeadwoodFrame.getInstance().createPlayerLabels();
        //displayPlayers();
        XMLParser.getInstance().printInfo();
    }

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void displayPlayers(){
        ArrayList<Player> players = GameSystem.getInstance().getPlayerList();

        //deadwoodFrame.
        //for(int i = 0;)
    }

    public static IntroFrame getIntroFrame() {
        return introFrame;
    }
}

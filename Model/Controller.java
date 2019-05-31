package Model;

import Presentation.Views.DeadwoodFrame;
import Presentation.Views.IntroFrame;
import Presentation.Views.XMLParser;

public class Controller {

    private static Controller instance;
    private static IntroFrame introFrame;

    private Controller() {}

    public static void main(String[] args) {
        introFrame = new IntroFrame();
        introFrame.setSize(300,270);
        introFrame.setResizable(false);
        introFrame.setVisible(true);
    }

    public void showMainFrame(){
        introFrame.setVisible(false);
        DeadwoodFrame frame = new DeadwoodFrame();
        frame.setSize(1336, 920);
        frame.setResizable(false);
        frame.setVisible(true);

        XMLParser.getInstance().printInfo();
    }

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

}

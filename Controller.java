import Presentation.Views.DeadwoodFrame;
import Presentation.Views.XMLParser;

import javax.swing.*;

public class Controller {
    public static void main(String[] args) {
        DeadwoodFrame frame = new DeadwoodFrame();
        frame.setSize(1336, 920);
        frame.setResizable(false);
        frame.setVisible(true);

        XMLParser.getInstance().printInfo();
    }



}

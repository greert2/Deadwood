package Model;

import Presentation.Views.DeadwoodFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Scene {

    private Role[] roles;
    private int budget;
    private boolean visited;
    private boolean wrapped = false;
    private String sceneName;
    private String description;


    //Scenes can either have two or three roles, so two constructors
    public Scene(String sceneName, String description, int budget, String role1, String role2, String role3) {
        roles = new Role[3];
        String arr[];
        this.sceneName = sceneName;
        this.description = description;
        this.budget = budget;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        roles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        roles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role3.split("_", 3);
        roles[2] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Scene(String sceneName, String description, int budget, String role1, String role2) {
        roles = new Role[2];
        String arr[];
        this.sceneName = sceneName;
        this.description = description;
        this.budget = budget;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        roles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        roles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Scene(String sceneName, String description, int budget, String role1) {
        roles = new Role[1];
        String arr[];
        this.sceneName = sceneName;
        this.description = description;
        this.budget = budget;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        roles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Role[] getRoles(){
        return roles;
    }

    public void visit() {
        visited = true;
        return;
    }

    public int getBudget() {
        return budget;
    }

    public void wrap() {
        //this wraps the scene. Removes it from board and gives bonuses if needed
        this.wrapped = true;
        boolean needToGiveBonus = false;
        ArrayList<Player> players;
        Room currRoom = null;
        Role[] roles = this.getRoles();
        //Check for players on the scene
        for(Role r : roles) {
            if(r.getPlayer() != null) {
                //If there are any players on the scene
                needToGiveBonus = true;
                currRoom = r.getPlayer().getCurrentRoom();
            }
        }

        if(needToGiveBonus) {
            String dialogMessage = ((Set)currRoom).givePlayersMainBonus();
            dialogMessage += ((Set)currRoom).givePlayersExtraBonus();
            if(Controller.getInstance().getPlayerMap().size() > 0){
                //playerMap isn't empty, which means we are in GUI mode
                //display a dialog to show what
                JOptionPane.showMessageDialog(DeadwoodFrame.getInstance(), dialogMessage,
                        "Bonuses!", JOptionPane.INFORMATION_MESSAGE,
                        Controller.getInstance().getPlayerMap().get(
                                Controller.getInstance().getCurrPlayer().getColor()).getIcon());
            }
        }
        //Take the scene off the board
        Board.getInstance().removeScene(this);
        ArrayList<Player> playersInRoom = Controller.getInstance().getCurrPlayer().getCurrentRoom().getPlayersHere();
        for(Player p : playersInRoom) {
            p.updateRole(null);
        }

        return;
    }

    public String getDescription() {
        return description;
    }

    public String getSceneName() {
        return sceneName;
    }

    public boolean getVisited() {
        return visited;
    }

    public boolean isWrapped() {
        return wrapped;
    }

}

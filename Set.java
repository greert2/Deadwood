import java.util.Arrays;
import java.util.ArrayList;

public class Set extends Room{
    private Room[] adjacentRooms;
    private ArrayList<Player> playersHere;
    private String roomName;
    private Scene currScene;
    private int shotCounters;
    private boolean finished;
    private Role[] offCardRoles;

    public Set(String roomName) {
        this.roomName = roomName;
        this.finished = false;
    }

    public int getShotsLeft() {
        return shotCounters;
    }

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public String getRoomName() {
        return roomName;
    }

    public Scene getCurrScene() {
        return currScene;
    }

    public void setCurrScene(Scene scene) {
        this.currScene = scene;
    }

    public void removeShotCounter() {
        return;
    }

    public void giveRole(Role role, Player player) {
        return;
    }

    public void givePlayersMainBonus() {
        //roll 'budget' number of dice. Distribute to players (wraps around). Pay based on rolled numbers
        Dice dice = new Dice(); //make static?
        int[] rolls = dice.roll(this.getCurrScene().getBudget()); //roll 'budget' # of dice
        Arrays.sort(rolls);
        Role[] roles = this.getCurrScene().getRoles();
        int[] pointsForRoles; //array that adds dice amount when distributing among roles

        //pointsForRoles should hold added dice values for each role
        pointsForRoles = new int[roles.length];
        int rollsIx = 0;
        while(rollsIx < this.getCurrScene().getBudget()) { //distribute dice and add pts (check rulebook if unclear)
            for (int i = 0; i < roles.length; i++) {
                pointsForRoles[i] += rolls[rollsIx];
                rollsIx++;
            }
        }
        //give each player the correct points
        for(int i = 0; i < roles.length; i++) {
            roles[i].getPlayer().addMoney(pointsForRoles[i]);
        }
        return;
    }

    public void givePlayersExtraBonus() {
        //give each player on extra roles money equal to the rank req'd for their role
        for(int i = 0; i < this.getOffCardRoles().length; i++) {
            this.getOffCardRoles()[i].getPlayer().addMoney(this.getOffCardRoles()[i].getReqRank());
        }
        return;
    }

    public Role[] getOffCardRoles() {
        return offCardRoles;
    }
}


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

    public Set(String roomName, int shotCounters, String role1, String role2) {
        offCardRoles = new Role[2];
        String arr[];
        this.roomName = roomName;
        this.finished = false;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        offCardRoles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        offCardRoles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Set(String roomName, int shotCounters, String role1, String role2, String role3) {
        offCardRoles = new Role[3];
        String arr[];
        this.roomName = roomName;
        this.finished = false;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        offCardRoles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        offCardRoles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role3.split("_", 3);
        offCardRoles[2] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
    }

    public Set(String roomName, int shotCounters, String role1, String role2, String role3, String role4) {
        offCardRoles = new Role[4];
        String arr[];
        this.roomName = roomName;
        this.finished = false;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        offCardRoles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role2.split("_", 3);
        offCardRoles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role3.split("_", 3);
        offCardRoles[2] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
        arr = role4.split("_", 3);
        offCardRoles[3] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], true);
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


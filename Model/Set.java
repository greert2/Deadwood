package Model;

import java.util.Arrays;

public class Set extends Room{
    private Room[] adjacentRooms;
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
        this.shotCounters = shotCounters;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        offCardRoles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
        arr = role2.split("_", 3);
        offCardRoles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
    }

    public Set(String roomName, int shotCounters, String role1, String role2, String role3) {
        offCardRoles = new Role[3];
        String arr[];
        this.roomName = roomName;
        this.finished = false;
        this.shotCounters = shotCounters;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        offCardRoles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
        arr = role2.split("_", 3);
        offCardRoles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
        arr = role3.split("_", 3);
        offCardRoles[2] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
    }

    public Set(String roomName, int shotCounters, String role1, String role2, String role3, String role4) {
        offCardRoles = new Role[4];
        String arr[];
        this.roomName = roomName;
        this.finished = false;
        this.shotCounters = shotCounters;

        //[ReqRank_RoleName_Phrase/RoleName_Phrase...]
        arr = role1.split("_", 3);
        offCardRoles[0] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
        arr = role2.split("_", 3);
        offCardRoles[1] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
        arr = role3.split("_", 3);
        offCardRoles[2] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
        arr = role4.split("_", 3);
        offCardRoles[3] = new Role(arr[1], Integer.parseInt(arr[0]), arr[2], false);
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
        this.shotCounters -= 1;
        System.out.printf("%d shot counters remaining for this set.\n", this.getShotsLeft());
        if(this.getShotsLeft() == 0) {
            //The scene is finished and needs to be wrapped

            this.getCurrScene().wrap();
        }
        return;
    }

    public void giveRole(Role role, Player player) { //TODO: remove?
        return;
    }

    public void givePlayersMainBonus() {
        //roll 'budget' number of dice. Distribute to players (wraps around). Pay based on rolled numbers
        Role[] roles = this.getCurrScene().getRoles();

        Dice dice = new Dice();
        int[] rolls = dice.roll(this.getCurrScene().getBudget()); //roll 'budget' # of dice
        Arrays.sort(rolls); //put the rolls in order (least to greatest, we want opposite)
        int[] pointsForRoles; //array that adds dice amount when distributing among roles

        //pointsForRoles should hold added dice values for each role
        pointsForRoles = new int[roles.length];
        int rollsIx = rolls.length-1;
        while(rollsIx > 0) { //distribute dice and add pts (check rulebook if unclear)
            for (int i = 0; i < roles.length; i++) {
                pointsForRoles[i] += rolls[rollsIx];
                rollsIx--;
            }
        }
        //give each player the correct points
        for(int i = 0; i < roles.length; i++) {
            if(roles[i].getPlayer() != null){
                roles[i].getPlayer().addMoney(pointsForRoles[i]);
                System.out.printf("Player %s got $%d.\n", roles[i].getPlayer(), pointsForRoles[i]);
                roles[i].getPlayer().clearRole();
            }
        }

        return;
    }

    public void givePlayersExtraBonus() {
        //give each player on extra roles money equal to the rank req'd for their role
        Role[] roles = this.getOffCardRoles();
        for(int i = 0; i < roles.length; i++) {
            if(roles[i].getPlayer() != null){
                roles[i].getPlayer().addMoney(roles[i].getReqRank());
                roles[i].getPlayer().clearRole();
            }

        }
        return;
    }

    public Role[] getOffCardRoles() {
        return offCardRoles;
    }

    public void printInfo() {
        //prints the info for this Set
        System.out.println("The set has these off-card roles:");
        /* Print off-card roles */
        for(int i = 0; i < this.getOffCardRoles().length; i++) {
            System.out.println(i + ": " + this.getOffCardRoles()[i].getRoleInfo());
        }
        if(this.getCurrScene() != null){
            System.out.println("\nThe scene here is: '" + this.getCurrScene().getSceneName() + "'.");
            System.out.println("Description: " + this.getCurrScene().getDescription());
            System.out.println("It has on-card roles:");
            /* Cycle through on card roles, number them increasing from last time */
            for(int i = 0; i < this.getCurrScene().getRoles().length; i++) {
                System.out.println(i + ": " + this.getCurrScene().getRoles()[i].getRoleInfo());
            }
        }
    }

}


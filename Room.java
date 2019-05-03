public class Room extends Scene{
    private Scene currScene; //TODO: change on diagram
    private Room[] adjacentRooms;
    private int shotCounters;
    private boolean finished;

    public int getShotsLeft() {
        return shotCounters;
    }

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    private Room[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public void removeShotCounter() {
        return;
    }

//    public Role selectRole(Role role) {
//        Role selectedRole;
//        return selectedRole;
//    }

    public void giveRole(Role role, Player player) {
        return;
    }
}

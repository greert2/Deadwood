public class Set extends Room{
    private Room[] adjacentRooms;
    private Player[] playersHere;
    private Scene currScene;
    private int shotCounters;
    private boolean finished;

    public int getShotsLeft() {
        return shotCounters;
    }

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public void removeShotCounter() {
        return;
    }

    public void giveRole(Role role, Player player) {
        return;
    }
}

public class Set extends Room{
    private Room[] adjacentRooms;
//    private ArrayList<Player> playersHere;
    private String roomName;
    private Scene currScene;
    private int shotCounters;
    private boolean finished;

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
}

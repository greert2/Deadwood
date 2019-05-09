import java.util.ArrayList;

public abstract class Room {
    private Room[] adjacentRooms;
    private ArrayList<Player> playersHere;
    private String roomName;

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public String getRoomName() {
        return roomName;
    }
}

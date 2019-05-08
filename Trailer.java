public class Trailer extends Room{
    private Room[] adjacentRooms;
    private Player[] playersHere;
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

    public int calculateScore(Player player) {
        int score = 0;
        return score;
    }
}

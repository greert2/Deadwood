package Model;

public class Trailer extends Room{
    private Room[] adjacentRooms;
    private Player[] playersHere;
    private String roomName;

    public Trailer(String roomName, int[] playerCoords) {
        this.roomName = roomName;
        this.setPlayerCoords(playerCoords);
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

    public int calculateScore(Player player) {
        int score = 0;
        return score;
    }

    public void printInfo() {
        System.out.println("This is your starting location on the board");
    }
}

public class Trailer extends Room{
    private Room[] adjacentRooms;
    private Player[] playersHere;

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public int calculateScore(Player player) {
        int score = 0;
        return score;
    }
}

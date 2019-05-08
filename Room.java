public abstract class Room {
    private Room[] adjacentRooms;
    private Player[] playersHere;

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room[] getAdjacentRooms() {
        return adjacentRooms;
    }
}

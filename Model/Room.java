package Model;

import java.util.ArrayList;

public abstract class Room {
    private Room[] adjacentRooms;
    private ArrayList<Player> playersHere = new ArrayList<Player>();
    private String roomName;
    private int[] playerCoords;

    public void setAdjacentRooms(Room[] adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room[] getAdjacentRooms() {
        return adjacentRooms;
    }

    public String getRoomName() {
        return roomName;
    }

    public void visit(Player p) {
        playersHere.add(p);
        if(this instanceof Set) {
            ((Set)this).getCurrScene().visit();
        }
    }

    public void leave(Player p) { //TODO: ADD TO DIAGRAM
        playersHere.remove(p);
    }

    public abstract void printInfo();

    public int[] getPlayerCoords(){
        return playerCoords;
    }

    public void setPlayerCoords(int[] coords){
        this.playerCoords = coords;
    }
}

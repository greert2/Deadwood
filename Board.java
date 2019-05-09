public class Board {

    private int roomNum;
    private Room[] rooms;
    private int roomIx;

    public void startBoard() {
        createRooms();
        //Create scenes
        //Get random scenes

        return;
    }

    private void createRooms() {
        int ix = 0;
        //list of names of all the Sets
        String[] setNames = {"Saloon", "Main Street", "Hotel", "Bank", "Church", "Ranch", "Secret Hideout", "Jail",
                "Train Station", "General Store"};
        rooms = new Room[setNames.length + 2]; //plus two for Trailers and Casting Office
        for(ix = 0; ix < setNames.length; ix++) {
            rooms[ix] = new Set(setNames[ix]); //Store Sets polymorphically as Rooms so they can be with other types
        }

        rooms[ix] = new Trailer("Trailer");
        rooms[++ix] = new CastingOffice("Casting Office");

        for(Room r : rooms) {
            System.out.printf("%s\n", r.getRoomName()); //remove
        }
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Room getSpecificRoom(String room) { //TODO: add to diagram
        for(Room r : rooms) {
            if(r.getRoomName().equals(room)) {
                return r;
            }
        }
        return null;
    }

    public void removeScenes() {
        for(Room r : rooms) {
            if(r instanceof Set) {
                ((Set)r).setCurrScene(null);
            }
        }
        return;
    }

    public void resetBoard() {
        //TODO
        return;
    }

    public void placeScenes(Scene[] scenes) {
        //TODO: Place them on the board
        return;
    }

    public void endDay() {
        return;
    }

    public void updateRoomCnt() {
        //TODO
    }

    public Room[] getAdjacentRooms(Room room) {
        Room temp = getSpecificRoom(room.getRoomName());
        return temp.getAdjacentRooms();
    }

    public void removeRoom(Room room) {
        Room temp = getSpecificRoom(room.getRoomName());
        //TODO: remove I think, we should only ever get rid of the scenes
        return;
    }
}

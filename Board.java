import java.io.*;

public class Board {

    private int roomNum;
    private Room[] rooms;
    private Scene[] scenes; //TODO: add to diagram
    private int roomIx;

    public void startBoard() {
        createRooms();
        //Create scenes
        createScenes();
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

    public void createScenes() {
        final int TOTAL_SCENES = 40;
        BufferedReader br;
        String tempSceneName;
        String tempDescription;
        int tempBudget;
        int sceneIx = 0;
        scenes = new Scene[TOTAL_SCENES];

        try{
            //read in the file
            br = new BufferedReader(new FileReader("scenes.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                //Alternating syntax: First line is [SceneName/Budget/Description]
                String[] arr;
                arr = line.split("/", 3);
                tempSceneName = arr[0];
                tempBudget = Integer.parseInt(arr[1]);
                tempDescription = arr[2];

                // and next line is [ReqRank_RoleName_Phrase/RoleName_Phrase...]
                line = br.readLine();
                arr = line.split("/", 3);
                if(arr.length == 3) {
                    scenes[sceneIx] = new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1], arr[2]);
                }else{
                    scenes[sceneIx] = new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1]);
                }
                sceneIx++;
            }

        }catch(IOException e){
            System.out.println("Couldn't find scene file 'scene.txt");
            System.exit(1);
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

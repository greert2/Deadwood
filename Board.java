import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private int roomNum;
    //private Room[] rooms;
    private ArrayList<Room> rooms; //TODO: diagram.
    private ArrayList<Scene> scenes;
    private int sceneCnt;

    public void startBoard() {
        createRooms();
        //Create scenes
        createScenes();
        //Get random scenes
        placeScenes();
        createRoomAdjacencies();
        return;
    }

    private void createRooms() {
        rooms = new ArrayList<Room>();
        BufferedReader br;
        String tempRoomName;
        int tempShotCounters;
        int roomIx = 0;

        try{
            //read in the file
            br = new BufferedReader(new FileReader("rooms.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                //Alternating syntax: First line is [RoomName/ShotCounters]
                String[] arr;
                arr = line.split("/", 3);
                tempRoomName = arr[0];
                tempShotCounters = Integer.parseInt(arr[1]);
                //tempDescription = arr[2];

                // and next line is [ReqRank_RoleName_Phrase/ReqRank_RoleName_Phrase...]
                line = br.readLine();
                arr = line.split("/", 4);
                if(arr.length == 3) {
                    //scenes[sceneIx] = new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1], arr[2]);
                    rooms.add(new Set(tempRoomName, tempShotCounters, arr[0], arr[1], arr[2]));
                }else if(arr.length == 2){
                    //scenes[sceneIx] = new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1]);
                    rooms.add(new Set(tempRoomName, tempShotCounters, arr[0], arr[1]));
                }else if(arr.length == 4) {
                    rooms.add(new Set(tempRoomName, tempShotCounters, arr[0], arr[1], arr[2], arr[3]));
                }
                roomIx++;
            }

        }catch(IOException e){
            System.out.println("Couldn't find Room file 'rooms.txt");
            System.exit(1);
        }

        //Add remaining two specialty rooms
        rooms.add(new CastingOffice("Casting Office"));
        rooms.add(new Trailer("Trailer"));
    }


    private void createScenes() {
        //final int TOTAL_SCENES = 40;
        scenes = new ArrayList<Scene>();
        BufferedReader br;
        String tempSceneName;
        String tempDescription;
        int tempBudget;
        int sceneIx = 0;
        //scenes = new Scene[TOTAL_SCENES];

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
                    //scenes[sceneIx] = new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1], arr[2]);
                    scenes.add(new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1], arr[2]));
                }else if(arr.length == 2){
                    //scenes[sceneIx] = new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1]);
                    scenes.add(new Scene(tempSceneName, tempDescription, tempBudget, arr[0], arr[1]));
                }else if(arr.length == 1) {
                    scenes.add(new Scene(tempSceneName, tempDescription, tempBudget, arr[0]));
                }
                sceneIx++;
            }

        }catch(IOException e){
            System.out.println("Couldn't find scene file 'scene.txt");
            System.exit(1);
        }

    }

    private void createRoomAdjacencies(){
        //rooms = new ArrayList<Room>();
        BufferedReader br;
        String RoomName;
        Room[] adjRooms;
        int numAdjRooms;
        int roomIx = 0;

        try{
            //read in the file
            br = new BufferedReader(new FileReader("adjRooms.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                //Alternating syntax: First line is [RoomName:Room1/Room2...]
                String[] arr;
                arr = line.split(":", 2);
                RoomName = arr[0];
                line = arr[1]; // [Room1/Room2...]

                arr = line.split("/", 4);
                if(arr.length == 1) {
                    adjRooms = new Room[]{this.getSpecificRoom(arr[0])};
                }else if(arr.length == 2){
                    adjRooms = new Room[]{this.getSpecificRoom(arr[0]), this.getSpecificRoom(arr[1])};
                }else if(arr.length == 3) {
                    adjRooms = new Room[]{this.getSpecificRoom(arr[0]), this.getSpecificRoom(arr[1]),
                            this.getSpecificRoom(arr[2])};
                }else if(arr.length == 4) {
                    adjRooms = new Room[]{this.getSpecificRoom(arr[0]), this.getSpecificRoom(arr[1]),
                            this.getSpecificRoom(arr[2]), this.getSpecificRoom(arr[3])};
                }else {
                    adjRooms = null;
                }
                this.getSpecificRoom(RoomName).setAdjacentRooms(adjRooms);
                roomIx++;
            }

        }catch(IOException e){
            System.out.println("Couldn't find Adjacent Room file 'adjRooms.txt");
            System.exit(1);
        }
    }

    public ArrayList<Room> getRooms() { //TODO: diagram
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
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i) instanceof Set) {
                ((Set)rooms.get(i)).setCurrScene(null);
            }
        }
        return;
    }

    public void resetBoard() {
        ArrayList<Player> players = Deadwood.getPlayerList();
        for(Player p : players) {
            //move all players back to their trailers
            p.moveRoom(this.getSpecificRoom("Trailer"));
        }
        removeScenes();
        placeScenes();
        return;
    }



    public void placeScenes() {
        //Shuffle list, remove 10 scenes & place them on the board
        Collections.shuffle(scenes);
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i) instanceof Set) {
                ((Set)rooms.get(i)).setCurrScene(scenes.remove(0));
            }
        }
        return;
    }

    public void endDay() {
        removeScenes();
        placeScenes();
        //reset shotCounters
        resetSceneCnt();
        return;
    }

    public int updateSceneCnt() { //TODO: alter diagram
        this.sceneCnt--;
        return this.sceneCnt;
    }

    public void resetSceneCnt() {
        this.sceneCnt = 10;
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

    public ArrayList<Scene> getScenes() {
        return scenes;
    }
}

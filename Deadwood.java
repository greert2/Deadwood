import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class Deadwood {
    //Tyler: Room, Scene, Board, Casting Office
    //Brooks: Player, Role, System, Dice

    private static Queue<Player> players = new LinkedList<Player>();
    private static ArrayList<Player> playerList = new ArrayList<Player>();

    //should only expect 1 argument, number of players
    public static void main(String[] args) {
        int playerCnt = 0;

        if(args.length != 1) {
            System.out.println("Run with only [number of players].");
            System.exit(1); //throw exception?
        }else{
            /* Attempt to get number of players */
            try{
                playerCnt = Integer.parseInt(args[0]);
            }catch(NumberFormatException err) {
                System.out.println("Incorrect run. Run with only [number of players].");
                System.exit(1);
            }
        }

        /* Have player count */
        System.out.println("Welcome to Deadwood!");

        Board board = new Board();
        board.startBoard();
        //TODO:set up the board


        /* Set up players */
        for(int i = 0; i < playerCnt; i++) {
            playerList.add(new Player(1, 0, 0, board.getSpecificRoom("Trailer"), "testColor")); //TODO: give players starting rank, location, etc. (constructor)
            players.add(playerList.get(i)); //add all players to a queue for rotation
        }



        return;
    }

    public static ArrayList<Player> getPlayerList() {
        return playerList;
    }


}

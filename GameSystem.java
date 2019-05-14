import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameSystem {
	private static GameSystem instance = new GameSystem();

	private int playerCnt;
	//private Player[] players;
	private int numCnt;
	private int dayCnt;
	private int dayTot;
	private Player whosTurn;
	private Queue<Player> players = new LinkedList<Player>();
	private ArrayList<Player> playerList = new ArrayList<Player>();


	private GameSystem() {}

	public void startGame(int playerCnt) {
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

		dayTot = 3; //for 2 to 3 players.. otherwise needs system to change this

		//TODO: loop that is the turns of the player

	}

	public static GameSystem getInstance(){
		return instance;
	}
		
	public boolean selectRoom(Room room) {
		//TODO
		return true;
	}
	
	public void takeMoney(Player player, int amount) {
		//TODO
	}
	
	public int getDaysLeft() {
		//TODO
		return dayTot;
	}

	public int getDay() {
		//TODO
		return dayCnt;
	}
	
	public Player nextTurn() {
		//TODO
		return whosTurn;
	}
	
	public void calculateScores(Player[] players) {
		//TODO
	}


	public ArrayList<Player> getPlayerList() {
        return playerList;
    }
	
	
	
}

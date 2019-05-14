import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GameSystem {
	private static GameSystem instance = new GameSystem();

	private int playerCnt;
	//private Player[] players;
	private int numCnt;
	private int dayCnt;
	private int dayTot;
	private Player whosTurn;
	private Queue<Player> playersQueue = new LinkedList<Player>();
	private ArrayList<Player> playerList = new ArrayList<Player>();


	private GameSystem() {}

	public void startGame(int playerCnt) {
		Scanner scan = new Scanner(System.in);
		String line, command;
		String[] words;
		/* Create list of colors to assign to players */
		Queue<String> colors = new LinkedList<String>();
		colors.add("blue");colors.add("cyan");colors.add("green");colors.add("orange");
		colors.add("pink");colors.add("red");colors.add("violet");colors.add("yellow");

		System.out.println("Welcome to Deadwood!");

		Board board = new Board();
		board.startBoard();
		//TODO:set up the board


		/* Set up players */
		for(int i = 0; i < playerCnt; i++) {
			playerList.add(new Player(1, 0, 0, board.getSpecificRoom("Trailer"), colors.remove())); //TODO: give players starting rank, location, etc. (constructor)
			playersQueue.add(playerList.get(i)); //add all players to a queue for rotation
		}

		dayCnt = 0;
		dayTot = 3; //for 2 to 3 players.. otherwise needs system to change this

		//TODO: loop that is the turns of the player
		while(this.getDaysLeft() > 0) {
			Player currPlayer = playersQueue.remove(); //pop the first player off the queue
			System.out.println("It is " + currPlayer.getColor() + "'s turn.");
			System.out.println("What would you like to do?");
			line = scan.nextLine();
			words = line.split(" ", 2);
			command = words[0].toLowerCase();
			if(command.equals("move") && words.length == 2) {
				if(currPlayer.moveRoom(board.getSpecificRoom(words[1]))){
					System.out.println("Successfully moved to " + words[1]);
				}else{
					System.out.println("Failed to move");
				}
			}
			playersQueue.add(currPlayer);
		}

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
		return dayTot - dayCnt;
	}

	public int getDay() {
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

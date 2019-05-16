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
		boolean loop;
		boolean alreadyActed, alreadyRehearsed; //per turn
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
			playerList.add(new Player(1, 0, 0, board.getSpecificRoom("Trailer"), colors.remove()));
			playersQueue.add(playerList.get(i)); //add all players to a queue for rotation
		}

		dayCnt = 0;
		dayTot = 3; //for 2 to 3 players.. otherwise needs system to change this

		//TODO: loop that is the turns of the player
		while(this.getDaysLeft() > 0) {
			Player currPlayer = playersQueue.remove(); //pop the first player off the queue
			System.out.println("It is " + currPlayer.getColor() + "'s turn.");
			loop = true;
			alreadyActed = false;
			alreadyRehearsed = false;
			while(loop){
				System.out.println("What would you like to do?");
				line = scan.nextLine();
				words = line.split(" ", 2);
				command = words[0].toLowerCase();
				if(command.equals("move") && words.length == 2) {
					if(currPlayer.getRole() != null) {
						System.out.println("You must finish your role before moving!");
					}else {
						if(currPlayer.moveRoom(board.getSpecificRoom(words[1]))){
							System.out.println("Successfully moved to " + words[1]);
						}else{
							System.out.println("Failed to move");
						}
					}


				}else if(command.equals("move") && words.length == 1 && currPlayer.getRole() == null){
					/* Scenario when just "move" is submitted. Tells adjRooms */
					Room[] adjRooms = currPlayer.getCurrentRoom().getAdjacentRooms();
					System.out.println("These rooms are adjacent: ");
					for(Room r : adjRooms){
						System.out.println(r.getRoomName());
					}


				}else if(command.equals("end")){
					loop = false;


				}else if(command.equals("where")){
					System.out.println("You are located at '" + currPlayer.getCurrentRoom().getRoomName() + "'.");
					if(currPlayer.getCurrentRoom() instanceof Set){
						Set currSet = ((Set)currPlayer.getCurrentRoom());
						System.out.println("The set has these off-card roles:");
						/* Print off-card roles */
						for(int i = 0; i < currSet.getOffCardRoles().length; i++) {
							System.out.println(i + ": " + currSet.getOffCardRoles()[i].getRoleInfo());
						}
						if(currSet.getCurrScene() != null){
							System.out.println("The scene here is: '" + currSet.getCurrScene().getSceneName() + "'.");
							System.out.println("It has on-card roles:");
							/* Cycle through on card roles, number them increasing from last time */
							for(int i = 0; i < ((Set)currPlayer.getCurrentRoom()).getCurrScene().getRoles().length; i++) {
								System.out.println(i + ": " + currSet.getCurrScene().getRoles()[i].getRoleInfo());
							}
						}
					}


				}else if(command.equals("who")){
					System.out.println("You are player " + currPlayer.getColor() + ". You have $" +
					currPlayer.getMoney() + " and " + currPlayer.getCredits() + " credits. You are of rank " +
					currPlayer.getRank() + ".");


				}else if(command.equals("take")){
					if(currPlayer.getRole() != null) {
						System.out.println("You already have a role. You cannot take another.");
					}else{
						//get the on and off card roles
						Role[] offRoles = ((Set)currPlayer.getCurrentRoom()).getOffCardRoles();
						Role[] onRoles = ((Set)currPlayer.getCurrentRoom()).getCurrScene().getRoles();
						//loop through the role's matching it to their selection
						for(int i = 0; i < offRoles.length; i++){
							if(offRoles[i].getName().equals(words[1])){
								if(offRoles[i].takeRole(currPlayer)){
									System.out.println("You have successfully taken this off card role.");
									continue;
								}else{
									System.out.println("You are not eligible for this role.");
								}
							}
						}
						for(int i = 0; i < onRoles.length; i++){
							if(onRoles[i].getName().equals(words[1])){
								if(onRoles[i].takeRole(currPlayer)){
									System.out.println("You have successfully taken this on card role.");
								}else{
									System.out.println("You are not eligible for this role.");
								}
							}
						}
					}


				}else if(command.equals("rehearse")){
					if(alreadyRehearsed) {
						System.out.println("You've already rehearsed this turn.");
					}else {
						currPlayer.rehearse();
						alreadyRehearsed = true;
					}

				}else if(command.equals("act")){
					if(alreadyActed) {
						System.out.println("You've already acted this turn.");
					}else {
						currPlayer.act();
						alreadyActed = true;
					}
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

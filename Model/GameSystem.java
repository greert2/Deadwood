package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GameSystem {
	private static GameSystem instance = null;
	private int playerCnt;
	private int numCnt;
	private int dayCnt;
	private int dayTot;
	private Player currPlayer;
	private Queue<Player> playersQueue = new LinkedList<Player>();
	private ArrayList<Player> playerList = new ArrayList<Player>();


	private GameSystem() {}

	public void startGame(int playerCnt) {
		Scanner scan = new Scanner(System.in);
		String line, command;
		String[] words;
		boolean loop;
		boolean alreadyActed, alreadyRehearsed, alreadyMoved; //per turn
		/* Create list of colors to assign to players */
		Queue<String> colors = new LinkedList<String>();
		colors.add("blue");colors.add("cyan");colors.add("green");colors.add("orange");
		colors.add("pink");colors.add("red");colors.add("violet");colors.add("yellow");

		System.out.println("Welcome to Deadwood!" + playerCnt);

		Board.getInstance().startBoard();


		/* Set up players */
		for(int i = 0; i < playerCnt; i++) {
			playerList.add(new Player(1, 0, 0, Board.getInstance().getSpecificRoom("Trailer"), colors.remove()));
			playersQueue.add(playerList.get(i)); //add all players to a queue for rotation
		}

		//set starting day, and the ending day
		dayCnt = 0;
		dayTot = 2; //for 2 to 3 players.. otherwise needs system to change this (3 days)

		//Give each player a turn, loop until game end
		while(this.getDaysLeft() > 0) {
			currPlayer = playersQueue.remove(); //pop the first player off the queue
			System.out.println("It is " + currPlayer.getColor() + "'s turn.");
			loop = true;
			alreadyActed = false;
			alreadyRehearsed = false;
			alreadyMoved = false;
			while(loop){
				System.out.println("What would you like to do?");
				line = scan.nextLine();
				words = line.split(" ", 2);
				command = words[0].toLowerCase();


				if(command.equals("move") && words.length == 2) {
					if(currPlayer.getRole() != null) {
						System.out.println("You must finish your role before moving!");
					}else {
						if(!alreadyMoved && currPlayer.moveRoom(Board.getInstance().getSpecificRoom(words[1]))){
							System.out.println("Successfully moved to " + words[1]);
							alreadyMoved = true;
						}else if(alreadyMoved){
							System.out.println("You can only move once per turn.");
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
					//end players turn
					loop = false;


				}else if(command.equals("where")){
					//tell player where it is located, and about scene & roles
					System.out.println("You are located at '" + currPlayer.getCurrentRoom().getRoomName() + "'.");
					currPlayer.getCurrentRoom().printInfo();


				}else if(command.equals("who")){
					//tells about the player asking. Color, rank, money, credits.
					System.out.println("You are player " + currPlayer.getColor() + ". You have $" +
					currPlayer.getMoney() + " and " + currPlayer.getCredits() + " credits. You are of rank " +
					currPlayer.getRank() + ".");


				}else if(command.equals("take") && words.length == 2){
					//allow player to take role (in player's current room)
					if(currPlayer.getRole() != null) {
						System.out.println("You already have a role. You cannot take another.");
					}else{
						//get the on and off card roles
						if(currPlayer.getCurrentRoom() instanceof Set){
							Role[] offRoles = ((Set)currPlayer.getCurrentRoom()).getOffCardRoles();
							Role[] onRoles = ((Set)currPlayer.getCurrentRoom()).getCurrScene().getRoles();
							//loop through the role's matching it to their selection
							for(int i = 0; i < offRoles.length; i++){
								if(offRoles[i].getName().equals(words[1])){
									if(offRoles[i].takeRole(currPlayer)){
										System.out.println("You have successfully taken this off card role.");
										continue;
									}else{
										System.out.println("You cannot take this role.");
									}
								}
							}
							for(int i = 0; i < onRoles.length; i++){
								if(onRoles[i].getName().equals(words[1])){
									if(onRoles[i].takeRole(currPlayer)){
										System.out.println("You have successfully taken this on card role.");
									}else{
										System.out.println("You cannot take this role.");
									}
								}
							}
							}
						}



				}else if(command.equals("rehearse")){
					if(alreadyRehearsed) {
						//the player has already rehearsed on this turn
						System.out.println("You've already rehearsed this turn.");
					}else {
						currPlayer.rehearse();
						alreadyRehearsed = true;
					}


				}else if(command.equals("act")){
					if(alreadyActed) {
						//the player has already acted on this turn
						System.out.println("You've already acted this turn.");
					}else {
						currPlayer.act();
						alreadyActed = true;
					}


				}else if(command.equals("active?")) {
					//print info about the active player
					currPlayer.printInfo();


				}else if(command.equals("upgrade") && words.length > 1) {
					//allow the player to upgrade when in the Casting Office
					if(currPlayer.getCurrentRoom() instanceof CastingOffice) {
						((CastingOffice)currPlayer.getCurrentRoom()).selectUpgrade(currPlayer, words[1]);
					}else{
						System.out.println("You must be in the Casting Office to upgrade");
					}
				}else if(command.equals("upgrade") && words.length == 1) {
					//Just print the upgrades
					if(currPlayer.getCurrentRoom() instanceof CastingOffice) {
						((CastingOffice)currPlayer.getCurrentRoom()).printUpgrades();
						System.out.println("To upgrade: upgrade [money/credits] [rank]");
					}else{
						System.out.println("You must be in the Casting Office to upgrade");
					}
				}


			}
			playersQueue.add(currPlayer); //add the player ending their turn back to the queue
		}

	}

	public void startGameGUI(int playerCnt) {
		//Scanner scan = new Scanner(System.in);
		//String line, command;
		//String[] words;
		boolean loop;
		boolean alreadyActed, alreadyRehearsed, alreadyMoved; //per turn
		/* Create list of colors to assign to players */
		Queue<String> colors = new LinkedList<String>();
		colors.add("blue");colors.add("cyan");colors.add("green");colors.add("orange");
		colors.add("pink");colors.add("red");colors.add("violet");colors.add("yellow");

		System.out.println("Welcome to Deadwood!" + playerCnt);

		Board.getInstance().startBoard();


		/* Set up players */
		for(int i = 0; i < playerCnt; i++) {
			playerList.add(new Player(1, 0, 0, Board.getInstance().getSpecificRoom("Trailer"), colors.remove()));
			playersQueue.add(playerList.get(i)); //add all players to a queue for rotation
		}

		//set starting day, and the ending day
		dayCnt = 0;
		dayTot = 2; //for 2 to 3 players.. otherwise needs system to change this (3 days)


	}

	public static GameSystem getInstance(){
		//allow for singleton design
		if(instance == null) {
			instance = new GameSystem();
		}
		return instance;
	}

	
	public int getDaysLeft() {
		return dayTot - this.getDay();
	}

	public int getDay() {
		return dayCnt;
	}
	
	public Player thisTurn() {
		return currPlayer;
	}
	
	public void calculateScores(Player[] players) {
		int highScore = 0;
		int tempScore;
		Player winner = null;
		for(int i = 0; i < players.length; i++) {
			tempScore = 0;
			tempScore += players[i].getMoney();
			tempScore += players[i].getCredits();
			tempScore += (players[i].getRank() * 5);
			if(tempScore > highScore) {
				highScore = tempScore;
				winner = players[i];
			}
		}
		System.out.printf("Player %s wins!\n", winner.getColor());
	}


	public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void addDay() {
		this.dayCnt++;
		if(this.getDaysLeft() == 0) {
			this.calculateScores(playerList.toArray(new Player[playerList.size()]));
		}
	}
	
	public Player getCurrPlayer() {
		return playersQueue.peek();
	}

	public void endTurn() {
		Player p = playersQueue.remove();
		playersQueue.add(p);
		currPlayer = playersQueue.peek();
	}
	
}

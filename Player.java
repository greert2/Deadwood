import java.util.Arrays;

public class Player {
	private int rank;
	private int dollars;
	private int credits;
	private Room currentRoom;
	private int rehearseChips;
	private Role role;
	private String color;

	public Player (int rank, int dollars, int credits, Room startRoom, String color) {
		this.rank = rank;
		this.dollars = dollars;
		this.credits = credits;
		this.currentRoom = startRoom;
		this.color = color; //maybe change to index based color assigning
	}
		
	public boolean moveRoom(Room room) { //TODO: diagram, change to bool
		if(room == null) {return false;}
		if(roomIsAdjacent(room)) {
			this.currentRoom.leave(this);
			this.currentRoom = room;
			this.currentRoom.visit(this);
			return true;
		}
		return false;
	}

	private boolean roomIsAdjacent(Room room) {
		for(Room r : currentRoom.getAdjacentRooms()) {
			if(r.getRoomName().equals(room.getRoomName()))
				return true;
		}
		return false;
	}
	
	public void addMoney(int amount) {
		this.dollars += amount;
	}
	
	public void addCredits(int amount) {
		this.credits += amount;
	}
	
	public void updateRole(Role role) {
		this.role = role;
	}
	
	public void updateRank(int rank) {
		this.rank = rank;
	}
	
	public void act() {
		//Does: roll (add practice chips), compare to value, pay/not
		Dice dice = new Dice();
		int[] rollArr = dice.roll(1);
		int roll = rollArr[0];
		roll += rehearseChips;
		if(roll >= ((Set)this.currentRoom).getCurrScene().getBudget()) {
			//Successful Acting!
			if(this.getRole().isExtra()) {
				((Set)this.getCurrentRoom()).removeShotCounter();
				this.addCredits(1);
				this.addMoney(1);
			}else{
				//NOT an extra
				((Set)this.getCurrentRoom()).removeShotCounter();
				this.addCredits(2);
			}
		}else{
			if(this.getRole().isExtra()) {
				//Extra role failed, but still gets a dollar!
				this.addMoney(1);
			}
		}
	}
	
	public void rehearse() {
		this.rehearseChips += 1;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getMoney() {
		return dollars;
	}
	
	public int getCredits() {
		return credits;
	}

	public int getRehearseChips() { //TODO: add to diagram
		return rehearseChips;
	}

	public Role getRole() {
		return role;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public int getRank() {
		return rank;
	}
}

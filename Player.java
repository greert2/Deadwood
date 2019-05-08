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
		
	public void moveRoom(Room room) {
		if(roomIsAdjacent(room)) {
			this.currentRoom = room;
		}
	}

	private boolean roomIsAdjacent(Room room) {
		for(Room r : currentRoom.getAdjacentRooms()) {
			if(r == room) //TODO: probably will have to change
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
		//TODO: roll (add practice chips), compare to value, pay/not
	}
	
	public void rehearse() {
		this.rehearseChips += 1;
	}
	
	public void givePlayerMainBonus(int roll, Role role) {
		//TODO
	}
	
	public void givePlayerExtraBonus(int roll, Role role) {
		//TODO
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
}

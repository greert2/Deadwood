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

	public boolean payMoney(int amount) {
		if(this.getMoney() >= amount){
			this.dollars -= amount;
			return true;
		}else{
			//Insufficient funds
			return false;
		}
	}

	public boolean payCredits(int amount) { //TODO: add to diagram
		if(this.getCredits() >= amount){
			this.credits -= amount;
			return true;
		}else{
			//Insufficient funds
			return false;
		}
	}
	
	public void updateRole(Role role) {
		this.role = role;
	}
	
	public void updateRank(int rank) {
		this.rank = rank;
	}
	
	public void act() {
		//Does: roll (add practice chips), compare to value, pay/not
		if(this.getRole() == null) {
			System.out.println("You must have a role to act.");
			return;
		}
		Dice dice = new Dice();
		int[] rollArr = dice.roll(1);
		int roll = rollArr[0];
		roll += this.getRehearseChips();
		if(roll >= ((Set)this.getCurrentRoom()).getCurrScene().getBudget()) {
			//Successful Acting!
			if(this.getRole().isExtra()) {
				((Set)this.getCurrentRoom()).removeShotCounter();
				this.addCredits(1);
				this.addMoney(1);
				System.out.println("You acted successfully! You gained $1 and 1 credit.");
			}else{
				//NOT an extra
				((Set)this.getCurrentRoom()).removeShotCounter();
				this.addCredits(2);
				System.out.println("You acted successfully! You gained 2 credits.");
			}
		}else{
			if(this.getRole().isExtra()) {
				//Extra role failed, but still gets a dollar!
				this.addMoney(1);
				System.out.println("You failed as an extra, but you got a dollar for trying.");
			}
		}
		//Win or Fail, acting on this role is over
		this.getRole().done();
		this.role = null;
	}
	
	public boolean rehearse() {
		if(this.getRole() == null) {
			System.out.println("You must have a role to rehearse for it.");
			return false;
		}else {
			this.rehearseChips += 1;
			System.out.println("You have rehearsed. You now have " + this.getRehearseChips() + " chips.");
			return true;
		}
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

	public void resetRehearseChips(){
		this.rehearseChips = 0;
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

	public void printInfo(){
		System.out.printf("The active player is player %s. They have $%d and %d credits\n",
				this.getColor(), this.getMoney(), this.getCredits());
		if(this.getRole() != null) {
			System.out.printf("They are working the role %s, \"%s\"\n",
					this.getRole().getName(), this.getRole().getLine());
		}else{
			//this player does not have a role
			System.out.println("This player doesn't currently have a role.");
		}
	}

	public void clearRole() { //TODO: add to diagram
		this.role = null;
	}

}

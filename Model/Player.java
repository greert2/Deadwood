package Model;

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
	
	public String act() {
		//Does: roll (add practice chips), compare to value, pay/not
		String output = null;
		if(this.getRole() == null) {
			System.out.println("You must have a role to act.");
			return "You must have a role to act.";
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
				output = "You acted successfully! You gained $1 and 1 credit.";
			}else{
				//NOT an extra
				((Set)this.getCurrentRoom()).removeShotCounter();
				this.addCredits(2);
				System.out.println("You acted successfully! You gained 2 credits.");
				output = "You acted successfully! You gained 2 credits.";
			}
		}else{
			if(this.getRole().isExtra()) {
				//Extra role failed, but still gets a dollar!
				((Set)this.getCurrentRoom()).removeShotCounter();
				this.addMoney(1);
				System.out.println("You failed as an extra, but you got a dollar for trying.");
				output = "You failed as an extra, but you got a dollar for trying.";
			}else if(!this.getRole().isExtra()) {
				((Set)this.getCurrentRoom()).removeShotCounter();
				output = "You acted badly.";
			}
		}
		//Win or Fail, acting on this role is over
		if(this.getRole() != null)
			this.getRole().done();
		this.role = null;
		return output;
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

	public boolean takeRole(String roleName) {
		//allow player to take role (in player's current room)
		if(this.getRole() != null) {
			System.out.println("You already have a role. You cannot take another.");
		}else{
			//get the on and off card roles
			if(this.getCurrentRoom() instanceof Set){
				Role[] offRoles = ((Set)this.getCurrentRoom()).getOffCardRoles();
				Role[] onRoles = ((Set)this.getCurrentRoom()).getCurrScene().getRoles();
				//loop through the role's matching it to their selection
				for(int i = 0; i < offRoles.length; i++){
					if(offRoles[i].getName().equals(roleName)){
						if(offRoles[i].takeRole(this)){
							this.updateRole(offRoles[i]);
							System.out.println("You have successfully taken this off card role.");
							return true;
						}else{
							System.out.println("You cannot take this role.");
							return false;
						}
					}
				}
				for(int i = 0; i < onRoles.length; i++){
					if(onRoles[i].getName().equals(roleName)){
						if(onRoles[i].takeRole(this)){
							this.updateRole(onRoles[i]);
							System.out.println("You have successfully taken this on card role.");
							return true;
						}else{
							System.out.println("You cannot take this role.");
							return false;
						}
					}
				}
			}
		}
		return false;
	}

}

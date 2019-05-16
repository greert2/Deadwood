public class Role {
	private String name;
	private int reqRank;
	private String phrase;
	private boolean taken;
	private Player actor;
	private boolean onCard;
	
	public Role(String name, int reqRank, String phrase, boolean onCard) {
      this.name = name;
      this.reqRank = reqRank;
      this.phrase = phrase;
      this.onCard = onCard;
   }
	
	public boolean isExtra() {
		return !onCard;
	}


	public boolean takeRole(Player p) {
		if(this.roleAvailable() && (p.getRank() >= this.getReqRank())){
			p.updateRole(this);
			this.taken = true;
			this.actor = p;
			p.resetRehearseChips();
			return true;
		}
		return false;
	}
	
	public String getRoleInfo() {
		return this.name + ": '" + this.getLine() + "'. It requires " + this.getReqRank() + " rank.";
	}

	public String getName(){ //TODO: add
		return name;
	}
	
	public boolean roleAvailable() {
		return !taken;
	}
	
	public String getLine() {
		return phrase;

	}
	
	public int getReqRank() {
		return reqRank;
	}

	public Player getPlayer() {
		return actor;
	}


	
	

}

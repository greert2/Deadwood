package Model;

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
		//give the role to player, if eligible
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

	public void done() {
		this.actor = null;
	}

	public boolean isTaken() {
		return taken;
	}

	public void resetRole() {
		this.taken = false;
		if(this.getPlayer() != null){
			//player is still here, kick them out!
			this.getPlayer().updateRole(null);
		}
		this.actor = null;
	}
	

}

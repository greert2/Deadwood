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
	
	public void takeRole(Player player) {
		this.actor = player;
		return;
	}
	
	public String getRoleInfo() {
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

public class Role {
	private String name;
	private int reqRank;
	private String phrase;
	private boolean taken;
	private Player actor;
	private boolean onCard;
	
	public Role() {}
	
	public boolean isExtra() {
		//TODO
		return true;
	}
	
	public void takeRole(Player player) {
		//TODO
	}
	
	public String getRoleInfo() {
		//TODO
		return name;
	}
	
	public boolean roleAvailable() {
		//TODO
		return taken;
	}
	
	public String getLine() {
		//TODO
		return phrase;

	}
	
	public int getReqRank() {
		//TODO
		return reqRank;
	}
	
	

}

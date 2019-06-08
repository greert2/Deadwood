package Model;

public class CastingOffice extends Room {
    private Room[] adjacentRooms;
    private Player[] playersHere;
    private String roomName;
    private int[][] upgrades = new int[][]{
            /* Col0 is dollars, col1 is credits */
            /* i = 0 is equal to rank 2 */
            /* i = 4 is the max rank of 6 */
            {4,5},
            {10,10},
            {18,15},
            {28,20},
            {40,25}
    };

    public CastingOffice(String roomName, int[] playerCoords) {
        this.roomName = roomName;
        this.setPlayerCoords(playerCoords);
    }

    public void printUpgrades() {
        System.out.println("Here are all upgrades and their cost in dollars and credits");
        for(int r = 0; r < upgrades.length; r++){
            System.out.printf("For rank %d, it is $%d, or %d credits.\n", r+2, upgrades[r][0], upgrades[r][1]);
        }
        return;
    }

    public void selectUpgrade(Player p, String upgrade) {
        //Gives the player the upgrade, if they have the proper funds
        String words[];
        String type;
        int rank;
        if(p.getRank() < 6){
            words = upgrade.split(" ", 2);
            type = words[0];
            rank = Integer.parseInt(words[1]);
            if(type.equals("money")){
                //wants to pay in $
                if(p.payMoney(upgrades[rank-2][0])){
                    p.updateRank(rank);
                    System.out.printf("You are now rank %d and have $%d.\n", p.getRank(), p.getMoney());
                }else{
                    System.out.printf("You do not have sufficient funds. Rank %d costs $%d.\n", rank, upgrades[rank-2][0]);
                }
            }else if(type.equals("credits")) {
                //wants to pay in credits
                if(p.payCredits(upgrades[rank-2][1])){
                    p.updateRank(rank);
                    System.out.printf("You are now rank %d and have %d credits.\n", p.getRank(), p.getCredits());
                }else{
                    System.out.printf("You do not have sufficient funds. Rank %d costs %d credits.\n", rank, upgrades[rank-2][1]);
                }
            }

        }else{
            System.out.println("You are already the highest rank, congrats!");
        }
        return;
    }

    public String getRoomName() {
        return roomName;
    }

    public void printInfo() {
        System.out.println("You may upgrade your rank here.");
    }
    public boolean canUpgrade(Player p, int rank, String type) {
    	if(type.equals("money") && rank > p.getRank() && rank < 7) {
    		return (p.getMoney() >= upgrades[rank - 2][0]);
    	}
    	else if(type.equals("credits") && rank > p.getRank() && rank < 7) {
    		return (p.getCredits() >= upgrades[rank - 2][1]);
    	}
    	return false;
    }
}
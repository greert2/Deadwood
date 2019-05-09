public class CastingOffice extends Room {
    private Room[] adjacentRooms;
    private Player[] playersHere;
    private String roomName;
    private int[][] upgrades;

    public CastingOffice(String roomName) {
        this.roomName = roomName;
    }

    public String[] getUpgrades(int money, int credits) {
    String[] availUpgrades = {};
    return availUpgrades;
  }

    public void selectUpgrade(String upgrade) {
    return;
  }

    public String getRoomName() {
        return roomName;
    }
}

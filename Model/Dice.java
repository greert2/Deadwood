package Model;

public class Dice {
    private final int MAX_DICE_VALUE = 6;
    private final int MIN_DICE_VALUE = 1;

    public int[] roll(int numberOfDice){
        int[] rollResult = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            rollResult[i] = (int)(Math.random()*MAX_DICE_VALUE) + MIN_DICE_VALUE;
        }
        return rollResult;
    }
}

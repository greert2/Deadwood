
import java.util.ArrayList;

public class Deadwood {
    //Tyler: Room, Scene, Board, Casting Office
    //Brooks: Player, Role, System, Dice

    //should only expect 1 argument, number of players
    public static void main(String[] args) {
        int playerCnt = 0;

        if(args.length != 1) {
            System.out.println("Run with only [number of players].");
            System.exit(1); //throw exception?
        }else{
            /* Attempt to get number of players */
            try{
                playerCnt = Integer.parseInt(args[0]);
            }catch(NumberFormatException err) {
                System.out.println("Incorrect run. Run with only [number of players].");
                System.exit(1);
            }
        }

        GameSystem.getInstance().startGame(playerCnt);


        return;
    }




}

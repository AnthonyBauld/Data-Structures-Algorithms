import battleship.BattleShip2;

/**
 * I Anthony Bauld, certify that this material is my original work. No other person's work has been used without due acknowledgement. 
 * I have not made my work available to anyone else.
 *
 * @author Anthony Bauld 
 */
public class Assignment6_Start {
    public static void main(String[] args) {

        // DO NOT add any logic to this code
        // All logic must be added to your Bot implementation
        // see fireShot in the ExampleBot class

        final int NUMBEROFGAMES = 10000;
        System.out.println(BattleShip2.getVersion());
        BattleShip2 battleShip = new BattleShip2(NUMBEROFGAMES, new GioBot2022());
        int[] gameResults = battleShip.run();

        // You may add some analysis code to look at all the game scores that are returned in gameResults
        // This can be useful for debugging purposes.

        battleShip.reportResults();
    }
}

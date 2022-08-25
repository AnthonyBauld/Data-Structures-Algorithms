import battleship.BattleShip2;
import battleship.BattleShipBot;

import java.util.*;
import java.awt.Point;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit - This is not a good solution to the problem!
 *
 * @author Giordano Iacoviello, Anthony Bauld
 */
public class Bot2022 implements BattleShipBot {
    private int gameSize;
    private BattleShip2 battleShip;
    private Random random;
    private int[][] gameMap;
    private int hitPhase;
    private Point firstHit;
    private Point hitPhasePoint;
    private Queue<Point> pathPoints;
    private final ThreadLocal<ArrayList<Integer>> shipsSunk = new ThreadLocal<ArrayList<Integer>>();
    private int hitCount;

    /**
     * Constructor keeps a copy of the BattleShip instance
     * Create instances of any Data Structures and initialize any variables here
     * @param b previously created battleship instance - should be a new game
     */

    @Override
    public void initialize(BattleShip2 b) {
        battleShip = b;
        gameSize = BattleShip2.BOARD_SIZE;
        hitPhase = 0;
        firstHit = new Point(0,0);
        hitPhasePoint = new Point(0,0);
        pathPoints = new LinkedList<>();
        shipsSunk.set(new ArrayList<>());
        hitCount = 2;


        gameMap = new int[gameSize][gameSize];

        for (int[] ints : gameMap) {
            Arrays.fill(ints, 0);
        }

        // Need to use a Seed if you want the same results to occur from run to run
        // This is needed if you are trying to improve the performance of your code

        random = new Random(0xAAAAAAAA);   // Needed for random shooter - not required for more systematic approaches
    }

    /**
     * Create a random shot and calls the battleship shoot method
     * Put all logic here (or in other methods called from here)
     * The BattleShip API will call your code until all ships are sunk
     */

    @Override
    public void fireShot() {
        boolean uniqueShot = false;
        int x = 0;
        int y = 0;

        // Left
        if (hitPhase == 1) {
            if (!(hitPhasePoint.x == 0)) {
                x = hitPhasePoint.x - 1;
                y = hitPhasePoint.y;
            } else {
                hitPhase = 2;
            }
        }

        // Right
        if (hitPhase == 2) {
            if (!(hitPhasePoint.x == gameSize - 1)) {
                x = hitPhasePoint.x + 1;
                y = hitPhasePoint.y;
            } else {
                hitPhase = 3;
            }
        }

        // Up
        if (hitPhase == 3) {
            if (!(hitPhasePoint.y == 0)) {
                x = hitPhasePoint.x;
                y = hitPhasePoint.y - 1;
            } else {
                hitPhase = 4;
            }
        }

        // Down
        if (hitPhase == 4) {
            if (!(hitPhasePoint.y == 0)) {
                x = hitPhasePoint.x;
                y = hitPhasePoint.y + 1;
            } else {
                hitPhase = 0;
            }
        }

        // If hitPhase is 5, two shots beside each other have been found. There is now a path to reliably shoot on.
        if (hitPhase == 5) {
            // If ship is horizontally placed, first hit on left and the second hit on the right
            if (firstHit.x != hitPhasePoint.x && hitPhasePoint.x > firstHit.x) {
                for (int i = 1; i < 5; i++) {
                    if (firstHit.x - i > -1) {
                        Point point = new Point(firstHit.x - i, firstHit.y);
                        pathPoints.add(point);
                    }
                }

                for (int i = 1; i < 5; i++) {
                    if (hitPhasePoint.x + i < gameSize - 1) {
                        Point point = new Point(hitPhasePoint.x + i, firstHit.y);
                        pathPoints.add(point);
                    }
                }

                //hitPhase = 6;
                // If ship is horizontally placed and the second hit was on the left, first hit on the right
            }

            if (firstHit.x != hitPhasePoint.x && hitPhasePoint.x < firstHit.x) {
                for (int i = 1; i < 5; i++) {
                    if (hitPhasePoint.x - i > -1) {
                        Point point = new Point(hitPhasePoint.x - i, firstHit.y);
                        pathPoints.add(point);
                    }
                }

                for (int i = 1; i < 5; i++) {
                    if (firstHit.x + i < gameSize - 1) {
                        Point point = new Point(firstHit.x + i, firstHit.y);
                        pathPoints.add(point);
                    }
                }

                //hitPhase = 6;
                // If ship is placed vertically and second hit above, first hit below
            }

            if (firstHit.y != hitPhasePoint.y && hitPhasePoint.y < firstHit.y) {
                for (int i = 1; i < 5; i++) {
                    if (hitPhasePoint.y - i > -1) {
                        Point point = new Point(firstHit.x, hitPhasePoint.y - i);
                        pathPoints.add(point);
                    }
                }

                for (int i = 1; i < 5; i++) {
                    if (firstHit.y + i < gameSize - 1) {
                        Point point = new Point(firstHit.x, firstHit.y + i);
                        pathPoints.add(point);
                    }
                }

                //hitPhase = 6;
                // If ship is placed vertically and first hit above, second hit below
            } else if (firstHit.y != hitPhasePoint.y) {
                for (int i = 1; i < 5; i++) {
                    if (firstHit.y - i > -1) {
                        Point point = new Point(firstHit.x, firstHit.y - i);
                        pathPoints.add(point);
                    }
                }

                for (int i = 1; i < 5; i++) {
                    if (hitPhasePoint.y + i < gameSize - 1) {
                        Point point = new Point(firstHit.x, hitPhasePoint.y + i);
                        pathPoints.add(point);
                    }
                }

                //hitPhase = 6;
            }

            hitPhase = 6;
        }

        // Will find a coordinate to shoot that has not previously been shot, if it is in
        // phase 0 (randomly shooting until it gets a hit)
        while (!uniqueShot && hitPhase == 0) {
            x = random.nextInt(gameSize);
            y = random.nextInt(gameSize);

            if (gameMap[x][y] == 0) {
                uniqueShot = true;
            }
        }

        if (hitPhase == 6) {
            if (pathPoints.peek() != null) {
                x = pathPoints.peek().x;
                y = pathPoints.remove().y;
            } else {
                hitPhase = 0;
                firstHit.x = 0;
                firstHit.y = 0;
                hitPhasePoint.x = 0;
                hitPhasePoint.y = 0;

                shipsSunk.get().add(hitCount);
                hitCount = 2;

            }
        }

        if (y == 15) {
            y = 14;
        }

        // Will return true if we hit a ship
        boolean hit = battleShip.shoot(new Point(x,y));

        // If in phase 1-4 and everything worked out, this will increment to the next phase.
        if (hitPhase > 0 && hitPhase < 5) {
            hitPhase++;
        }

        if (hit) {

            if (hitPhase == 6) {
                hitCount++;
            }

            if (hitPhase == 0) {
                firstHit.x = x;
                firstHit.y = y;
            }
            // This makes it so that when in phases 1-4 (hitting in the cross pattern), if it hits a coordinate with a
            // battleship that has previously been shot, it will not restart the process from phase 1 (which would create a
            // never-ending loop)
            if (gameMap[x][y] == 0 && hitPhase < 5) {
                hitPhasePoint.x = x;
                hitPhasePoint.y = y;
                hitPhase = 1;
            }

            if (firstHit.x != hitPhasePoint.x || firstHit.y != hitPhasePoint.y) {
                if (hitPhase != 6) {
                    hitPhase = 5;
                }
            }

            // Mark hit on map
            gameMap[x][y] = 2;
        } else {
            // Mark miss on map
            gameMap[x][y] = 1;
        }
    }

    /**
     * Authorship of the solution - must return names of all students that contributed to
     * the solution
     * @return names of the authors of the solution
     */

    @Override
    public String getAuthors() {
        return "Giordano Iacoviello, Anthony Bauld ";
    }
}

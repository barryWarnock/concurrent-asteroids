package game;

import java.util.Random;

public class Game {
    protected Random rand = new Random();

    private static Game game = new Game();

    private Game(){

    }

    public static Game getInstance(){
        return game;
    }

    public static int getScore(){
        return player.getScore();
    }

    /**
     * returns a random number in a given range (inclusive)
     * @param min the minimum value
     * @param max the maximum value
     * @return a random number x where min <= x <= max
     */
    public int randomInRange(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    public boolean randomBool() {
        return rand.nextBoolean();
    }
}

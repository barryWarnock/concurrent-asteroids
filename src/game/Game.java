package game;
import entity.Entity;
import entity.Player;
import gui.Screen;
import java.util.ArrayList;

import java.util.Random;

public class Game {
    protected Random rand = new Random();

    private static Game game = new Game();
    private ArrayList<Entity> entityList = new ArrayList<Entity>();


    private Game(){

    }

    public void addEntity(Entity e){
        entityList.add(e);
    }

    public boolean removeEntity(Entity e){
        if (entityList.contains(e)) {
            entityList.remove(e);
            return true;
        }
        else {
            return false;
        }
    }


    public static Game getInstance(){
        return game;
    }

    public static int getScore(){
        return player.getScore();
    }

    public int getScreenWidth(){
        return Screen.getInstance().getWidth();
    }

    public int getScreenHeight(){
        return Screen.getInstance().getHeight();
    }



    public void run(){
        while (true){

        }
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

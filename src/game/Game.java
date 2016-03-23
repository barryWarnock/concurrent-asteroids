package game;
import collision.CollisionChecker;
import collision.QuadTree;
import entity.Entity;
import entity.Player;
import gui.Screen;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class Game {
    protected Random rand = new Random();

    private static Game game = new Game();
    private List<Entity> entityList = new ArrayList<>();
    private static int score;


    private Game() { }

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

    public int getScore(){
        return score;
    }

    public int getScreenWidth(){
        return Screen.getInstance().getWidth();
    }

    public int getScreenHeight(){
        return Screen.getInstance().getHeight();
    }

    /**
     * update and draw all entities
     */
    public void gameLoop() throws InterruptedException {
        while (true){
            //update entities
            List<Thread> entityThreads = new ArrayList<>();

            for (Entity e: entityList) {
                Thread newThread = new Thread(e);
                newThread.run();
                entityThreads.add(newThread);
            }

            //wait for updates to finish
            for (Thread t : entityThreads) {
                t.join();
            }

            //check collision
            CollisionChecker collision = new QuadTree(5); //at most 5 entities per node
            collision.checkCollisions(entityList);

            //draw
            for (Entity e : entityList) {
                //e.draw(); //TODO this
            }

            drawUI();

            //if {user exited}
            //then: break
        }
    }

    /**
     * overlay score and lives over the screen
     */
    protected void drawUI() {

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

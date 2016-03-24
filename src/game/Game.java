package game;
import collision.CollisionChecker;
import collision.QuadTree;
import entity.Entity;
import entity.Player;
import gui.Screen;
import logger.Log;

import java.awt.event.KeyListener;
import java.util.*;

public class Game {

    protected Random rand = new Random();

    private static Game game = new Game();

    private Set<Entity> entityList = new HashSet<Entity>();

    private int score;
    private int lives;


    private Game() {
        lives = 3;
        entityList.add(Player.getInstance());
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
     * @param fps   This is the frames per second of the draw calls.
     *              The "physics" backend may run a multiple of this.
     */
    public void gameLoop(int fps) throws InterruptedException {
        while (true){
            long startTime = System.currentTimeMillis();

            Iterator<Entity> entityItr = entityList.iterator();

            //<----UPDATE SECTION------->
            List<Thread> entityThreads = new ArrayList<Thread>();

            while(entityItr.hasNext()) {
                Thread newThread = new Thread(entityItr.next());
                newThread.run();
                entityThreads.add(newThread);
            }

            //wait for updates to finish
            for (Thread t : entityThreads) {
                t.join();
            }
            //<-----END UPDATE SECTION------->

            //check collision
            entityItr = entityList.iterator(); //re-init the iterator
            CollisionChecker collision = new QuadTree(5); //at most 5 entities per node
            collision.checkCollisions(entityItr);

            entityItr = entityList.iterator(); //re-init the iterator
            while(entityItr.hasNext()) {
                entityItr.next().draw(Screen.getInstance());
            }

            drawUI();

            //this delays the frames to the right side amount
            while (System.currentTimeMillis()-startTime < 1000/fps) {
                Thread.sleep(1); //Leave this at one since 60fps => 16ms per frame only
            }

            /*
            This next line flips the backBuffer. It is crucial
            that this is done some small amount of time after
            the last draw call to the back buffer to prevent
            stuttering.
             */
            Screen.getInstance().repaint();
        }
    }

    /**
     * Draws the score and lives on the screen.
     */
    private void drawUI() {
        Screen theScreen = Screen.getInstance();
        theScreen.drawText(score + "\n" + "Lives: " + lives);
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

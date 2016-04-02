package game;
import collision.CollisionChecker;
import collision.CollisionQuadTree;
import entity.Entity;
import entity.Player;
import gui.Screen;

import java.util.*;

public class Game {

    protected Random rand = new Random();

    private static Game game = new Game();

    private ArrayList<Entity> entityList = new ArrayList<>();

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
        long lastUpdate = 0;
        long lastDraw   = 0;
        while (true){
            if (System.currentTimeMillis() - lastUpdate > (1000/60)) {

                //<----UPDATE SECTION------->
                List<Thread> entityThreads = new ArrayList<>();

                for (int i = 0; i < entityList.size(); i++) {
                    Thread newThread = new Thread(entityList.get(i));
                    newThread.run();
                    entityThreads.add(newThread);
                }

                //wait for updates to finish
                for (Thread t : entityThreads) {
                    t.join();
                }
                //<-----END UPDATE SECTION------->
                lastUpdate = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - lastDraw > (1000/30)) {

                //check collision
                CollisionChecker collision = new CollisionQuadTree(5); //at most 5 entities per node
                collision.checkCollisions(entityList);

                Screen screen = Screen.getInstance();

                for (int i = 0; i < entityList.size(); i++) {
                    entityList.get(i).draw(screen);
                }

                drawUI();

            /*
            This next line flips the backBuffer. It is crucial
            that this is done some small amount of time after
            the last draw call to the back buffer to prevent
            stuttering.
             */
                Screen.getInstance().repaint();
                lastDraw = System.currentTimeMillis();
            }
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

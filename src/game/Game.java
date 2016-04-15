package game;

import collision.BruteForceCollision;
import collision.CollisionChecker;
import collision.CollisionQuadTree;
import entity.Asteroid;
import entity.AsteroidSize;
import entity.Entity;
import entity.Alien;
import entity.Player;
import gui.Screen;
import main.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Game {

    protected Random rand = new Random();

    private static Game game = new Game();
    private static int frameCount = 0;

    private ArrayList<Entity> entityList = new ArrayList<>();

    public boolean areThereAliens = false;


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

    public void spawnAlien(){

    }

    public void Level1(int fps) throws InterruptedException {
        for(int i=0; 2 > i; i++) {
          //  entityList.add(new Asteroid(AsteroidSize.BIG));
        }
        //entityList.add(new Alien());
        gameLoop(fps);
    }

    public void loseLife(){
        lives--;
        if(lives == -1){
            Main.playerLost = true;
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
        long startTime = System.currentTimeMillis();
        gameLoop:
        while (true){
            if (Main.testMode || System.currentTimeMillis() - lastUpdate > (1000/fps)) {
                frameCount++;
                //check collision
                CollisionChecker collision;
                if(Main.runQuadTree) {
                    collision = new CollisionQuadTree(Main.quadTreeThreshold);
                } else {
                    collision = new BruteForceCollision();
                }

                collision.checkCollisions(entityList);

                if (Main.runThreaded) {

                    ExecutorService executor =
                            Executors.newFixedThreadPool(4);
                    for (int i=0; entityList.size() > i; i++) {
                        executor.execute(entityList.get(i));
                    }
                    executor.shutdown();
                    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//                    List<Thread> entityThreads = new ArrayList<>();
//
//                    for (int i=0; entityList.size() > i; i++) {
//                        Thread newThread = new Thread(entityList.get(i));
//                        newThread.run();
//                        entityThreads.add(newThread);
//                    }
//                    //wait for updates to finish
//                    for (Thread t : entityThreads) {
//                        t.join();
//                    }
                } else {
                    for (int i=0; entityList.size() > i; i++) {
                        entityList.get(i).update();
                    }
                }


                if(Main.playerLost){
                    onLose();
                }
                lastUpdate = System.currentTimeMillis();
                if(Main.testMode && lastUpdate - startTime > Main.testDuration) {
                    break gameLoop;
                }
                if(Main.testMode) {
                    continue gameLoop; /*this prevents redraws which are slow and could
                                        * effect performance in tests
                                        */
                }
                /*
                This next line flips the backBuffer. It is crucial
                that this is done some small amount of time after
                the last draw call to the back buffer to prevent
                stuttering.
                 */
                Screen screen = Screen.getInstance();

                for (int i=0; entityList.size() > i; i++) {
                    entityList.get(i).draw(screen);
                }
                drawUI();
                Screen.getInstance().repaint();
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

    public double randomDouble(double min, double max){return min + (max - min) * rand.nextDouble();}
    public double stressTest(int elements) throws InterruptedException {
        entityList = new ArrayList<>(elements);
        double avgFrameTime;
        frameCount = 0;

        //add n asteroids to list
        while(elements > 0) {
            entityList.add(new Asteroid(AsteroidSize.BIG));
            elements--;
        }
        gameLoop(0);
        for(int i = 0; i < entityList.size(); i++) {
            entityList.get(i).die();
        }
        avgFrameTime = (Main.testDuration/(double)frameCount);
        System.gc();
        return avgFrameTime;
    }

    private void onLose()
    {
        //JOptionPane.showMessageDialog("You Lost. \nYour score was: " + score);
        JOptionPane.showMessageDialog(null, "You Lost. \nYour score was: " + score, "Loser", JOptionPane.INFORMATION_MESSAGE);
    }
}
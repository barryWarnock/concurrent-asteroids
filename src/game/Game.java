package game;
import entity.Entity;
import entity.Player;
import gui.Screen;
import java.util.ArrayList;

/**
 * Created by Travis Kurucz on 2016-03-19.
 * Singleton class that controls the entities and updates the game status
 */
public class Game {



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
        return Player.getScore();
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


}

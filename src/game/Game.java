package game;
import entity.Player;
import gui.Screen;


/**
 * Created by Travis Kurucz on 2016-03-19.
 */
public class Game {



    private static Game game = new Game();



    private Game(){

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

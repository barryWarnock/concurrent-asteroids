package game;

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
        return player.getScore();
    }



}

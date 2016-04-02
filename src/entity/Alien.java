package entity;

import game.Game;

public class Alien extends Entity {











    //TODO this
    @Override
    public void die(){
        Game game = Game.getInstance();
        game.removeEntity(this);
        //TODO play death animation
    }

}

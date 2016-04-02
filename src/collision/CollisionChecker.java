package collision;

import entity.Entity;

import java.util.ArrayList;

public interface CollisionChecker {
    /**
     * checks a set of entities for collision
     * @param entities the entities to check
     */
    public void checkCollisions(ArrayList<Entity> entities);
}

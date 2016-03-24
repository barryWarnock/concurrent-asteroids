package collision;

import entity.Entity;

import java.util.Iterator;
import java.util.List;

public interface CollisionChecker {
    /**
     * checks a set of entities for collision
     * @param entities the entities to check
     */
    public void checkCollisions(Iterator<Entity> entities);
}

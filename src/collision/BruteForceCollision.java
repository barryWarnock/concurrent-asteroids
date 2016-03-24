package collision;

import entity.Entity;

import java.util.Iterator;
import java.util.List;

public class BruteForceCollision implements CollisionChecker{
    public void checkCollisions(Iterator<Entity> entities) {


        while (entities.hasNext()) {
            Entity current = entities.next();

            //TODO this is horribly broken
            //The iterator must be cloned or deep copied.
            //This version will not work at all.
            Iterator<Entity> otherIt = entities;
            while(otherIt.hasNext()) {
                Entity other = otherIt.next();
                if (current.checkCollision(other)) {
                    current.reportCollision(other);
                    other.reportCollision(current);
                }
            }
        }
    }
}

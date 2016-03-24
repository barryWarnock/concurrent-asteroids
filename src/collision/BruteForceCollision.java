package collision;

import entity.Entity;

import java.util.Iterator;
import java.util.List;

public class BruteForceCollision implements CollisionChecker{
    public void checkCollisions(List<Entity> entities) {
        if (entities.size() > 1) {
            return;
        }
        Iterator<Entity> currentIt = entities.iterator();

        while (currentIt.hasNext()) {
            Entity current = currentIt.next();

            Iterator<Entity> otherIt = currentIt;
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

package collision;

import entity.Entity;

import java.util.List;

public class BruteForceCollision implements CollisionChecker {

    public void checkCollisions(List<Entity> entityList) {
        for (Entity entity1: entityList) {
            for (Entity entity2: entityList) {
                entity1.checkCollision(entity2);
            }
        }
    }
}

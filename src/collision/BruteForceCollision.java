package collision;

import entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BruteForceCollision implements CollisionChecker {

    public void checkCollisions(Iterator<Entity> entities) {

        List<Entity> entityList = new ArrayList<>();

        while (entities.hasNext()) {
            entityList.add(entities.next());
        }
        entities = entityList.iterator();

        while(entities.hasNext()) {
            Iterator<Entity> tempList = entityList.iterator();
            Entity ent1 = entities.next();

            while(tempList.hasNext()) {
                Entity ent2 = tempList.next();

                if(ent1.get_x() < ent2.get_x()) {
                    if( (ent1.get_x() + ent1.get_width()) >= ent2.get_x()) {
                        if(ent1.get_y() < ent2.get_y()) {
                            if( (ent1.get_y() + ent1.get_height()) >= ent2.get_y()) {
                                ent1.checkCollision(ent2);
                            }
                        }
                    }
                }

            }

        }

    }
}

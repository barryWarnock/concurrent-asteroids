package collision;

import entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollisionQuadTree implements CollisionChecker {
	
	public CollisionQuadTree(int splitThreshold) {
		//TODO define width and height somewhere else
		QuadTreeNode.set_splitThreshold(splitThreshold);
	}

    public void checkCollisions(List<Entity> entities) {
        Game game = Game.getInstance();
        int width = game.getScreenWidth();
        int height = game.getScreenHeight();

        QuadTreeNode tree = new QuadTreeNode(0, 0, width, height);

        for (Entity entity : entities) {
            tree.insert(entity);
        }

        tree.check_collisions();
    }
}

enum QuadTreeIntersect {
    NONE,
    PARTIAL,
    TOTAL
}

class QuadTreeNode {
    protected static int splitThreshold;
    protected boolean isSplit = false;

    double x, y, width, height;

    private List<Entity> entities;

    private QuadTreeNode child_1;
    private QuadTreeNode child_2;
    private QuadTreeNode child_3;
    private QuadTreeNode child_4;

    /**
     * @param x      the x position of this nodes upper left position
     * @param y      the y position of this nodes upper left position
     * @param width  the width of this node
     * @param height the height of this node
     */
    public QuadTreeNode(double x, double y, double width, double height) {
        child_1 = null;
        child_2 = null;
        child_3 = null;
        child_4 = null;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        entities = new ArrayList();
    }

    public static void set_splitThreshold(int splitThreshold) {
        QuadTreeNode.splitThreshold = splitThreshold;
    }

    public static int get_splitThreshold() {
        return QuadTreeNode.splitThreshold;
    }

    /**
     * @return the number of entities in this node
     */
    protected int node_size() {
        return this.entities.size();
    }

    /**
     * @return the total number of entities in the sub-tree that has this node as the root
     */
    protected int total_size() {
        int count = this.entities.size();
        count += (this.child_1 != null) ? (this.child_1.total_size()) : (0);
        count += (this.child_2 != null) ? (this.child_2.total_size()) : (0);
        count += (this.child_3 != null) ? (this.child_3.total_size()) : (0);
        count += (this.child_4 != null) ? (this.child_4.total_size()) : (0);
        return count;
    }

    /**
     * checks an entity for intersection with this node
     *
     * @param e the entity to check for intersection
     * @return NONE if the entity does not intersect
     * PARTIAL if the entity only partially intersects
     * TOTAL if the entity is completly within the node
     */
    protected QuadTreeIntersect check_entity_is_in(Entity e) {
        double left = this.x;
        double right = this.x + this.width;
        double top = this.y;
        double bottom = this.y + this.height;

        double eLeft = e.get_x();
        double eRight = e.get_x() + e.get_width();
        double eTop = e.get_y();
        double eBottom = e.get_y() + e.get_height();

        QuadTreeIntersect intersectType = null;

        //check for partial
        if (((Math.abs(this.x - e.get_x()) * 2) < (this.width + e.get_width())) &&
                ((Math.abs(this.y - e.get_y()) * 2) < (this.height + e.get_height()))) {
            intersectType = QuadTreeIntersect.PARTIAL;

            //check for total
            if (eRight < right && eBottom < bottom && eLeft > left && eTop > top) {
                intersectType = QuadTreeIntersect.TOTAL;
            }
        } else {
            intersectType = QuadTreeIntersect.NONE;
        }

        return intersectType;
    }

    /**
     * inserts the entity into the deepest child it fits
     *
     * @param e the entity to insert
     * @return false if the entity will not fit in this node
     */
    public boolean insert(Entity e) {
        if (this.check_entity_is_in(e) == QuadTreeIntersect.NONE) {
            return false;
        }

        if (!isSplit) {
            entities.add(e);
            if (this.node_size() > splitThreshold) {
                this.split();
            }
            return true;
        }

        QuadTreeIntersect child1Intersect, child2Intersect, child3Intersect, child4Intersect;
        child1Intersect = child_1.check_entity_is_in(e);
        child2Intersect = child_2.check_entity_is_in(e);
        child3Intersect = child_3.check_entity_is_in(e);
        child4Intersect = child_4.check_entity_is_in(e);

        if (child1Intersect == QuadTreeIntersect.TOTAL) {
            child_1.insert(e);
        } else if (child2Intersect == QuadTreeIntersect.TOTAL) {
            child_2.insert(e);
        } else if (child3Intersect == QuadTreeIntersect.TOTAL) {
            child_3.insert(e);
        } else if (child4Intersect == QuadTreeIntersect.TOTAL) {
            child_4.insert(e);
        } else { //if the entity does not fit neatly into any of the children add to this node
            this.entities.add(e);
        }
        return true;
    }

    /**
     * splits a node into four sub-nodes
     */
    protected void split() {
        this.isSplit = true;

        double newWidth = this.width/2;
        double newHeight = this.height/2;
        child_1 = new QuadTreeNode(this.x, this.y, newWidth, newHeight);
        child_2 = new QuadTreeNode(this.x+newWidth, this.y, newWidth, newHeight);
        child_3 = new QuadTreeNode(this.x, this.y+newHeight, newWidth, newHeight);
        child_2 = new QuadTreeNode(this.x+newWidth, this.y+newHeight, newWidth, newHeight);

        List<Entity> entitiesCopy = this.entities;
        this.entities = null;

        for (Entity entity : entitiesCopy) {
            this.insert(entity);
        }
    }

    /**
     * calls check_collisions(ArrayList<Entity> parentsEntitys)
     */
    public void check_collisions() {
        check_collisions(null);
    }

    /**
     * checks an entities list against itself then passes it down until it reaches a leaf node
     * once there everything in each leaf is checked against everything else in that node and everything that only
     * partially intersects that node
     * @param parentsEntities
     */
    public void check_collisions(ArrayList<Entity> parentsEntities) {
        //check nodes entities against themselves
        if (this.entities.size() > 1) {
            Iterator<Entity> restOfList = this.entities.iterator();
            do {
                Entity current = restOfList.next();
                Iterator<Entity> others = restOfList;
                while (others.hasNext()) {
                    Entity other = others.next();
                    if (other.checkCollision(current)) {
                        other.reportCollision(current);
                        current.reportCollision(other);
                    }
                }
            } while (restOfList.hasNext());
        }

        //check this node against parentsEntities
        if (parentsEntities != null) {
            Iterator<Entity> mine    = this.entities.iterator(); //this nodes entities
            Iterator<Entity> parents = parentsEntities.iterator();
            while (mine.hasNext()) {
                Entity current = mine.next();
                while (parents.hasNext()) {
                    Entity other = parents.next();
                    if (other.checkCollision(current)) {
                        other.reportCollision(current);
                        current.reportCollision(other);
                    }
                }
            }
        }

        //pass parentsEntities + this.entities to each of the children nodes for them to check against
        if (isSplit) {
            parentsEntities.addAll(this.entities);

            child_1.check_collisions(parentsEntities);
            child_2.check_collisions(parentsEntities);
            child_3.check_collisions(parentsEntities);
            child_4.check_collisions(parentsEntities);
        }
    }
}

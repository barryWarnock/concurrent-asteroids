package collision;

import entity.Entity;
import game.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuadTree implements CollisionChecker {

    private int splitThreshold;
    private List<Entity> overLapped;

	public QuadTree(int splitThreshold) {
		//TODO define width and height somewhere else
		this.splitThreshold = splitThreshold;
        overLapped = new ArrayList<>();
	}

    public void checkCollisions(Iterator<Entity> entities) {
        Game game = Game.getInstance();
        int width = game.getScreenWidth();
        int height = game.getScreenHeight();

        QuadTreeNode root = new QuadTreeNode(0, 0, width, height);

        while(entities.hasNext()) {
            root.insert(entities.next());
        }

        root.checkCollisions();
    }

    public void addOverlapped(Entity e) {
        overLapped.add(e);
    }

    public int getSplitThreshold() {
        return splitThreshold;
    }
}

enum QuadTreeIntersect {
    NONE,
    PARTIAL,
    TOTAL
}

class QuadTreeNode {

    double x, y, width, height;
    boolean isSplit;

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

        isSplit = false;

        entities = new ArrayList();
    }

    /**
     * @return the number of entities in this node
     */
    protected int nodeSize() {
        return entities.size();
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
        double left = x;
        double right = x + width;
        double top = y;
        double bottom = y + height;

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

        /* checking if child one exists is
         * sufficient because upon its generation all
         * child nodes are created simultaneously
         */

        if (null == child_1) {
            //TODO check if this element should actually be in this node
            entities.add(e);
            //TODO swap out magic number for splitThreshold
            if (this.nodeSize() > 5) {
                this.split();
            }
            return true;
        }

        if (child_1.check_entity_is_in(e) == QuadTreeIntersect.TOTAL) {
            child_1.insert(e);
        } else if (child_2.check_entity_is_in(e) == QuadTreeIntersect.TOTAL) {
            child_2.insert(e);
        } else if (child_3.check_entity_is_in(e) == QuadTreeIntersect.TOTAL) {
            child_3.insert(e);
        } else if (child_4.check_entity_is_in(e) == QuadTreeIntersect.TOTAL) {
            child_4.insert(e);
        } else { //if the entity does not fit neatly into any of the children add to the global list
            //TODO have this move up the call stack
        }
        return true;
    }

    /**
     * splits a node into four sub-nodes
     */
    protected void split() {
        isSplit = true;

        double newWidth = width/2;
        double newHeight = height/2;
        child_1 = new QuadTreeNode(x, y, newWidth, newHeight);
        child_2 = new QuadTreeNode(x+newWidth, y, newWidth, newHeight);
        child_3 = new QuadTreeNode(x, y+newHeight, newWidth, newHeight);
        child_2 = new QuadTreeNode(x+newWidth, y+newHeight, newWidth, newHeight);

        List<Entity> entitiesCopy = this.entities;
        this.entities = null;

        for (Entity entity : entitiesCopy) {
            this.insert(entity);
        }
    }

    public void checkCollisions() {


        //check nodes entities against themselves
        if (this.entities.size() > 1) {
            Iterator<Entity> restOfList = this.entities.iterator();
            do {
                Entity current = restOfList.next();
                //TODO this next line is horribly broken
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
        //TODO broken
        /*
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
        */
    }
    /**
     * @return the total number of entities in the sub-tree that has this node as the root
     */
    protected int totalSize() {
        int count = entities.size();
        count += (child_1 != null) ? (child_1.totalSize()) : (0);
        count += (child_2 != null) ? (child_2.totalSize()) : (0);
        count += (child_3 != null) ? (child_3.totalSize()) : (0);
        count += (child_4 != null) ? (child_4.totalSize()) : (0);
        return count;
    }
}

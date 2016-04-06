package collision;

import entity.Entity;
import game.Game;
import gui.Screen;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollisionQuadTree implements CollisionChecker {




    public CollisionQuadTree(int splitThreshold) {
        QuadTreeNode.set_splitThreshold(splitThreshold);
    }

    public void checkCollisions(List<Entity> entities) {
        Game game = Game.getInstance();
        int width = game.getScreenWidth();
        int height = game.getScreenHeight();

        QuadTreeNode tree = new QuadTreeNode(0, 0, width, height);
        tree.init();

        for(int i=0; entities.size() > i; i++) {
            tree.insert(entities.get(i));
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

    protected static List<Entity> partialEntities;
    double xStart, yStart, width, height;

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
        this.xStart = x;
        this.yStart = y;
        this.width = width;
        this.height = height;

        entities = new ArrayList<>();

        Graphics2D graphics = Screen.getInstance().getBuffer().createGraphics();
        graphics.setPaint(Color.RED);
        graphics.draw(new Rectangle2D.Double(x, y, width, height));
    }

    public static void set_splitThreshold(int splitThreshold) {
        QuadTreeNode.splitThreshold = splitThreshold;
    }

    /**
     * @return the number of entities in this node
     */
    protected int node_size() {
        return entities.size();
    }
    protected void init() {
        partialEntities = new ArrayList<>();
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
        double eX      = e.get_x();
        double eY      = e.get_y();
        double eWidth  = e.get_width();
        double eHeight = e.get_height();

        QuadTreeIntersect intersectType = QuadTreeIntersect.NONE;

        if( (xStart < eX && xStart + width > eX + eWidth) ||
                (yStart < eY && yStart + height > eY + eHeight)) {
            intersectType = QuadTreeIntersect.TOTAL;
        } else if( (yStart < eY + eHeight && eY < yStart) ||
                (eX < xStart && eX + eWidth > xStart) ||
                (eY < yStart + height && yStart + height < eY + eHeight) ||
                (eX < xStart + width && xStart + width < eX + eHeight)) {
            intersectType = QuadTreeIntersect.PARTIAL;
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
            if (node_size() > splitThreshold) {
                split();
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
        } else { //if the entity does not fit neatly into any of the children add to this node
//            entities.add(e);
            partialEntities.add(e);
        }
        return true;
    }

    /**
     * splits a node into four sub-nodes
     */
    protected void split() {
        isSplit = true;

        double newWidth = this.width/2;
        double newHeight = this.height/2;
        child_1 = new QuadTreeNode(xStart, yStart, newWidth, newHeight);
        child_2 = new QuadTreeNode(xStart + newWidth, yStart, newWidth, newHeight);
        child_3 = new QuadTreeNode(xStart, yStart + newHeight, newWidth, newHeight);
        child_4 = new QuadTreeNode(xStart + newWidth, yStart + newHeight, newWidth, newHeight);

        List<Entity> entitiesCopy = entities;
        entities = new ArrayList<>();
        entitiesCopy.forEach(this::insert);
    }

    /**
     * calls check_collisions(List<Entity> parentsEntities)
     */
//    public void check_collisions_old() {
//        check_collisions(null);
//    }

    public void check_collisions() {
        if(isSplit) {
            child_1.check_collisions();
            child_2.check_collisions();
            child_3.check_collisions();
            child_4.check_collisions();
        } else {
            //check partial entities against ones inside the node
            for(int i=0; partialEntities.size() > i; i++) {
                for(int j=0; entities.size() > j; j++) {
                    partialEntities.get(i).checkCollision(entities.get(j));
                }
            }
            //check all partials against each other
            for(int i=0; partialEntities.size() > i; i++) {
                for(int j=i; partialEntities.size() > j; j++) {
                    partialEntities.get(i).checkCollision(partialEntities.get(j));
                }
            }
            //check all elements in the node against each other
            for(int i=0; entities.size() > i; i++) {
                for(int j=i; entities.size() > j; j++) {
                    entities.get(i).checkCollision(entities.get(j));
                }
            }
        }
    }
    /**
     * checks an entities list against itself then passes it down until it reaches a leaf node
     * once there everything in each leaf is checked against everything else in that node and everything that only
     * partially intersects that node
     * @param parentsEntities the entities from the parent lists
     */
    public void check_collisions(List<Entity> parentsEntities) {

        //check nodes entities against themselves
        if (entities.size() > 1) {
            Iterator<Entity> restOfList = entities.iterator();
            do {
                Entity current = restOfList.next();
                Iterator<Entity> others = restOfList;
                while (others.hasNext()) {
                    Entity other = others.next();
                    other.checkCollision(current);
                }
            } while (restOfList.hasNext());
        }

        //check this node against parentsEntities
        if (parentsEntities != null) {
            Iterator<Entity> mine    = entities.iterator(); //this nodes entities
            Iterator<Entity> parents = parentsEntities.iterator();
            while (mine.hasNext()) {
                Entity current = mine.next();
                while (parents.hasNext()) {
                    Entity other = parents.next();
                    other.checkCollision(current);
                }
            }
        }

        //pass parentsEntities + this.entities to each of the children nodes for them to check against
        if (isSplit) {
            if (parentsEntities != null) {
                parentsEntities.addAll(this.entities);
            } else {
                parentsEntities = this.entities;
            }

            child_1.check_collisions(parentsEntities);
            child_2.check_collisions(parentsEntities);
            child_3.check_collisions(parentsEntities);
            child_4.check_collisions(parentsEntities);
        }
    }
}
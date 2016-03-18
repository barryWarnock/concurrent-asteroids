package collision;

enum QuadTreeIntersect {
    NONE,
    PARTIAL,
    TOTAL
}

public class QuadTreeNode<T> {
    protected static int splitThreshold;
    protected boolean isSplit = false;

    double x, y, width, heigth;

    private List<Entity> entities;

    private QuadTreeNode<T> child_1;
    private QuadTreeNode<T> child_2;
    private QuadTreeNode<T> child_3;
    private QuadTreeNode<T> child_4;

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
        this.width = w;
        this.height = h;

        entities = new ArrayList();
    }

    public void set_splitThreshold(int splitThreshold) {
        QuadTreeNode.splitThreshold = splitThreshold;
    }

    public int get_splitThreshold() {
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
        count += (child_1) ? (child_1.total_size()) : (0);
        count += (child_2) ? (child_2.total_size()) : (0);
        count += (child_3) ? (child_3.total_size()) : (0);
        count += (child_4) ? (child_4.total_size()) : (0);
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
        int left = this.x;
        int right = this.x + this.width;
        int top = this.y;
        int bottom = this.y + this.height;

        int eLeft = e.x;
        int eRight = e.x + e.width;
        int eTop = e.y;
        int eBottom = e.y + e.height;

        QuadTreeIntersect intersectType = null;

        //check for partial
        if (left < eLeft && eLeft < right && top < eTop && eTop < bottom) {
            intersectType = PARTIAL;

            //check for total
            if (eRight < right && eBottom < bottom) {
                intersectType = TOTAL;
            }
        } else {
            intersectType = NONE;
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
        if (this.check_entity_is_in(e) == NONE) {
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

        if (child1Intersect == TOTAL) {
            child_1.insert(e);
        } else if (child2Intersect == TOTAL) {
            child_2.insert(e);
        } else if (child3Intersect == TOTAL) {
            child_3.insert(e);
        } else if (child4Intersect == TOTAL) {
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
        child_1 = new QuadTreeNode<Entity>(this.x, this.y, newWidth, newHeight);
        child_2 = new QuadTreeNode<Entity>(this.x+newWidth, this.y, newWidth, newHeight);
        child_3 = new QuadTreeNode<Entity>(this.x, this.y+newHeight, newWidth, newHeight);
        child_2 = new QuadTreeNode<Entity>(this.x+newWidth, this.y+newHeight, newWidth, newHeight);

        ArrayList entitiesCopy = this.entities;
        this.entities = null;

        for (entity : entitiesCopy) {
            this.insert(entity);
        }
    }
}

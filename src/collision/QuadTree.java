package collision;

import entity.Entity;
import game.Game;

public class QuadTree {
	
	private QuadTreeNode root;
	
	public QuadTree(int splitThreshold) {
		//TODO define width and height somewhere else
		root = new QuadTreeNode(0, 0, 600, 400);
		QuadTreeNode.set_splitThreshold(splitThreshold);
	}

    public void insert(Entity e) {
        root.insert(e);
    }

    public void check_collisions() {
        root.check_collisions();
    }
}
package collision;

public class QuadTreeNode<T> {
	int x, y, width, heigth;

	private ArrayList<Entity> entities;

	private QuadTreeNode<T> child_1;
	private QuadTreeNode<T> child_2;
	private QuadTreeNode<T> child_3;
	private QuadTreeNode<T> child_4;
	
	public QuadTreeNode<T>(int x_, int y_, int w_, int h_) {
		child_1 = null;
		child_2 = null;
		child_3 = null;
		child_4 = null;
		
		x = x_;
		y = y_;
		width  = w_;
		height = h_;

		entities = new ArrayList();
	}

	
}
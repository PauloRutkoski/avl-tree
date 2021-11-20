package model;

public class Node implements Cloneable{
	private Object key;
	private Integer index;
	private Node left;
	private Node right;
	private int height;


	public Node(Integer key) {
		this.key = key;
		height = 1;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Node clone() {
		try {
			return (Node) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}

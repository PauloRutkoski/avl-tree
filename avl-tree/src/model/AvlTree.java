package model;

import java.util.ArrayList;
import java.util.List;

public class AvlTree {

	private Node root;
	private List<Integer> preOrder;
	private List<Integer> postOrder;
	private List<Integer> inOrder;
	private List<Integer> search;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public List<Integer> getPreOrder() {
		return preOrder;
	}

	public List<Integer> getPostOrder() {
		return postOrder;
	}

	public List<Integer> getInOrder() {
		return inOrder;
	}

	public List<Integer> getSearch() {
		return search;
	}

	public void insert(int key) {
		this.root = insert(root, key);
	}
	
	private Node insert(Node node, int key) {
		if (node == null) {
			return new Node(key);
		}
		if (node.getKey() != key) {
			if (node.getKey() < key) {
				node.setRight(insert(node.getRight(), key));
			} else {
				node.setLeft(insert(node.getLeft(), key));
			}
		}
		return verifyBalance(node);
	}
	
	public void delete(int key) {
		this.root = delete(root, key);
	}

	private Node delete(Node node, int key) {
		if (node == null) {
			return null;
		}
		if(node.getKey() == key) {
			node = executeDelete(node);
		}else {
			if(key > node.getKey()) {
				node.setRight(delete(node.getRight(), key));
			}else {
				node.setLeft(delete(node.getLeft(), key));
			}
		}
		return verifyBalance(node);
	}
	
	private Node executeDelete(Node node) {
		if(node.getHeight() == 1) {
			node = null;
		}else if(node.getLeft() == null || node.getRight() == null) {
			node = node.getLeft() == null ? node.getRight() : node.getLeft();
		}else{
			Node right = node.getRight().clone();
			Node left = node.getLeft().clone();
			node.setKey(left.getKey());
			node.setRight(left.getRight());
			node.setLeft(left.getLeft());
			Node mostRight = findMostRight(node);
			mostRight.setRight(right);
		}
		return node;
	}
	
	private Node findMostRight(Node node) {
		if(node.getRight() == null) {
			return node;
		}
		return findMostRight(node.getRight());
	}
	
 
	public void updateHeight() {
		updateHeight(root);
	}

	private void updateHeight(Node node) {
		if (node == null) {
			return;
		}
		updateHeight(node.getLeft());
		updateHeight(node.getRight());
		node.setHeight(nodeHeight(node));
	}

	private int nodeHeight(Node node) {
		if (node == null) {
			return 0;
		}
		int left = node.getLeft() == null ? 1 : 1 + nodeHeight(node.getLeft());
		int right = node.getRight() == null ? 1 : 1 + nodeHeight(node.getRight());
		return Math.max(left, right);
	}

	private Node verifyBalance(Node node) {
		if (node == null) {
			return null;
		}
		updateHeight(node);
		int factor = getFactor(node);
		balance(node, factor);
		return node;
	}

	private int getFactor(Node node) {
		if (node == null) {
			return 0;
		}
		int left = node.getLeft() == null ? 0 : node.getLeft().getHeight();
		int right = node.getRight() == null ? 0 : node.getRight().getHeight();
		return left - right;
	}

	private void balance(Node node, int factor) {
		if (factor < -1) {
			int dbRotateFactor = getFactor(node.getRight());
			if (dbRotateFactor > 0) {
				rotateSimpleRight(node.getRight());
			}
			rotateSimpleLeft(node);
		} else if (factor > 1) {
			int dbRotateFactor = getFactor(node.getLeft());
			if (dbRotateFactor < 0) {
				rotateSimpleLeft(node.getLeft());
			}
			rotateSimpleRight(node);
		}
	}

	private void rotateSimpleRight(Node root) {
		Node origin = root.clone();
		Node left = root.getLeft();
		Node rightChild = left.getRight();

		origin.setLeft(null);
		root.setKey(left.getKey());
		root.setLeft(left.getLeft());

		if (rightChild != null) {
			origin.setLeft(rightChild);
		}
		root.setRight(origin);
	}

	private void rotateSimpleLeft(Node root) {
		Node origin = root.clone();
		Node right = root.getRight();
		Node leftChild = right.getLeft();

		origin.setRight(null);
		root.setKey(right.getKey());
		root.setRight(right.getRight());

		if (leftChild != null) {
			origin.setRight(leftChild);
		}
		root.setLeft(origin);
	}

	public void executeRoutes() {
		preOrder = new ArrayList<>();
		postOrder = new ArrayList<>();
		inOrder = new ArrayList<>();

		execRoutePreOrder(this.root);
		execRoutePostOrder(this.root);
		execRouteInOrder(this.root);
	}

	private void execRoutePreOrder(Node node) {
		if (node == null) {
			return;
		}
		preOrder.add(node.getKey());
		execRoutePreOrder(node.getLeft());
		execRoutePreOrder(node.getRight());
	}

	private void execRoutePostOrder(Node node) {
		if (node == null) {
			return;
		}
		execRoutePostOrder(node.getLeft());
		execRoutePostOrder(node.getRight());
		postOrder.add(node.getKey());
	}

	private void execRouteInOrder(Node node) {
		if (node == null) {
			return;
		}
		execRouteInOrder(node.getLeft());
		inOrder.add(node.getKey());
		execRouteInOrder(node.getRight());
	}

	public boolean exists(int key) {
		this.search = new ArrayList<>();
		Node node = find(root, key);
		return node != null;
	}

	private Node find(Node node, int key) {
		if (node == null) {
			return null;
		}
		this.search.add(node.getKey());
		if (node.getKey() == key) {
			return node;
		} else if (node.getKey() > key) {
			return find(node.getLeft(), key);
		} else if (node.getKey() < key) {
			return find(node.getRight(), key);
		}
		return null;
	}
}

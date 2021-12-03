package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvlTree {

	private Node root;
	private List<Object> preOrder;
	private List<Object> postOrder;
	private List<Object> inOrder;
	private List<Object> search;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public List<Object> getPreOrder() {
		return preOrder;
	}

	public List<Object> getPostOrder() {
		return postOrder;
	}

	public List<Object> getInOrder() {
		return inOrder;
	}

	public List<Object> getSearch() {
		return search;
	}

	public void insert(Object key) {
		this.root = insert(root, key);
	}
	
	private Node insert(Node node, Object key) {
		if (node == null) {
			return new Node(key);
		}
		if (!node.getKey().equals(key)) {
			// if (node.getKey() < key) {
			if (verifyIfIsLess(node, key)) {
				node.setRight(insert(node.getRight(), key));
			} else {
				node.setLeft(insert(node.getLeft(), key));
			}
		}
		return verifyBalance(node);
	}

	// method which returns if the node value is less or comes before the key and returns
	private boolean verifyIfIsLess(Node node, Object key) {
		if(isString(node.getKey()) && isString(key))
			return ((String) node.getKey()).compareToIgnoreCase((String) key) < 0;
		else if(isDate(node.getKey()) && isDate(key))
			return ((LocalDate) node.getKey()).isBefore((LocalDate) key);
		return false;
	}

	// an enjambration kkkk of the 'typeof' method for strings
	private boolean isString(Object obj) { return obj.getClass().getTypeName().contains("String"); }
	private boolean isDate(Object obj) { return obj.getClass().getTypeName().contains("LocalDate"); }

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
		boolean isLess = verifyIfIsLess(node, key);
		if (node.getKey().equals(key)) {
			return node;
		} else if (isLess) {
			return find(node.getRight(), key);
		} else {
			return find(node.getLeft(), key);
		}
		// return null;
	}
}

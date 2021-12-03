package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AvlTree<T extends Comparable<T>> {

    private Node<T> root;
    private List<Integer> search;

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public List<Integer> getSearch() {
        return search;
    }

    public void insert(T key, Integer index) {
        this.root = insert(root, key, index);
    }

    private Node<T> insert(Node<T> node, T key, Integer index) {
        if (node == null) {
            return new Node<T>(key, index);
        }
        Integer compare = compare(node.getKey(), key);
        if (compare > 0) {
            node.setRight(insert(node.getRight(), key, index));
        } else {
            node.setLeft(insert(node.getLeft(), key, index));
        }

        return verifyBalance(node);
    }

    private Integer compare(T nodeKey, T key) {
        return key.compareTo(nodeKey);
    }

    private void updateHeight(Node<T> node) {
        if (node == null) {
            return;
        }
        updateHeight(node.getLeft());
        updateHeight(node.getRight());
        node.setHeight(nodeHeight(node));
    }

    private int nodeHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int left = node.getLeft() == null ? 1 : 1 + nodeHeight(node.getLeft());
        int right = node.getRight() == null ? 1 : 1 + nodeHeight(node.getRight());
        return Math.max(left, right);
    }

    private Node<T> verifyBalance(Node<T> node) {
        if (node == null) {
            return null;
        }
        updateHeight(node);
        int factor = getFactor(node);
        balance(node, factor);
        return node;
    }

    private int getFactor(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int left = node.getLeft() == null ? 0 : node.getLeft().getHeight();
        int right = node.getRight() == null ? 0 : node.getRight().getHeight();
        return left - right;
    }

    private void balance(Node<T> node, int factor) {
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

    private void rotateSimpleRight(Node<T> root) {
        Node<T> origin = root.clone();
        Node<T> left = root.getLeft();
        Node<T> rightChild = left.getRight();

        origin.setLeft(null);
        root.setKey(left.getKey());
        root.setLeft(left.getLeft());

        if (rightChild != null) {
            origin.setLeft(rightChild);
        }
        root.setRight(origin);
    }

    private void rotateSimpleLeft(Node<T> root) {
        Node<T> origin = root.clone();
        Node<T> right = root.getRight();
        Node<T> leftChild = right.getLeft();

        origin.setRight(null);
        root.setKey(right.getKey());
        root.setRight(right.getRight());

        if (leftChild != null) {
            origin.setRight(leftChild);
        }
        root.setLeft(origin);
    }


    public void findUnique(T key) {
        this.search = new ArrayList<>();
        Node<T> node = searchUnique(root, key);
        if (node != null) {
            search.add(node.getIndex());
        }
    }

    private Node<T> searchUnique(Node<T> node, T key) {
        if (node == null) {
            return null;
        }
        int compare = this.compare(node.getKey(), key);
        if (compare == 0) {
            return node;
        } else if (compare == 1) {
            return searchUnique(node.getRight(), key);
        } else {
            return searchUnique(node.getLeft(), key);
        }
    }

    public void findLike(T key) {
        this.search = new ArrayList<>();
        searchLike(root, key);
    }

    private void searchLike(Node<T> node, T key) {
        if (node == null) {
            return;
        }
        if (startsWith(node.getKey(), key)) {
            execRouteInOrderStartsWith(node, key);
        } else {
            int compare = compareString(key, node.getKey());
            if (compare > 0) {
                searchLike(node.getRight(), key);
            } else {
                searchLike(node.getLeft(), key);
            }
        }
    }

    private Integer compareString(T key, T nodeKey) {
        String key1 = (String) key;
        String key2 = (String) nodeKey;
        return key1.toLowerCase().compareTo(key2.toLowerCase());
    }

    private void execRouteInOrderStartsWith(Node<T> node, T key) {
        if (node == null) {
            return;
        }

        if (startsWith(node.getKey(), key)) {
            execRouteInOrderStartsWith(node.getLeft(), key);
            this.search.add(node.getIndex());
            execRouteInOrderStartsWith(node.getRight(), key);
        }
    }

    private boolean startsWith(T nodeKey, T key) {
        String nodeKeyStr = (String) nodeKey;
        String searchKey = (String) key;
        return nodeKeyStr.toLowerCase().startsWith(searchKey.toLowerCase());
    }

    public void findByPeriod(T start, T end) {
        this.search = new ArrayList<>();
        searchPeriod(start, end, root);
    }

    private void searchPeriod(T start, T end, Node<T> node) {
        if (node == null) {
            return;
        }
        if (isInLimit(start, end, node.getKey())) {
            execRouteInOrderPeriod(start, end, node);
        } else {
            int compareStart = this.compare(node.getKey(), start);
            if (compareStart > 1) {
                searchPeriod(start, end, node.getRight());
            } else {
                searchPeriod(start, end, node.getLeft());
            }
        }
    }

    private void execRouteInOrderPeriod(T start, T end, Node<T> node) {
        if (node == null) {
            return;
        }

        if (isInLimit(start, end, node.getKey())) {
            execRouteInOrderPeriod(start, end, node.getLeft());
            this.search.add(node.getIndex());
            execRouteInOrderPeriod(start, end, node.getRight());
        }
    }

    private boolean isInLimit(T start, T end, T key) {
        int afterStart = this.compare(start, key);
        int beforeEnd = this.compare(end, key);
        return afterStart >= 0 && beforeEnd <= 0;
    }


    private Node<T> find(Node<T> node, int key) {
        if (node == null) {
            return null;
        }

        return null;
    }


}

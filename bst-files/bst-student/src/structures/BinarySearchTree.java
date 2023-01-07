package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int getSize() {
		
		return subtreeGetSize(root);
	}

	private int subtreeGetSize(BSTNode<T> node) {
		if (node == null){
			return 0;
		} 

		else{
			return 1 + subtreeGetSize(node.getLeft())
					+ subtreeGetSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		
		return containsHelper(t, root);
	}
	
	private boolean containsHelper(T element, BSTNode<T> tree) {
		 if (tree == null){
			 return false; 
		 }
		 else if (element.compareTo(tree.getData()) < 0){
			 return containsHelper(element, tree.getLeft()); // Search left subtree
		 }
		 else if (element.compareTo(tree.getData()) > 0){
			 return containsHelper(element, tree.getRight()); // Search right subtree
		}
		 else{
			 return true; 
		 }
	}

	public boolean removeElement(T t) {
		
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} 
		else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} 
		else { 
			if (node.getLeft() == null) {
				return node.getRight();
			} 
			else if (node.getRight() == null) {
				return node.getLeft();
			} 
			else { 
				T predecessorValue = getHighestValueFromSubtree(node.getLeft());
				node.setLeft(removeRightmostFromSubtree(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}
	
	public T getHighestValueFromSubtree(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} 
		else {
			return getHighestValueFromSubtree(node.getRight());
		}
	}

	public T getLowestValueFromSubtree(BSTNode<T> node) {
		
		if (node.getLeft() == null) {
			return node.getData();
		} 
		else {
			return getLowestValueFromSubtree(node.getLeft());
		}
	}

	private BSTNode<T> removeRightmostFromSubtree(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} 
		else {
			node.setRight(removeRightmostFromSubtree(node.getRight()));
			if (node.getRight() != null){
				node.getRight().setParent(node);
			}
			return node;
		}
	}

	public BSTNode<T> removeLeftmostFromSubtree(BSTNode<T> node) {
		
		if (node.getLeft() == null) {
			return node.getRight();
		} 
		else {
			node.setLeft(removeLeftmostFromSubtree(node.getLeft()));
			if (node.getLeft() != null){
				node.getLeft().setParent(node);
			}
			return node;
		}
	}

	public T getElement(T t) {
		
		if (t == null){
			throw new NullPointerException();
		}
		return get(t, root);
	}

	private T get(T t, BSTNode<T> node) {
		if (node == null){ 
			return null;
		}

		int result = t.compareTo(node.getData());

		if (result == 0){
			return node.getData();
		}
		else if(result < 0){ 
			return get(t, node.getLeft());
		}
		else {
			return get(t, node.getRight());
		}
	}

	public void addElement(T t) {
		
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}

		int result = toAdd.getData().compareTo(node.getData());
		
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} 
		else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}
	@Override
	public T getMin() {
		
		if (root == null){
			 return null;
		}
		return getMinimum(root);
	}
	
	private T getMinimum(BSTNode<T> node) {
		if (node.getLeft() == null) {
			return node.getData();
		} 
		else {
			return getMinimum(node.getLeft());
		}
	}

	@Override
	public T getMax() {
		
		if (root == null){
			return null;
		}
		return getHighestValueFromSubtree(root);
	}

	@Override
	public int height() {
		
		return height(root);
	}
	
	private int height(BSTNode<T> node) {
		if (node == null){
			return -1;
		}
		return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	public Iterator<T> preorderIterator() {
		
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}

	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}
	@Override
	public boolean equals(BSTInterface<T> other) {
		
		return equals(root, other.getRoot());
	}
	
	private boolean equals(BSTNode<T> one, BSTNode<T> two) {
		if (one == null && two == null){
			return true;
		}
		else if (one == null || two == null){
			return false;
		}
		else {
			if (!one.getData().equals(two.getData())){
				return false;
			}
			return equals(one.getLeft(), two.getLeft()) && equals(one.getRight(), two.getRight());
		}
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
	
		Iterator<T> iter1 = this.inorderIterator();
		Iterator<T> iter2 = other.inorderIterator();
		while (iter1.hasNext() && iter2.hasNext()){ 
			if (!iter1.next().equals(iter2.next())){
				return false;
			}
		}
		return !iter1.hasNext() && !iter2.hasNext();
	}
	
	
	@Override
	public int countRange(T min, T max) {
    
		int count = 0;

		
		preorderIterator();
		inorderIterator();
		postorderIterator();
		
	
		
		return count;
	}


	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.addElement(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.removeElement(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.addElement(r);
		}
		System.out.println(tree.getSize());
		System.out.println(tree.height());
		System.out.println(tree.countRange("a", "g"));
		System.out.println(tree.countRange("c", "f"));
	}
}
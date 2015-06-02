package Chapter3.Section5;

import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/zoffyzhang
 * @Date Jun 1, 2015
 */
public class No09
{
	// test
	public static void main(String[] args)
	{
		Integer[] keys = { 1, 1, 1, 2, 3, 3 };
		String[] vals = { "aaa", "aaa", "bbb", "xxx", "xxx", "yyy" };

		BST<Integer, String> bst = new BST<>();
		for (int i = 0; i < keys.length; i++)
		{
			bst.put(keys[i], vals[i]);
		}

		for (Integer key : bst.keys())
		{
			StdOut.print(key + ": ");
			for (String val : bst.get(key))
				StdOut.print(val + " ");
			StdOut.println();
		}

	}
}

// 修改自algs4的BST， 重复的值将被保存在同一个Queue里，缺点是这种方法会造成内存大幅上升。
// 如果按照一个节点一个值的方式修改，可能又要一整个晚上了
// 用这种方法甚至连delete都不用修改，偷懒成功不觉得羞耻反而有点小高兴啊\(^o^)/~
// 为了便于测试，get将返回Queue中的所有值
class BST<Key extends Comparable<Key>, Value>
{
	private Node root;             // root of BST

	private class Node
	{
		private Key key;
		private Queue<Value> val = new Queue<>();     // 值Queue
		private Node left, right;
		private int N;

		public Node(Key key, Value val, int N)
		{
			this.key = key;
			this.val.enqueue(val);
			this.N = N;
		}
	}

	// is the symbol table empty?
	public boolean isEmpty()
	{
		return size() == 0;
	}

	// return number of key-value pairs in BST
	public int size()
	{
		return size(root);
	}

	// return number of key-value pairs in BST rooted at x
	private int size(Node x)
	{
		if (x == null)
			return 0;
		else
			return x.N;
	}

	/***********************************************************************
	 * Search BST for given key, and return associated value if found, return
	 * null if not found
	 ***********************************************************************/
	// does there exist a key-value pair with given key?
	public boolean contains(Key key)
	{
		return get(key) != null;
	}

	// return value associated with the given key, or null if no such key exists
	public Queue<Value> get(Key key)
	{
		return get(root, key);
	}

	private Queue<Value> get(Node x, Key key)
	{
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return get(x.left, key);
		else if (cmp > 0)
			return get(x.right, key);
		else
			return x.val;	// 返回同键中最后被enqueue的值
	}

	/***********************************************************************
	 * Insert key-value pair into BST If key already exists, update with new
	 * value
	 ***********************************************************************/
	public void put(Key key, Value val)
	{
		if (val == null)
		{
			delete(key);
			return;
		}
		root = put(root, key, val);
		assert check();
	}

	private Node put(Node x, Key key, Value val)
	{
		if (x == null)
			return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, key, val);
		else if (cmp > 0)
			x.right = put(x.right, key, val);
		else
		{
			for (Value v : x.val)
			{
				// 对于不重复的值，enqueue
				if (v == val)
					break;
				x.val.enqueue(val);
			}
		}
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}

	/***********************************************************************
	 * Delete
	 ***********************************************************************/

	public void deleteMin()
	{
		if (isEmpty())
			throw new NoSuchElementException("Symbol table underflow");
		root = deleteMin(root);
		assert check();
	}

	private Node deleteMin(Node x)
	{
		if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public void deleteMax()
	{
		if (isEmpty())
			throw new NoSuchElementException("Symbol table underflow");
		root = deleteMax(root);
		assert check();
	}

	private Node deleteMax(Node x)
	{
		if (x.right == null)
			return x.left;
		x.right = deleteMax(x.right);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public void delete(Key key)
	{
		root = delete(root, key);
		assert check();
	}

	private Node delete(Node x, Key key)
	{
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = delete(x.left, key);
		else if (cmp > 0)
			x.right = delete(x.right, key);
		else
		{
			if (x.right == null)
				return x.left;
			if (x.left == null)
				return x.right;
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	/***********************************************************************
	 * Min, max, floor, and ceiling
	 ***********************************************************************/
	public Key min()
	{
		if (isEmpty())
			return null;
		return min(root).key;
	}

	private Node min(Node x)
	{
		if (x.left == null)
			return x;
		else
			return min(x.left);
	}

	public Key max()
	{
		if (isEmpty())
			return null;
		return max(root).key;
	}

	private Node max(Node x)
	{
		if (x.right == null)
			return x;
		else
			return max(x.right);
	}

	public Key floor(Key key)
	{
		Node x = floor(root, key);
		if (x == null)
			return null;
		else
			return x.key;
	}

	private Node floor(Node x, Key key)
	{
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return x;
		if (cmp < 0)
			return floor(x.left, key);
		Node t = floor(x.right, key);
		if (t != null)
			return t;
		else
			return x;
	}

	public Key ceiling(Key key)
	{
		Node x = ceiling(root, key);
		if (x == null)
			return null;
		else
			return x.key;
	}

	private Node ceiling(Node x, Key key)
	{
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return x;
		if (cmp < 0)
		{
			Node t = ceiling(x.left, key);
			if (t != null)
				return t;
			else
				return x;
		}
		return ceiling(x.right, key);
	}

	/***********************************************************************
	 * Rank and selection
	 ***********************************************************************/
	public Key select(int k)
	{
		if (k < 0 || k >= size())
			return null;
		Node x = select(root, k);
		return x.key;
	}

	// Return key of rank k.
	private Node select(Node x, int k)
	{
		if (x == null)
			return null;
		int t = size(x.left);
		if (t > k)
			return select(x.left, k);
		else if (t < k)
			return select(x.right, k - t - 1);
		else
			return x;
	}

	public int rank(Key key)
	{
		return rank(key, root);
	}

	// Number of keys in the subtree less than key.
	private int rank(Key key, Node x)
	{
		if (x == null)
			return 0;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return rank(key, x.left);
		else if (cmp > 0)
			return 1 + size(x.left) + rank(key, x.right);
		else
			return size(x.left);
	}

	/***********************************************************************
	 * Range count and range search.
	 ***********************************************************************/
	public Iterable<Key> keys()
	{
		return keys(min(), max());
	}

	public Iterable<Key> keys(Key lo, Key hi)
	{
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue, lo, hi);
		return queue;
	}

	private void keys(Node x, Queue<Key> queue, Key lo, Key hi)
	{
		if (x == null)
			return;
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		if (cmplo < 0)
			keys(x.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0)
			queue.enqueue(x.key);
		if (cmphi > 0)
			keys(x.right, queue, lo, hi);
	}

	public int size(Key lo, Key hi)
	{
		if (lo.compareTo(hi) > 0)
			return 0;
		if (contains(hi))
			return rank(hi) - rank(lo) + 1;
		else
			return rank(hi) - rank(lo);
	}

	// height of this BST (one-node tree has height 0)
	public int height()
	{
		return height(root);
	}

	private int height(Node x)
	{
		if (x == null)
			return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	// level order traversal
	public Iterable<Key> levelOrder()
	{
		Queue<Key> keys = new Queue<Key>();
		Queue<Node> queue = new Queue<Node>();
		queue.enqueue(root);
		while (!queue.isEmpty())
		{
			Node x = queue.dequeue();
			if (x == null)
				continue;
			keys.enqueue(x.key);
			queue.enqueue(x.left);
			queue.enqueue(x.right);
		}
		return keys;
	}

	/*************************************************************************
	 * Check integrity of BST data structure
	 *************************************************************************/
	private boolean check()
	{
		if (!isBST())
			StdOut.println("Not in symmetric order");
		if (!isSizeConsistent())
			StdOut.println("Subtree counts not consistent");
		if (!isRankConsistent())
			StdOut.println("Ranks not consistent");
		return isBST() && isSizeConsistent() && isRankConsistent();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since
	// order is strict
	private boolean isBST()
	{
		return isBST(root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(Node x, Key min, Key max)
	{
		if (x == null)
			return true;
		if (min != null && x.key.compareTo(min) <= 0)
			return false;
		if (max != null && x.key.compareTo(max) >= 0)
			return false;
		return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	}

	// are the size fields correct?
	private boolean isSizeConsistent()
	{
		return isSizeConsistent(root);
	}

	private boolean isSizeConsistent(Node x)
	{
		if (x == null)
			return true;
		if (x.N != size(x.left) + size(x.right) + 1)
			return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}

	// check that ranks are consistent
	private boolean isRankConsistent()
	{
		for (int i = 0; i < size(); i++)
			if (i != rank(select(i)))
				return false;
		for (Key key : keys())
			if (key.compareTo(select(rank(key))) != 0)
				return false;
		return true;
	}

}

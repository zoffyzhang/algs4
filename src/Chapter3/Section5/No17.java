package Chapter3.Section5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/zoffyzhang
 * @Date Jun 3, 2015
 */
public class No17
{
	public static void main(String[] args)
	{

		MathSET<String> set1 = new MathSET<>();
		MathSET<String> set2 = new MathSET<>();
		set1.add("www.cs.princeton.edu");
		set1.add("www.cs.princeton.edu");    // skip this value
		set1.add("www.princeton.edu");
		set1.add("www.amazon.com");
		set1.add("www.google.com");
		set1.add("www.stanford.edu");

		set2.add("www.google.com");
		set2.add("www.stanford.edu");
		set2.add("www.ibm.com");
		set2.add("www.apple.com");
		set2.add("www.espn.com");

		StdOut.println("intersection:");
		set1.intersection(set2);
		set1.print();
		StdOut.println("size:" + set1.size());

		StdOut.println("union:");
		set1.union(set2);
		set1.print();
		StdOut.println("size:" + set1.size());

	}

}

// 用链表的方法保存数据
class MathSET<Key extends Comparable<Key>> implements Iterable<Key>
{
	private Node first;
	private int size;

	private class Node
	{
		private Key key;
		private Node next;

		public Node(Key k)
		{
			key = k;
		}
	}

	public MathSET()
	{}

	public MathSET(Key[] universe)
	{
		for (int i = 0; i < universe.length; i++)
		{
			this.add(universe[i]);
		}
	}

	public MathSET(Key key)
	{
		first = new Node(key);
		size = 1;
	}

	public void add(Key key)
	{
		if (contains(key))
			return;
		Node old = first;
		first = new Node(key);
		first.next = old;
		size++;
	}

	public void delete(Key key)
	{
		if (!contains(key))
			return;
		Node pre = first;
		for (Node cur = first; cur != null; cur = cur.next)
		{
			if (cur != first && cur != first.next)
				pre = pre.next;
			if (cur.key.equals(key) && cur == first)
			{
				first = first.next;
				size--;
				return;
			}
			if (cur.key.equals(key))
			{
				pre.next = cur.next;
				size--;
				return;
			}
		}

	}

	public boolean contains(Key key)
	{
		for (Key x : this)
			if (x.equals(key))
				return true;
		return false;
	}

	// 这个看不懂题目要求
	public MathSET<Key> complement()
	{
		return null;
	}

	// 并集
	public void union(MathSET<Key> a)
	{
		for (Key key_a : a)
		{
			if (!this.contains(key_a))
				this.add(key_a);
		}
	}

	// 交集
	public void intersection(MathSET<Key> a)
	{
		for (Key key_this : this)
		{
			if (!a.contains(key_this))
				this.delete(key_this);
		}
	}

	public boolean isEmpty()
	{
		return size == 0 ? true : false;
	}

	public int size()
	{
		return size;
	}

	public void print()
	{
		for (Key i : this)
			StdOut.print(i + "  ");
		StdOut.println();
	}

	@Override
	public Iterator<Key> iterator()
	{

		return new SetIterator();

	}

	private class SetIterator implements Iterator<Key>
	{
		private Node current;

		public SetIterator()
		{
			current = first;
		}

		@Override
		public boolean hasNext()
		{
			if (current != null)
				return true;
			return false;
		}

		@Override
		public Key next()
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}
			Node ret = current;
			current = current.next;
			return ret.key;
		}
	}

}
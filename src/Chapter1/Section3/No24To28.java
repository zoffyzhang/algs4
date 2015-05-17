package Chapter1.Section3;

import java.util.Iterator;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 8, 2015
 * @param <T>
 */
public class No24To28<T>
{
	// test
	public static void main(String[] args)
	{
		List<String> stringList = new List<>();
		List<Integer> intList = new List<>();
		String[] string = { "aaa", "bbb", "ccc", "ddd","aaa","aaa" };
		Integer[] integer = { 111, 222, 333, 444 };

		for (int i = 0; i < string.length; i++)
			stringList.enqueue(string[i]);
		for (int i = 0; i < integer.length; i++)
			intList.enqueue(integer[i]);
		StdOut.println("两条链表的初始的状态为:");
		stringList.printList();
		intList.printList();
		
		StdOut.println("测试remove(),removeAfter(),insertAfter():");
		stringList.remove(stringList, "aaa");
		stringList.printList();
		stringList.removeAfter(stringList.randomNode());
		stringList.printList();
		//stringList.insertAfter(stringList.randomNode(),stringList.getHead()); 这是一条错误的测试
		//stringList.printList();
		
		StdOut.println("测试max(),recursiveMax():");
		StdOut.println(intList.max(intList.getHead()));
		StdOut.println(intList.recursiveMax());
	}
}

// Node类应该写成一个外部类比较好，因为这样的话将一些方法写成static形式比较方便
class List<T> implements Iterable<T>
{
	private Node head;
	private Node tail;

	// No24
	public void removeAfter(Node node)
	{
		if (node == null || node.next == null)
			return;
		node.next = null; // 切断引用链，由JVM自动回收剩下的结点
	}

	// No25 我原意是想在调用此方法时new一个Node作为node2插入到node1后面，但调用时才发现没办法new Node(),好忧桑
	public void insertAfter(Node node1, Node node2)
	{
		if (node1 == null || node2 == null)
			return;
		Node tmp = node1.next;
		node1.next = node2;
		node2.next = tmp;
	}

	// No26
	public void remove(List<String> list, String key)
	{
		while (head.data == key)
		{
			head = head.next;
		}
		Node pre = head;
		Node cur = head.next;
		while (cur != null)
		{
			if (cur.data == key)
			{
				pre.next = cur.next;
				cur = cur.next;
			}
			else
			{
				pre = pre.next;
				cur = cur.next;
			}
		}
	}

	// No27
	public Integer max(Node node)
	{
		Node cur = node;
		if (cur == null)
			return 0;
		Integer maxData = (Integer) cur.data;
		while (cur != null)
		{
			if ((Integer) cur.next.data > maxData)
			{
				maxData = (Integer) cur.next.data;
				cur=cur.next;
			}
			cur=cur.next;
		}
		return maxData;
	}

	// No28
	public Integer recursiveMax()
	{
		return recursiveMax(head, 0);
	}

	private Integer recursiveMax(Node cur, int max)
	{
		if (cur == null)
			return max;
		if ((Integer) cur.getData() > max)
			return recursiveMax(cur.next, (Integer) cur.getData());
		else
			return recursiveMax(cur.next, max);
	}

	// 返回一个随机的，直接从链表中引用的Node
	public Node randomNode()
	{
		int random = StdRandom.uniform(countSize());
		Node cur = head;
		while (random > 0)
		{
			cur = cur.next;
			random--;
		}
		return cur;
	}

	//print
	public void printList()
	{
		Node cur = head;
		while (cur != null)
		{
			StdOut.print(cur.data + " ");
			cur = cur.next;
		}
		StdOut.println();
	}

	/*
	 * 
	 * 以下为QueueList常规代码
	 */
	private class Node
	{
		private T data;
		private Node next;

		public Node(T data)
		{
			this.data = data;
		}

		public T getData()
		{
			return data;
		}
	}

	public List()
	{
		head = tail = null;
	}

	public boolean isEmpty()
	{
		return head == null;
	}

	public void enqueue(T data)
	{
		if (isEmpty())
			head = tail = new Node(data);
		else
		{
			Node old = tail;
			tail = new Node(data);
			old.next = tail;
		}
	}

	public T dequeue()
	{
		if (isEmpty())
			throw new NullPointerException("List underflow");
		T data = head.data;
		head = head.next;
		return data;
	}

	public int countSize()
	{
		int counter = 0;
		Node cur = head;
		while (cur != null)
		{
			counter++;
			cur = cur.next;
		}
		return counter;
	}

	public Node getHead()
	{
		return head;
	}

	@Override
	public Iterator<T> iterator()
	{
		return new ListIterator();
	}

	private class ListIterator implements Iterator<T>
	{
		Node current;

		public ListIterator()
		{
			current = head;
		}

		@Override
		public boolean hasNext()
		{
			if (head != null)
				return true;
			return false;
		}

		@Override
		public T next()
		{
			if (!hasNext())
				throw new NullPointerException(
						"don't you use the hasNext method?");
			T data = current.data;
			current = current.next;
			return data;
		}

	}
}

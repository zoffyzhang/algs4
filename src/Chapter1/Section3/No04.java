package Chapter1.Section3;

import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date Mar 31, 2015
 */
public class No04
{

	public static void main(String[] args)
	{
		String test1 = "[()]{}{[()()]()}";
		String test2 = "[[[)))]]]";
		StdOut.println(Stack.Parentheses(test1));
		StdOut.println(Stack.Parentheses(test2));
	}
}

class Stack<T>
{
	private int size;
	private Node<T> head;

	//
	public static boolean Parentheses(String symbols)
	{
		Stack<String> stack = new Stack<>();
		stack.push(symbols.substring(0, 1));
		String x, y;
		int length = symbols.length();
		for (int i = 1; i < length; i++)
		{
			x = stack.pop();
			y = symbols.substring(i, i + 1);
			if (!match(x, y))
			{
				stack.push(x);
				stack.push(y);
			}
			// 考虑栈空的情况
			if (stack.size == 0 && i < length - 2)
			{
				stack.push(symbols.substring(i + 1, i + 2));
				i++;
			}
		}
		if (stack.size == 0)
			return true;
		return false;
	}

	// 三种匹配情况
	private static boolean match(String x, String y)
	{
		if (x.equals("(") && y.equals(")"))
			return true;
		if (x.equals("[") && y.equals("]"))
			return true;
		if (x.equals("{") && y.equals("}"))
			return true;
		return false;

	}

	// 以下为stack的常规方法
	private class Node<Item>
	{
		Item item;
		Node<Item> next;

		public Node(Item item)
		{
			this.item = item;
		}
	}

	public Stack()
	{
		size = 0;
		head = null;
	}

	public void push(T item)
	{
		Node<T> old = head;
		head = new Node<T>(item);
		head.next = old;
		size++;
	}

	public T pop()
	{
		if (head == null)
			throw new NullPointerException();
		Node<T> p = head;
		head = head.next;
		size--;
		return p.item;
	}

	public int getSize()
	{
		return size;
	}

}
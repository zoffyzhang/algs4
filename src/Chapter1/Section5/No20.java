package Chapter1.Section5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 15, 2015
 */
public class No20
{

	public static void main(String[] args)
	{
		LinkedListUF uf = new LinkedListUF();
		Scanner scanner;
		try
		{
			scanner = new Scanner(new File("src/Chapter1/Section5/TinyUF.txt"));
			while (scanner.hasNext())
			{
				int p = scanner.nextInt();
				int q = scanner.nextInt();

				uf.union(p, q);
				StdOut.println(p + " " + q);
			}
			StdOut.println(uf.getCount() + " components"); // 测试结果正确
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

/*
 * 这题是很值得思考的一题，非常突出数组与链表的不同之处，想了数种方法才确定了一下方法。
 * 由于list大小不确定、find操作要求要有确定的序号，查找操作变的很奇怪。 在写出正确的find后，union就相对简单了
 */
class LinkedListUF
{
	private int count; // 连通分量数
	private LinkedList<Integer> list;

	public LinkedListUF()
	{
		list = new LinkedList<>();
	}

	public Integer find(int p)
	{
		while (list.size() <= p)
			list.add(-1); // 申请足够的长度，并将它们赋值为-1
		if (list.get(p) == -1)
			return null;
		if (list.get(p) != p)
			return find(list.get(p));
		return p;
	}

	public void union(int p, int q)
	{
		Integer pRoot = find(p);
		Integer qRoot = find(q);

		if (pRoot == null && qRoot == null)
		{
			list.set(p, p);
			list.set(q, p);
			count++;
			return;
		}
		if (pRoot == null && qRoot != null)
		{
			list.set(p, q);
			return;
		}
		if (qRoot == null && pRoot != null)
		{
			list.set(q, p);
			return;
		}
		// if (pRoot != null && qRoot != null)
		{
			if (pRoot == qRoot)
				return;
			list.set(qRoot, pRoot); // 注意这里是Root
			count--;
			return;
		}

	}

	public int newSite(int p)
	{
		if (p < 0 || p >= list.size())
			return -1;
		return list.get(p);
	}

	public boolean isConnected(int p, int q)
	{
		Integer pRoot = find(p);
		Integer qRoot = find(q);
		if (pRoot == null || qRoot == null)
			return false;
		return pRoot == qRoot;
	}

	public int getCount()
	{
		return count;
	}

}

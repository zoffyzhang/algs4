package Chapter1.Section3;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 9, 2015
 */
public class No37
{
	public static void main(String[] args)
	{
		StdOut.println("default N(people) and M(counter):7 2");
		// final int nPeople = StdIn.readInt();
		// final int mCounter = StdIn.readInt();
		final int nPeople = 7;
		final int mCounter = 2;
		Queue<Integer> josephus = new Queue<>();
		int[] printList = new int[nPeople];
		for (int i = 0; i < nPeople; i++)
			josephus.enqueue(i);
		
		//基本上就是一连串的enqueue和dequeue
		for (int i = 0; i < nPeople; i++)
		{
			int counter = mCounter;
			while (--counter > 0)
			{
				int tmp = josephus.dequeue();
				josephus.enqueue(tmp);
			}
			printList[i] = josephus.dequeue();
		}
		for (int i : printList)
			StdOut.print(i + " ");
	}
}

package Chapter1.Section1;

import java.util.Arrays;
import java.util.Vector;
import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date Mar 22, 2015
 */
public class No39
{
	private static Vector<Integer> blacklist = new Vector<>();

	public static boolean BlacklistDoesntContains(int key)
	{
		int size = blacklist.size();
		for (int i = 0; i < size; i++)
			if (key == blacklist.get(i))
				return false;
		return true;
	}

	public static void main(String[] args)
	{
		StdOut.print("input a positive integer(better not larger than 10):");
		int T = StdIn.readInt();

		// 1_000_000所需的计算时间太长了，所以去掉
		int[] N ={ 1000, 10_000, 100_000/* 1_000_000 */};
		int[] a;
		int[] b;
		int tmp;
		int total = 0;

		StdOut.print(" N\\T     ");
		for (int i = 0; i < T; i++)
			StdOut.printf("%-9d", i);
		StdOut.print("Average\n");

		// 对于每一个N
		for (int i = 0; i < N.length; i++)
		{
			StdOut.printf("%-9d", N[i]);
			a = new int[N[i]];
			b = new int[N[i]];

			// 运行T遍
			for (int j = 0; j < T; j++)
			{
				// 生成两个大小为N的随机6位正整数数组
				for (int m = 0; m < N[i]; m++)
				{
					a[m] = StdRandom.uniform(100_000, 1_000_000);
					b[m] = StdRandom.uniform(100_000, 1_000_000);
				}
				Arrays.sort(a);
				Arrays.sort(b);

				// 找到存在两组数组中且不存在blacklist中的元素，并将其加入blacklist
				for (int k = 0; k < N[i]; k++)
					if ((tmp = BinarySearch.rank(a[k], b)) != -1
							&& BlacklistDoesntContains(a[k]))
						blacklist.add(a[tmp]);
				// 输出相同元素的数量
				StdOut.printf("%-9d", blacklist.size());
				total += blacklist.size();
				blacklist.clear();
			}
			StdOut.printf("%7.2f", (double) total / T);
			total = 0;
			StdOut.println();
		}

	}
}

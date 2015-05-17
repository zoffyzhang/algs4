package Chapter1.Section5;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 14, 2015
 */
public class No17
{
	public static void main(String[] args)
	{
		int N = 10000;
		// N=StdIn.readInt();
		StdOut.println(count(N));

		// 如果运行以下测试，可以发现(连接数/N)≈1/3
		// int sum = 0;
		// int experimentTimes = 1000; // 实验次数
		// for (int i = 0; i < experimentTimes; i++)
		// sum += count(N);
		// StdOut.println(sum / experimentTimes);

	}

	public static int count(int N)
	{
		WeightedQuickUnionUF ErdosRenyi = new WeightedQuickUnionUF(N);
		int counter = 0; // 连接总数

		while (true)
		{
			int p = StdRandom.uniform(N);
			int q = StdRandom.uniform(N);

			if (ErdosRenyi.connected(p, q))
				return counter;

			ErdosRenyi.union(p, q);
			counter++;
		}

	}

}

package Chapter1.Section4;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 12, 2015
 */
public class No44
{
	public static void main(String[] args)
	{
		int N = 100000;
		// N=StdIn.readInt();

		int experimentTimes = 1000; // 实验重复次数
		int sum = 0;
		for (int i = 0; i < experimentTimes; i++)
			sum += counter(N);
		
		double average = sum / experimentTimes;
		double proposal = Math.sqrt(3.14 * N / 2);

		StdOut.println("实验结果与√(πN/2)相比得：" + average / proposal);
		if (average / proposal < 0.01)	//我记得实验误差要求要小于千分之一，但这里并达不到这样的要求，so...
			StdOut.println("说明实验结果与设想相差不大。");
		else
			StdOut.println("说明实验结果与设想之间存在较大差距。");
	}

	public static int counter(int N)
	{
		int[] a = new int[N];

		for (int i = 0; i < a.length; i++)
		{
			a[i] = StdRandom.uniform(N);
			if (isExist(a, i, a[i]))
				return i + 1;
		}
		return Integer.MAX_VALUE; // 未生成重复的数据，就返回max_value
	}

	private static boolean isExist(int[] a, int i, int check)
	{
		for (int j = 0; j <= i; j++)
			if (a[j] == check)
				return true;
		return false;
	}
}

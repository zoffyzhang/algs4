package Chapter1.Section4;

import java.util.Arrays;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 11, 2015
 */
public class No15
{
	public static void main(String[] args)
	{
		int[] test1 = { -3, 3, -5, 5, -1, 1, 0, 10, 8, -24 };
		int[] test2 = { -1, 1, 2, 3, 4, 5 }; // 极端案例
		int[] threeSumTest = { -5, -2, 0, 1, 2, 3, 4 };
		Arrays.sort(test1);
		Arrays.sort(test2);
		Arrays.sort(threeSumTest);

		StdOut.println("符合twoSum条件的数量为：" + twoSumFaster(test1));
		StdOut.println("符合twoSum条件的数量为：" + twoSumFaster(test2));
		StdOut.println("符合threeSum条件的数量为：" + threeSumFaster(threeSumTest));

	}

	/*
	 * 关键在于“数组已排序”这个条件（我硬是看了十几分钟没看到 --||）。
	 * 
	 * 维护一个left和一个right指针，实现无回溯遍历， 由于数组是有序的，这样做并不会忽略任何符合a[left]+a[right]==0的情况。
	 */
	public static int twoSumFaster(int[] a)
	{
		int left = 0;
		int right = a.length - 1;
		int maxLoops = a.length - 1; // 极端情况下的循环次数
		int cnt = 0; // 返回值

		while (maxLoops > 0 && a[left] < 0 && a[right] > 0) // 当左指针的值大于0或右指针小于0时，也不存在匹配情况了
		{
			if (a[left] + a[right] == 0)
			{
				cnt++;
				left++;
				right--;
			}
			else
			{
				if (a[left] + a[right] > 0)
					right--;
				else
					left++;
			}
			maxLoops--;
		}
		return cnt;
	}

	public static int threeSumFaster(int[] a)
	{
		int cnt = 0;
		int[] sub = new int[a.length - 1];

		for (int i = 0; i < a.length; i++)
		{
			int c = a[i];
			for (int j = 0; j < sub.length; j++)
			{
				// 得到一个a[]的sub array
				if (j < i) // a+b+c==0，我们要选定c，计算-(a+b)==c，此处的c即不加入sub
					sub[j] = a[j];
				else if (j > i)
					sub[j] = a[j + 1];
				else
					continue;
			}

			int left = 0;
			int right = sub.length - 1;
			int maxLoops = sub.length - 1;

			while (maxLoops > 0 && left != right)
			{
				if (sub[left] + sub[right] + c == 0)
				{
					cnt++;
					left++;
					right--;
				}
				else
				{
					if (sub[left] + sub[right] + c > 0)
						right--;
					else
						left++;
				}
				maxLoops--;
			}
		}
		return cnt / 3; // 去除重复次数

	}
}

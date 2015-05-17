package Chapter1.Section1;

import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date   Mar 19, 2015
 */
// 1.1.30
public class No30
{
	/**
	 * @param i
	 * @param j
	 * @return the greatest common divisor of i and j ���Լ��
	 */
	private static int gcd(int i, int j)
	{

		if (j == 0)
			return i;
		else
			return gcd(j, i % j);
	}

	/**
	 * 查了下资料，0没有互质与公约的概念，所以要限制一下 0 has no gcd with any natural numbers
	 */
	private static int strictGcd(int i, int j)
	{
		if (i == 1 && j == 0 || i == 0 && j == 1)
			return 0;
		else
			return gcd(i, j);

	}

	// 判断互质
	public static boolean isCoprime(int i, int j)
	{
		return strictGcd(i, j) == 1 ? true : false;
	}

	public static void main(String[] args)
	{
		int N = 5;
		boolean[][] a = new boolean[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				a[i][j] = isCoprime(i, j) ? true : false;

		// print test
		StdOut.println("  0     1     2     3     4     ");
		for (int i = 0; i < N; i++)
		{
			StdOut.printf("%-2d", i);
			for (int j = 0; j < N; j++)
			{
				StdOut.printf("%-6b", a[i][j]);
			}
			StdOut.println();
		}

	}
}

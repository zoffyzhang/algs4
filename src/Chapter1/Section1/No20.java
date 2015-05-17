package Chapter1.Section1;

import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date Mar 19, 2015
 */
// 1.1.20
public class No20
{
	public static double LogFac(int N)
	{
		return N >= 2 ? Math.log(N) + LogFac(N - 1) : 0.0;
	}

	public static void main(String[] args)
	{
		StdOut.print(No20.LogFac(10));
	}
}

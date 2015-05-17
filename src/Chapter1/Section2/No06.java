package Chapter1.Section2;

import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date Mar 28, 2015
 */
public class No06
{
	// 没想到题目要求的indexOf()用在哪
	public static boolean isCircularRoatation(String s, String t)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (s.equals(t))
				return true;
			s = s.substring(1) + s.charAt(0);
		}
		return false;
	}

	public static void main(String[] args)
	{
		String a = "ATGCATT";
		String b = "ATTATGC";
		String c = "AAATTT";
		StdOut.println(isCircularRoatation(a, b));
		StdOut.println(isCircularRoatation(a, c));
	}
}

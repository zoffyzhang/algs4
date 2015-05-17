package Chapter1.Section2;

import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date Mar 29, 2015
 */
public class No12
{

	// Zeller Formula: w=y+[y/4]+[c/4]-2c+[26(m+1)/10]+d-1
	public static String dayOfTheWeek(int y, int m, int d)
	{
		y = y - 2000;
		if (m == 1 || m == 2)
		{
			m = m + 12;
			y--;
		}

		int f = (y + (int) (y / 4) - 35 + (int) (26 * (m + 1) / 10) + d - 1) % 7;
		if (f < 0)
			f = f + 7;
		switch (f)
		{
		case 0:
			return "Sun";
		case 1:
			return "Mon";
		case 2:
			return "Tue";
		case 3:
			return "Wed";
		case 4:
			return "Thu";
		case 5:
			return "Fri";
		case 6:
			return "Sat";
		default:
			return null;
		}
	}

	public static void main(String[] args)
	{
		StdOut.print(dayOfTheWeek(2015, 3, 29));

	}
}

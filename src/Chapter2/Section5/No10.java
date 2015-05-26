package Chapter2.Section5;

import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date   May 26, 2015
 */
public class No10
{
	public static void main(String[] args)
	{
		Version ver1 = new Version("115.1.1");
		Version ver2 = new Version(115, 10, 2);

		StdOut.println(ver1.compareTo(ver2) > 0);
	}

}

@SuppressWarnings("rawtypes")
class Version implements Comparable
{
	private int part[] = new int[3];

	@SuppressWarnings("unused")
	private Version()
	{}

	public Version(String str)
	{
		String[] splitStr = new String[3];
		splitStr = str.split("\\.");

		for (int i = 0; i < splitStr.length; i++)
		{
			part[i] = Integer.parseUnsignedInt(splitStr[i], 10);
		}

	}

	public Version(int a, int b, int c)
	{
		part[0] = a;
		part[1] = b;
		part[2] = c;
	}

	@Override
	public int compareTo(Object o)
	{
		Version that = (Version) o;
		if (this.part[0] > that.part[0])
			return 1;
		if (this.part[0] < that.part[0])
			return -1;
		if (this.part[1] > that.part[1])
			return 1;
		if (this.part[1] > that.part[1])
			return -1;
		if (this.part[2] > that.part[2])
			return 1;
		if (this.part[2] > that.part[2])
			return -1;
		return 0;
	}

	@Override
	public String toString()
	{
		return part[0] + "." + part[1] + "." + part[2];
	}

}

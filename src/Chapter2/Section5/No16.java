package Chapter2.Section5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 27, 2015
 */
public class No16
{

}

class California
{
	public static void main(String[] args)
	{
		String s = "RWQOJMVAHBSGZXNTCIEKUPDYFL";
		Character[] specOrder = new Character[s.length()];
		for (int i = 0; i < s.length(); i++)
			specOrder[i] = s.charAt(i);

		// data
		String[] tmpString = { "KOBE", "RED", "WITNEY", "WHITE", "JACKSON", "JACK", "OBAMA",
				"FREEMAN", "JAMES", "ROBERT" };
		MyString[] names = new MyString[tmpString.length];
		for (int i = 0; i < tmpString.length; i++)
		{
			names[i] = new MyString(tmpString[i]);
		}

		// sort with specified order
		Arrays.sort(names, new CharsOrder(specOrder));
		for (int i = 0; i < names.length; i++)
		{
			StdOut.print(names[i] + " ");
		}

	}
}

/**
 * 用MyString 代替String。 因为String 的排序规则由Unicode表决定， 我并没有想到怎样去控制这个规则，所以只能用MyString
 * 的Character来确定排序规则。
 */
class MyString
{
	private Character[] chars;

	public MyString(String str)
	{
		chars = new Character[str.length()];
		for (int i = 0; i < chars.length; i++)
		{
			chars[i] = str.charAt(i);
		}
	}

	public Character[] getChars()
	{
		return chars;
	}

	public Character charAt(int i)
	{
		return chars[i];
	}

	@Override
	public String toString()
	{
		String retVal = "";
		for (int i = 0; i < chars.length; i++)
		{
			retVal += Character.toString(chars[i]);
		}
		return retVal;
	}

}

/**
 * 由于完全实现字符串排序比较繁琐，所以这里只是粗略地实现了 字符串首字符的排序规则， 从第二个字符开始就完全不排序了
 */
class CharsOrder implements Comparator<MyString>
{
	private Hashtable<Character, Integer> ht;

	public CharsOrder(Character[] specOrder)
	{
		ht = new Hashtable<>(specOrder.length);
		for (int i = 0; i < specOrder.length; i++)
		{
			ht.put(specOrder[i], i);
		}

	}

	@Override
	public int compare(MyString o1, MyString o2)
	{
		int i = ht.get(o1.charAt(0));
		int j = ht.get(o2.charAt(0));
		if (i < j)
			return -1;
		if (i > j)
			return 1;
		return 0;
	}
}

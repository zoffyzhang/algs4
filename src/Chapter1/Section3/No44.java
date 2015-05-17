package Chapter1.Section3;

import java.util.Stack;
import edu.princeton.cs.introcs.StdOut;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 9, 2015
 */
public class No44
{
	public static void main(String[] args)
	{
		Buffer textBuffer = new Buffer();
		Character[] chars = new Character[26];
		// 得到26个大写字母
		for (int i = 0; i < chars.length; i++)
			chars[i] = (char) (i + 65);

		for (int i = 0; i < chars.length; i++)
			textBuffer.insert(chars[i]);
		StdOut.println("初始编辑文本为：");
		textBuffer.print();

		StdOut.println("将光标左移至开头插入'a'和 'b',再右移两位，并删除2个字符：");
		textBuffer.left(26);
		textBuffer.insert('a');
		textBuffer.insert('b');
		textBuffer.right(2);
		int i = 2;
		while (i-- > 0)
			textBuffer.delete();
		textBuffer.print();

	}

}

// 这就是光标移动的逻辑实现？有点神奇！
class Buffer
{
	private Stack<Character> previous; // 保存光标前的内容
	private Stack<Character> following; // 保存光标后的内容

	public Buffer()
	{
		previous = new Stack<>();
		following = new Stack<>();
	}

	public void insert(Character c)
	{
		previous.push(c);
	}

	// 此处的delete定义为删除光标右侧的字符
	public Character delete()
	{
		if (!following.isEmpty())
			return following.pop();
		return null;
	}

	public void left(int k)
	{
		if (k <= previous.size())
			while (k-- > 0)
				following.push(previous.pop());
	}

	public void right(int k)
	{
		if (k <= following.size())
			while (k-- > 0)
				previous.push(following.pop());
	}

	public int size()
	{
		return previous.size() + following.size();
	}

	// 打印全部内容
	public void print()
	{
		for (int i = 0; i < previous.size(); i++)
			StdOut.print(previous.get(i) + " ");
		for (int i = 0; i < following.size(); i++)
			StdOut.print(following.get(following.size()-i-1) + " ");
		StdOut.println();
	}

}
package Chapter1.Section1;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date Mar 20, 2015
 */
//画坐标图的关键是将图形的数值width跟逻辑的数值numberWidth分清楚，混淆了的话就感觉很乱
public class No32
{
	public static void main(String[] args)
	{
		// 题目要求输入数据流太麻烦，改为生成随机数
		double[] input = new double[200];
		for (int i = 0; i < 200; i++)
			input[i] = StdRandom.uniform(0.0, 100.0);
		// 输入参数也很麻烦，默认赋值吧
		int N = 9;
		double L = 10.0;
		double R = 90.0;
		// StdOut.print("input N(int),L(double),R(double) uniformly in-50<L<R<150��");
		// int N = StdIn.readInt();
		// double L = StdIn.readDouble();
		// double R = StdIn.readDouble();
		// if (L >= R)
		// {
		// StdOut.print("L must less than R !!!");
		// return;
		// }

		// 统计
		double width = 0.8 / N; 			// ͼ����ÿ���width
		double numberWidth = (R - L) / N; 	// ��ֵ��ÿ���width
		int[] counter = new int[N];

		for (int i = 0; i < N; i++)
		{
			double left = L + numberWidth * i;
			double right = L + numberWidth * (i + 1);
			for (int j = 0; j < input.length; j++)
				if (input[j] >= left && input[j] < right)
					counter[i]++;
		}

		// 以最高组为单位计算每组高度
		int max = counter[0];
		for (int i = 1; i < counter.length; i++)
			if (max < counter[i])
				max = counter[i];
		double[] height = new double[N];
		for (int i = 0; i < N; i++)
			height[i] = 0.8 / max * counter[i];

		// Draw
		StdDraw.line(.1, .1, .9, .1);
		StdDraw.text(0.05, 0.05, "L");
		StdDraw.text(0.95, 0.05, "R");
		StdDraw.setPenColor(StdDraw.BOOK_BLUE);
		for (int i = 0; i < N; i++)
		{
			double x = 0.1 + (0.5 + i) * width;
			double y = 0.1 + height[i] / 2;
			StdDraw.filledRectangle(x, y, width / 2, height[i] / 2);
			StdDraw.text(x - width / 2, 0.05, String.format("%d", (i) * 10));
		}
		StdDraw.text(0.9, 0.05, "90");
	}
}

package Chapter1.Section1;

import java.awt.geom.Point2D;
import edu.princeton.cs.introcs.Draw;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date   Mar 19, 2015
 */
//1.1.31
public class No31
{
	public static void DoTheJob(int N, double chance)
	{
		Point2D.Double[] point = new Point2D.Double[N];
		for (int i = 0; i < N; i++)
			point[i] = new Point2D.Double();

		Draw d = new Draw("Random Connection");
		d.circle(.5, .5, .4);// 题目要求大小为0.05太小了，改成0.4了
		d.setPenRadius(.008);

		//计算每个点的位置
		double theta = 2 * Math.PI / N;
		for (int i = 0; i < N; i++)
		{
			double px = 0.5 + 0.4 * Math.cos(theta * i);
			double py = 0.5 + 0.4 * Math.sin(theta * i);
			point[i].setLocation(px, py);
		}

		// 画点
		for (Point2D.Double iter : point)
			d.point(iter.getX(), iter.getY());

		// 每两点间有chance的概率连线
		d.setPenRadius();
		d.setPenColor(Draw.GRAY);
		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
			{
				if (StdRandom.bernoulli(chance))
					d.line(point[i].getX(), point[i].getY(), point[j].getX(),
							point[j].getY());
			}
	}

	public static void main(String[] args)
	{
		// 输入一个整数及一个<1的double值,如20 0.5ֵ,��20 0.5
		StdOut.print("input (for example: 20 0.5) :");
		int N = StdIn.readInt();
		double chance = StdIn.readDouble();

		No31.DoTheJob(N, chance);
	}
}


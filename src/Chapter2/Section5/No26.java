package Chapter2.Section5;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import edu.princeton.cs.introcs.StdDraw;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 28, 2015
 */
public class No26
{
	public static void main(String[] args)
	{
		int num = 6;
		Point2D.Double[] points = new Point2D.Double[num];
		points[0] = new Point2D.Double(0.1, 0.8);
		points[1] = new Point2D.Double(0.9, 0.1);
		points[2] = new Point2D.Double(0.1, 0.1);
		points[3] = new Point2D.Double(0.5, 0.8);
		points[4] = new Point2D.Double(0.3, 0.9);
		points[5] = new Point2D.Double(0.2, 0.7);

		// construct 以使 MakePointsLogical.ht 得到相应的逻辑关系
		new MakePointsLogical(points);

		// sort
		Arrays.sort(points, new YnXOrder());

		// draw
		for (int i = 0; i < points.length - 1; i++)
		{
			StdDraw.line(points[i].x, points[i].y, points[i + 1].x,
					points[i + 1].y);
		}
		StdDraw.line(points[num - 1].x, points[num - 1].y, points[0].x,
				points[0].y);
	}
}

// This class should be called after the MakePointsLogical class has been
// constructed
class YnXOrder implements Comparator<Point2D.Double>
{

	@Override
	public int compare(Point2D.Double o1, Point2D.Double o2)
	{
		double cmp1 = MakePointsLogical.ht.get(o1);
		double cmp2 = MakePointsLogical.ht.get(o2);

		if (cmp1 < cmp2)
			return -1;
		if (cmp1 > cmp2)
			return 1;
		return 0;
	}
}

// 不考虑与x，y轴平行的直线上有多于两个point的情况，因为确实很繁琐
class MakePointsLogical
{
	public static Hashtable<Point2D.Double, Double> ht;
	private Point2D.Double origin;
	private Double[] offAngles;

	public MakePointsLogical(Point2D.Double[] points)
	{
		ht = new Hashtable<Point2D.Double, Double>(100);
		offAngles = new Double[points.length];
		findOrigin(points);
		computeOffAngles(points);
		packUp(points);
	}

	private void findOrigin(Point2D.Double[] points)
	{
		origin = points[0];
		for (int i = 1; i < points.length; i++)
		{
			if (points[i].y < origin.y)
				origin = points[i];
			else if (points[i].y == origin.y && points[i].x < origin.x)
				origin = points[i];
			else
				continue;
		}
	}

	private void computeOffAngles(Point2D.Double[] points)
	{
		for (int i = 0; i < points.length; i++)
		{
			double X = points[i].x - origin.x;
			double Y = points[i].y - origin.y;

			if (points[i].equals(origin))
				offAngles[i] = -2.0;	// 确定原点的偏移角为一个（够用的！）值
			if (X != 0 && Y != 0)
				offAngles[i] = Y / X;
			if (X == 0 && !points[i].equals(origin))
				offAngles[i] = Double.MAX_VALUE;
			if (Y == 0 && !points[i].equals(origin))
				offAngles[i] = -1.0;
		}

	}

	private void packUp(Point2D.Double[] points)
	{
		for (int i = 0; i < points.length; i++)
		{
			ht.put(points[i], offAngles[i]);
		}
	}

}

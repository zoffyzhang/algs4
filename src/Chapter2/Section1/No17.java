package Chapter2.Section1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 20, 2015
 */

public class No17
{
	public static void main(String[] args)
	{
		int N = 100;
		Double[] randomData = new Double[N];
		for (int i = 0; i < N; i++)
			randomData[i] = StdRandom.uniform(-80.0, 120.0);

		JFrame frame = new JFrame("Sort Animation");
		frame.getContentPane().add(new SortPanel(randomData, 1));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}

/**
 * <p>
 * 1.本算法对每一次数组发生变化都生成一帧画面，以每秒重绘SortPanel.FPS张图片来生成动画。
 * <p>
 * 2.动画的持续时间只与数组的变动次数有关，与算法时间复杂度并非线性关系，因为动画忽略了比较所花费的时间
 */
class SortPanel extends JPanel
{
	private static final long serialVersionUID = 7912515918599760257L;

	private final int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private final int height = 600;	// Panel的height
	private int width;

	// 长方柱属性
	private double yScale;
	private int rectangleWidth = 5;
	private int gap;				// 柱间间隔

	private Double[] randomData;

	public static final long FPS = 24;
	public static final long SPF = 1000 / FPS;	// Second Per Frames

	public SortPanel(Double[] randomData, int gap)
	{
		this.randomData = randomData;
		this.gap = gap;
		int dataLen = randomData.length;
		if (dataLen * (rectangleWidth + gap) > maxWidth)
		{
			width = maxWidth;
			rectangleWidth = (maxWidth - gap * dataLen) / dataLen;
		}
		else
			width = rectangleWidth * dataLen;
		setPreferredSize(new Dimension(width, height));

		// 计算 y scale
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < dataLen; i++)
		{
			if (randomData[i] > max)
				max = randomData[i];
			if (randomData[i] < min)
				min = randomData[i];
		}
		max = Math.abs(max);
		min = Math.abs(min);
		double tmp = max > min ? max : min;
		yScale = height / 2 / tmp;

		//
		startPaintThread();
		startSortThread();
	}

	private void startSortThread()
	{
		Insertion.sort(randomData);
	}

	private void startPaintThread()
	{
		new Thread()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						repaint();
						Thread.sleep(SPF);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setCoordinate(g);
		drawAll(g);
		drawTheRedOne(g);
	}

	private void drawAll(Graphics g)
	{
		g.setColor(Color.GRAY);
		for (int i = 0; i < randomData.length; i++)
		{
			// 坐标上下反转后，fill3DRect()的坐标参数也从左上角变成了左下角
			if (randomData[i] >= 0)
				g.fill3DRect(i * (rectangleWidth + gap), 0, rectangleWidth,
						(int) (randomData[i] * yScale), true);
			else
			{
				int leftButtomY = (int) (randomData[i] * yScale);
				g.fill3DRect(i * (rectangleWidth + gap), leftButtomY,
						rectangleWidth, -leftButtomY, true);

			}
		}
	}

	private void drawTheRedOne(Graphics g)
	{
		int i = Insertion.pos;
		g.setColor(Color.RED);

		if (randomData[i] >= 0)
			g.fill3DRect(i * (rectangleWidth + gap), 0, rectangleWidth,
					(int) (randomData[i] * yScale), true);
		else
		{
			int leftButtomY = (int) (randomData[i] * yScale);
			g.fill3DRect(i * (rectangleWidth + gap), leftButtomY,
					rectangleWidth, -leftButtomY, true);

		}

	}

	private void setCoordinate(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		// 记住一定要设置了原点后再用scale反转，图像画不出来让我找了两天资料!!!!!
		g2d.translate(0, height / 2);	// 设置原点
		g2d.scale(1.0, -1.0);			// 反转y轴
		g2d.draw(new Line2D.Double(0.0, 0.0, width, 0.0)); // 画x轴
	}

}

class Insertion
{
	public static int pos;

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a)
	{
		new Thread()
		{
			public void run()
			{
				for (int i = 1; i < a.length; i++)
					for (int j = i; j > 0; j--)
						if (less(a[j], a[j - 1]))
						{
							exch(a, j, j - 1);
							pos = j - 1;
							try
							{
								sleep(SortPanel.SPF);
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
			};
		}.start();

	}

	@SuppressWarnings("rawtypes")
	public static void exch(Comparable[] a, int i, int j)
	{
		Comparable tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean less(Comparable x, Comparable y)
	{
		return x.compareTo(y) < 0;
	}

	@SuppressWarnings("rawtypes")
	public static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isSorted(Comparable[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}
}

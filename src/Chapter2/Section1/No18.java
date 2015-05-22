package Chapter2.Section1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.princeton.cs.introcs.StdRandom;

/**
 * @Author Zoffy Zhang
 * @GitHub https://github.com/ZoffyZhang
 * @Date May 23, 2015
 */
public class No18
{
	public static void main(String[] args)
	{
		int N = 200;
		Double[] randomData = new Double[N];
		for (int i = 0; i < N; i++)
			randomData[i] = StdRandom.uniform(-40.0, 60.0);

		JFrame frame = new JFrame("Sort Track");
		frame.setLayout(new GridLayout(10, 1));
		frame.setSize(600, 800);

		SortTrackPanel[] panel = new SortTrackPanel[10];

		for (int i = 0; i < 10; i++)
			panel[i] = new SortTrackPanel(randomData, 0, i);

		for (int i = 0; i < 10; i++)
			frame.add(panel[i]);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

/**
 * draw localeData , sort randomData
 */
class SortTrackPanel extends JPanel
{
	private static final long serialVersionUID = 7912515918599760257L;

	private final int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private final int height = 80;	// Panel的height
	private int width;

	// 长方柱属性
	private double yScale;
	private int rectangleWidth = 3;
	private int gap;				// 柱间间隔
	private Double[] localeData;

	public SortTrackPanel(Double[] randomData, int gap, int num)
	{

		this.gap = gap;
		int dataLen = randomData.length;
		if (dataLen * (rectangleWidth + gap) > maxWidth)
		{
			width = maxWidth;
			rectangleWidth = (maxWidth - gap * dataLen) / dataLen;
		}
		else
			width = (rectangleWidth + gap) * dataLen;
		setSize(width, height);
		setLocation(0, num * 80);

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
		max = max > min ? max : min;
		yScale = 80 / 2 / max;

		Selection.sort(randomData, num);
		drawAllRect(randomData);
	}

	private void drawAllRect(Double[] randomData)
	{
		localeData = new Double[randomData.length];
		for (int i = 0; i < randomData.length; i++)
		{
			localeData[i] = randomData[i];
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setCoordinate(g);
		drawAllRect(g);
	}

	private void setCoordinate(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(0, 40);	 // 设置原点
		g2d.draw(new Line2D.Double(0.0, 0.0, width, 0.0)); // 画x轴
	}

	private void drawAllRect(Graphics g)
	{
		g.setColor(Color.GRAY);
		for (int i = 0; i < localeData.length; i++)
		{
			if (localeData[i] >= 0)
				g.fill3DRect(i * (rectangleWidth + gap),
						(int) (-localeData[i] * yScale), rectangleWidth,
						(int) (localeData[i] * yScale), true);
			else
				g.fill3DRect(i * (rectangleWidth + gap), 0, rectangleWidth,
						(int) (-localeData[i] * yScale), true);
		}
	}

}

class Selection
{
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a, int sortPos)
	{
		for (int i = sortPos * 20; i < sortPos * 20 + 20; i++)
		{
			int min = i;
			for (int j = i + 1; j < a.length; j++)
				if (less(a[j], a[min]))
					min = j;
			exch(a, i, min);

		}

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

package Chapter2.Section1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.princeton.cs.introcs.StdRandom;

public class No17
{
	public static void main(String[] args)
	{
		int N = 100;
		Double[] randomData = new Double[N];
		for (int i = 0; i < N; i++)
			randomData[i] = StdRandom.uniform(-20.0, 20.0);

		// frame stuff
		JFrame frame = new JFrame("Sort Animation");
		frame.getContentPane().add(new SortPanel(randomData, 1));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}

/*
 * class ArrayPainter extends JFrame {
 * 
 * public ArrayPainter(Double[] a) {

 * 
 * public void paintThe() { for (int i = 0; i < a.length; i++) { int leftTopX =
 * (int) (rectangleWidth * i); if (a[i] >= 0.0) { rectangleHeight = (int)
 * (heightBound / yScale * a[i]); g.fill(new Rectangle(leftTopX,
 * rectangleHeight, rectangleWidth, // 待check，fill会不会上下颠倒 rectangleHeight)); }
 * else { rectangleHeight = -(int) (heightBound / yScale * a[i]);
 * g.fillRect(leftTopX, 0, rectangleWidth, rectangleHeight);
 * 
 * } } } }
 */

class SortPanel extends JPanel
{
	private final int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private final int height = 600;		// 与setCoordinate()有对应
	private int width;

	// 长方柱属性
	private double yScale;
	private int rectangleWidth = 10;
	private int rectangleHeight;
	private int gap;

	private Double[] randomData;
	private Graphics2D g2d;

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
		yScale = Math.abs(max) > Math.abs(min) ? max : -min;

		startSort();
		startPaintThread();

	}

	private void startSort()
	{

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
		drawAll(g);
		drawTheRedOne(g);
	}

	
	private void drawTheRedOne(Graphics g)
	{

	}

	private void drawAll(Graphics g)
	{
		setCoordinate(g);
		g.drawRect(0, 0, 100, 100);
	}

	private void setCoordinate(Graphics g)
	{
		// 设置坐标系
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(1.0, -1.0);		// 得到y轴向上的坐标系
		g2d.translate(0, 300);		// 设置原点
		
		g2d.drawRect(0, 0, 100, 100);
//		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.draw(new Line2D.Double(0.0, 0.0, width, 0.0));;	// 画x轴
		this.g2d = g2d;
	}

}

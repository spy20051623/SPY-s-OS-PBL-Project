import javax.swing.*;
import java.awt.*;

public class Main {
	/**
	 * 主界面窗体
	 */
	public static JFrame frame;
	/**
	 * 开始按钮
	 */
	public static JButton buttonStart;
	/**
	 * 下一步按钮
	 */
	public static JButton buttonNext;
	/**
	 * 连续播放按钮
	 */
	public static JButton buttonPlay;
	/**
	 * 停止播放按钮
	 */
	public static JButton buttonStop;
	/**
	 * 事务输入框
	 */
	public static JTextArea textJob;
	/**
	 * 等待队列展示框
	 */
	public static JTextArea textQueue;
	/**
	 * 当前时间展示标签
	 */
	public static JLabel labelTime;
	/**
	 * 当前运行列表名称标签
	 */
	public static JLabel labelCurrent;
	/**
	 * 当前运行列表展示标签
	 */
	public static JLabel labelList;
	/**
	 * 等待队列名称标签
	 */
	public static JLabel labelQueue;
	/**
	 * 调度功能类对象
	 */
	public static Calculate calculate;

	/**
	 * 图形界面窗口建立
	 */
	private static void buildFrame() {
		frame = new JFrame("Multiple Cores");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
	}

	/**
	 * 图形界面按钮布置
	 */
	private static void buildButtons() {
		buttonStart = new JButton("Start");
		buttonStart.setBounds(210, 450, 80, 40);
		buttonStart.setVisible(true);
		frame.add(buttonStart);
		buttonNext = new JButton("Next");
		buttonNext.setBounds(310, 450, 80, 40);
		buttonNext.setVisible(true);
		frame.add(buttonNext);
		buttonPlay = new JButton("Play");
		buttonPlay.setBounds(410, 450, 80, 40);
		buttonPlay.setVisible(true);
		frame.add(buttonPlay);
		buttonStop = new JButton("Stop");
		buttonStop.setBounds(510, 450, 80, 40);
		buttonStop.setVisible(true);
		frame.add(buttonStop);
	}

	/**
	 * 图形界面文本框布置
	 */
	private static void buildText() {
		textJob = new JTextArea();
		textJob.setBounds(50, 50, 250, 350);
		textJob.setVisible(true);
		frame.add(textJob);
		textQueue = new JTextArea();
		textQueue.setBounds(350, 150, 380, 250);
		textQueue.setEditable(false);
		textQueue.setVisible(true);
		frame.add(textQueue);
	}

	/**
	 * 图形界面标签布置
	 */
	private static void buildLabel() {
		labelTime = new JLabel("Current Time: 0");
		labelTime.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelTime.setBounds(350, 50, 400, 20);
		labelTime.setVisible(true);
		frame.add(labelTime);
		labelCurrent = new JLabel("Current Running:");
		labelCurrent.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelCurrent.setBounds(350, 75, 400, 20);
		labelCurrent.setVisible(true);
		frame.add(labelCurrent);
		labelList = new JLabel("Null");
		labelList.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelList.setBounds(350, 100, 400, 20);
		labelList.setVisible(true);
		frame.add(labelList);
		labelQueue = new JLabel("Queue:");
		labelQueue.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelQueue.setBounds(350, 125, 400, 20);
		labelQueue.setVisible(true);
		frame.add(labelQueue);
	}

	/**
	 * 主函数，主要为引导
	 *
	 * @param args 传入参数，一般为空
	 */
	public static void main(String[] args) {
		buildFrame();
		buildButtons();
		buildText();
		buildLabel();
		frame.setVisible(true);
	}
}

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class Main {
	/**
	 * 主界面窗体
	 */
	public static JFrame frame;
	/**
	 * 主界面面板
	 */
	public static JPanel panel;
	/**
	 * 滚动区域（作业）
	 */
	public static JScrollPane paneJob;
	/**
	 * 滚动区域（等待队列）
	 */
	public static JScrollPane paneQueue;
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
	 * CPU核数量输入框
	 */
	public static JTextField textCores;
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
	 * 等待队列
	 */
	public static Map<Integer, Integer> mapQueue;
	/**
	 * 事务发生时间
	 */
	public static Map<Integer, Integer> mapTime;
	/**
	 * 计时器
	 */
	public static Timer timer;

	/**
	 * 图形界面窗口建立
	 */
	private static void buildFrame() {
		frame = new JFrame("Multiple Cores");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
	}

	/**
	 * 图形界面按钮布置
	 */
	private static void buildButtons() {
		buttonStart = new JButton("Start");
		buttonStart.setBounds(210, 450, 80, 40);
		panel.add(buttonStart);
		buttonNext = new JButton("Next");
		buttonNext.setBounds(310, 450, 80, 40);
		panel.add(buttonNext);
		buttonPlay = new JButton("Play");
		buttonPlay.setBounds(410, 450, 80, 40);
		panel.add(buttonPlay);
		buttonStop = new JButton("Stop");
		buttonStop.setBounds(510, 450, 80, 40);
		panel.add(buttonStop);
	}

	/**
	 * 图形界面文本框布置
	 */
	private static void buildText() {
		textCores = new JTextField();
		textCores.setBounds(50, 50, 250, 30);
		panel.add(textCores);
		paneJob = new JScrollPane();
		paneJob.setBounds(50, 100, 250, 300);
		panel.add(paneJob);
		textJob = new JTextArea();
		paneJob.setViewportView(textJob);
		paneJob.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		paneQueue = new JScrollPane();
		paneQueue.setBounds(350, 150, 380, 250);
		panel.add(paneQueue);
		textQueue = new JTextArea();
		textQueue.setEditable(false);
		paneQueue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		paneQueue.setViewportView(textQueue);
	}

	/**
	 * 图形界面标签布置
	 */
	private static void buildLabel() {
		labelTime = new JLabel("Current Time: 0");
		labelTime.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelTime.setBounds(350, 50, 400, 20);
		panel.add(labelTime);
		labelCurrent = new JLabel("Current Running:");
		labelCurrent.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelCurrent.setBounds(350, 75, 400, 20);
		panel.add(labelCurrent);
		labelList = new JLabel("Null");
		labelList.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelList.setBounds(350, 100, 400, 20);
		panel.add(labelList);
		labelQueue = new JLabel("Queue:");
		labelQueue.setFont(new Font("Consolas", Font.PLAIN, 20));
		labelQueue.setBounds(350, 125, 400, 20);
		panel.add(labelQueue);
	}

	/**
	 * 步进一个时间片
	 */
	private static void stepIn() {
		Vector<Job> v = calculate.work();
		labelTime.setText("Current Time: " + calculate.nowTime);
		if (v.isEmpty()) {
			labelList.setText("Null");
		} else {
			StringBuilder str = new StringBuilder();
			for (Job i : v) {
				if (str.length() == 0) {
					str.append(i.pid);
				} else {
					str.append(" ").append(i.pid);
				}
				mapQueue.put(i.pid, mapQueue.get(i.pid) - 1);
				if (mapQueue.get(i.pid) == 0) mapQueue.remove(i.pid);
			}
			labelList.setText(String.valueOf(str));
			textQueue.setText("");
			for (Map.Entry<Integer, Integer> i : mapQueue.entrySet()) {
				textQueue.append(i.getKey() + " " + i.getValue() + (calculate.nowTime >= mapTime.get(i.getKey()) ? " (In Queue)\n" : " (Schedule)\n"));
			}
		}
	}

	/**
	 * 设置按钮执行操作
	 */
	private static void setListeners() {
		mapQueue = new TreeMap<>();
		mapTime = new TreeMap<>();
		timer = new Timer(1000, e -> stepIn());
		buttonStart.addActionListener(e -> {
			for (char i : textCores.getText().toCharArray()) {
				if (i < '0' || i > '9') {
					JOptionPane.showMessageDialog(null, "CPU内核数量格式错误", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			int cores = Integer.parseInt(textCores.getText());
			if (cores <= 0 || cores > 8) {
				JOptionPane.showMessageDialog(null, "CPU内核数量不符合要求（1-8）", "警告", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Vector<Job> v = new Vector<>();
			String[] jobs = textJob.getText().split("\n");
			for (String i : jobs) {
				String[] num = i.split(" ");
				if (num.length != 4) {
					JOptionPane.showMessageDialog(null, "事务输入格式错误", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				for (String j : num) {
					for (char k : j.toCharArray()) {
						if (k < '0' || k > '9') {
							JOptionPane.showMessageDialog(null, "事务输入格式错误", "警告", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
				v.add(new Job(Integer.parseInt(num[0]), Integer.parseInt(num[1]), Integer.parseInt(num[2]), Integer.parseInt(num[3])));
			}
			mapQueue.clear();
			Map<Integer, Vector<Job>> tmp = new TreeMap<>();
			for (Job i : v) {
				mapQueue.put(i.pid, i.costTime);
				mapTime.put(i.pid, i.startTime);
				tmp.computeIfAbsent(i.startTime, k -> new Vector<>());
				tmp.get(i.startTime).add(i);
			}
			calculate = new Calculate(cores, tmp);
		});
		buttonNext.addActionListener(e -> stepIn());
		buttonPlay.addActionListener(e -> timer.start());
		buttonStop.addActionListener(e -> timer.stop());
	}

	/**
	 * main方法
	 *
	 * @param args 传入参数，留空
	 */
	public static void main(String[] args) {
		buildFrame();
		buildButtons();
		buildText();
		buildLabel();
		setListeners();
		frame.setVisible(true);
	}
}

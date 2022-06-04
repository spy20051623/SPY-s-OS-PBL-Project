import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * 计算功能类
 */
public class Calculate {
	/**
	 * CPU核的数量
	 */
	private final int cores;
	/**
	 * 事务队列（堆实现）
	 */
	private final PriorityQueue<Job> q;
	/**
	 * 当前时间片处理事务临时列表
	 */
	private final Vector<Job> v;
	/**
	 * 当前时间
	 */
	public int nowTime;
	/**
	 * 输入的事务列表
	 */
	private final Map<Integer, Vector<Job>> map;

	/**
	 * 实例化一个Calculate对象
	 *
	 * @param cores CPU核的数量
	 * @param map   事务列表
	 */
	public Calculate(int cores, Map<Integer, Vector<Job>> map) {
		this.cores = cores;
		q = new PriorityQueue<>((o1, o2) -> {
			if (o1.startTime < o2.startTime) return 1;
			if (o1.startTime > o2.startTime) return -1;
			if (o1.priority < o2.priority) return 1;
			if (o1.priority > o2.priority) return -1;
			return Integer.compare(o2.costTime, o1.costTime);
		});
		v = new Vector<>();
		nowTime = 0;
		this.map = map;
	}

	/**
	 * 新增一个事务
	 *
	 * @param job 事务对象
	 */
	public void addJob(Job job) {
		q.add(job);
	}

	/**
	 * 进行当前时间片工作
	 *
	 * @return 当前时间片工作进程列表
	 */
	public Vector<Job> work() {
		++nowTime;
		if (map.get(nowTime) != null) {
			for (Job i : map.get(nowTime)) {
				addJob(i);
			}
		}
		v.clear();
		for (int i = 0; i < this.cores; ++i) {
			if (q.isEmpty()) break;
			v.add(q.poll());
		}
		for (Job i : v) {
			if (i.costTime > 1) {
				q.add(new Job(i.pid, nowTime + i.priority, i.costTime - 1, i.priority));
			}
		}
		return v;
	}
}

/**
 * 事务类
 */
class Job {
	/**
	 * 事务序号
	 */
	public int pid;
	/**
	 * 事务开始时间
	 */
	public int startTime;
	/**
	 * 事务持续时间
	 */
	public int costTime;
	/**
	 * 事务优先级
	 */
	public int priority;

	/**
	 * 实例化一个Job对象
	 *
	 * @param pid       序号
	 * @param startTime 开始时间
	 * @param costTime  持续时间
	 * @param priority  优先级
	 */
	public Job(int pid, int startTime, int costTime, int priority) {
		this.pid = pid;
		this.startTime = startTime;
		this.costTime = costTime;
		this.priority = priority;
	}
}
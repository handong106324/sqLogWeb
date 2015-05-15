package com.jiangge.utils.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �̳߳ع���
 * @author jiang.li
 * @date 2013-12-17 11:10
 */
public class TaskPoolManager {
	
	/**����һ���������̳߳�**/
	private static TaskPoolManager tpm = new TaskPoolManager();
	
    private TaskPoolManager() {
    	
    }
    
	public static TaskPoolManager newInstance() { 
		return tpm;
	}

	/**�̳߳�ά���̵߳���������**/
	private final static int CORE_POOL_SIZE = 4;
	
	/**�̳߳�ά���̵߳��������**/
	private final static int MAX_POOL_SIZE = 100;
	
	/**�̳߳�ά���߳�������Ŀ���ʱ��**/
	private final static int KEEP_ALIVE_TIME = 0;
	
	/**�̳߳���ʹ�õĻ�����д�С**/
	private final static int WORK_QUEUE_SIZE = 100;
	
	/**��Ϣ�������**/
	private Queue<TaskEntity> taskQueue = new LinkedList<TaskEntity>();
	
	/**������Ϣ����ĵ����߳�**/
	final Runnable accessBufferThread = new Runnable() {
		public void run() {
			/**�鿴�Ƿ��д�����������У��򴴽�һ���µ�TaskEntity������ӵ��̳߳���**/
			if (hasMoreAcquire()) {
				TaskEntity msg = taskQueue.poll();
				Runnable task = new TaskRunner(msg);
				threadPool.execute(task);
			}
		}
	};
	
	final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			taskQueue.offer(((TaskRunner) r).getTask());
		}
	};
	
	/**�����̳߳�**/
	final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE), this.handler);
	
	/**�����̳߳�**/
	final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	final ScheduledFuture<?> taskHandler = scheduler.scheduleAtFixedRate(accessBufferThread, 0, 1, TimeUnit.SECONDS);


	/**
	 * �ж��̳߳�ʱ��Ϊ��
	 * @return
	 */
	private boolean hasMoreAcquire() {
		return !taskQueue.isEmpty();
	}

	/**
	 * ���̳߳���ӵ�������
	 * @param msg
	 */
	public void addTask(TaskEntity msg) {
		Runnable task = new TaskRunner(msg);
		threadPool.execute(task);
	}
	
	/**
	 * ���̳߳���Ӷ������
	 * @param msgList
	 */
	public void addTasks(List<TaskEntity> msgList) {
		Runnable task = null;
		for(TaskEntity msg : msgList){
			task = new TaskRunner(msg);
			threadPool.execute(task);
		}
	}
}

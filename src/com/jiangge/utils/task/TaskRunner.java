package com.jiangge.utils.task;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ִ��������߳�
 * @author jiang.li
 * @date 2013-12-17 13:17
 */
public class TaskRunner implements Runnable{

	/**����ʵ�嶨��**/
	private TaskEntity task;

	public TaskEntity getTask() {
		return task;
	}
	
	public TaskRunner(TaskEntity task) {
		this.task = task;
	}
	
	/**
	 * �߳̿�ʼִ��
	 */
	public void run() {
		try {
			/**����Java�������ʵ���������**/
			Class<?> className = task.getTaskClass();
			String classMethod = task.getTaskMethod();
			Method method = className.getMethod(classMethod, Map.class);
	        method.invoke(className.newInstance(),task.getTaskParam());
		}catch (Exception e) {
			System.out.println("�����ˣ�" + e.getMessage());
		}
	}

}

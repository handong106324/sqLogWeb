package com.jiangge.utils;

import java.util.List;

import com.jiangge.utils.task.TaskEntity;
import com.jiangge.utils.task.TaskPoolManager;

/**
 * Java���̡߳�����ʵ���������
 * @author jiang.li
 * @date 2013-12-23 14:25
 */
public class TaskUtils {
	
	
    /**
     * ����첽����(�����б�)
     * @param taskList
     */
	public static void addTaskList(List<TaskEntity> taskList){
		TaskPoolManager.newInstance().addTasks(taskList);
	}
	
	 /**
     * ����첽����(��������)
     * @param taskList
     */
	public static void addTask(TaskEntity task){
		TaskPoolManager.newInstance().addTask(task);
	}
	
}

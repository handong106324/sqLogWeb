package com.jiangge.utils.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiangge.utils.TaskUtils;
import com.jiangge.utils.task.TaskEntity;

public class TaskTest {

	/**
	 * ���Զ��������
	 * @param args
	 */
	public static void main(String[] args){
		/**���������б�**/
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		TaskEntity task = null;
		Map<String, String> taskParam = null;
		for(int i=0;i<5;i++){
			task = new TaskEntity();
			task.setTaskClass(SendSMS.class);
			task.setTaskMethod("send");
			taskParam = new HashMap<String,String>();
			taskParam.put("phone", "1888888888" + i);
			taskParam.put("content", "��������" + i);
			task.setTaskParam(taskParam);
			taskList.add(task);
		}
		/**��������ӵ�����**/
		TaskUtils.addTaskList(taskList);
	}
}

package com.sq.log.result;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.sq.base.ResultHandlerType;
import com.sq.model.BaseFlow;
import com.sq.model.PageParam;
import com.sq.model.TaskFlow;
import com.sq.utils.ClassUtils;

public class ResultHandler {

	public static String HANDLER_TYPE_BASIC = "BASIC";
	String[] mainKeys = {"交任务","接任务"};
	List<BaseFlow> datas = new ArrayList<BaseFlow>();
	/**
	 * @return the datas
	 */
	public List<BaseFlow> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List<BaseFlow> datas) {
		this.datas = datas;
	}

	PageParam pageParam;
	boolean isMany;
	List<String> list;
	public ResultHandler(boolean isMany2, List<String> list2, PageParam pageParam2,String handleType){
		this.isMany = isMany2;
		this.pageParam = pageParam2;
		this.list = list2;
		init(handleType);
	}
	
	public void init(String handlerType){

		if(list == null){
			return;
		}
		if(HANDLER_TYPE_BASIC.equals(handlerType) || StringUtils.isBlank(handlerType)){
			handlerBasic();
		}else {
			for(String s :list){
				BaseFlow bf= getFlow(s,pageParam,handlerType);
				if(bf != null  && bf.canUse()){
					datas.add(bf);
				}
			}
		}
	}
	

	private BaseFlow getFlow(String s, PageParam pageParam2, String handlerType) {
		Set<Class<?>> clazzes = ClassUtils.getClasses(BaseFlow.class.getPackage());
		Iterator<Class<?>> ite = clazzes.iterator();
		while (ite.hasNext()) {
			Class<?> cls = ite.next();
			if (cls.isAnnotationPresent(ResultHandlerType.class)) {
				ResultHandlerType first = (ResultHandlerType) cls.getAnnotation(ResultHandlerType.class);
				if(first.type().equals(handlerType)){
					try {
						Constructor<?> cFlow = cls.getConstructor(String.class,PageParam.class);
						BaseFlow bf = (BaseFlow) cFlow.newInstance(s,pageParam2);
						return bf;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		return null;
	}

	private void handlerBasic() {
		List<TaskFlow> tasks = new ArrayList<TaskFlow>();
		if(isMany){
			for(String s :list){
				if(isMain(s)){
					datas.add(new BaseFlow(s,pageParam));
				}else {
					tasks.add(new TaskFlow(s));
				}
			}
			reSetFlows(tasks);
		}else {
			for(String s :list){
				datas.add(new BaseFlow(s,pageParam));
			}
		}
	}

	private void reSetFlows( List<TaskFlow> tasks) {
		for(BaseFlow flow :datas){
			String playerName = flow.getMapInfos().get("name");
			String task = flow.getMapInfos().get("任务名称");
			String type = flow.getMapInfos().get("类型");
			for(TaskFlow tf :tasks){
				if(type != null && type .equals("交任务")&&task != null && playerName != null && playerName.equals(tf.getPlayerName()) && task.contains(tf.getTaskName())){
					String jiangli = flow.getMapInfos().get("任务奖励");
					flow.getMapInfos().put("任务奖励", (jiangli == null?"":jiangli)+tf.getReward());
				}
			}
		}
		
	}

	private boolean isMain(String s) {
		for(String key : mainKeys){
			if(s.contains(key)){
				return true;
			}
		}
		return false;
	}
}

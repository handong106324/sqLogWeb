package com.sq.log.result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;

import com.sq.common.Constant;
import com.sq.entity.LogPageInfo;
import com.sq.model.BaseFlow;
import com.sq.model.PageParam;
import com.sq.shell.ShellDao;

public class LogResultInfo {

	private BlockingQueue<String> datas = new LinkedBlockingDeque<String>();
	private String msg = "";
	private PageParam pageParam;
	private int size;
	private String sessionId;
	private ReentrantLock lock = new ReentrantLock();
	private Map<String,Integer> resultCountMap = new HashMap<String,Integer>();
	private Map<String,Boolean> statMap = new HashMap<String,Boolean>();
	private boolean cancel = false;
	private int status = Constant.SHELL_STATUS_READY ;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Map<String,String> scheduleMsgMap = new HashMap<String,String>();
	private boolean farceFinish = false;
	private String scheduleMsg;
	private Object cancelMsg;
	private String resultHandleType;
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	public void setScheduleMsg(String sch){
		if(this.scheduleMsg == null){
			this.scheduleMsg ="["+ sch+"]";
		}else {
			this.scheduleMsg +="<br>["+sch+"]";
		}
	}
	
	public String getScheduleMsg(){
		return scheduleMsg;
	}

	public LogResultInfo(String type,String game){
		this.pageParam = new PageParam(type,game);
	}
	
	public void setOK(String key){
		statMap.put(checkKey(key),true);
	}
	
	public boolean getOK(String key){
		return statMap.get(checkKey(key));
	}
	
	public boolean isFinish(){
		if(farceFinish)return true;
		return (statMap.size() >0)&&!statMap.containsValue(false);
	}
	/**
	 * @return the datas
	 */
	public BlockingQueue<String> getDatas() {
		return datas;
	}
//	/**
//	 * @param datas the datas to set
//	 */
	public void setDatas(BlockingQueue<String> datas) {
		this.datas = datas;
	}
	public String getTableInfo() {
		int len = datas.size();
		List<String> list = new ArrayList<String>();
		for(int i = 0 ; i < len ; i++){
			list.add(datas.poll());
		}
		List<BaseFlow> fls = new ResultHandler(false, list, pageParam,this.getResultHandleType()).getDatas();
		List<LogPageInfo> pis = pageParam.getUsefualPages();
		StringBuilder sb = new StringBuilder("");
		for(BaseFlow bf : fls){
			sb.append("<tr>");
			for(LogPageInfo li :pis){
				sb.append("<td>"+getVal(li, bf)+"</td>");
			}
			sb.append("<td>"+bf.getTime()+"</td></tr>");
		}
		return sb.toString();
	}
	
	public String getVal(LogPageInfo pro,BaseFlow bf){
		String val = bf.getMapInfos().get(pro.get("colName"));
		return val==null?"":val;
	}


	public String getMsg() {
		if(this.status == Constant.SHELL_STATUS_CHECK_BASIC){
			return "校验基础配置......";
		}else if(status == Constant.SHELL_STATUS_CHECK_FILE){
			return "校验文件......";
		}else if(status == Constant.SHELL_STATUS_READY){
			return "准备校验配置信息......";
		}else if(status == Constant.SHELL_STATUS_ERROR){
			return "查询失败";
		}
		Iterator<String> it = this.scheduleMsgMap.keySet().iterator();
		if(it.hasNext())msg = "";
		while(it.hasNext()){
			String key = it.next();
			setMsg(scheduleMsgMap.get(key));
		}
		if(this.cancel)msg +="<br><font color='red'>取消原因:"+cancelMsg+"</font>";
		return msg;
	}


	public void setMsg(String msg) {
		if(this.msg == null){
			this.msg ="["+ msg+"]";
		}else {
			this.msg +="<br>["+msg+"]";
		}
	}
	
	

	public void setScheduleMsg(String key, String info){
		String in = scheduleMsgMap.get(key);
		this.scheduleMsgMap.put(key, in==null?info:in+","+info);
	}

	
	public void setSize(int index) {
		lock.lock();
		size +=index;
		if(size >= 500){
			cancel("总数据量超过500");
		}
		lock.unlock();
	}

	public void setSize(String key,int count){
		key = checkKey(key);
		Integer c = resultCountMap.get(key);
		if(c == null){
			resultCountMap.put(key, 0);
			c = resultCountMap.get(key);
		}
		c++;
		resultCountMap.put(key, c);
	}
	
	public int getFileResultCount(String key){
		Integer cou = resultCountMap.get(checkKey(key));
		return cou == null?0:cou;
	}
	



	public void add(String key) {
		statMap.put(checkKey(key), false);
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public void cancel(String reason) {
		this.cancel = true;
		this.cancelMsg = reason;
	}


	public boolean isCancel() {
		return cancel;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogResultInfo [ msg=" + msg
				+ ", pageParam=" + pageParam + ", size=" + size
				+ ", sessionId=" + sessionId + ", lock=" + lock
				+ ", resultCountMap=" + resultCountMap + ", statMap=" + statMap
				+ ", cancel=" + cancel + "]";
	}


	private String checkKey(String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		
		if((key.indexOf('-') == 4 && key.lastIndexOf('-') == 7) && (key.contains("场景服")||key.contains("逻辑服"))){
			return key;
		}
		return ShellDao.getFileShowName(key);
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public void finish() {
		if(statMap.size()==0){
			this.farceFinish = true;
		}else {
			System.out.println("查询进行中，不能强制完成");
		}
	}

	public String getResultHandleType() {
		return resultHandleType;
	}

	public void setResultHandleType(String resultHandleType) {
		this.resultHandleType = resultHandleType;
	}

	
}

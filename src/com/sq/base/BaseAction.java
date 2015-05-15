package com.sq.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.sq.entity.Group;
import com.sq.entity.LogFileInfo;
import com.sq.entity.Role;
import com.sq.entity.User;
/**
 * 提取一些经常的代码，主要用于继承
 * @author handong
 *
 */
public class BaseAction extends Controller {
	public Logger logger = Logger.getLogger(getClass());
	public int ROLE_SUPER = 0 ;
	public int ROLE_GROUPMANAGER= 1;
	public int ROLE_EMP = 2;
	SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	private User sessionUser;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 组装实体
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Model setValues(Model model){
		String[] props = model.getColumns();
		for(String prop:props){
			String val = getPara(prop);
			if(prop.equals("userId")){
				model.set("userId", getUser().getInt("id"));
				continue;
			}
			if ("createTime".equals(prop)) {
				model.set(prop, new Date());
				continue;
			}
			if(val != null && val.length() >0 && !val.equals("undefined")){
				model.set(prop, val.trim());
			}
		}
		return model;
	}
	public User getUser() {
		if(sessionUser == null){
			sessionUser = getSessionAttr("userInfo");
		}
		return sessionUser;
	}
	/**
	 * 加载GroupUser
	 */
	public void loadGroupUsers(){
		List<Group> groups = Group.dao.find("select * from t_group");
		Map<Integer,List<User>> groupUser = new HashMap<Integer,List<User>>();
		for(Group group : groups){
			groupUser.put(group.getInt("id"), User.dao.find("select * from t_user where id in(select userId from t_group_user where groupId = " + group.getInt("id") +" )"));
		}
		setAttr("groupUsers", groupUser);
		setAttr("groups", groups);
		setSessionAttr("group", groups);
		setSessionAttr("groupUsers", groupUser);
	}
	
	/**
	 * 获取某组所有的用户
	 */
	public void getUsersJson(){
		String id = getPara("id");
		List<User> userList = new ArrayList<User>();
		userList = User.dao.find("select * from t_user where id in(select userId from t_group_user where groupId in( " + id +") )");
		renderJson("result",userList);
	}
	/**
	 * 判断是否是新建or更新
	 * @return
	 */
	public boolean getAddFlag(){
		String id = getPara("id");
		if(id != null && id.length() >0){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 保存或者更新
	 * @param model
	 */
	public boolean saveOrUpdateToDb(Model model){
		model = setValues(model);
		boolean ok =false;
		if(getAddFlag()){
			if(!checkUnique(model)){
				return false;
			}
			ok = model.save();
		}else {
			ok = model.update();
		}
		return ok;
	}
	protected boolean checkUnique(Model model) {
		// TODO Auto-generated method stub
		String[] cols = getCols(model.getClass());
		if(cols == null)return true;
		String sql = "select * from "+model.getTableName() +" where ";
		int len = cols.length;
		for(int i = 0 ; i < len; i++){
			String col = cols[i];
			sql += col+"='"+model.getStr(col)+"'";
			Model m = model.findFirst(sql);
			if(m != null){
				this.msg ="["+model.getStr(col)+"]已经存在";
				return false;
			}
		}
		this.msg = null;
		return true;
	}
	//校验：流程、文档、用户、组、角色、事件类型、排期表
	private String[] getCols(Class<? extends Model> class1) {
		Map<String,String[]> map = UniqueCheckFactory.getInstance(null);
		String[] clos = map.get(class1.getName());
		return clos;
	}
	
	public void logInfo(String msg){
		if(logger.isInfoEnabled()){
			logger.info(msg);
		}
	}
	
	public void logDebug(String msg){
		if(logger.isDebugEnabled()){
			logger.info(msg);
		}
	}
	
	public int getPageNumber(){
		if(getPara("pageNumber") != null){
			return getParaToInt("pageNumber");
		}
		return 1;
	}
	
	
	
	public boolean contains(List<? extends Model> models , Map<String,Object> paraMap){
		for(Model model :models){
			Iterator<String> keys = paraMap.keySet().iterator();
			while(keys.hasNext()){
				String key = keys.next();
				if(model.get(key).equals(paraMap.get(key))){
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean saveOrUpdate(boolean addFlag,Model gs){
		if(addFlag){
			return gs.save();
		}else {
			return gs.update();
		}
	}
	
	public Model setValues(Model model , String da ,String parChar,String subChar){
		String[] paras = da.split(parChar);
		for(String pro : paras){
			String[] kv = pro.split(subChar);
			model.set(kv[0]	, kv[1]);
		}
		return model;
	}
	
	public int getRoleFlag(){
//		Integer roleFlag = getSessionAttr("roleFlag");
//
//		if(roleFlag != null){
//			return roleFlag;
//		}
		int roleFlag = 1;
		int roleId = getUser().getInt("roleId");
		Role role = Role.dao.findById(roleId);
		if(role.get("isSuper")!= null && role.getBoolean("isSuper")){
			roleFlag = ROLE_SUPER;
		}else if(role.get("isGroupManager") != null && role.getBoolean("isGroupManager")){
			roleFlag = ROLE_GROUPMANAGER;
		}else {
			roleFlag = ROLE_EMP;
		}
//		setSessionAttr("roleFlag", roleFlag);
		return roleFlag;
	}
	
	public String createCMD(String[] vals) {
		String cmd = " ";
		for(String val : vals){
			if(StringUtils.isNotBlank(val))cmd +=(" | grep "+ val);
		}
		return cmd;
	}

//	public String getPathByServerName(String serverName,String type,String sufix) {
//		LogFileInfo info = LogFileInfo.dao.findFirstByCondition(" serverName='"+serverName+"' and type='"+type+"'");
//		if(info == null)return null;
//		String fileName = info.getStr("fileName");
//		if(!fileName.endsWith(".log")){
//			fileName += ".log";
//		}
//		return info.getStr("dir")+File.separator+fileName + sufix;
//	}
	
}

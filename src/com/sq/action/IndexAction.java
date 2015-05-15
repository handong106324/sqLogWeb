package com.sq.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;
import com.jiangge.utils.algorithm.MD5;
import com.sq.base.SqageActionInterface;
import com.sq.cache.Cache;
import com.sq.entity.Group;
import com.sq.entity.Resource;
import com.sq.entity.User;
import com.sq.interfaces.GameServerOperation;
import com.sq.shell.currency.ActiveThread;

@SqageActionInterface(path="/")
public class IndexAction extends Controller {

	public void index() {
		setAttr("msg", "");
		setSessionAttr("userName", Cache.getMessage("账号"));
		setSessionAttr("password", Cache.getMessage("密码"));
		setAttr("userName", Cache.getMessage("账号"));
		setAttr("password", Cache.getMessage("密码"));
		this.getSession().removeAttribute("msg");
		if(this.getSession().getAttribute("userInfo") != null){
			renderJsp("/index.jsp");
		}else{
			renderJsp("/login.jsp");
		}
	}
	public void logout(){
		this.getSession().removeAttribute("userInfo");
		renderJsp("/login.jsp");
	}
	public void login(){
		if(this.getSessionAttr("userInfo")!=null){
			render("index.jsp");
			return;
		}
		String userName = getPara("loginName");
		String password = getPara("password");
		if(userName == null && password == null){
			setAttr("msg", "");
			render("/login.jsp");
			return;
		}
		
//		new ActiveThread().start();
		List<User> userList = User.dao.find("select * from t_user where loginName = '" + userName +"'");
		if(userList != null && userList.size() > 0){
			User user = userList.get(0);
			if(2 == user.getInt("isValid")){
				setAttr("msg", "用户已禁用");
				render("/login.jsp");
				return;
			}
			if(!user.get("password").equals(MD5.GetMD5Code(password,true))){
				setAttr("msg", "密码不正确！");
				render("/login.jsp");
			}else {
				//进入加载首页列表
				setAttr("msg", "登陆成功");
				HttpSession session = this.getSession();
				session.setAttribute("resourceSessionList", getResourceList((Integer)user.get("roleId")));
				session.setAttribute("userInfo", user);
				session.setAttribute("sq_title",Cache.getMessage("神奇时代日志查询系统"));
				setAttr("weizhi", Cache.getMessage("您的位置")+Cache.getMessage("首页"));

				session.setAttribute("logout", Cache.getMessage("注销"));
				if(user.set("lastLoginTime", new Date()).update()){
					System.out.println("修改最后登陆时间成功");;
				}else {
					System.out.println("修改最后登录时间失败");
				}
				/*User user2 = (User)session.getAttribute("userInfo");
				System.out.println(user2.getStr("realName"));
				
				setAttr("resourceSessionList", getResourceList(user.get("id").toString()));
				*/
				GameServerOperation.reloadServer();
				setAttr("userInfo", user);
				setSessionAttr("sessionGroup", Group.dao);
				render("/index.jsp");
			}
		}else {
			setAttr("msg", "用户不存");
			render("/login.jsp");
		}
	}
	public List<Map<String, Object>> getResourceList(int roleId) {
		List<Resource> ll = Resource.dao.find("select a.* from t_resource a inner join t_role_resource b " +
				"on a.id=b.resourceId where b.roleId="+roleId+" order by sortIndex desc");
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		for(Resource res : ll){
			Map<String, Object> map = new HashMap<String, Object>();
			for(int i=0;i<res.getAttrNames().length;i++){
				map.put(res.getAttrNames()[i], res.get(res.getAttrNames()[i]));
			}
			allList.add(map);
		}
		//获取已选中的
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>(); 
		List<Map<String, Object>> list_temp = new ArrayList<Map<String, Object>>();//存放一级菜单
		
		for(Map<String, Object> res : allList){
			if(res.get("parentId") !=null && ((Integer)res.get("parentId")).intValue() == 0){
				
				list_temp.add(res);
			}
		}
		for(Map<String, Object> map : list_temp){
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for(Map<String, Object> map2 : allList){
				if(((Integer)map2.get("parentId")).intValue() == ((Integer)map.get("id")).intValue()){
					tempList.add(map2);
					List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
					for(Map<String, Object> map3 : allList){
						if(((Integer)map3.get("parentId")).intValue() == ((Integer)map2.get("id")).intValue()){
							list3.add(map3);
							map2.put("hasList", "hasList");
						}
						map2.put("list", list3);
					}
					
				}
			}
			map.put("list", tempList);
			list2.add(map);
		}
		return list2;
	}
	
	public List<Map<String, Object>> getResourceList(User user){
		return getResourceList((Integer)user.get("roleId"));
	}
}

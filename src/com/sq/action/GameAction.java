package com.sq.action;

import java.util.List;

import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.entity.GAME;
@SqageActionInterface(path="game")
public class GameAction extends BaseAction {

	public void list(){
		List<GAME> games = GAME.dao.findAll();
		setAttr("list", games);
	}
	
	public void showAddOrUpdate(){
		boolean addFlag = getAddFlag();
		if(!addFlag){
			GAME bean = GAME.dao.findById(getPara("id"));
			setAttr("bean", bean);
		}
		render("addOrUpdate.jsp");
	}
	
	public void addOrUpdate(){
		GAME game = null;
		if(getAddFlag()){
			game = new GAME();
			String showName = getPara("intro");
			String name = getPara("name");
			GAME checkGame = GAME.dao.findFirstByCondition(" intro='"+showName+"' or name ='"+name+"'");
			if(checkGame != null){
				renderJson("result", "已存在 显示名为["+showName+"] 实际名称为["+name+"]的游戏数据");
				return;
			}
		
		}else {
			game = GAME.dao.findById(getPara("id"));
		}
		
		if(saveOrUpdateToDb(game)){
			renderJson("result","success");
		}else {
			renderJson("result","保存失败");
		}
		
	}
}

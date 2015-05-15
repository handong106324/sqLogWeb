package com.sq.config;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.sq.base.SqageActionInterface;
import com.sq.base.SqageBaseEntity;
import com.sq.common.Constant;
import com.sq.entity.User;
import com.sq.log.UserInterceptor;
import com.sq.plugin.LogPlugin;
import com.sq.plugin.MailPlugin;
import com.sq.utils.ClassUtils;
import com.sq.utils.PropertiesUtils;

public class AppConfig extends JFinalConfig {
	Logger logger = Logger.getLogger(AppConfig.class);

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new JfinalSkipHandler());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new UserInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		// 加载数据库插件
		me.add(new LogPlugin());
		String haiwai = PropertiesUtils.getPropertiesValue("jdbc.properties", Constant.HAIWAI_FLAG);
		if(haiwai != null&&haiwai.equals("true")){
			logger.debug("----海外使用----");
			Constant.isHaiwai = true;
		}else {
			logger.debug("----国内使用，增加邮件插件----");
			me.add(new MailPlugin());
		}
		loadDbPlugin(me);
	}


	@Override
	public void configRoute(Routes me) {
		logger.debug("---开始加载Action---");
		try {
			logger.debug("---开始自动加载Action---");
			regeistAction(me,com.sq.action.UserAction.class.getPackage());
//			regeistAction(me,TestActionOfLuc.class.getPackage());
//			regeistAction(me, WxActionCenter.class.getPackage());
			logger.debug("---自动加载Action成功---");
		}catch (Exception e) {
			logger.error("---自动加载Action失败---");
		}
		
		logger.debug("---开始手动加载Action---");
		//me.add("/bug",BugAction.class)
		logger.debug("---手动加载Action成功---");
	}


	// 加载数据库插件
	private void loadDbPlugin(Plugins me) {
		logger.debug("---加载数据库插件开始--- ");
		C3p0Plugin c3p0Plugin = null;
		Properties pro = loadPropertyFile("classes/jdbc.properties");
		if (pro != null) {
			String jdbcUtil = getProperty("jdbcUrl");
			String username = getProperty("username");
			String password = getProperty("password");
			logger.info("数据库连接：" + jdbcUtil +" userName:"+ username +" password: " + password);
			c3p0Plugin = new C3p0Plugin(jdbcUtil, username, password);
			me.add(c3p0Plugin);
		} else {
			logger.info("文件不存在，选择固定设置");
			c3p0Plugin = new C3p0Plugin("jdbc:mysql://124.248.40.5:3306/caiwu","devuser","devuser");
			me.add(c3p0Plugin);
			
		}
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		regeistEntity(arp);
		logger.debug("---加载数据库插件完成--- ");
	}
	/**
	 * 根据注释加载Model
	 * @param arp
	 */
	private void regeistEntity(ActiveRecordPlugin arp) {
		logger.debug("---加载映射开始--- ");
		Set<Class<?>> clazzes = ClassUtils.getClasses(User.class.getPackage());
		Iterator ite = clazzes.iterator();
		while (ite.hasNext()) {
			Class<? extends Model<?>> cls = (Class<? extends Model<?>>) ite.next();
			if (cls.isAnnotationPresent(SqageBaseEntity.class)) {
				SqageBaseEntity first = (SqageBaseEntity) cls.getAnnotation(SqageBaseEntity.class);
				arp.addMapping(first.tableName(), cls);
				logger.debug("实体："+cls.getName()+":"+first.tableName());
			}
		}
		logger.debug("---加载映射完成--- ");
	}

	private void regeistAction(Routes me,Package pack) throws InstantiationException, IllegalAccessException {
		Set<Class<?>> clazzes = ClassUtils.getClasses(pack);
		Iterator ite = clazzes.iterator();
		while (ite.hasNext()) {
			Class cls = (Class) ite.next();
			if (cls.isAnnotationPresent(SqageActionInterface.class)) {
				SqageActionInterface first = (SqageActionInterface) cls.getAnnotation(SqageActionInterface.class);
				me.add(first.path(), cls);
			}
		}
	}
	
//	private void regeistWxAction(Routes me) throws InstantiationException, IllegalAccessException {
//		Set<Class<?>> clazzes = ClassUtils.getClasses(ArticleAction.class.getPackage());
//		Iterator ite = clazzes.iterator();
//		while (ite.hasNext()) {
//			Class cls = (Class) ite.next();
//			if (cls.isAnnotationPresent(SqageActionInterface.class)) {
//				SqageActionInterface first = (SqageActionInterface) cls.getAnnotation(SqageActionInterface.class);
//				me.add(first.path(), cls);
//			}
//		}
//	}
}

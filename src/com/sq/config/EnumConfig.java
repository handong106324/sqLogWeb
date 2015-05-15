package com.sq.config;

public interface EnumConfig {
	
	/**
	 * 获得枚举ID
	 * @return
	 */
	public int getId();
	
	/**
	 * 获得枚举名字
	 * @return
	 */
	public String getName();
	
	/**
	 * 获得枚举简单描述
	 * @return
	 */
	public String getIntro();
	
	/**
	 * 获得枚举详细描述
	 * @return
	 */
	public String getDescription();

}

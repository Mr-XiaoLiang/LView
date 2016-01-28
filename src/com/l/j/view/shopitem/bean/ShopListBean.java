package com.l.j.view.shopitem.bean;

import java.util.ArrayList;

/**
 * 商城列表
 * @author LiuJ
 *
 */
public class ShopListBean {
	/**
	 * 轮播图
	 */
	public static final int HomePagersItem_101 = 101;
	/**
	 * 大三图并列
	 */
	public static final int HomeBigThreeItem_102 = 102;
	/**
	 * 中型横幅
	 */
	public static final int HomeMediumBannerItem_103 = 103;
	/**
	 * 文字标题
	 */
	public static final int HomeTextTitleItem_104 = 104;
	/**
	 * 小型横幅
	 */
	public static final int HomeSmallBannerItem_105 = 105;
	/**
	 * item的类型
	 */
	private ArrayList<Integer> types;
	/**
	 * item的属性
	 */
	private ArrayList<Object> beans;
	public ArrayList<Integer> getTypes() {
		return types;
	}
	public Integer getTypes(int i) {
		return types.get(i);
	}
	public int getTypesSize() {
		return types.size();
	}
	public void setTypes(ArrayList<Integer> types) {
		this.types = types;
	}
	public ArrayList<Object> getBeans() {
		return beans;
	}
	public Object getBeans(int i) {
		return beans.get(i);
	}
	public int getBeansSize() {
		return beans.size();
	}
	public void setBeans(ArrayList<Object> beans) {
		this.beans = beans;
	}
	public void addBean(Object o){
		beans.add(o);
	}
	public void addType(int i){
		types.add(i);
	}
	public ShopListBean() {
		super();
		this.types = new ArrayList<Integer>();
		this.beans = new ArrayList<Object>();
	}
	public ShopListBean(ArrayList<Integer> types, ArrayList<Object> beans) {
		super();
		this.types = types;
		this.beans = beans;
	}
	
}

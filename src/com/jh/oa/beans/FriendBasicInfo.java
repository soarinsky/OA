package com.jh.oa.beans;

import com.jh.oa.utils.PinyinUtils;

public class FriendBasicInfo {
	
	private int id;                         //id主键
	private String realName;                //姓名
	private String department;              //部门
	private String shortPhoneNumber;        //手机短号
	private String longPhoneNumber;         //手机长号
	private String py;	                      //姓名首字母   排序使用
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		setPy(PinyinUtils.converterToFirstSpell(realName));
		this.realName = realName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getShortPhoneNumber() {
		return shortPhoneNumber;
	}
	public void setShortPhoneNumber(String shortPhoneNumber) {
		this.shortPhoneNumber = shortPhoneNumber;
	}
	public String getLongPhoneNumber() {
		return longPhoneNumber;
	}
	public void setLongPhoneNumber(String longPhoneNumber) {
		this.longPhoneNumber = longPhoneNumber;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	
}

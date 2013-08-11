package com.jh.oa.beans;

import com.jh.oa.utils.PinyinUtils;

public class FriendBasicInfo {
	
	private int id;                         //id����
	private String realName;                //����
	private String department;              //����
	private String shortPhoneNumber;        //�ֻ��̺�
	private String longPhoneNumber;         //�ֻ�����
	private String py;	                      //��������ĸ   ����ʹ��
	
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

package com.jh.oa.beans;

public class Organization {

	private String organizationName = "";           //部门名称
	private String organizationCode = "";           //部门编码
	private String organizationTopcode = "";        //社团编码 部门高一级
	
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationTopcode() {
		return organizationTopcode;
	}
	public void setOrganizationTopcode(String organizationTopcode) {
		this.organizationTopcode = organizationTopcode;
	}
	
	
}

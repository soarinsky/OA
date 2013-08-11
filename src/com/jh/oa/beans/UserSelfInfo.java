package com.jh.oa.beans;

import java.util.List;

public class UserSelfInfo extends UserInfo{

	private List organizations ;      //用户所属部门列表

	public List getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List organizations) {
		this.organizations = organizations;
	}
	
	
}

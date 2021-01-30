package com.linkin.utils;

public enum RoleEnum {
	ADMIN("ROLE_ADMIN"), MEMBER("ROLE_MEMBER");
	private String roleName;

	RoleEnum(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

package com.naver.phone.dto;

public class UserDto {

	private String userid;
	private String userpw;
	private String userph;
	
	
	@Override
	public String toString() {
		return "UserDto [userid=" + userid + ", userpw=" + userpw + ", userph=" + userph + "]";
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getUserph() {
		return userph;
	}
	public void setUserph(String userph) {
		this.userph = userph;
	}
	
	
	
}

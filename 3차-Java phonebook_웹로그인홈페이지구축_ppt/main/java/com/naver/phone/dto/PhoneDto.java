package com.naver.phone.dto;

public class PhoneDto {

	private String userid;
	private String userpw;
	private String userph;
	
	private int mbid;
	private String mbnm;
	private String mbph;
	private String mbad;
	private String mbgno;

	private String groupName;

	
	
	@Override
	public String toString() {
		return "PhoneDto [userid=" + userid + ", userpw=" + userpw + ", userph=" + userph + ", mbid=" + mbid + ", mbnm="
				+ mbnm + ", mbph=" + mbph + ", mbad=" + mbad + ", mbgno=" + mbgno + ", groupName=" + groupName + "]";
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

	public int getMbid() {
		return mbid;
	}

	public void setMbid(int mbid) {
		this.mbid = mbid;
	}

	public String getMbnm() {
		return mbnm;
	}

	public void setMbnm(String mbnm) {
		this.mbnm = mbnm;
	}

	public String getMbph() {
		return mbph;
	}

	public void setMbph(String mbph) {
		this.mbph = mbph;
	}

	public String getMbad() {
		return mbad;
	}

	public void setMbad(String mbad) {
		this.mbad = mbad;
	}

	public String getMbgno() {
		return mbgno;
	}

	public void setMbgno(String mbgno) {
		this.mbgno = mbgno;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	
}

package com.uwang.dao;

import java.util.Date;


public class PageBean {
	//这是一个员工的实体信息内容
	
	private int eimId;
	
	private String eifName;
	
	private int age;
	
	private String img;
	
	private String workExperience;
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	private Date creationTime;
	
	private Date updateTime;
	
	public int getEimId() {
		return eimId;
	}
	public void setEimId(int eimId) {
		this.eimId = eimId;
	}
	public String getEifName() {
		return eifName;
	}
	public void setEifName(String eifName) {
		this.eifName = eifName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getWorkExperience() {
		return workExperience;
	}
	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "PageBean [eimId=" + eimId + ", eifName=" + eifName + ", age=" + age + ", img=" + img
				+ ", workExperience=" + workExperience + ", creationTime=" + creationTime + ", updateTime=" + updateTime
				+ "]";
	}



	
}

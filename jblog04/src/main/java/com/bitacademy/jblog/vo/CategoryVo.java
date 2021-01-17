package com.bitacademy.jblog.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.bitacademy.security.ValidationGroups;

public class CategoryVo {
	
	@Positive(groups = {ValidationGroups.option.class})
	Long no;
	
	@NotEmpty(groups = {ValidationGroups.full.class})
	String name;
	
	String description;
	String regDate;
	
	@NotEmpty(groups = {ValidationGroups.full.class})
	String id;
	
	Integer postsCount;
	
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(Integer i) {
		this.postsCount = i;
	}

	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", regDate=" + regDate
				+ ", id=" + id + ", postsCount=" + postsCount + "]";
	}
}

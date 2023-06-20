package com.tejas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Marks {
	
	@Id
	private Integer aid;
	private Integer english;
	private Integer math;
	
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Integer getEnglish() {
		return english;
	}
	public void setEnglish(Integer english) {
		this.english = english;
	}
	public Integer getMath() {
		return math;
	}
	public void setMath(Integer math) {
		this.math = math;
	}
	@Override
	public String toString() {
		return "Marks [aid=" + aid + ", english=" + english + ", math=" + math + "]";
	}
	public Marks(Integer english, Integer math) {
		super();
		this.english = english;
		this.math = math;
	}
	
	
	
}

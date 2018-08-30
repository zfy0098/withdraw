package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;


/**
 *  @author hadoop
 */
public class DownLoadNum implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private Date date;
	private Integer num;
	private String notes;
	

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}

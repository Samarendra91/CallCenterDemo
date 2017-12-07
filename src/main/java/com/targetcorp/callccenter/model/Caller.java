package com.targetcorp.callccenter.model;

public class Caller {
	private String name;
	private int userId;

	public Caller(int id, String nm) {
		userId = id;
		name = nm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

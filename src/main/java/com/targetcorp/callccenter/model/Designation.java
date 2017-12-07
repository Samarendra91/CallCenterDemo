package com.targetcorp.callccenter.model;

public enum Designation {
	JuniorExecutive(0), SeniorExecutive(1), ProjectManager(2);
	
	private int value;

	private Designation(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}

package com.targetcorp.callccenter.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.targetcorp.callccenter.model.Caller;
import com.targetcorp.callccenter.model.Designation;
import com.targetcorp.callccenter.model.Employee;


public class Call {
	
	private static final Logger LOG = LoggerFactory.getLogger(Call.class);
	private Designation empType;
	private Caller caller;
	private Employee handler;

	public Call(Caller c) {
		empType = Designation.JuniorExecutive;
		caller = c;
	}

	public void setHandler(Employee e) {
		handler = e;
	}

	public void reply(String message) {
		LOG.info(message);
	}

	public Designation getEmpType() {
		return empType;
	}

	public void setEmpType(Designation empType) {
		this.empType = empType;
	}

	public Designation incrementDesignation() {
		if (empType == Designation.JuniorExecutive) {
			empType = Designation.SeniorExecutive;
		} else if (empType == Designation.SeniorExecutive) {
			empType = Designation.ProjectManager;
		}
		return empType;
	}

	public void disconnect() {
		reply("thanks for calling");
	}
}

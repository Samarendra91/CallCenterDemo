package com.targetcorp.callccenter.sevice;

import java.util.ArrayList;
import java.util.Optional;

import com.targetcorp.callccenter.model.Caller;
import com.targetcorp.callccenter.model.Designation;
import com.targetcorp.callccenter.model.Employee;
import com.targetcorp.callccenter.model.JuniorExecutive;
import com.targetcorp.callccenter.model.ProjectManager;
import com.targetcorp.callccenter.model.SeniorExecutive;

public class CallHandler {
	private static CallHandler instance;
	private final int LEVELS = 3;
	private final int NUM_JES = 8;
	private final int NUM_SES = 4;
	private final int NUM_PMS = 2;
	// list of employees by level
	ArrayList<ArrayList<Employee>> employeeLevels;
	ArrayList<ArrayList<Call>> callQueues;
	private boolean isHandled;
	
	public static CallHandler getInstance() {
		if (instance == null) {
			instance = new CallHandler();
		}
		return instance;
	}

	public CallHandler() {
		employeeLevels = new ArrayList<ArrayList<Employee>>(LEVELS);
		callQueues = new ArrayList<ArrayList<Call>>(LEVELS);

		ArrayList<Employee> juniorExecutives = new ArrayList<Employee>(NUM_JES);
		for (int i = 0; i < NUM_JES; i++) {
			juniorExecutives.add(new JuniorExecutive());
		}
		employeeLevels.add(juniorExecutives);

		ArrayList<Employee> seniorExecutives = new ArrayList<Employee>(NUM_SES);
		seniorExecutives.add(new SeniorExecutive());
		employeeLevels.add(seniorExecutives);

		ArrayList<Employee> manager = new ArrayList<Employee>(NUM_PMS);
		manager.add(new ProjectManager());
		employeeLevels.add(manager);
	}

	public boolean assignCall(Employee emp) {
		// get the highest rank this employee can serve
		for (int rank = emp.getRank().getValue(); rank >= 0; rank--) {
			ArrayList<Call> queue = callQueues.get(rank);

			if (queue.size() > 0) {
				Call call = queue.remove(0);
				if (call != null) {
					// receive call
					return true;
				}
			}
		}
		return false;
	}

	public Optional<Employee> setHandlerForCall(Call call) {
		
		switch (call.getEmpType().getValue()) {
		
		/* check all junior executives */
		case 0: 
		for (Employee junior : employeeLevels.get(0)) {
			if (junior.isFree()) {
				return Optional.of(junior);
			}
		}
		
		/* check all senior executives */
		case 1:
		for (Employee senior : employeeLevels.get(1)) {
			if (senior.isFree())
				return Optional.of(senior);
		}
		
		/* check all managers */
		case 2:
		for (Employee manager : employeeLevels.get(2)) {
			if (manager.isFree())
				return Optional.of(manager);
		}
		
		// No one is free
		default:
		return Optional.empty();
		}
		
	}

	/*
	 * public Optional<Employee> setHandlerForCall(Call call) { for (int level =
	 * call.getRank().getValue(); level < LEVELS; level++) { ArrayList<Employee>
	 * employeeLevel = employeeLevels.get(level); for (Employee e : employeeLevel) {
	 * if (e.isFree()) return e; } } return null; }
	 */
	public void dispatchCall(Call call) {
		Optional<Employee> handler = setHandlerForCall(call);
		
		if (handler.isPresent()) {
			Employee executive = handler.get();
			call.setHandler(executive);
			isHandled = executive.receiveAndHandleCall(call);
			if(isHandled) {
				executive.assignNewCall();
			}else {
				executive.escalateAndReassign();
			}
		} else {
			call.reply("please wait for the next free employee");
			callQueues.get(call.getEmpType().getValue()).add(call);
		}
	}

	public void dispatchCaller(Caller caller) {
		Call call = new Call(caller);
		dispatchCall(call);
	}
}

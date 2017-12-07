package com.targetcorp.callccenter.model;

import java.util.Timer;
import java.util.TimerTask;

import com.targetcorp.callccenter.sevice.Call;
import com.targetcorp.callccenter.sevice.CallHandler;

public class Employee {
	
	protected Call currentCall = null;  
    protected Designation empType;  
    
    public Employee () {  
    
    }  
    
    public boolean receiveAndHandleCall(Call call) {  
      currentCall = call;  
      int minutes = 7;
      minutes = (call.getEmpType().getValue() == 0) ? 7 : ((call.getEmpType().getValue() == 1) ? 10 : 15);
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
    	  @Override
    	  public void run() { // Function runs every MINUTES minutes.
    		  // Run the code you want here
    		  escalateAndReassign(); // If the function you wanted was static
    	  }
      }, 0, 1000 * 60 * minutes);

      return false;
    }  
    
	public void callFinished() {  
      if (currentCall != null) {  
        currentCall.disconnect();  
        currentCall = null;  
      }  
      assignNewCall();  
    }  
    
    public void escalateAndReassign() {  
      if (currentCall != null) {  
        currentCall.incrementDesignation();  
        //assign the current call  
        CallHandler.getInstance().dispatchCall(currentCall);  
        currentCall = null;  
      }  
      assignNewCall();  
    }  
    
    public boolean assignNewCall() {  
      if(!isFree()) {  
        return false;  
      }  
      return CallHandler.getInstance().assignCall(this);  
    }  
    
    public boolean isFree() {  
      return currentCall == null;  
    }  
    
    public Designation getEmpType() {  
      return empType;  
    }  

}

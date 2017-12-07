package com.targetcorp.callccenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.targetcorp.callccenter.model.Caller;
import com.targetcorp.callccenter.sevice.Call;
import com.targetcorp.callccenter.sevice.CallHandler;

@SpringBootApplication
public class CallCenterDemoApplication implements CommandLineRunner {

	
	CallHandler callHandler = CallHandler.getInstance(); 
	
	public static void main(String[] args) {
		SpringApplication.run(CallCenterDemoApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Caller caller = new Caller(1234567, "dev");
		callHandler.dispatchCaller(caller);
	}
}

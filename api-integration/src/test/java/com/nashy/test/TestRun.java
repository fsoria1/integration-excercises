package com.nashy.test;

import Model.PtsV2PaymentsCapturesPost201Response;
import Model.PtsV2PaymentsPost201Response;

import com.nashy.integration.*;

public class TestRun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Francisco's integration tests are starting....");
		
		/*
		 
1.       One authorization transaction and one capture a transaction in two different calls (two different requests)
	SimpleAuthorizationInternet
	public static boolean userCapture = false; //no capture
	  */
		SimpleAuthorizationInternet.userCapture = false;
		PtsV2PaymentsPost201Response paymentResponse = SimpleAuthorizationInternet.run();
		String authorizationId = paymentResponse.getId();
		System.out.println("Test 1.1 One authorization transaction");
		
		System.out.println("Test 1.2 capture transaction");
		CapturePayment.run(authorizationId);
		


		/*
2.       One authorization transaction and one capture a transaction in one single call (one single request)
	public static boolean userCapture = true; //capture
	    */
		System.out.println("Test 2 One authorization transaction and one capture a transaction in one single call (one single request)");
		SimpleAuthorizationInternet.userCapture = true;
		paymentResponse = SimpleAuthorizationInternet.run();
				
		/*
3.       One authorization reversal
        */
		System.out.println("Test 3 One authorization reversal");
		SimpleAuthorizationInternet.userCapture = false;
		paymentResponse = SimpleAuthorizationInternet.run();
		ProcessAuthorizationReversal.run(paymentResponse.getId());
		
		
		/*
4.       One authorization credit (refund)
        */
		RefundPayment.run();

		/*
5.       Trigger one decision manager rejection rule, rejecting transactions using the email address assessment@reject.com.br 
		 */
		
		//1. Add to fraud list
		//AddDataToList.run();
		
		//Decision Manager
		DMWithBuyerInformation.run();
	}

}

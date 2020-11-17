This is Francisco Soria's integration exercise, an integration with a Cybersource API.


1.       One authorization transaction and one capture a transaction in two different calls (two different requests)
	SimpleAuthorizationInternet
	public static boolean userCapture = false; //no capture
	

2.       One authorization transaction and one capture a transaction in one single call (one single request)
	public static boolean userCapture = true; //capture

3.       One authorization reversal


4.       One authorization credit (refund)

5.       Trigger one decision manager rejection rule, rejecting transactions using the email address assessment@reject.com.br 
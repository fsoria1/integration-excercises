package com.nashy.integration;

import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;
import com.google.common.base.Strings;
import Api.*;
import Invokers.ApiClient; 
import Invokers.ApiException;
import Model.*;
import com.nashy.data.Configuration;


public class SimpleAuthorizationInternet {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	public static boolean userCapture = false; //no capture
	//public static boolean userCapture = true; //capture
	
	public static void main(String args[]) throws Exception {
		run();
	}

	public static PtsV2PaymentsPost201Response run() {
		//System.out.println("PtsV2PaymentsPost201Response run() starting!");
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.capture(false);
		System.out.println("userCapture is set to: "+userCapture);
		if (userCapture) {
			//System.out.println("Processing Capture");
			processingInformation.capture(true);
		}
		
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("4111111111111111");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2031");
		paymentInformation.card(paymentInformationCard);
		
		requestObj.paymentInformation(paymentInformation);
		
		//System.out.println("Added card");

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("11.21");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);
		
		//System.out.println("Added order info amounts");

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("Pancho");
		orderInformationBillTo.lastName("Villa");
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.locality("san francisco");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.postalCode("94105");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("assessment@reject.com.br");
		orderInformationBillTo.phoneNumber("4158880000");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);
		
		//System.out.println("Added order info bill to");

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createPayment(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			//System.out.println("result.getId(): "+result.getId());
			//System.out.println("ResponseCode :" + responseCode);
			//System.out.println("ResponseMessage :" + status);
			//System.out.println(result);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}

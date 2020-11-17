package com.nashy.integration;

import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import com.nashy.data.*;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.nashy.integration.*;

public class ProcessAuthorizationReversal {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run(args[0]);
	}

	public static PtsV2PaymentsReversalsPost201Response run(String id) {
		//PtsV2PaymentsPost201Response paymentResponse = SimpleAuthorizationInternet.run();
		//String id = paymentResponse.getId();
	
		AuthReversalRequest requestObj = new AuthReversalRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsidreversalsClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidreversalsReversalInformation reversalInformation = new Ptsv2paymentsidreversalsReversalInformation();
		Ptsv2paymentsidreversalsReversalInformationAmountDetails reversalInformationAmountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
		reversalInformationAmountDetails.totalAmount("11.21");
		reversalInformation.amountDetails(reversalInformationAmountDetails);

		reversalInformation.reason("testing");
		requestObj.reversalInformation(reversalInformation);

		PtsV2PaymentsReversalsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ReversalApi apiInstance = new ReversalApi(apiClient);
			result = apiInstance.authReversal(id, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}

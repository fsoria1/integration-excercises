package com.nashy.integration;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import com.nashy.data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class DMWithMerchantDefinedInformation {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static RiskV1DecisionsPost201Response run() {
	
		CreateBundledDecisionManagerCaseRequest requestObj = new CreateBundledDecisionManagerCaseRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("54323007");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1decisionsPaymentInformation paymentInformation = new Riskv1decisionsPaymentInformation();
		Riskv1decisionsPaymentInformationCard paymentInformationCard = new Riskv1decisionsPaymentInformationCard();
		paymentInformationCard.number("4444444444444448");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2020");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Riskv1decisionsOrderInformation orderInformation = new Riskv1decisionsOrderInformation();
		Riskv1decisionsOrderInformationAmountDetails orderInformationAmountDetails = new Riskv1decisionsOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.totalAmount("144.14");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Riskv1decisionsOrderInformationBillTo orderInformationBillTo = new Riskv1decisionsOrderInformationBillTo();
		orderInformationBillTo.address1("96, powers street");
		orderInformationBillTo.administrativeArea("NH");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("Clearwater milford");
		orderInformationBillTo.firstName("James");
		orderInformationBillTo.lastName("Smith");
		orderInformationBillTo.phoneNumber("7606160717");
		orderInformationBillTo.email("test@visa.com");
		orderInformationBillTo.postalCode("03055");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);


		List <Riskv1decisionsMerchantDefinedInformation> merchantDefinedInformation =  new ArrayList <Riskv1decisionsMerchantDefinedInformation>();
		Riskv1decisionsMerchantDefinedInformation merchantDefinedInformation1 = new Riskv1decisionsMerchantDefinedInformation();
		merchantDefinedInformation1.key("1");
		merchantDefinedInformation1.value("Test");
		merchantDefinedInformation.add(merchantDefinedInformation1);

		Riskv1decisionsMerchantDefinedInformation merchantDefinedInformation2 = new Riskv1decisionsMerchantDefinedInformation();
		merchantDefinedInformation2.key("2");
		merchantDefinedInformation2.value("Test2");
		merchantDefinedInformation.add(merchantDefinedInformation2);

		requestObj.merchantDefinedInformation(merchantDefinedInformation);

		RiskV1DecisionsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			DecisionManagerApi apiInstance = new DecisionManagerApi(apiClient);
			result = apiInstance.createBundledDecisionManagerCase(requestObj);

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

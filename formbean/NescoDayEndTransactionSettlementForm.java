package com.adminpanel.merchantadminpanel.nesco.formbean;
import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;


public class NescoDayEndTransactionSettlementForm extends ValidatorActionForm {

	
	private String sReferenceId="";
	public String getsReferenceId() {
		return sReferenceId;
	}

	public void setsReferenceId(String sReferenceId) {
		this.sReferenceId = sReferenceId;
	}
	
	private ArrayList aUserIDGeneratedList = new ArrayList();
	public ArrayList getUserIDGeneratedList() {
		return aUserIDGeneratedList;
	}

	public void setUserIDGeneratedList(ArrayList userIDGeneratedList) {
		this.aUserIDGeneratedList = userIDGeneratedList;
	}
	
	private String sBillNo="";
	private String sAmount="";
	private String sVat="";
	private String sDueDate="";
	private String sLpc="";
	private String sTotalBill="";
	private String sComission="";
	private String sComissionOnVat="";
	private String sRevenuestamp="";
	private String sAgentMsg="";
	private String sCreateBy="";
	private String sTotalBillWithoutVat="";
	private String sTotalVat="";
	private String sTotalRevenueStamp="";
	private String sDayendRefId1="";
	private String sDayendRefId2="";
	private String RESPONSE_CODE="";
	private String RESPONSE_MSG="";
	private String REF_NO="";
	private String AgentDocNum="";
	private String NescoResMsg="";
	
	public String getAgentDocNum() {
		return AgentDocNum;
	}

	public void setAgentDocNum(String agentDocNum) {
		AgentDocNum = agentDocNum;
	}

	public String getNescoResMsg() {
		return NescoResMsg;
	}

	public void setNescoResMsg(String nescoResMsg) {
		NescoResMsg = nescoResMsg;
	}
	
	public String getRESPONSE_CODE() {
		return RESPONSE_CODE;
	}

	public void setRESPONSE_CODE(String rESPONSE_CODE) {
		RESPONSE_CODE = rESPONSE_CODE;
	}

	public String getRESPONSE_MSG() {
		return RESPONSE_MSG;
	}

	public void setRESPONSE_MSG(String rESPONSE_MSG) {
		RESPONSE_MSG = rESPONSE_MSG;
	}

	public String getREF_NO() {
		return REF_NO;
	}

	public void setREF_NO(String rEF_NO) {
		REF_NO = rEF_NO;
	}
	
	public String getsDayendRefId1() {
		return sDayendRefId1;
	}

	public void setsDayendRefId1(String sDayendRefId1) {
		this.sDayendRefId1 = sDayendRefId1;
	}

	public String getsDayendRefId2() {
		return sDayendRefId2;
	}

	public void setsDayendRefId2(String sDayendRefId2) {
		this.sDayendRefId2 = sDayendRefId2;
	}
	
	public String getsTotalBillWithoutVat() {
		return sTotalBillWithoutVat;
	}

	public void setsTotalBillWithoutVat(String sTotalBillWithoutVat) {
		this.sTotalBillWithoutVat = sTotalBillWithoutVat;
	}
	
	public String getsTotalVat() {
		return sTotalVat;
	}

	public void setsTotalVat(String sTotalVat) {
		this.sTotalVat = sTotalVat;
	}

	public String getsTotalRevenueStamp() {
		return sTotalRevenueStamp;
	}

	public void setsTotalRevenueStamp(String sTotalRevenueStamp) {
		this.sTotalRevenueStamp = sTotalRevenueStamp;
	}

	
	
	
	
	
	
	
	
	
	private String sDebitAcc="";
	public String getsDebitAcc() {
		return sDebitAcc;
	}

	public void setsDebitAcc(String sDebitAcc) {
		this.sDebitAcc = sDebitAcc;
	}

	public String getsBillNo() {
		return sBillNo;
	}

	public void setsBillNo(String sBillNo) {
		this.sBillNo = sBillNo;
	}

	public String getsAmount() {
		return sAmount;
	}

	public void setsAmount(String sAmount) {
		this.sAmount = sAmount;
	}

	public String getsVat() {
		return sVat;
	}

	public void setsVat(String sVat) {
		this.sVat = sVat;
	}

	public String getsDueDate() {
		return sDueDate;
	}

	public void setsDueDate(String sDueDate) {
		this.sDueDate = sDueDate;
	}

	public String getsLpc() {
		return sLpc;
	}

	public void setsLpc(String sLpc) {
		this.sLpc = sLpc;
	}

	public String getsTotalBill() {
		return sTotalBill;
	}

	public void setsTotalBill(String sTotalBill) {
		this.sTotalBill = sTotalBill;
	}

	public String getsComission() {
		return sComission;
	}

	public void setsComission(String sComission) {
		this.sComission = sComission;
	}

	public String getsComissionOnVat() {
		return sComissionOnVat;
	}

	public void setsComissionOnVat(String sComissionOnVat) {
		this.sComissionOnVat = sComissionOnVat;
	}

	public String getsRevenuestamp() {
		return sRevenuestamp;
	}

	public void setsRevenuestamp(String sRevenuestamp) {
		this.sRevenuestamp = sRevenuestamp;
	}

	public String getsCreateBy() {
		return sCreateBy;
	}

	public void setsCreateBy(String sCreateBy) {
		this.sCreateBy = sCreateBy;
	}
	
	public String getsAgentMsg() {
		return sAgentMsg;
	}

	public void setsAgentMsg(String sAgentMsg) {
		this.sAgentMsg = sAgentMsg;
	}
	private String sTransactionNo="";
	private String sOnlineID="";
	private String sCardTranID="";
	private String sSerialNo = "";
	private String sErrorCode = "";
	private String sErrorMessage = "";
	private ArrayList aList = new ArrayList();
	private String sMenu = "";
	private ArrayList aMenuList = new ArrayList();
	private ArrayList aMenuNameList = new ArrayList();


	private String sUserStatus = "";
	private ArrayList aUserStatusList = new ArrayList();
	private ArrayList aUserStatusNameList = new ArrayList();	 

	private String sFromDate = "";
	private String sToDate = "";
	private String sFile = "";



	public String getFile() {
		return sFile;
	}

	public void setFile(String file) {
		this.sFile = file;
	}
	public String getTransactionNo() {
		return sTransactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.sTransactionNo = transactionNo;
	}

	public String getOnlineID() {
		return sOnlineID;
	}

	public void setOnlineID(String onlineID) {
		this.sOnlineID = onlineID;
	}

	public String getCardTranID() {
		return sCardTranID;
	}

	public void setCardTranID(String cardTranID) {
		this.sCardTranID = cardTranID;
	}

	private String sOperationID = "";
	/**
	 * @return the sApprovedBy
	 */

	public String getOperationID() {
		return sOperationID;
	}

	public void setOperationID(String operationID) {
		this.sOperationID = operationID;
	}

	/**
	 * @return the sSerialNo
	 */
	public String getSerialNo() {
		return sSerialNo;
	}

	/**
	 * @param sSerialNo the sSerialNo to set
	 */
	public void setSerialNo(String sSerialNo) {
		this.sSerialNo = sSerialNo;
	}

	/**
	 * @return the sErrorCode
	 */
	public String getErrorCode() {
		return sErrorCode;
	}

	/**
	 * @param sErrorCode the sErrorCode to set
	 */
	public void setErrorCode(String sErrorCode) {
		this.sErrorCode = sErrorCode;
	}

	/**
	 * @return the sErrorMessage
	 */
	public String getErrorMessage() {
		return sErrorMessage;
	}

	/**
	 * @param sErrorMessage the sErrorMessage to set
	 */
	public void setErrorMessage(String sErrorMessage) {
		this.sErrorMessage = sErrorMessage;
	}

	/**
	 * @return the aList
	 */
	public ArrayList getList() {
		return aList;
	}

	/**
	 * @param aList the aList to set
	 */
	public void setList(ArrayList aList) {
		this.aList = aList;
	}

	/**
	 * @return the sMenu
	 */
	public String getMenu() {
		return sMenu;
	}

	/**
	 * @param sMenu the sMenu to set
	 */
	public void setMenu(String sMenu) {
		this.sMenu = sMenu;
	}

	/**
	 * @return the aMenuList
	 */
	public ArrayList getMenuList() {
		return aMenuList;
	}

	/**
	 * @param aMenuList the aMenuList to set
	 */
	public void setMenuList(ArrayList aMenuList) {
		this.aMenuList = aMenuList;
	}

	/**
	 * @return the aMenuNameList
	 */
	public ArrayList getMenuNameList() {
		return aMenuNameList;
	}

	/**
	 * @param aMenuNameList the aMenuNameList to set
	 */
	public void setMenuNameList(ArrayList aMenuNameList) {
		this.aMenuNameList = aMenuNameList;
	}

	/**
	 * @return
	 */
	public String getFromDate() {
		return sFromDate;
	}

	/**
	 * @param fromDate
	 */
	public void setFromDate(String fromDate) {
		sFromDate = fromDate;
	}

	/**
	 * @return
	 */
	public String getToDate() {
		return sToDate;
	}

	/**
	 * @param toDate
	 */
	public void setToDate(String toDate) {
		sToDate = toDate;
	}
	/**
	 * @return
	 */
	public ArrayList getUserStatusNameList() {
		return aUserStatusNameList;
	}

	/**
	 * @param userStatusNameList
	 */
	public void setUserStatusNameList(ArrayList userStatusNameList) {
		aUserStatusNameList = userStatusNameList;
	}

	/**
	 * @return
	 */
	public String getUserStatus() {
		return sUserStatus;
	}

	/**
	 * @param userStatus
	 */
	public void setUserStatus(String userStatus) {
		sUserStatus = userStatus;
	}

	public ArrayList getUserStatusList() {
		return aUserStatusList;
	}

	public void setUserStatusList(ArrayList aUserStatusList) {
		this.aUserStatusList = aUserStatusList;
	}

}



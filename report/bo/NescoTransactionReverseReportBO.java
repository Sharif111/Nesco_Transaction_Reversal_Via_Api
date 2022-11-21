package com.adminpanel.merchantadminpanel.nesco.report.bo;
import java.io.Serializable;
import java.util.ArrayList;

public class NescoTransactionReverseReportBO implements Serializable {

	private String sReferenceId = "";
	private String sBillNo = "";
	private String sAmount = "";
	private String sVat = "";
	private String sDueDate = "";
	private String sLpc = "";
	private String sComission = "";
	private String sComissionOnVat = "";
	private String sRevenuestamp = "";
	private String sAgentMsg = "";
	private String sCreateBy = "";
	private String sTotalBill = "";
	private String sTotalBillWithoutVat = "";
	private String sTotalVat = "";
	private String sTotalRevenueStamp = "";
	private String sDayendRefId1 = "";
	private String sDayendRefId2 = "";
	private String RESPONSE_CODE = "";
	private String RESPONSE_MSG = "";
	private String REF_NO = "";
	private String AgentDocNum = "";
	private String NescoResMsg = "";
	private String Operation = "";
	private String AgentRefNo = "";
	private String CbsRefNo = "";
	private String DayendRefNo = "";
	private String AgentRevCode = "";
	private String AgentRevMsg = "";

	private String mailId = "";
	private String sessionId = "";
	
	
    private String agentApiUser = "";
    private String agentApiPass = "";
    private String agentApiUrl = "";
    private String cbsUserId = "";
    private String cbsAuthKey = "";
    private String cbsChkSum = "";
    private String cbsRequestType = "";
     private String agentApiSecrtekey = "";
    private String agentApiSaltkey = "";
    private String agentReverseUrl = "";
    
    private String agentReveseRequestNo = "";
    
    private String sSl = "";

    
    
	public String getsSl() {
		return sSl;
	}

	public void setsSl(String sSl) {
		this.sSl = sSl;
	}

	public String getAgentReveseRequestNo() {
		return agentReveseRequestNo;
	}

	public void setAgentReveseRequestNo(String agentReveseRequestNo) {
		this.agentReveseRequestNo = agentReveseRequestNo;
	}

	public String getCbsUserId() {
		return cbsUserId;
	}

	public void setCbsUserId(String cbsUserId) {
		this.cbsUserId = cbsUserId;
	}

	public String getCbsAuthKey() {
		return cbsAuthKey;
	}

	public void setCbsAuthKey(String cbsAuthKey) {
		this.cbsAuthKey = cbsAuthKey;
	}

	public String getCbsChkSum() {
		return cbsChkSum;
	}

	public void setCbsChkSum(String cbsChkSum) {
		this.cbsChkSum = cbsChkSum;
	}

	public String getCbsRequestType() {
		return cbsRequestType;
	}

	public void setCbsRequestType(String cbsRequestType) {
		this.cbsRequestType = cbsRequestType;
	}

	public String getAgentApiUser() {
		return agentApiUser;
	}

	public void setAgentApiUser(String agentApiUser) {
		this.agentApiUser = agentApiUser;
	}

	public String getAgentApiPass() {
		return agentApiPass;
	}

	public void setAgentApiPass(String agentApiPass) {
		this.agentApiPass = agentApiPass;
	}

	public String getAgentApiUrl() {
		return agentApiUrl;
	}

	public void setAgentApiUrl(String agentApiUrl) {
		this.agentApiUrl = agentApiUrl;
	}

	public String getAgentApiSecrtekey() {
		return agentApiSecrtekey;
	}

	public void setAgentApiSecrtekey(String agentApiSecrtekey) {
		this.agentApiSecrtekey = agentApiSecrtekey;
	}

	public String getAgentApiSaltkey() {
		return agentApiSaltkey;
	}

	public void setAgentApiSaltkey(String agentApiSaltkey) {
		this.agentApiSaltkey = agentApiSaltkey;
	}

	public String getAgentReverseUrl() {
		return agentReverseUrl;
	}

	public void setAgentReverseUrl(String agentReverseUrl) {
		this.agentReverseUrl = agentReverseUrl;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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

	public String getAgentRevCode() {
		return AgentRevCode;
	}

	public void setAgentRevCode(String agentRevCode) {
		AgentRevCode = agentRevCode;
	}

	public String getAgentRevMsg() {
		return AgentRevMsg;
	}

	public void setAgentRevMsg(String agentRevMsg) {
		AgentRevMsg = agentRevMsg;
	}

	public String getAgentRefNo() {
		return AgentRefNo;
	}

	public void setAgentRefNo(String agentRefNo) {
		AgentRefNo = agentRefNo;
	}

	public String getCbsRefNo() {
		return CbsRefNo;
	}

	public void setCbsRefNo(String cbsRefNo) {
		CbsRefNo = cbsRefNo;
	}

	public String getDayendRefNo() {
		return DayendRefNo;
	}

	public void setDayendRefNo(String dayendRefNo) {
		DayendRefNo = dayendRefNo;
	}

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String operation) {
		Operation = operation;
	}

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

	private String sDebitAcc = "";

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

	private String sTransactionNo = "";
	private String sOnlineID = "";
	private String sCardTranID = "";
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


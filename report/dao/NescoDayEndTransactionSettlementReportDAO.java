package com.adminpanel.merchantadminpanel.nesco.report.dao;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.adminpanel.dbconnection.application.DBCPNewConnection;
import com.adminpanel.merchantadminpanel.nesco.report.bo.NescoDayEndTransactionSettlementReportBO;

import api.ApiResponse;
import cbsdayendclient.CbsDayendClient;





public class NescoDayEndTransactionSettlementReportDAO {
	
	
	
	SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public NescoDayEndTransactionSettlementReportBO getPermissionCheckPro(	String sUserID,
			String sSessionID,
			String sCompanyID,
			String sBranchID,
			String sIPAddress,
			String sBranchActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoDayEndTransactionSettlementReportBO oStatusReportofNPSBOutgoingBO = new NescoDayEndTransactionSettlementReportBO();
		CallableStatement oStmt =oConn.prepareCall("{call MyBank.DPR_MYBANK_BANK_USER_CHECK(?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sBranchID);
		oStmt.setString(5, sIPAddress);
		oStmt.setString(6, sBranchActionPath);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		try {
			oStmt.execute();
			oStatusReportofNPSBOutgoingBO.setErrorCode("" + oStmt.getInt(7));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oStatusReportofNPSBOutgoingBO;
	}



	public NescoDayEndTransactionSettlementReportBO getMenuCheckPro( String sUserID,
			String sSessionID,
			String sCompanyID,
			String sBranchID,
			String sRemoteIPAddress,
			String sActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoDayEndTransactionSettlementReportBO oStatusReportofNPSBOutgoingBO = new NescoDayEndTransactionSettlementReportBO();
		CallableStatement oStmt =oConn.prepareCall("{call ibmm.dpr_ibk_main_menu_check(?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sCompanyID);
		oStmt.setString(4, sBranchID);
		oStmt.setString(5, sRemoteIPAddress);
		oStmt.setString(6, sActionPath);
		oStmt.registerOutParameter(7, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oStatusReportofNPSBOutgoingBO.setErrorCode("" + oStmt.getInt(7));
			oStatusReportofNPSBOutgoingBO.setErrorMessage(oStmt.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {

			DBCPNewConnection.releaseConnection(oConn);
		}
		return oStatusReportofNPSBOutgoingBO;
	}


	public NescoDayEndTransactionSettlementReportBO getMenuList( String sUserID,
			String sSessionID)		throws Exception {

		NescoDayEndTransactionSettlementReportBO oStatusReportofNPSBOutgoingBO = new NescoDayEndTransactionSettlementReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ArrayList aMenuList = new ArrayList();
		ArrayList aMenuNameList = new ArrayList();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT NVL(URL,' '), NVL(NODENAME,' ')");
			sql.append("FROM MyBank.SY_FAVORITES ");
			sql.append("WHERE MAILID = '");
			sql.append(sUserID);
			sql.append("' AND SESSIONID = '");
			sql.append(sSessionID);
			sql.append("' ORDER BY SERNUM ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				aMenuList.add(oRs.getString(1));
				aMenuNameList.add(oRs.getString(2));
			}
			oStatusReportofNPSBOutgoingBO.setMenuList(aMenuList);
			oStatusReportofNPSBOutgoingBO.setMenuNameList(aMenuNameList);
		} catch (Exception sq) {
			sq.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			if (oRs != null) {
				oRs.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oStatusReportofNPSBOutgoingBO;
	}
	public NescoDayEndTransactionSettlementReportBO getStatusListData( String sUserID,String sSessionID)		throws Exception {

		NescoDayEndTransactionSettlementReportBO oStatusReportofNPSBOutgoingBO = new NescoDayEndTransactionSettlementReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();
		ArrayList aStatusList = new ArrayList();
		ArrayList aStatusNameList = new ArrayList();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql = new StringBuffer();
			sql.append("SELECT ACTYPE,ACDESC from  LK_PARAMETER WHERE ACTCOD = 'NESCO_BILL_SATATUS' AND ACTFLG = 'Y' order by SERNUM ASC ");
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {
				aStatusList.add(oRs.getString(1));
				aStatusNameList.add(oRs.getString(2));
			}
			oStatusReportofNPSBOutgoingBO.setUserStatusList(aStatusList);
			oStatusReportofNPSBOutgoingBO.setUserStatusNameList(aStatusNameList);
		} catch (Exception sq) {
			sq.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			if (oRs != null) {
				oRs.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oStatusReportofNPSBOutgoingBO;
	}
	public NescoDayEndTransactionSettlementReportBO getNescoDataPro( 
			String sUserID,
			String sSessionID,
			String sToDate,
			String ALL
			)	throws Exception {

		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoDayEndTransactionSettlementReportBO oPassportTransactionListBO = new NescoDayEndTransactionSettlementReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBMM.dpr_mm_nesco_bill_inf (?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sToDate);
		oStmt.setString(4, ALL);
		oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oPassportTransactionListBO.setErrorCode("" + oStmt.getInt(5));
			oPassportTransactionListBO.setErrorMessage(oStmt.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oPassportTransactionListBO;
	}
	
	public NescoDayEndTransactionSettlementReportBO getExecutePro( 
			String sUserID,
			String sSessionID,
			String sToDate,
			String sStatus
			)	throws Exception {

		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoDayEndTransactionSettlementReportBO oPassportTransactionListBO = new NescoDayEndTransactionSettlementReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBMM.dpr_mm_nesco_dayend_execute (?,?,?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sToDate);
		oStmt.setString(4, sStatus);
		oStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
		oStmt.registerOutParameter(10, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
		try {
			oStmt.execute();
			oPassportTransactionListBO.setsTotalBill(oStmt.getString(5));
			System.out.println("TotalBill===>" + oPassportTransactionListBO.getsTotalBill());
			oPassportTransactionListBO.setsTotalBillWithoutVat(oStmt.getString(6));
			System.out.println("TotalBillWithoutVat===>" + oPassportTransactionListBO.getsTotalBillWithoutVat());
			oPassportTransactionListBO.setsTotalVat(oStmt.getString(7));
			System.out.println("TotalVat===>" + oPassportTransactionListBO.getsTotalVat());
			oPassportTransactionListBO.setsTotalRevenueStamp(oStmt.getString(8));
			System.out.println("TotalRevenueStamp===>" + oPassportTransactionListBO.getsTotalRevenueStamp());
			oPassportTransactionListBO.setsDayendRefId1(oStmt.getString(9));
			System.out.println("DayendRefId1===>" + oPassportTransactionListBO.getsDayendRefId1());
//			oPassportTransactionListBO.setsDayendRefId2(oStmt.getString(10));
//			System.out.println("DayendRefId2===>" + oPassportTransactionListBO.getsDayendRefId2());		
			oPassportTransactionListBO.setErrorCode("" + oStmt.getInt(10));
			oPassportTransactionListBO.setErrorMessage(oStmt.getString(11));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oPassportTransactionListBO;
	}
	
	
	public NescoDayEndTransactionSettlementReportBO NescoDataUpdatePro( 
			String sUserID,
			String sSessionID,
			String sToDate,
			String sStatus,
			String sDayendRefId1,			
            String RESPONSE_CODE,
            String RESPONSE_MSG,
            String REF_NO
			)	throws Exception {
		
		System.out.println("Status--->"+sStatus);
		System.out.println("DayendRefId1--->"+sDayendRefId1);
		System.out.println("RESPONSE_CODE--->"+RESPONSE_CODE);
		System.out.println("RESPONSE_MSG--->"+RESPONSE_MSG);
		System.out.println("REF_NO--->"+REF_NO);

		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoDayEndTransactionSettlementReportBO oPassportTransactionListBO = new NescoDayEndTransactionSettlementReportBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBMM.dpr_mm_nesco_dayend_execute_update(?,?,?,?,?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sToDate);
		oStmt.setString(4, sStatus);
		oStmt.setString(5, sDayendRefId1);		
		oStmt.setString(6, RESPONSE_CODE);
		oStmt.setString(7, RESPONSE_MSG);
		oStmt.setString(8, REF_NO);
		oStmt.registerOutParameter(9, java.sql.Types.INTEGER);
		oStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
		
		try {
			oStmt.execute();
			oPassportTransactionListBO.setErrorCode("" + oStmt.getInt(9));
			oPassportTransactionListBO.setErrorMessage(oStmt.getString(10));		
			System.out.println("ErrorMessage--->"+oPassportTransactionListBO.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oPassportTransactionListBO;
	}
	
	
	
	 public NescoDayEndTransactionSettlementReportBO getNescoData(String sUserID, String sSessionID)
			    throws Exception
			  {
		 NescoDayEndTransactionSettlementReportBO oNescoDayEndTransactionSettlementReportBO = new NescoDayEndTransactionSettlementReportBO();
			    Connection oConn = null;
			    Statement oStmt = null;
			    oConn = DBCPNewConnection.getConnection();
			    oStmt = oConn.createStatement();
			    ArrayList aCustomerIDList = new ArrayList();
			    ResultSet oRs = null;
			    StringBuffer sql = new StringBuffer();
			    try
			    {
			      sql = new StringBuffer();
			      sql.append("SELECT REFERENCEID,DEBITACCOUNT,NESCOACCOUNT,BILLNO,AMOUNT,VAT,METERNO,CUSTOMERNAME,"		      			     
			    		  + " DUEDATE,LPC,SNDID,TOTALBILL,COMISSION,COMISSIONONVAT, "
						  + " REVENEWSTAMPT,AGENTPAYTMENTMESSAGE,APPVERSION,DAYENDAGENTREFNO,DAYENDCBSTRANSREF,DAYENDREFNO,AGENTDOCNUM,NESCORESPONSE,CREATEBY from DT_NESCO_REQ ");	     
			      sql.append("WHERE USERID  = '");
				  sql.append(sUserID);
				  sql.append("' AND SESSIONID  = '");
				  sql.append(sSessionID);
				  sql.append("' order by CREATETIME desc ");
			      oRs = oStmt.executeQuery(sql.toString());
			     // System.out.println("sql.toString()==> "+ sql.toString());
			      while (oRs.next()) {
			    	  NescoDayEndTransactionSettlementReportBO oUserIDGenerationListBO = new NescoDayEndTransactionSettlementReportBO();
			    	  oUserIDGenerationListBO.setsSl(String.valueOf(oRs.getRow()));
			          oUserIDGenerationListBO.setsReferenceId(oRs.getString("REFERENCEID"));
			          oUserIDGenerationListBO.setsDebitAcc(oRs.getString("DEBITACCOUNT"));			          
			          oUserIDGenerationListBO.setsBillNo(oRs.getString("BILLNO"));
			          oUserIDGenerationListBO.setsAmount(oRs.getString("AMOUNT"));
			          oUserIDGenerationListBO.setsVat(oRs.getString("VAT"));
			          oUserIDGenerationListBO.setsDueDate(oRs.getString("DUEDATE"));
			          oUserIDGenerationListBO.setsLpc(oRs.getString("LPC"));
			          oUserIDGenerationListBO.setsTotalBill(oRs.getString("TOTALBILL"));
			          oUserIDGenerationListBO.setsComission(oRs.getString("COMISSION"));
			          oUserIDGenerationListBO.setsComissionOnVat(oRs.getString("COMISSIONONVAT"));
			          oUserIDGenerationListBO.setsRevenuestamp(oRs.getString("REVENEWSTAMPT"));			          
			          oUserIDGenerationListBO.setAgentDocNum(oRs.getString("AGENTDOCNUM"));
			          oUserIDGenerationListBO.setNescoResMsg(oRs.getString("NESCORESPONSE"));
			          oUserIDGenerationListBO.setAgentRefNo(oRs.getString("DAYENDAGENTREFNO"));
			          oUserIDGenerationListBO.setCbsRefNo(oRs.getString("DAYENDCBSTRANSREF"));
			          oUserIDGenerationListBO.setDayendRefNo(oRs.getString("DAYENDREFNO"));
			          oUserIDGenerationListBO.setsCreateBy(oRs.getString("CREATEBY"));
			          //System.out.println("setsReferenceId()==> "+ oUserIDGenerationListBO.getsReferenceId());
			          aCustomerIDList.add(oUserIDGenerationListBO);
			      }
			      oNescoDayEndTransactionSettlementReportBO.setUserIDGeneratedList(aCustomerIDList);
			    //System.out.println("oNescoDayEndTransactionSettlementReportBO.setUserIDGeneratedList==>"+oNescoDayEndTransactionSettlementReportBO.getUserIDGeneratedList());
			    }
			    catch (Exception sq)
			    {
			      sq.printStackTrace();
			      try
			      {
			        oConn.rollback();
			      }
			      catch (Exception localException1) {}
			    }
			    finally
			    {
			      if (oStmt != null) {
			        oStmt.close();
			      }
			      if (oRs != null) {
			        oRs.close();
			      }
			      DBCPNewConnection.releaseConnection(oConn);
			    }
			    return oNescoDayEndTransactionSettlementReportBO;
			  }

	 
	 public NescoDayEndTransactionSettlementReportBO getNescoTotalData(String sUserID, String sSessionID)
			    throws Exception
			  {
		// NescoDayEndTransactionSettlementReportBO oNescoDayEndTransactionSettlementReportBO = new NescoDayEndTransactionSettlementReportBO();
			    Connection oConn = null;
			    Statement oStmt = null;
			    oConn = DBCPNewConnection.getConnection();
			    oStmt = oConn.createStatement();
			 //   ArrayList aCustomerIDList = new ArrayList();
			    NescoDayEndTransactionSettlementReportBO oUserIDGenerationListBO = new NescoDayEndTransactionSettlementReportBO();
			    ResultSet oRs = null;
			    StringBuffer sql = new StringBuffer();
			    try
			    {
			      sql = new StringBuffer();
			      sql.append("SELECT SUM (AMOUNT) amount,"  
			      	 +	"                   SUM (AMOUNT) - SUM (VAT) amountwithouvat,"  
			      	 +	"                   SUM (VAT) totalvatamount,"  
			      	 +  "                   SUM (REVENEWSTAMPT) totalrevenew FROM DT_NESCO_REQ ");	     
			      sql.append("WHERE USERID  = '");
				  sql.append(sUserID);
				  sql.append("' AND  SESSIONID  = '");
				  sql.append(sSessionID);
				  sql.append("' order by CREATETIME desc ");
			      oRs = oStmt.executeQuery(sql.toString());
			     // System.out.println("sql.toString()==> "+ sql.toString());
			      if (oRs.next()) {
			    	
			          oUserIDGenerationListBO.setsTotalBill(oRs.getString("amount"));
			          oUserIDGenerationListBO.setsTotalBillWithoutVat(oRs.getString("amountwithouvat"));			          
			          oUserIDGenerationListBO.setsTotalVat(oRs.getString("totalvatamount"));
			          oUserIDGenerationListBO.setsTotalRevenueStamp(oRs.getString("totalrevenew"));			          
			         // System.out.println("setsTotalBill()==> "+ oUserIDGenerationListBO.getsTotalBill());
			          //System.out.println("setsTotalBillWithoutVat()==> "+ oUserIDGenerationListBO.getsTotalBillWithoutVat());
			         // System.out.println("setsTotalVat()==> "+ oUserIDGenerationListBO.getsTotalVat());
			         // System.out.println("setsTotalRevenueStamp()==> "+ oUserIDGenerationListBO.getsTotalRevenueStamp());
			         // aCustomerIDList.add(oUserIDGenerationListBO);
			      }
			     // oNescoDayEndTransactionSettlementReportBO.setUserIDGeneratedList(aCustomerIDList);
			    //System.out.println("oNescoDayEndTransactionSettlementReportBO.setUserIDGeneratedList==>"+oNescoDayEndTransactionSettlementReportBO.getUserIDGeneratedList());
			    }
			    catch (Exception sq)
			    {
			      sq.printStackTrace();
			      try
			      {
			        oConn.rollback();
			      }
			      catch (Exception localException1) {}
			    }
			    finally
			    {
			      if (oStmt != null) {
			        oStmt.close();
			      }
			      if (oRs != null) {
			        oRs.close();
			      }
			      DBCPNewConnection.releaseConnection(oConn);
			    }
			    return oUserIDGenerationListBO;
			  }

	public NescoDayEndTransactionSettlementReportBO getStatusType() throws Exception{

		NescoDayEndTransactionSettlementReportBO oFundTransfertoBkashWalletReportListBO = new NescoDayEndTransactionSettlementReportBO();
		Connection oConn = null;
		Statement oStmt = null;
		oConn = DBCPNewConnection.getConnection();
		oStmt = oConn.createStatement();						
		ArrayList aUserActivityHistoryTypeList = new ArrayList();
		ArrayList aUserActivityHistoryNameList = new ArrayList();
		ResultSet oRs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT sernum,ACTYPE, ACDESC ");
			sql.append("FROM IBANKING.LK_PARAMETER ");
			sql.append("WHERE ACTCOD = 'NPS_OUTGOING_STATUS' AND ACTFLG = 'Y' ");		
			sql.append(" order by sernum ");	
			oRs = oStmt.executeQuery(sql.toString());
			while (oRs.next()) {

				aUserActivityHistoryTypeList.add(oRs.getString(2));
				aUserActivityHistoryNameList.add(oRs.getString(3));

			}
			oFundTransfertoBkashWalletReportListBO.setUserStatusList(aUserActivityHistoryTypeList);	
			oFundTransfertoBkashWalletReportListBO.setUserStatusNameList(aUserActivityHistoryNameList);				
		} catch (Exception sq) {
			sq.printStackTrace();
			try {
				oConn.rollback();
			} catch (Exception E) {
			}
		} finally {
			if (oStmt != null) {
				oStmt.close();
			}
			if (oRs != null) {
				oRs.close();
			}
			DBCPNewConnection.releaseConnection(oConn);
		}
		return oFundTransfertoBkashWalletReportListBO;
	}
	
	
	
    public NescoDayEndTransactionSettlementReportBO AgentDayEndRequest(NescoDayEndTransactionSettlementReportBO model) {
        
        
    	NescoDayEndTransactionSettlementReportBO outModel = new NescoDayEndTransactionSettlementReportBO();
        StringBuffer sb = new StringBuffer();

        /**
         * CA -->Party Account BA -->GL
         *
         */
        sb.append("<TRANSACTION>");

        sb.append("<HEADER>");
        sb.append("<USER_ID>IBCORP</USER_ID>");
        sb.append("<USER_PASS>IBCORP</USER_PASS>");
        sb.append("<TOKEN_NO />");
        sb.append("<REQ_NO>"+model.getsDayendRefId1()+"</REQ_NO>");
        sb.append("<REFERENCE1 />");
        sb.append("<REFERENCE2 />");
        sb.append("<REFERENCE3 />");
        sb.append("</HEADER>");

        sb.append("<BODY>");
        //debit
        sb.append("<DEBIT>");
        sb.append("<BRANCH>108</BRANCH>");
        sb.append("<AC_TYPE>BA</AC_TYPE>");
        sb.append("<AC_NO>24100-123</AC_NO>");
        sb.append("<AMOUNT>" + model.getsTotalBill() + "</AMOUNT>");
        sb.append("<NARRATION>debit 24100</NARRATION>");
        sb.append("</DEBIT>");

        
        //Credit 1
        sb.append("<CREDIT>");
        sb.append("<BRANCH>108</BRANCH>");
        sb.append("<AC_TYPE>BA</AC_TYPE>");
        sb.append("<AC_NO>14100-24</AC_NO>");
        sb.append("<AMOUNT>" + model.getsTotalBill() + "</AMOUNT>");
        sb.append("<NARRATION>Credit to 24100-24</NARRATION>");
        sb.append("</CREDIT>");

        
      //debit
        sb.append("<DEBIT>");
        sb.append("<BRANCH>108</BRANCH>");
        sb.append("<AC_TYPE>BA</AC_TYPE>");
        sb.append("<AC_NO>14100-24</AC_NO>");
        sb.append("<AMOUNT>" + model.getsTotalRevenueStamp() + "</AMOUNT>");
        sb.append("<NARRATION>debit revenew</NARRATION>");
        sb.append("</DEBIT>");
        
        //Credit 1
        sb.append("<CREDIT>");
        sb.append("<BRANCH>108</BRANCH>");
        sb.append("<AC_TYPE>BA</AC_TYPE>");
        sb.append("<AC_NO>20850-86</AC_NO>");
        sb.append("<AMOUNT>" + model.getsTotalRevenueStamp() + "</AMOUNT>");
        sb.append("<NARRATION>credit revenew</NARRATION>");
        sb.append("</CREDIT>");

      

        sb.append("</BODY>");

        sb.append("</TRANSACTION>");

        System.out.println("sb = " + sb);

        String url = "http://192.183.155.44:8888/emob/emob/bankasia/agentbanking/transactionv2";
        System.out.println("url = " + url);

        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        org.json.JSONObject jsonObject = new org.json.JSONObject();
      
      try {
    	       jsonObject.put("requestinfo", sb);
    	       
    	       StringEntity params = new StringEntity(String.valueOf(jsonObject));
    	       post.addHeader("content-type", "application/json");
    	       post.setEntity(params);
    	       
        	   CloseableHttpResponse httpResponse = httpClient.execute(post);
               int StatusCode = httpResponse.getStatusLine().getStatusCode();
               System.out.println("StatusCode ======= >>>>>>> " + StatusCode);
               String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
               System.out.println("responseString Bulk Transfer ======= >>>>>>> " + responseString);
              // JSONObject sResponse = new JSONObject(responseString);
               org.json.JSONObject obj = new org.json.JSONObject(responseString);
            String responseinfo = "";
            
            if (obj.has("responseinfo") && !obj.isNull("responseinfo")) {
                responseinfo = (String) obj.get("responseinfo");
            }
            System.out.println("responseinfo = " + responseinfo);
         

          

            if (responseinfo != "") {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseinfo));
                Document doc = db.parse(is);
                NodeList transactionNodeList = doc.getElementsByTagName("TRANSACTION");
                System.out.println("transactionNodeList = " + transactionNodeList);

                String REF_NO = "";
                String RESPONSE_CODE = "";
                String RESPONSE_MSG = "";
                for (int i = 0; i < transactionNodeList.getLength(); i++) {
                	
                    Element element = (Element) transactionNodeList.item(i);

                    if (element.getElementsByTagName("REF_NO").getLength() != 0) {
                        REF_NO = element.getElementsByTagName("REF_NO").item(0).getTextContent();
                    }
                    if (element.getElementsByTagName("RESPONSE_CODE").getLength() != 0) {
                        RESPONSE_CODE = element.getElementsByTagName("RESPONSE_CODE").item(0).getTextContent();
                    }

                    RESPONSE_CODE = element.getElementsByTagName("RESPONSE_CODE").item(0).getTextContent();
                    System.out.println("RESPONSE_CODE = " + RESPONSE_CODE);

                    if (element.getElementsByTagName("RESPONSE_MSG").getLength() != 0) {
                        RESPONSE_MSG = element.getElementsByTagName("RESPONSE_MSG").item(0).getTextContent();
                    }

                    System.out.println("REF_NO = " + REF_NO);
                    System.out.println("RESPONSE_CODE = " + RESPONSE_CODE);
                    System.out.println("RESPONSE_MSG = " + RESPONSE_MSG);
                    
                    if ("000".equals(RESPONSE_CODE)) {

                        outModel.setErrorCode("0");
                        outModel.setErrorMessage(RESPONSE_MSG);
                        outModel.setRESPONSE_CODE(RESPONSE_CODE);
                        outModel.setRESPONSE_MSG(RESPONSE_MSG);
                        outModel.setREF_NO(REF_NO);
                        outModel.setsDayendRefId1(model.getsDayendRefId1());
                        
                       // outModel.setAgentDocNumber(REF_NO);
                        return outModel;
                    } else {
                    	
                        outModel.setErrorCode("1");
                        outModel.setErrorMessage(RESPONSE_MSG);
                        return outModel;
                    }

                }

            } else {
                outModel.setErrorCode("1");
                outModel.setErrorMessage("Agent API Response is not Found");
                return outModel;

            }

        } catch (Exception ex) {
            ex.printStackTrace();

            outModel.setErrorCode("1");
            outModel.setErrorMessage("Error-->" + ex.getMessage());
            model.setErrorCode(ex.getMessage());
            return outModel;
        }
        return outModel;
    }
    
    
    
    public NescoDayEndTransactionSettlementReportBO doCBSApi(
    		NescoDayEndTransactionSettlementReportBO model) {
        System.out.println("CBS Api caling***** ");

        //Begin Api Calling credential
        String inUserId = "eNESCO";
        String inAuthKey = "123456";
        String inChkSum = "FEA0119DA714BAA616EE18F0344CB2E5650CDD5A";
        String inRequestId = "";
        String inBinData = "";
        String inRequestType = "req_transfer";
        String inAddParamOne = "100";
        String inAddParamTwo = "";
        
        NescoDayEndTransactionSettlementReportBO outModel = new NescoDayEndTransactionSettlementReportBO();
        
        //End Api Calling credential
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        String currentdate = formatter.format(date);
  
        inRequestId = model.getsDayendRefId1();

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0'?>");
        sb.append("<REQ_FUND_TRANSFER>");
        sb.append(" <ACCOUNT_DETAIL>");
        //account list
        //Debit 1
        sb.append("<ACCOUNT_LIST>");
        sb.append("<ACCOUNT_NO>14100-108</ACCOUNT_NO>");
        sb.append("<DEBIT_CREDIT>D</DEBIT_CREDIT>");
        sb.append("<BRANCHCODE>024</BRANCHCODE>");
        sb.append("<AMOUNT>" + model.getsTotalBill() + "</AMOUNT>");
        sb.append("<CURRENCY>BDT</CURRENCY>");
        sb.append("<EXCHANGE_RATE>1</EXCHANGE_RATE>");
        sb.append("<CHQSER>Z</CHQSER>");
        sb.append("<CHQDAT>" + currentdate + "</CHQDAT>");
        sb.append("<CHQNUM>123</CHQNUM>");
        sb.append("</ACCOUNT_LIST>");
        //Credit 1
        sb.append("<ACCOUNT_LIST>");
        sb.append("<ACCOUNT_NO>02436000059</ACCOUNT_NO>");
        sb.append("<DEBIT_CREDIT>C</DEBIT_CREDIT>");
        sb.append("<BRANCHCODE>024</BRANCHCODE>");
        sb.append("<AMOUNT>" + model.getsTotalBillWithoutVat() + "</AMOUNT>");
        sb.append("<CURRENCY>BDT</CURRENCY>");
        sb.append("<EXCHANGE_RATE>1</EXCHANGE_RATE>");
        sb.append("<CHQSER>Z</CHQSER>");
        sb.append("<CHQDAT>" + currentdate + "</CHQDAT>");
        sb.append("<CHQNUM>123</CHQNUM>");
        sb.append("</ACCOUNT_LIST>");
        //Credit 2
        sb.append("<ACCOUNT_LIST>");
        sb.append("<ACCOUNT_NO>02433001885</ACCOUNT_NO>");
        sb.append("<DEBIT_CREDIT>C</DEBIT_CREDIT>");
        sb.append("<BRANCHCODE>024</BRANCHCODE>");
        sb.append("<AMOUNT>" + model.getsTotalVat() + "</AMOUNT>");
        sb.append("<CURRENCY>BDT</CURRENCY>");
        sb.append("<EXCHANGE_RATE>1</EXCHANGE_RATE>");
        sb.append("<CHQSER>Z</CHQSER>");
        sb.append("<CHQDAT>" + currentdate + "</CHQDAT>");
        sb.append("<CHQNUM>123</CHQNUM>");
        sb.append("</ACCOUNT_LIST>");
        //Debit 2
        sb.append("<ACCOUNT_LIST>");
        sb.append("<ACCOUNT_NO>02436000059</ACCOUNT_NO>");
        sb.append("<DEBIT_CREDIT>D</DEBIT_CREDIT>");
        sb.append("<BRANCHCODE>024</BRANCHCODE>");
        sb.append("<AMOUNT>" + model.getsTotalRevenueStamp() + "</AMOUNT>");
        sb.append("<CURRENCY>BDT</CURRENCY>");
        sb.append("<EXCHANGE_RATE>1</EXCHANGE_RATE>");
        sb.append("<CHQSER>Z</CHQSER>");
        sb.append("<CHQDAT>" + currentdate + "</CHQDAT>");
        sb.append("<CHQNUM>123</CHQNUM>");
        sb.append("</ACCOUNT_LIST>");
        //Credit 3
        sb.append("<ACCOUNT_LIST>");
        sb.append("<ACCOUNT_NO>14100-108</ACCOUNT_NO>");
        sb.append("<DEBIT_CREDIT>C</DEBIT_CREDIT>");
        sb.append("<BRANCHCODE>024</BRANCHCODE>");
        sb.append("<AMOUNT>" + model.getsTotalRevenueStamp() + "</AMOUNT>");
        sb.append("<CURRENCY>BDT</CURRENCY>");
        sb.append("<EXCHANGE_RATE>1</EXCHANGE_RATE>");
        sb.append("<CHQSER>Z</CHQSER>");
        sb.append("<CHQDAT>" + currentdate + "</CHQDAT>");
        sb.append("<CHQNUM>123</CHQNUM>");
        sb.append("</ACCOUNT_LIST>");

        sb.append("</ACCOUNT_DETAIL>");
        sb.append("<TRN_DATE>" + currentdate + "</TRN_DATE>");
        sb.append("<NARRATION>test</NARRATION>");
        sb.append("<CHANNELID>" + inUserId + "</CHANNELID>");
        sb.append("</REQ_FUND_TRANSFER>");
        System.out.println("sb = " + sb);

        ApiResponse response = CbsDayendClient.dataInsert(
                inUserId,
                inAuthKey,
                inChkSum,
                model.getsDayendRefId1(),
                sb.toString(),
                inBinData,
                inRequestType,
                inAddParamOne,
                inAddParamTwo);
        String out_transaction = response.getOutXMLData();
        System.out.println("out_transaction = " + out_transaction);
        if (out_transaction.isEmpty()) {

            System.out.println("Tranaction is not responding");
            outModel.setErrorCode("1");
            outModel.setErrorMessage("");
            return outModel;
        } else {
            try {

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();

                is.setCharacterStream(new StringReader(response.getOutXMLData().trim()));
                //DOMParser parser = new DOMParser();
                Document doc = db.parse(is);
                NodeList nodeList = doc.getElementsByTagName("QRYRESULT");
                String ISSUCCESS = "";
                String WARNING = "";
                String MESSAGE = "";
                String TRN_REF = "";
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);

                    if (element.getElementsByTagName("ISSUCCESS").getLength() != 0) {
                        ISSUCCESS = element.getElementsByTagName("ISSUCCESS").item(0).getTextContent();
                    }

                    if (element.getElementsByTagName("WARNING").getLength() != 0) {
                        WARNING = element.getElementsByTagName("WARNING").item(0).getTextContent();
                    }

                    if (element.getElementsByTagName("MESSAGE").getLength() != 0) {
                        MESSAGE = element.getElementsByTagName("MESSAGE").item(0).getTextContent();
                    }
                    if (element.getElementsByTagName("TRN_REF").getLength() != 0) {
                        TRN_REF = element.getElementsByTagName("TRN_REF").item(0).getTextContent();
                    }

                    System.out.println("ISSUCCESS = " + ISSUCCESS);
                    System.out.println("WARNING = " + WARNING);
                    System.out.println("MESSAGE = " + MESSAGE);
                    System.out.println("TRN_REF = " + TRN_REF);
                    
                    if ("Y".equals(ISSUCCESS)) {

                        outModel.setErrorCode("0");
                        outModel.setErrorMessage(MESSAGE);
                        outModel.setRESPONSE_CODE(ISSUCCESS);
                        outModel.setRESPONSE_MSG(MESSAGE);
                        outModel.setREF_NO(TRN_REF);
                        
                        
                       // outModel.setAgentDocNumber(REF_NO);
                        return outModel;
                    } else {
                    	
                        outModel.setErrorCode("1");
                        outModel.setErrorMessage(WARNING+" "+MESSAGE);
                        return outModel;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                outModel.setErrorCode("1");
                outModel.setErrorMessage(e.getMessage());
                return outModel;
            }

        }
        
        return outModel;

    }
}



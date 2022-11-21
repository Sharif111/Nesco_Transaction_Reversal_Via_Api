package com.adminpanel.merchantadminpanel.nesco.dao;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Hex;
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
import com.adminpanel.merchantadminpanel.nesco.bo.NescoTransactionReverseBO;


import api.ApiResponse;
import cbsdayendclient.CbsDayendClient;




public class NescoTransactionReverseDAO {
	
	   private static SecretKeySpec secretKey;
	    private static byte[] key;
	
	SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public NescoTransactionReverseBO getPermissionCheckPro(	String sUserID,
			String sSessionID,
			String sCompanyID,
			String sBranchID,
			String sIPAddress,
			String sBranchActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoTransactionReverseBO oStatusReportofNPSBOutgoingBO = new NescoTransactionReverseBO();
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



	public NescoTransactionReverseBO getMenuCheckPro( String sUserID,
			String sSessionID,
			String sCompanyID,
			String sBranchID,
			String sRemoteIPAddress,
			String sActionPath)throws Exception {
		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoTransactionReverseBO oStatusReportofNPSBOutgoingBO = new NescoTransactionReverseBO();
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


	public NescoTransactionReverseBO getMenuList( String sUserID,
			String sSessionID)		throws Exception {

		NescoTransactionReverseBO oStatusReportofNPSBOutgoingBO = new NescoTransactionReverseBO();
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
	public NescoTransactionReverseBO getStatusListData( String sUserID,String sSessionID)		throws Exception {

		NescoTransactionReverseBO oStatusReportofNPSBOutgoingBO = new NescoTransactionReverseBO();
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
	public NescoTransactionReverseBO getNescoDataPro( 
			String sUserID,
			String sSessionID,
			String sToDate,
			String ERR
			)	throws Exception {

		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoTransactionReverseBO oPassportTransactionListBO = new NescoTransactionReverseBO();
		CallableStatement oStmt = oConn.prepareCall("{call IBMM.dpr_mm_nesco_bill_inf (?,?,?,?,?,?)}");
		oStmt.setString(1, sUserID);
		oStmt.setString(2, sSessionID);
		oStmt.setString(3, sToDate);
		oStmt.setString(4, ERR);
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
	
	public NescoTransactionReverseBO getExecutePro( 
			String sUserID,
			String sSessionID,
			String sToDate,
			String sStatus
			)	throws Exception {

		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoTransactionReverseBO oPassportTransactionListBO = new NescoTransactionReverseBO();
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
	
	
	public NescoTransactionReverseBO NescoDataUpdatePro( 
			String sUserID,
			String sSessionID,
			String sToDate,
			String sStatus,
			String sDayendRefId1,			
            String RESPONSE_CODE,
            String RESPONSE_MSG,
            String REF_NO
			)	throws Exception {
		
		

		Connection oConn = null;
		oConn = DBCPNewConnection.getConnection();
		NescoTransactionReverseBO oPassportTransactionListBO = new NescoTransactionReverseBO();
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
	
	
	
	 public NescoTransactionReverseBO getNescoData(String sUserID, String sSessionID)
			    throws Exception
			  {
		 NescoTransactionReverseBO oNescoTransactionReverseBO = new NescoTransactionReverseBO();
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
			    		  + " DUEDATE,LPC,SNDID,TOTALBILL,COMISSION,COMISSIONONVAT,AGENTREVCODE,AGENTREVMESSAGE, "
						  + " REVENEWSTAMPT,AGENTPAYTMENTMESSAGE,APPVERSION,DAYENDAGENTREFNO,DAYENDCBSTRANSREF,DAYENDREFNO,AGENTDOCNUM,NESCORESPONSE,CREATEBY from DT_NESCO_REQ ");	     
			      sql.append("WHERE USERID  = '");
				  sql.append(sUserID);
				  sql.append("' AND SESSIONID  = '");
				  sql.append(sSessionID);
				  sql.append("' order by CREATETIME desc ");
			      oRs = oStmt.executeQuery(sql.toString());
			     // System.out.println("sql.toString()==> "+ sql.toString());
			      while (oRs.next()) {
			    	  NescoTransactionReverseBO oUserIDGenerationListBO = new NescoTransactionReverseBO();
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
			          oUserIDGenerationListBO.setAgentRevCode(oRs.getString("AGENTREVCODE"));
			          oUserIDGenerationListBO.setAgentRevMsg(oRs.getString("AGENTREVMESSAGE"));
			          oUserIDGenerationListBO.setsCreateBy(oRs.getString("CREATEBY"));
			          //System.out.println("setsReferenceId()==> "+ oUserIDGenerationListBO.getsReferenceId());
			          aCustomerIDList.add(oUserIDGenerationListBO);
			      }
			      oNescoTransactionReverseBO.setUserIDGeneratedList(aCustomerIDList);
			    //System.out.println("oNescoTransactionReverseBO.setUserIDGeneratedList==>"+oNescoTransactionReverseBO.getUserIDGeneratedList());
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
			    return oNescoTransactionReverseBO;
			  }

	 
	 public NescoTransactionReverseBO getNescoTotalData(String sUserID, String sSessionID)
			    throws Exception
			  {
		// NescoTransactionReverseBO oNescoTransactionReverseBO = new NescoTransactionReverseBO();
			    Connection oConn = null;
			    Statement oStmt = null;
			    oConn = DBCPNewConnection.getConnection();
			    oStmt = oConn.createStatement();
			 //   ArrayList aCustomerIDList = new ArrayList();
			    NescoTransactionReverseBO oUserIDGenerationListBO = new NescoTransactionReverseBO();
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
			     // oNescoTransactionReverseBO.setUserIDGeneratedList(aCustomerIDList);
			    //System.out.println("oNescoTransactionReverseBO.setUserIDGeneratedList==>"+oNescoTransactionReverseBO.getUserIDGeneratedList());
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

	public NescoTransactionReverseBO getStatusType() throws Exception{

		NescoTransactionReverseBO oFundTransfertoBkashWalletReportListBO = new NescoTransactionReverseBO();
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
	
	
	
    public NescoTransactionReverseBO AgentDayEndRequest(NescoTransactionReverseBO model) {
        
        
    	NescoTransactionReverseBO outModel = new NescoTransactionReverseBO();
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
    
    
    
    public NescoTransactionReverseBO doCBSApi(
    		NescoTransactionReverseBO model) {
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
        
        NescoTransactionReverseBO outModel = new NescoTransactionReverseBO();
        
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
    
    public NescoTransactionReverseBO getAuthCode(NescoTransactionReverseBO model) {
    	NescoTransactionReverseBO outModel = new NescoTransactionReverseBO();
        Connection oConn = null;
        CallableStatement oStmt = null;

        try {
            oConn = DBCPNewConnection.getConnection();
            oStmt = oConn.prepareCall("{CALL ibmm.dpr_nesco_api_user (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            oStmt.setString(1, model.getMailId());
            oStmt.setString(2, model.getSessionId());
            oStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(9, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(13, java.sql.Types.INTEGER);
            oStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
            oStmt.execute();

            outModel.setAgentApiUser(oStmt.getString(3));
            outModel.setAgentApiPass(oStmt.getString(4));
            outModel.setAgentApiUrl(oStmt.getString(5));
            outModel.setCbsUserId(oStmt.getString(6));
            outModel.setCbsAuthKey(oStmt.getString(7));
            outModel.setCbsChkSum(oStmt.getString(8));
            outModel.setCbsRequestType(oStmt.getString(9));
            outModel.setAgentApiSecrtekey(oStmt.getString(10));
            outModel.setAgentApiSaltkey(oStmt.getString(11));
            outModel.setAgentReverseUrl(oStmt.getString(12));

//            System.out.println("setAgentApiUser = " + oStmt.getString(3));
//            System.out.println("setAgentApiPass = " + oStmt.getString(4));
//            System.out.println("setAgentApiUrl = " + oStmt.getString(5));
//            System.out.println("setCbsUserId = " + oStmt.getString(6));
//            System.out.println("setCbsAuthKey = " + oStmt.getString(7));
//            System.out.println("setCbsChkSum = " + oStmt.getString(8));
//            System.out.println("setCbsRequestType = " + oStmt.getString(9));
//            System.out.println("setAgentApiSecrtekey = " + oStmt.getString(10));
//            System.out.println("setAgentApiSaltkey = " + oStmt.getString(11));
//            System.out.println("reverseUrl = " + oStmt.getString(12));
        } catch (Exception ex) {
           // Logger.getLogger(NescoTransactionReverseDAO.class.getName()).log(Level.SEVERE, null, ex);
            outModel.setErrorCode("1");
            outModel.setErrorMessage(ex.getMessage());

        } finally {
            if (oConn != null) {
            	DBCPNewConnection.releaseConnection(oConn);
            }
        }
        return outModel;
    }
    
    /**First*/
    public NescoTransactionReverseBO getReverseData(NescoTransactionReverseBO model) {
    	NescoTransactionReverseBO outModel = new NescoTransactionReverseBO();
        Connection oConn = null;
        CallableStatement oStmt = null;

        try {
            oConn = DBCPNewConnection.getConnection();
            oStmt = oConn.prepareCall("{CALL ibmm.dpr_mm_agent_rev_req (?,?,?,?,?,?)}");

            oStmt.setString(1, model.getsReferenceId());
            oStmt.setString(2, model.getsDebitAcc());
            oStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            oStmt.registerOutParameter(5, java.sql.Types.INTEGER);
            oStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            oStmt.execute();

           
            outModel.setErrorCode(String.valueOf(oStmt.getInt(5)));
            outModel.setErrorMessage(oStmt.getString(6));
           

            if ("0".equals(outModel.getErrorCode())) {
            	
            	
                 
            	model.setAgentReveseRequestNo(oStmt.getString(3));
            	//model.setsReferenceId(oStmt.getString(4));
            	model.setAgentReveseReferenceId(oStmt.getString(4));
            	
            	
                 
                 
                 System.out.println("**********");
                 System.out.println("reverse request---" + model.getAgentReveseRequestNo());
                 System.out.println(" ref-->"+model.getsReferenceId());
                 System.out.println("agent reverse ref-->"+model.getAgentReveseReferenceId());
                 
           

                 NescoTransactionReverseDAO dao = new NescoTransactionReverseDAO();
                 NescoTransactionReverseBO reverse =   dao.reverseExecute(model);
                 
                 System.out.println("reverse complete ---" + reverse.getErrorCode());
                 System.out.println("reverse complete " + reverse.getErrorMessage());
                 outModel.setErrorCode(reverse.getErrorCode());
                 outModel.setErrorMessage(reverse.getErrorMessage());
                 return outModel;
                 
                
            }else {
            	 return outModel;
            	
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            outModel.setErrorCode("1");
            outModel.setErrorMessage(ex.getMessage());
            return outModel;

        } finally {
            if (oConn != null) {
            	DBCPNewConnection.releaseConnection(oConn);
            }
        }
       
       
    }
    
    
    public NescoTransactionReverseBO reverseExecute(NescoTransactionReverseBO model) {

    	
    	
    	NescoTransactionReverseBO authCode = getAuthCode(model);
        model.setAgentApiUser(authCode.getAgentApiUser());
        model.setAgentApiPass(authCode.getAgentApiPass());
        model.setAgentApiUrl(authCode.getAgentApiUrl());
        model.setAgentApiSecrtekey(authCode.getAgentApiSecrtekey());
        model.setAgentApiSaltkey(authCode.getAgentApiSaltkey());
        model.setAgentReverseUrl(authCode.getAgentReverseUrl());
        

        NescoTransactionReverseBO outModel = new NescoTransactionReverseBO();
        StringBuffer sb = new StringBuffer();

        sb.append("<TRANSACTION_REVERSE>");

        sb.append("<USER_ID>" + model.getAgentApiUser() + "</USER_ID>");
        sb.append("<USER_PASS>" + model.getAgentApiPass() + "</USER_PASS>");
        sb.append("<TOKEN_NO />");
        sb.append("<REQ_NO>" + model.getAgentReveseReferenceId() + "</REQ_NO>");
        sb.append("<REF_NO>" + model.getAgentReveseRequestNo() + "</REF_NO>");
        sb.append("</TRANSACTION_REVERSE>");

        System.out.println("sb = " + sb);

        String url = model.getAgentReverseUrl();

        System.out.println("url = " + url);

        //DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
       // HttpPost httppost = new HttpPost(url);
        //org.json.JSONObject jsonObject = new org.json.JSONObject();
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        org.json.JSONObject jsonObject = new org.json.JSONObject();

        try {

            String encrypt = encrypt(sb.toString(), model.getAgentApiSecrtekey());
            System.out.println("encrypt = " + encrypt);

            // encrypt = "AD7DCAD41F4C28DA905F0FFDEF70FF1FADE5FE5C622EB8F13081CC685AACFEF05D881FC8A6A46E8FA02F4A226EE0C44BF2265A1246842F9D773FEF3EED2643793D846D5900FBD206F3C72F50466E6F440B81BE415682317936DC1FDFC2BD63A262C91DA78EB15C214754EF828CFD260E9CD188B00A2B3E84BD6A7DC4C5064A93BD6142416E36CCE7568EDA1356B9F9C79423EDF865ECDD905D0A206AD432BC09021EDF31ED6D69A144246A0BC56610DA";
            System.out.println("encrypt = " + encrypt);

            StringEntity params = new StringEntity(String.valueOf(jsonObject));
            httppost.addHeader("content-type", "application/json");
            httppost.setHeader("requestinfo", encrypt);
            httppost.setEntity(params);

            //HttpResponse httpResponse = httpclient.execute(httppost);
            CloseableHttpResponse httpResponse = httpClient.execute(httppost);
            System.out.println("response = " + httpResponse);

            String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            System.out.println("responseString ======= >>>>>>> " + responseString);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("statusCode = " + statusCode);

            org.json.JSONObject obj = new org.json.JSONObject(responseString);
            String response = "";
            if (obj.has("response") && !obj.isNull("response")) {
                response = (String) obj.get("response");
            }
            System.out.println("response = " + response);

            
            if (response != "") {
                String decrypt_HexStr = decrypt(response, model);
                System.out.println("decrypt_HexStr = " + decrypt_HexStr);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(decrypt_HexStr));
                Document doc = db.parse(is);
                NodeList transactionNodeList = doc.getElementsByTagName("TRANSACTION_REVERSE");
                System.out.println("TRANSACTION_REVERSE = " + transactionNodeList);
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
                        
                        
                 
                       
                        model.setRESPONSE_CODE(RESPONSE_CODE);
                        model.setRESPONSE_MSG(RESPONSE_MSG);
                        model.setREF_NO(REF_NO);
                        reverseUpdate(model);
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
            //model.setError(ex.getMessage());
           // errorLog(model);
            return outModel;
        }

        return outModel;
    }
    
    public static String encrypt(String strToEncrypt, String ENCRYPTION_KEY) {
        String hexString = "";
        try {
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            setKey(ENCRYPTION_KEY);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            hexString = Hex.encodeHexString(cipher.doFinal(strToEncrypt.getBytes()));
            return hexString;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
    
 
    public static void setKey(String myKey) throws NoSuchAlgorithmException {
        key = myKey.getBytes();
        key = Arrays.copyOf(key, 32);
        secretKey = new SecretKeySpec(key, "AES");
    }
    
    private String decrypt(String data, NescoTransactionReverseBO model) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(model.getAgentApiSecrtekey().getBytes(), "AES");
        Cipher cipher = null;
        byte[] ivBytes = new byte[16];

        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(ivBytes));
        byte[] decodeval = hex2Byte(data);
        byte[] decval = cipher.doFinal(decodeval);
        return new String(decval);

    }
    
    public static byte[] hex2Byte(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer
                    .parseInt(str.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    
   /** update transacton*/
    public NescoTransactionReverseBO reverseUpdate(NescoTransactionReverseBO model) {
    	NescoTransactionReverseBO outModel = new NescoTransactionReverseBO();
        Connection oConn = null;
        CallableStatement oStmt = null;

        try {
            oConn = DBCPNewConnection.getConnection();
            oStmt = oConn.prepareCall("{CALL ibmm.dpr_mm_agent_rev_req_update (?,?,?,?,?,?,?)}");

            oStmt.setString(1, model.getsReferenceId());
            oStmt.setString(2, model.getsDebitAcc());
            oStmt.setString(3, model.getRESPONSE_CODE());
            oStmt.setString(4, model.getRESPONSE_MSG());
            oStmt.setString(5, model.getREF_NO());
            oStmt.registerOutParameter(6, java.sql.Types.INTEGER);
            oStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
            oStmt.execute();

           
            //System.out.println(oStmt.getString(7));
           // outModel.setErrorMessage(oStmt.getString(6));
           

            

        } catch (Exception ex) {
            ex.printStackTrace();
            outModel.setErrorCode("1");
            outModel.setErrorMessage(ex.getMessage());
            return outModel;

        } finally {
            if (oConn != null) {
            	DBCPNewConnection.releaseConnection(oConn);
            }
        }
        return outModel;
       
    }
}




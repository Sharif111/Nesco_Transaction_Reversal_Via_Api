package com.adminpanel.merchantadminpanel.nesco.action;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.adminpanel.merchantadminpanel.nesco.bo.NescoDayEndTransactionSettlementBO;
import com.adminpanel.merchantadminpanel.nesco.dao.NescoDayEndTransactionSettlementDAO;
import com.adminpanel.merchantadminpanel.nesco.formbean.NescoDayEndTransactionSettlementForm;
import com.adminpanel.utility.RemoveNullValue;
import com.adminpanel.utility.ReportManager;

public class NescoDayEndTransactionSettlementAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		NescoDayEndTransactionSettlementBO oNescoDayEndTransactionSettlementBO = new NescoDayEndTransactionSettlementBO();
		NescoDayEndTransactionSettlementDAO oNescoDayEndTransactionSettlementDAO =	new NescoDayEndTransactionSettlementDAO();
		NescoDayEndTransactionSettlementForm oNescoDayEndTransactionSettlementForm =(NescoDayEndTransactionSettlementForm) form;
		NescoDayEndTransactionSettlementBO oTransfertoNescoMessageBO =	new NescoDayEndTransactionSettlementBO();
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oNescoDayEndTransactionSettlementForm);
		String sActionPath = "";
		sActionPath = mapping.getPath();
		HttpSession session = request.getSession(true);
		String sSuccessAction = "success";
		String sFailureAction = "failure";
		String sFatalErrorAction = "fatalError";
		String sSessionExpireAction = "sessionExpire";
		String sSessionMyBankMenuAction = "sessionMyBankMenu";
		String sSuccess = sFatalErrorAction;
		String sActionPathName = "";
		String gsUserID = (String) session.getAttribute("GSUserID");
		String gsUserTitle = (String) session.getAttribute("GSUserTitle");
		String gsLastLogInDate =(String) session.getAttribute("GSLastLogInDate");
		String gsLogInUserID = (String) session.getAttribute("GSLogInUserID");
		String gsSessionID = (String) session.getAttribute("GSSessionID");
		String gsInternalCardID =(String) session.getAttribute("GSInternalCardID");
		String gsHeaderName = (String) session.getAttribute("GSHeaderName");
		String gsHeaderLogIn = (String) session.getAttribute("GSHeaderLogIn");
		String gsCompanyID = (String) session.getAttribute("GSCompanyCode");
		String gsBranchID = (String) session.getAttribute("GSBranchCode");
		String gsBranchName = (String) session.getAttribute("GSBranchName");
		String gsTellerID = (String) session.getAttribute("GSTellerID");
		String gsCompanyName = (String) session.getAttribute("GSCompanyName");		
		String gsBranchOpenDateDDFormat =(String) session.getAttribute("GSBranchOpenDateDDFormat");
		String sBranchActionPathName ="/merchantadminpanel/nescoDayEndTransactionSettlement.do";

		//First Day of the Month	
		Calendar calendar = Calendar.getInstance();		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date date = calendar.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		String firstDay = format1.format(date);

		
		calendar.add(Calendar.MONTH, 1);  
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		calendar.add(Calendar.DATE, -1);  
		
		
		// Current Date 
		String current_date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		if (sActionPath.equals("/nescoDayEndTransactionSettlement")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			clearForm(oNescoDayEndTransactionSettlementForm);
			clearSession(session);
			oNescoDayEndTransactionSettlementForm.setToDate(current_date);
			oNescoDayEndTransactionSettlementForm.setsTotalBill("");
			oNescoDayEndTransactionSettlementForm.setsTotalBillWithoutVat("");
			oNescoDayEndTransactionSettlementForm.setsTotalVat("");
			oNescoDayEndTransactionSettlementForm.setsTotalRevenueStamp("");
			
			session.setAttribute("GSBkashWalletFilePath", null);
			session.setAttribute("GSBkashWalletFileName", null);

			oNescoDayEndTransactionSettlementBO = oNescoDayEndTransactionSettlementDAO.getStatusListData(gsUserID, gsSessionID);
			populateStatusList(oNescoDayEndTransactionSettlementForm,oNescoDayEndTransactionSettlementBO);
			sSuccess = sSuccessAction;
		}

		else if (sActionPath.equals("/showDataNescoDayEndTransactionSettlement")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			session.setAttribute("oNescoDayEndTransactionSettlementBO", null);

			oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.getNescoDataPro(gsUserID,
					gsSessionID,	
					oNescoDayEndTransactionSettlementForm.getToDate(),
					oNescoDayEndTransactionSettlementForm.getUserStatus());

			if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("0")) {
				
				oNescoDayEndTransactionSettlementBO = oNescoDayEndTransactionSettlementDAO.getNescoData(gsUserID, gsSessionID);
				 oNescoDayEndTransactionSettlementForm.setUserIDGeneratedList(oNescoDayEndTransactionSettlementBO.getUserIDGeneratedList());
			     session.setAttribute("oNescoDayEndTransactionSettlementBO", oNescoDayEndTransactionSettlementBO);
			     NescoDayEndTransactionSettlementBO  bo = oNescoDayEndTransactionSettlementDAO.getNescoTotalData(gsUserID, gsSessionID);
				//oNescoDayEndTransactionSettlementForm.setUserIDGeneratedList(bo.getUserIDGeneratedList());
				
				oNescoDayEndTransactionSettlementForm.setsTotalBill(bo.getsTotalBill());
				oNescoDayEndTransactionSettlementForm.setsTotalBillWithoutVat(bo.getsTotalBillWithoutVat());
				oNescoDayEndTransactionSettlementForm.setsTotalVat(bo.getsTotalVat());
				oNescoDayEndTransactionSettlementForm.setsTotalRevenueStamp(bo.getsTotalRevenueStamp());
				System.out.println("getsTotalBill==>" + bo.getsTotalBill());
				System.out.println("getsTotalBillWithoutVat==>" + bo.getsTotalBillWithoutVat());
				System.out.println("getsTotalVat==>" + bo.getsTotalVat());
				System.out.println("getsTotalRevenueStamp==>" + bo.getsTotalRevenueStamp());
				
				 //session.setAttribute("oNescoDayEndTransactionSettlementBO", oNescoDayEndTransactionSettlementBO);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("1")) {
				String	FundTransfertoBkashWalletReportMessage ="";							
				FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);

				sSuccess = sFailureAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}									
		} 
		
		else if (sActionPath.equals("/executeNescoDayEndTransactionSettlement")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			session.setAttribute("oNescoDayEndTransactionSettlementBO", null);
			

			oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.getExecutePro(gsUserID,
					gsSessionID,	
					oNescoDayEndTransactionSettlementForm.getToDate(),
					oNescoDayEndTransactionSettlementForm.getUserStatus());
			
			
			System.out.println("getExecutePro -->"+oNescoDayEndTransactionSettlementBO.getErrorCode());

			if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("0")) {	
				System.out.println("getExecutePro2 -->"+oNescoDayEndTransactionSettlementBO.getErrorCode());
				
				   if (oNescoDayEndTransactionSettlementForm.getUserStatus().equals("A")) {
					   oNescoDayEndTransactionSettlementBO.setMailId(gsUserID);
					   oNescoDayEndTransactionSettlementBO.setSessionId(gsSessionID);
					   oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.AgentDayEndRequest(oNescoDayEndTransactionSettlementBO);
					    session.setAttribute("oNescoDayEndTransactionSettlementBO", oNescoDayEndTransactionSettlementBO);
					    
					    if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("0")) {
					    	 oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.NescoDataUpdatePro(gsUserID,			    		
										gsSessionID,	
										oNescoDayEndTransactionSettlementForm.getToDate(),
										oNescoDayEndTransactionSettlementForm.getUserStatus(),
										oNescoDayEndTransactionSettlementBO.getsDayendRefId1(),
										oNescoDayEndTransactionSettlementBO.getRESPONSE_CODE(),
										oNescoDayEndTransactionSettlementBO.getRESPONSE_MSG(),
										oNescoDayEndTransactionSettlementBO.getREF_NO());
					    	   String	FundTransfertoBkashWalletReportMessage ="";							
								FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
								session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);								
					    	   sSuccess = sSuccessAction;
					    	   clearForm(oNescoDayEndTransactionSettlementForm);
		   					   clearSession(session);
					    }else {
					    	String	FundTransfertoBkashWalletReportMessage ="";							
							FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
							session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
							sSuccess = sFailureAction;
					    }
					   
				   }
				   
                 if (oNescoDayEndTransactionSettlementForm.getUserStatus().equals("C")) {
                	// 
                	 oNescoDayEndTransactionSettlementBO.setMailId(gsUserID);
					   oNescoDayEndTransactionSettlementBO.setSessionId(gsSessionID);
					   
                	 oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.doCBSApi(
                			 oNescoDayEndTransactionSettlementBO
                			 );
					    session.setAttribute("oNescoDayEndTransactionSettlementBO", oNescoDayEndTransactionSettlementBO);
					    
					    System.out.println("action.getsDayendRefId1(),"+oNescoDayEndTransactionSettlementBO.getsDayendRefId1());
					    
					    if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("0")) {
					    	 oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.NescoDataUpdatePro(gsUserID,			    		
										gsSessionID,	
										oNescoDayEndTransactionSettlementForm.getToDate(),
										oNescoDayEndTransactionSettlementForm.getUserStatus(),
										oNescoDayEndTransactionSettlementBO.getsDayendRefId1(),
										oNescoDayEndTransactionSettlementBO.getRESPONSE_CODE(),
										oNescoDayEndTransactionSettlementBO.getRESPONSE_MSG(),
										oNescoDayEndTransactionSettlementBO.getREF_NO());
					    	   String	FundTransfertoBkashWalletReportMessage ="";							
								FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
								session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
								
					    	   sSuccess = sSuccessAction;
					    }else {
					    	String	FundTransfertoBkashWalletReportMessage ="";							
							FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
							session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
							sSuccess = sFailureAction;
					    }
					   
				   }
				   
				   
	
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("1")) {
				String	FundTransfertoBkashWalletReportMessage ="";							
				FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);

				sSuccess = sFailureAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}									
		} 

		else if (sActionPath.equals("/cancelNescoDayEndTransactionSettlement")) {
			session.setAttribute("oPassportTransactionListMessageBO",null);

			oNescoDayEndTransactionSettlementBO =oNescoDayEndTransactionSettlementDAO.getMenuCheckPro(gsUserID,
					gsSessionID,
					gsCompanyID,
					gsBranchID,
					request.getRemoteAddr(),
					sBranchActionPathName);

			if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("0")) {
				clearSession(session);
				String	FundTransfertoBkashWalletReportMessage ="";
				FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("1")) {
				String	StatusReportofNPSBOutgoingMessageBO =oNescoDayEndTransactionSettlementBO.getErrorMessage();									
				session.setAttribute("oPassportTransactionListMessageBO",StatusReportofNPSBOutgoingMessageBO);
				sSuccess = sFailureAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoDayEndTransactionSettlementBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}
		}



		return mapping.findForward(sSuccess);
	}

	private void populateStatusList(NescoDayEndTransactionSettlementForm oNescoDayEndTransactionSettlementForm,NescoDayEndTransactionSettlementBO oNescoDayEndTransactionSettlementBO) {
		oNescoDayEndTransactionSettlementForm.setUserStatusList(oNescoDayEndTransactionSettlementBO.getUserStatusList());
		oNescoDayEndTransactionSettlementForm.setUserStatusNameList(oNescoDayEndTransactionSettlementBO.getUserStatusNameList());

	}

	public void populateMenu(NescoDayEndTransactionSettlementForm oNescoDayEndTransactionSettlementForm,NescoDayEndTransactionSettlementBO oNescoDayEndTransactionSettlementBO) {
		oNescoDayEndTransactionSettlementForm.setMenuList(oNescoDayEndTransactionSettlementBO.getMenuList());
		oNescoDayEndTransactionSettlementForm.setMenuNameList(oNescoDayEndTransactionSettlementBO.getMenuNameList());
	}

	private void clearForm(NescoDayEndTransactionSettlementForm oNescoDayEndTransactionSettlementForm) {

		
		oNescoDayEndTransactionSettlementForm.setToDate("");
		oNescoDayEndTransactionSettlementForm.setUserStatus(" ");
		oNescoDayEndTransactionSettlementForm.setsTotalBill(" ");
		oNescoDayEndTransactionSettlementForm.setsTotalBillWithoutVat(" ");
		oNescoDayEndTransactionSettlementForm.setsTotalVat(" ");
		oNescoDayEndTransactionSettlementForm.setsTotalRevenueStamp(" ");

	}
	public void populateFileName(NescoDayEndTransactionSettlementForm oNescoDayEndTransactionSettlementForm,NescoDayEndTransactionSettlementBO oNescoDayEndTransactionSettlementBO) {
		oNescoDayEndTransactionSettlementForm.setFile(oNescoDayEndTransactionSettlementBO.getFile());
	}

	private void clearSession(HttpSession session) {
		session.setAttribute("oPassportTransactionListMessageBO", " ");	
		session.setAttribute("oStatusReportofNPSBOutgoingCustomerDetailsListBO", null);
		session.setAttribute("oStatusReportofNPSBOutgoingListBO", null);			
		session.setAttribute("oNescoDayEndTransactionSettlementBO", null);		
		session.setAttribute("GSBkashWalletFilePath", null);
		session.setAttribute("GSBkashWalletFileName", null);
	}

}


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

import com.adminpanel.merchantadminpanel.nesco.bo.NescoTransactionReverseBO;
import com.adminpanel.merchantadminpanel.nesco.dao.NescoTransactionReverseDAO;
import com.adminpanel.merchantadminpanel.nesco.formbean.NescoTransactionReverseForm;
import com.adminpanel.utility.RemoveNullValue;
import com.adminpanel.utility.ReportManager;

public class NescoTransactionReverseAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		NescoTransactionReverseBO oNescoTransactionReverseBO = new NescoTransactionReverseBO();
		NescoTransactionReverseDAO oNescoTransactionReverseDAO =	new NescoTransactionReverseDAO();
		NescoTransactionReverseForm oNescoTransactionReverseForm =(NescoTransactionReverseForm) form;
		NescoTransactionReverseBO oTransfertoNescoMessageBO =	new NescoTransactionReverseBO();
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oNescoTransactionReverseForm);
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
		String sBranchActionPathName ="/merchantadminpanel/nescoTransactionReverse.do";

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

		if (sActionPath.equals("/nescoTransactionReverse")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			clearForm(oNescoTransactionReverseForm);
			clearSession(session);
			oNescoTransactionReverseForm.setsReferenceId("");
			oNescoTransactionReverseForm.setsDebitAcc("");
			oNescoTransactionReverseForm.setsBillNo("");
			oNescoTransactionReverseForm.setsAmount("");
			
			session.setAttribute("GSBkashWalletFilePath", null);
			session.setAttribute("GSBkashWalletFileName", null);

			oNescoTransactionReverseBO = oNescoTransactionReverseDAO.getStatusListData(gsUserID, gsSessionID);
			populateStatusList(oNescoTransactionReverseForm,oNescoTransactionReverseBO);
			sSuccess = sSuccessAction;
		}
		
		else if (sActionPath.equals("/reversalDetailsOfNescoTransactionReversal")) {
			session.setAttribute("oTransfertoNescoMessageBO", " ");
			session.setAttribute("oNescoTransactionReverseBO", null);
			clearForm(oNescoTransactionReverseForm);
			clearSession(session);
			oNescoTransactionReverseForm.setToDate(current_date);
			oNescoTransactionReverseForm.setOperation("ALL");
			oNescoTransactionReverseForm.setsTotalBill("");
			oNescoTransactionReverseForm.setsTotalBillWithoutVat("");
			oNescoTransactionReverseForm.setsTotalVat("");
			oNescoTransactionReverseForm.setsTotalRevenueStamp("");
			sSuccess = sSuccessAction;
		}

		else if (sActionPath.equals("/showDataNescoTransactionReverse")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			session.setAttribute("oNescoTransactionReverseBO", null);

			oNescoTransactionReverseBO =oNescoTransactionReverseDAO.getNescoDataPro(gsUserID,
					gsSessionID,	
					oNescoTransactionReverseForm.getToDate(),
					"ERR");

			if (oNescoTransactionReverseBO.getErrorCode().equals("0")) {
				oNescoTransactionReverseBO = oNescoTransactionReverseDAO.getNescoData(gsUserID, gsSessionID);
				 oNescoTransactionReverseForm.setUserIDGeneratedList(oNescoTransactionReverseBO.getUserIDGeneratedList());
			     session.setAttribute("oNescoTransactionReverseBO", oNescoTransactionReverseBO);
			     NescoTransactionReverseBO  bo = oNescoTransactionReverseDAO.getNescoTotalData(gsUserID, gsSessionID);
				//oNescoTransactionReverseForm.setUserIDGeneratedList(bo.getUserIDGeneratedList());
				
				oNescoTransactionReverseForm.setsTotalBill(bo.getsTotalBill());
				oNescoTransactionReverseForm.setsTotalBillWithoutVat(bo.getsTotalBillWithoutVat());
				oNescoTransactionReverseForm.setsTotalVat(bo.getsTotalVat());
				oNescoTransactionReverseForm.setsTotalRevenueStamp(bo.getsTotalRevenueStamp());
				//System.out.println("getsTotalBill==>" + bo.getsTotalBill());
				//System.out.println("getsTotalBillWithoutVat==>" + bo.getsTotalBillWithoutVat());
				//System.out.println("getsTotalVat==>" + bo.getsTotalVat());
				//System.out.println("getsTotalRevenueStamp==>" + bo.getsTotalRevenueStamp());
				
				 //session.setAttribute("oNescoTransactionReverseBO", oNescoTransactionReverseBO);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("1")) {
				String	FundTransfertoBkashWalletReportMessage ="";							
				FundTransfertoBkashWalletReportMessage =oNescoTransactionReverseBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);

				sSuccess = sFailureAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}									
		}
		
		else if (sActionPath.equals("/executeNescoTransactionReverse")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			session.setAttribute("oNescoTransactionReverseBO", null);
			clearForm(oNescoTransactionReverseForm);
			clearSession(session);
			
		
			
			oNescoTransactionReverseBO.setMailId(gsUserID);
			oNescoTransactionReverseBO.setSessionId(gsSessionID);
			oNescoTransactionReverseBO.setsReferenceId(oNescoTransactionReverseForm.getsReferenceId());
			oNescoTransactionReverseBO.setsDebitAcc(oNescoTransactionReverseForm.getsDebitAcc());
			
			
			oNescoTransactionReverseBO =oNescoTransactionReverseDAO.getReverseData(oNescoTransactionReverseBO);

			if (oNescoTransactionReverseBO.getErrorCode().equals("0")) {
				String	FundTransfertoBkashWalletReportMessage ="";	
				FundTransfertoBkashWalletReportMessage =oNescoTransactionReverseBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);				
				sSuccess = sSuccessAction;
				
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("1")) {
				String	FundTransfertoBkashWalletReportMessage ="";							
				FundTransfertoBkashWalletReportMessage =oNescoTransactionReverseBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
				sSuccess = sFailureAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}									
		} 
		
		else if (sActionPath.equals("/cancelNescoTransactionReverse")) {
			session.setAttribute("oPassportTransactionListMessageBO",null);

			oNescoTransactionReverseBO =oNescoTransactionReverseDAO.getMenuCheckPro(gsUserID,
					gsSessionID,
					gsCompanyID,
					gsBranchID,
					request.getRemoteAddr(),
					sBranchActionPathName);

			if (oNescoTransactionReverseBO.getErrorCode().equals("0")) {
				clearSession(session);
				String	FundTransfertoBkashWalletReportMessage ="";
				FundTransfertoBkashWalletReportMessage =oNescoTransactionReverseBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("1")) {
				String	StatusReportofNPSBOutgoingMessageBO =oNescoTransactionReverseBO.getErrorMessage();									
				session.setAttribute("oPassportTransactionListMessageBO",StatusReportofNPSBOutgoingMessageBO);
				sSuccess = sFailureAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoTransactionReverseBO.getErrorCode().equals("3")) {
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

	private void populateStatusList(NescoTransactionReverseForm oNescoTransactionReverseForm,NescoTransactionReverseBO oNescoTransactionReverseBO) {
		oNescoTransactionReverseForm.setUserStatusList(oNescoTransactionReverseBO.getUserStatusList());
		oNescoTransactionReverseForm.setUserStatusNameList(oNescoTransactionReverseBO.getUserStatusNameList());

	}

	public void populateMenu(NescoTransactionReverseForm oNescoTransactionReverseForm,NescoTransactionReverseBO oNescoTransactionReverseBO) {
		oNescoTransactionReverseForm.setMenuList(oNescoTransactionReverseBO.getMenuList());
		oNescoTransactionReverseForm.setMenuNameList(oNescoTransactionReverseBO.getMenuNameList());
	}

	private void clearForm(NescoTransactionReverseForm oNescoTransactionReverseForm) {

		
		oNescoTransactionReverseForm.setToDate("");
		oNescoTransactionReverseForm.setUserStatus(" ");
		oNescoTransactionReverseForm.setsReferenceId(" ");
		oNescoTransactionReverseForm.setsDebitAcc(" ");
		oNescoTransactionReverseForm.setsBillNo(" ");
		oNescoTransactionReverseForm.setsAmount(" ");
		

	}
	public void populateFileName(NescoTransactionReverseForm oNescoTransactionReverseForm,NescoTransactionReverseBO oNescoTransactionReverseBO) {
		oNescoTransactionReverseForm.setFile(oNescoTransactionReverseBO.getFile());
	}

	private void clearSession(HttpSession session) {
		session.setAttribute("oPassportTransactionListMessageBO", " ");	
		session.setAttribute("oStatusReportofNPSBOutgoingCustomerDetailsListBO", null);
		session.setAttribute("oStatusReportofNPSBOutgoingListBO", null);			
		session.setAttribute("oNescoTransactionReverseBO", null);		
		session.setAttribute("GSBkashWalletFilePath", null);
		session.setAttribute("GSBkashWalletFileName", null);
	}

}




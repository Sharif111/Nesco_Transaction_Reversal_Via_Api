package com.adminpanel.merchantadminpanel.nesco.report.action;
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

import com.adminpanel.merchantadminpanel.nesco.report.bo.NescoTransactionReverseReportBO;
import com.adminpanel.merchantadminpanel.nesco.report.dao.NescoTransactionReverseReportDAO;
import com.adminpanel.merchantadminpanel.nesco.report.formbean.NescoTransactionReverseReportForm;
import com.adminpanel.utility.RemoveNullValue;
import com.adminpanel.utility.ReportManager;

public class NescoTransactionReverseReportAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		NescoTransactionReverseReportBO oNescoTransactionReverseReportBO = new NescoTransactionReverseReportBO();
		NescoTransactionReverseReportDAO oNescoTransactionReverseReportDAO =	new NescoTransactionReverseReportDAO();
		NescoTransactionReverseReportForm oNescoTransactionReverseReportForm =(NescoTransactionReverseReportForm) form;
		NescoTransactionReverseReportBO oTransfertoNescoMessageBO =	new NescoTransactionReverseReportBO();
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oNescoTransactionReverseReportForm);
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
		String sBranchActionPathName ="/merchantadminpanel/nescoTransactionReverseReport.do";

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

		if (sActionPath.equals("/nescoTransactionReverseReport")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			clearForm(oNescoTransactionReverseReportForm);
			clearSession(session);
			oNescoTransactionReverseReportForm.setToDate(current_date);
			oNescoTransactionReverseReportForm.setOperation("ALL");
			
			session.setAttribute("GSBkashWalletFilePath", null);
			session.setAttribute("GSBkashWalletFileName", null);

			oNescoTransactionReverseReportBO = oNescoTransactionReverseReportDAO.getStatusListData(gsUserID, gsSessionID);
			populateStatusList(oNescoTransactionReverseReportForm,oNescoTransactionReverseReportBO);
			sSuccess = sSuccessAction;
		}
		
		else if (sActionPath.equals("/reversalDetailsOfNescoTransactionReversalReport")) {
			session.setAttribute("oTransfertoNescoMessageBO", " ");
			session.setAttribute("oNescoTransactionReverseReportBO", null);
			clearForm(oNescoTransactionReverseReportForm);
			clearSession(session);
			oNescoTransactionReverseReportForm.setToDate(current_date);
			oNescoTransactionReverseReportForm.setOperation("ALL");
			oNescoTransactionReverseReportForm.setsTotalBill("");
			oNescoTransactionReverseReportForm.setsTotalBillWithoutVat("");
			oNescoTransactionReverseReportForm.setsTotalVat("");
			oNescoTransactionReverseReportForm.setsTotalRevenueStamp("");
			sSuccess = sSuccessAction;
		}

		else if (sActionPath.equals("/showDataNescoTransactionReverseReport")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			session.setAttribute("oNescoTransactionReverseReportBO", null);

			oNescoTransactionReverseReportBO =oNescoTransactionReverseReportDAO.getNescoDataPro(gsUserID,
					gsSessionID,	
					oNescoTransactionReverseReportForm.getToDate(),
					"RR");

			if (oNescoTransactionReverseReportBO.getErrorCode().equals("0")) {
				oNescoTransactionReverseReportBO = oNescoTransactionReverseReportDAO.getNescoData(gsUserID, gsSessionID);
				 oNescoTransactionReverseReportForm.setUserIDGeneratedList(oNescoTransactionReverseReportBO.getUserIDGeneratedList());
			     session.setAttribute("oNescoTransactionReverseReportBO", oNescoTransactionReverseReportBO);
			     NescoTransactionReverseReportBO  bo = oNescoTransactionReverseReportDAO.getNescoTotalData(gsUserID, gsSessionID);
				//oNescoTransactionReverseReportForm.setUserIDGeneratedList(bo.getUserIDGeneratedList());
				
				oNescoTransactionReverseReportForm.setsTotalBill(bo.getsTotalBill());
				oNescoTransactionReverseReportForm.setsTotalBillWithoutVat(bo.getsTotalBillWithoutVat());
				oNescoTransactionReverseReportForm.setsTotalVat(bo.getsTotalVat());
				oNescoTransactionReverseReportForm.setsTotalRevenueStamp(bo.getsTotalRevenueStamp());
				//System.out.println("getsTotalBill==>" + bo.getsTotalBill());
				//System.out.println("getsTotalBillWithoutVat==>" + bo.getsTotalBillWithoutVat());
				//System.out.println("getsTotalVat==>" + bo.getsTotalVat());
				//System.out.println("getsTotalRevenueStamp==>" + bo.getsTotalRevenueStamp());
				
				 //session.setAttribute("oNescoTransactionReverseReportBO", oNescoTransactionReverseReportBO);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoTransactionReverseReportBO.getErrorCode().equals("1")) {
				String	FundTransfertoBkashWalletReportMessage ="";							
				FundTransfertoBkashWalletReportMessage =oNescoTransactionReverseReportBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);

				sSuccess = sFailureAction;
			} 
			else if (oNescoTransactionReverseReportBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoTransactionReverseReportBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}									
		}
		
		else if (sActionPath.equals("/cancelNescoTransactionReverseReport")) {
			session.setAttribute("oPassportTransactionListMessageBO",null);

			oNescoTransactionReverseReportBO =oNescoTransactionReverseReportDAO.getMenuCheckPro(gsUserID,
					gsSessionID,
					gsCompanyID,
					gsBranchID,
					request.getRemoteAddr(),
					sBranchActionPathName);

			if (oNescoTransactionReverseReportBO.getErrorCode().equals("0")) {
				clearSession(session);
				String	FundTransfertoBkashWalletReportMessage ="";
				FundTransfertoBkashWalletReportMessage =oNescoTransactionReverseReportBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoTransactionReverseReportBO.getErrorCode().equals("1")) {
				String	StatusReportofNPSBOutgoingMessageBO =oNescoTransactionReverseReportBO.getErrorMessage();									
				session.setAttribute("oPassportTransactionListMessageBO",StatusReportofNPSBOutgoingMessageBO);
				sSuccess = sFailureAction;
			} 
			else if (oNescoTransactionReverseReportBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoTransactionReverseReportBO.getErrorCode().equals("3")) {
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

	private void populateStatusList(NescoTransactionReverseReportForm oNescoTransactionReverseReportForm,NescoTransactionReverseReportBO oNescoTransactionReverseReportBO) {
		oNescoTransactionReverseReportForm.setUserStatusList(oNescoTransactionReverseReportBO.getUserStatusList());
		oNescoTransactionReverseReportForm.setUserStatusNameList(oNescoTransactionReverseReportBO.getUserStatusNameList());

	}

	public void populateMenu(NescoTransactionReverseReportForm oNescoTransactionReverseReportForm,NescoTransactionReverseReportBO oNescoTransactionReverseReportBO) {
		oNescoTransactionReverseReportForm.setMenuList(oNescoTransactionReverseReportBO.getMenuList());
		oNescoTransactionReverseReportForm.setMenuNameList(oNescoTransactionReverseReportBO.getMenuNameList());
	}

	private void clearForm(NescoTransactionReverseReportForm oNescoTransactionReverseReportForm) {

		
		oNescoTransactionReverseReportForm.setToDate("");
		oNescoTransactionReverseReportForm.setUserStatus(" ");

	}
	public void populateFileName(NescoTransactionReverseReportForm oNescoTransactionReverseReportForm,NescoTransactionReverseReportBO oNescoTransactionReverseReportBO) {
		oNescoTransactionReverseReportForm.setFile(oNescoTransactionReverseReportBO.getFile());
	}

	private void clearSession(HttpSession session) {
		session.setAttribute("oPassportTransactionListMessageBO", " ");	
		session.setAttribute("oStatusReportofNPSBOutgoingCustomerDetailsListBO", null);
		session.setAttribute("oStatusReportofNPSBOutgoingListBO", null);			
		session.setAttribute("oNescoTransactionReverseReportBO", null);		
		session.setAttribute("GSBkashWalletFilePath", null);
		session.setAttribute("GSBkashWalletFileName", null);
	}

}





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

import com.adminpanel.merchantadminpanel.nesco.report.bo.NescoDayEndTransactionSettlementReportBO;
import com.adminpanel.merchantadminpanel.nesco.report.dao.NescoDayEndTransactionSettlementReportDAO;
import com.adminpanel.merchantadminpanel.nesco.report.formbean.NescoDayEndTransactionSettlementReportForm;
import com.adminpanel.utility.RemoveNullValue;
import com.adminpanel.utility.ReportManager;

public class NescoDayEndTransactionSettlementReportAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		NescoDayEndTransactionSettlementReportBO oNescoDayEndTransactionSettlementReportBO = new NescoDayEndTransactionSettlementReportBO();
		NescoDayEndTransactionSettlementReportDAO oNescoDayEndTransactionSettlementReportDAO =	new NescoDayEndTransactionSettlementReportDAO();
		NescoDayEndTransactionSettlementReportForm oNescoDayEndTransactionSettlementReportForm =(NescoDayEndTransactionSettlementReportForm) form;
		NescoDayEndTransactionSettlementReportBO oTransfertoNescoMessageBO =	new NescoDayEndTransactionSettlementReportBO();
		RemoveNullValue oRemoveNullValue = new RemoveNullValue();
		oRemoveNullValue.removeNullValue(oNescoDayEndTransactionSettlementReportForm);
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
		String sBranchActionPathName ="/merchantadminpanel/nescoDayEndTransactionSettlementReport.do";

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

		if (sActionPath.equals("/nescoDayEndTransactionSettlementReport")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			clearForm(oNescoDayEndTransactionSettlementReportForm);
			clearSession(session);
			oNescoDayEndTransactionSettlementReportForm.setToDate(current_date);
			oNescoDayEndTransactionSettlementReportForm.setOperation("ALL");
			oNescoDayEndTransactionSettlementReportForm.setsTotalBill("");
			oNescoDayEndTransactionSettlementReportForm.setsTotalBillWithoutVat("");
			oNescoDayEndTransactionSettlementReportForm.setsTotalVat("");
			oNescoDayEndTransactionSettlementReportForm.setsTotalRevenueStamp("");
			
			session.setAttribute("GSBkashWalletFilePath", null);
			session.setAttribute("GSBkashWalletFileName", null);

			oNescoDayEndTransactionSettlementReportBO = oNescoDayEndTransactionSettlementReportDAO.getStatusListData(gsUserID, gsSessionID);
			populateStatusList(oNescoDayEndTransactionSettlementReportForm,oNescoDayEndTransactionSettlementReportBO);
			sSuccess = sSuccessAction;
		}

		else if (sActionPath.equals("/showDataNescoDayEndTransactionSettlementReport")) {
			session.setAttribute("oTransfertoNescoMessageBO"," ");
			session.setAttribute("oNescoDayEndTransactionSettlementReportBO", null);

			oNescoDayEndTransactionSettlementReportBO =oNescoDayEndTransactionSettlementReportDAO.getNescoDataPro(gsUserID,
					gsSessionID,	
					oNescoDayEndTransactionSettlementReportForm.getToDate(),
					"ALL");

			if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("0")) {
				oNescoDayEndTransactionSettlementReportBO = oNescoDayEndTransactionSettlementReportDAO.getNescoData(gsUserID, gsSessionID);
				 oNescoDayEndTransactionSettlementReportForm.setUserIDGeneratedList(oNescoDayEndTransactionSettlementReportBO.getUserIDGeneratedList());
			     session.setAttribute("oNescoDayEndTransactionSettlementReportBO", oNescoDayEndTransactionSettlementReportBO);
			     NescoDayEndTransactionSettlementReportBO  bo = oNescoDayEndTransactionSettlementReportDAO.getNescoTotalData(gsUserID, gsSessionID);
				//oNescoDayEndTransactionSettlementReportForm.setUserIDGeneratedList(bo.getUserIDGeneratedList());
				
				oNescoDayEndTransactionSettlementReportForm.setsTotalBill(bo.getsTotalBill());
				oNescoDayEndTransactionSettlementReportForm.setsTotalBillWithoutVat(bo.getsTotalBillWithoutVat());
				oNescoDayEndTransactionSettlementReportForm.setsTotalVat(bo.getsTotalVat());
				oNescoDayEndTransactionSettlementReportForm.setsTotalRevenueStamp(bo.getsTotalRevenueStamp());
				//System.out.println("getsTotalBill==>" + bo.getsTotalBill());
				//System.out.println("getsTotalBillWithoutVat==>" + bo.getsTotalBillWithoutVat());
				//System.out.println("getsTotalVat==>" + bo.getsTotalVat());
				//System.out.println("getsTotalRevenueStamp==>" + bo.getsTotalRevenueStamp());
				
				 //session.setAttribute("oNescoDayEndTransactionSettlementReportBO", oNescoDayEndTransactionSettlementReportBO);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("1")) {
				String	FundTransfertoBkashWalletReportMessage ="";							
				FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementReportBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);

				sSuccess = sFailureAction;
			} 
			else if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("3")) {
				clearSession(session);
				sSuccess = sSessionMyBankMenuAction;
			} 
			else {
				clearSession(session);
				sSuccess = sFatalErrorAction;
			}									
		} 
		
		else if (sActionPath.equals("/cancelNescoDayEndTransactionSettlementReport")) {
			session.setAttribute("oPassportTransactionListMessageBO",null);

			oNescoDayEndTransactionSettlementReportBO =oNescoDayEndTransactionSettlementReportDAO.getMenuCheckPro(gsUserID,
					gsSessionID,
					gsCompanyID,
					gsBranchID,
					request.getRemoteAddr(),
					sBranchActionPathName);

			if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("0")) {
				clearSession(session);
				String	FundTransfertoBkashWalletReportMessage ="";
				FundTransfertoBkashWalletReportMessage =oNescoDayEndTransactionSettlementReportBO.getErrorMessage();							
				session.setAttribute("oTransfertoNescoMessageBO",FundTransfertoBkashWalletReportMessage);
				sSuccess = sSuccessAction;
			} 
			else if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("1")) {
				String	StatusReportofNPSBOutgoingMessageBO =oNescoDayEndTransactionSettlementReportBO.getErrorMessage();									
				session.setAttribute("oPassportTransactionListMessageBO",StatusReportofNPSBOutgoingMessageBO);
				sSuccess = sFailureAction;
			} 
			else if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("2")) {
				clearSession(session);
				sSuccess = sSessionExpireAction;
			} 
			else if (oNescoDayEndTransactionSettlementReportBO.getErrorCode().equals("3")) {
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

	private void populateStatusList(NescoDayEndTransactionSettlementReportForm oNescoDayEndTransactionSettlementReportForm,NescoDayEndTransactionSettlementReportBO oNescoDayEndTransactionSettlementReportBO) {
		oNescoDayEndTransactionSettlementReportForm.setUserStatusList(oNescoDayEndTransactionSettlementReportBO.getUserStatusList());
		oNescoDayEndTransactionSettlementReportForm.setUserStatusNameList(oNescoDayEndTransactionSettlementReportBO.getUserStatusNameList());

	}

	public void populateMenu(NescoDayEndTransactionSettlementReportForm oNescoDayEndTransactionSettlementReportForm,NescoDayEndTransactionSettlementReportBO oNescoDayEndTransactionSettlementReportBO) {
		oNescoDayEndTransactionSettlementReportForm.setMenuList(oNescoDayEndTransactionSettlementReportBO.getMenuList());
		oNescoDayEndTransactionSettlementReportForm.setMenuNameList(oNescoDayEndTransactionSettlementReportBO.getMenuNameList());
	}

	private void clearForm(NescoDayEndTransactionSettlementReportForm oNescoDayEndTransactionSettlementReportForm) {

		
		oNescoDayEndTransactionSettlementReportForm.setToDate("");
		oNescoDayEndTransactionSettlementReportForm.setUserStatus(" ");

	}
	public void populateFileName(NescoDayEndTransactionSettlementReportForm oNescoDayEndTransactionSettlementReportForm,NescoDayEndTransactionSettlementReportBO oNescoDayEndTransactionSettlementReportBO) {
		oNescoDayEndTransactionSettlementReportForm.setFile(oNescoDayEndTransactionSettlementReportBO.getFile());
	}

	private void clearSession(HttpSession session) {
		session.setAttribute("oPassportTransactionListMessageBO", " ");	
		session.setAttribute("oStatusReportofNPSBOutgoingCustomerDetailsListBO", null);
		session.setAttribute("oStatusReportofNPSBOutgoingListBO", null);			
		session.setAttribute("oNescoDayEndTransactionSettlementReportBO", null);		
		session.setAttribute("GSBkashWalletFilePath", null);
		session.setAttribute("GSBkashWalletFileName", null);
	}

}



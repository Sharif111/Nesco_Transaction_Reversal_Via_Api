<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.adminpanel.merchantadminpanel.nesco.bo.NescoDayEndTransactionSettlementBO"%>
<%String sHeaderName = (String) session.getAttribute("GSCompanyName");
  String sCompanyLogo = (String) session.getAttribute("GSCompanyCode");%>
<html lang="en">
<head>
<bean:message key="label.metaDescription"/>
<bean:message key="label.metaKeywords"/>
<bean:message key="label.metaAuthor"/>
<bean:message key="label.metaExpires"/>
<bean:message key="label.metaImagetoolbar"/>
<bean:message key="label.metaRating"/>
<bean:message key="label.metaGenerator"/>
<bean:message key="label.metaCopyright"/>
<bean:message key="label.metaRobots"/>
<bean:message key="label.metaRevisitAfter"/>
<bean:message key="label.metaPragma"/>
<bean:message key="label.metaDocRights"/>
<bean:message key="label.metaMSSmartTagsPreventParsing"/>
<bean:message key="label.metaLanguage"/>
<bean:message key="label.metaContentType"/>
<bean:message key="label.metaContentStyleType"/>
<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="HandheldFriendly" content="true">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" href="pages/stylesheet/style.css" type="text/css">
<link rel="stylesheet" href="pages/stylesheet/example.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/calender/CalendarPopup.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/tabber.js'/>"></script>
<link rel="stylesheet" href="pages/stylesheet/jquery-ui.css" type="text/css">
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-1.9.1.js'/>"></script>
<script language="JavaScript" src="<html:rewrite page='/pages/javascript/jquery-ui.js'/>"></script>
<script language="JavaScript">var cal = new CalendarPopup();</script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="pages/assets/bootstrap/css/bootstrap.min.css">
<script src="pages/assets/bootstrap/js/bootstrap.min.js"></script>
<script language="javascript" type="text/javascript">

	function   setBodyProperty() {
  		document.oncontextmenu = function(){return false};
  		document.oncut = function(){return false};
		document.ondragstart = function(){return false};
  		document.ondrag = function(){return false};
	}
	function doMainMenu(f){
		f.action="/merchantadminpanel/cancelNescoDayEndTransactionSettlement.do";
		f.submit();
	}	
	function doLogOut(f){		
	    var f = document.nescoDayEndTransactionSettlementForm;
		f.action="/merchantadminpanel/logOutIBanking.do";
		f.submit();
	}
	function doMenuList(f){
		f.action=document.nescoDayEndTransactionSettlementForm.menu.value;
		f.submit();
	}	
	function doOperationDetails(f){
		f.action="/merchantadminpanel/showDataNescoDayEndTransactionSettlement.do";
	 	f.submit();	
	}
	
	/*function doExecute(){		
		var f = document.nescoDayEndTransactionSettlementForm;			
	 	f.action="/merchantadminpanel/executeNescoDayEndTransactionSettlement.do";
	 	f.submit();			
	}*/
	
	function doExecute(f){
		if(document.nescoDayEndTransactionSettlementForm.userStatus.value==""){
			alert('Please Select Operation');
			document.nescoDayEndTransactionSettlementForm.userStatus.focus();
		} else {
			f.action="/merchantadminpanel/executeNescoDayEndTransactionSettlement.do";
		 	f.submit();	
		}
	}
	
	$(document).ready(
                   /* This is the function that will get executed after the DOM is fully loaded */
		function () {
			$("#datepicker").datepicker({
				dateFormat: 'dd/mm/yy',//this option for formatting a Date
				changeMonth: true, //this option for allowing user to select month
				changeYear: true, //this option for allowing user to select from year range
				yearRange: "-50:+50"
			});
			
			$("#datepicker2").datepicker({
				dateFormat: 'dd/mm/yy',//this option for formatting a Date
				changeMonth: true, //this option for allowing user to select month
				changeYear: true, //this option for allowing user to select from year range
				yearRange: "-50:+50"
			});	
		}
	);	
    function doClear(f){	 
	 	f.action="/merchantadminpanel/nescoDayEndTransactionSettlement.do";
		f.submit();	 
	 } 

</script>
<style type="text/css">
.ui-datepicker {
	background: #94A3DC;
	border: 1px solid #555;
	color: #EEE;
}
tr.spaceUnder>td {
	padding-bottom: 0.3em !important;
}
.tabletrtd {
 cellpadding= 3;
 cellspacing= 1;
}
.modelul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	border: 1px solid black;
}
.modelli {
	float: left;
}
.modelli {
	display: block;
	color: #000;
	padding: 16px;
	text-decoration: none;
}
.modalHeight {
	padding-top: 60px !important;
}
.modal-header {
	background-color: #103370 !important;
	color: #FFF !important;
}
.body-clr {
	background-color: #e6e6e6 !important;
}
#modal-terms-label {
	color: #FFF;
}
#table_box {
	width: 100%;
	font-size: 13px;
	border-collapse: collapse;
	text-align: center;
}
#table_box th {
	padding: 7px;
	color: #292929;
}
#table_box td {
	padding: 4px 0 4px 0;
	color: #464646;
	border: 1px solid #CCCCCC;
}
.maintb th {
	background-color: #174797;
	color: #FFF!important;
	text-align: center;
}
#table_box tr:nth-child(odd) {
	background-color: #ffffff
} /*odd*/
#table_box tr:nth-child(even) {
	background-color: #e6e6e6
} /* even*/
#table_box tr:hover {
	background-color: #fffbae;
} /* hovering */

.tabletrtd {
 cellpadding= 3;
 cellspacing= 1;
}
</style>
</head>
<body onLoad="setBodyProperty()">
  <html:form action="/nescoDayEndTransactionSettlement" enctype="multipart/form-data" style="margin: 0">
    <table style="width: 100%; " border: "0"> 
      <tr>
             
         
        <td style="height: 50">
        <table style="width: 100%; border: 0">
            <tr>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="8%" valign="top"><img src="pages/images/logo.png" height="40px" width="200px"/></td>
                    <td width="92%" class="myBankHeader"><table width="100%" border="0" cellspacing="3" cellpadding="0">
                        <tr>
                          <td width="7%" align="left" valign="top" class="usr-6">&nbsp;</td>
                          <td width="70%" align="center" valign="top" class="usr-6"><%out.print(session.getAttribute("GSCompanyName"));%></td>
                          <td width="16%" align="right" valign="top"><font class="usr-5"> Operation ID : </font></td>
                          <td width="7%" align="left" valign="top"><font class="usr-1"><%out.print(session.getAttribute("GSTellerID"));%></font></td>
                        </tr>
                        
                        <tr>
                          <td  align="left" valign="top" class="usr-4">&nbsp;</td>
                          <td align="center" valign="top"><font class="usr-4"><span id="BranchName"></span></font></td>
                          <td align="right" valign="top"><font class="usr-5"> Open Date : </font></td>
                          <td align="left" valign="top"><font class="usr-1"><%out.print(session.getAttribute("GSBranchOpenDateDDFormat"));%></font></td>
                        </tr>
                        
                        <tr>
                          <td height="21" align="left" valign="top">&nbsp;</td>
                             <td align="center" valign="top"><font class="usr-6"></font><font class="usr-1">
                            <bean:message key="label.-"/>
                            </font><font class="usr-4">Nesco Day End Transaction Settlement</font></td>
                          <td colspan="2" align="right" valign="top"><a href="#" onClick="doMainMenu(nescoDayEndTransactionSettlementForm)"><font class="usr-5">
                            <bean:message key="label.mainMenu"/>
                            </font></a> <font class="usr-1">
                            <bean:message key="label.|"/>
                            </font> <a href="#" value="Submit"  data-toggle="modal" data-target="#confirm-logout" ><font class="usr-5">
                            <bean:message key="label.signOut"/>
                            </font></a></td>
                        </tr>
                        
                      </table></td>
                  </tr>
                </table></td>
            </tr>
            
  
            <tr>
              <td colspan="3"><table style="width: 100%; border: 0">
                  <tr>
                    <td width="1%">&nbsp;</td>
                    <td width="98%"><table style="width: 100%; border: 0" cellpadding="3" cellspacing="1" >
  
                        <tr>
                          <td rowspan="3" align="center" class="msg-00"><strong>
                          <%out.print(session.getAttribute("oTransfertoNescoMessageBO"));%>
                          </strong></td>
                        </tr>
             
                      </table></td>
                    <td width="1%">&nbsp;</td>
                  </tr>
                </table></td>
            </tr>
          </table></td>
      </tr>
      
      
      <tr>
        <td valign="top">
            <table style="width: 100%; border: 0">
                <tr>
                  <td><table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="1" >
                  
                    <tr>
                      <td width="25%">&nbsp;</td>
                      <td width="20%">&nbsp;</td>
                      <td width="2%">&nbsp;</td>
                      <td width="20%">&nbsp;</td>
                      <td width="2%">&nbsp;</td>
                      <td width="30%">&nbsp;</td>
                      <td width="2%">&nbsp;</td>
                      <td width="20%">&nbsp;</td>
                      <td width="2%">&nbsp;</td>
                      <td width="9%">&nbsp;</td>
                    </tr>
                    <tr class="spaceUnder">
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>                         
                    </tr>
  
            <tr class="spaceUnder">
              <td>&nbsp;</td>
              <td align="right" class="lbl-n">Date </td>
              <td>&nbsp;</td>
              <td><html:text property="toDate" styleClass="txt-n" style="width:210px" styleId="datepicker2"/></td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
              <tr class="spaceUnder">
              <td>&nbsp;</td>
              <td align="right" class="lbl-n">Operation</td>
              <td>&nbsp;</td>
              <td onKeyPress="doOperationDetails(nescoDayEndTransactionSettlementForm)"><html:select property="userStatus"  style="width:210px;" onchange="doOperationDetails(nescoDayEndTransactionSettlementForm)">
                        <html:option value="" styleClass="txt-n">
                          <bean:message key="label.pleaseSelect"/>
                        </html:option>
                        <html:options property="userStatusList" styleClass="txt-n" labelProperty="userStatusNameList"/>
                      </html:select></td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>&nbsp;</td>                    
            </tr>
            <tr class="spaceUnder">
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td  class="lbl-n"></td>
              <td align="right mt-2"><input type="button" value="Submit" style="color:#03F;width:105px" data-toggle="modal" data-target="#confirm-submit" id="exeButton" />
                <input type="button" value="Clear" style="color:#03F;width:105px;margin-right:50px" name="sendMail" onClick="doClear(nescoDayEndTransactionSettlementForm)" /></td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr class="spaceUnder">
              <td>&nbsp;</td>
             <td>&nbsp;</td>
             <td>&nbsp;</td>
             <td>&nbsp;</td>
             <td>&nbsp;</td>       
             <td align="right" class="lbl-n">Total Amount</td>
             <td>&nbsp;</td>
             <td ><html:text property="sTotalBill" styleClass="txt-c" readonly="true" style="width:70px"/></td>
             <td>&nbsp;</td>
             <td>&nbsp;</td>
           </tr> 
           <tr class="spaceUnder">
            <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>       
           <td align="right" class="lbl-n">Amount without Vat</td>
           <td>&nbsp;</td>
           <td ><html:text property="sTotalBillWithoutVat" styleClass="txt-c" readonly="true" style="width:70px"/></td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
         </tr> 
         <tr class="spaceUnder">
          <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>       
         <td align="right" class="lbl-n">Total Vat</td>
         <td>&nbsp;</td>
         <td ><html:text property="sTotalVat" styleClass="txt-c" readonly="true" style="width:70px"/></td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
       </tr> 
       <tr class="spaceUnder">
        <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>       
       <td align="right" class="lbl-n">Total Revenue</td>
       <td>&nbsp;</td>
       <td ><html:text property="sTotalRevenueStamp" styleClass="txt-c" readonly="true" style="width:70px"/></td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
     </tr> 
            
            <tr class="spaceUnder">
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            
           </table></td>
          </tr>
         </table>
        </div>          
       </td>
      </tr>
         
      
    <div class="modal fade" id="confirm-submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-sm">
          <div class="modal-content">
              <div class="modal-header" style="background-color:#FFF; font-weight:bold">
                 Wait !
              </div>
              
              <div class="modal-body">
                  Are you sure you want to Submit ?
             </div>
  
         <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
              <a href="#" onClick="doExecute(nescoDayEndTransactionSettlementForm)" class="btn btn-success success">Submit</a>
          </div>
      </div>
    </div>
  </div>
  
  
  <div class="modal fade" id="confirm-logout" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-sm">
          <div class="modal-content">
              <div class="modal-header">
                Wait !
              </div>
              <div class="modal-body">
                 Are you sure? If you confirm press Log Out button
             </div>
  
           <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
              <a href="#" onClick="doLogOut()" class="btn btn-success success">Log Out</a>
          </div>
      </div>
    </div>
  </div>    
  </table></td>
  </tr>
  <tr>
    <td colspan="9">
        <table style="width: 100%" border="1" align="center" cellpadding="3" cellspacing="3" id="table_box" class="maintb">
             
        <tr bgcolor="#99CCCC">
          <th valign="middle" width="4%" style="height: 35" class="lbl-b-08" align="center">Sl</th>
            <th valign="middle" width="3%" class="lbl-b-08" align="center">Reference Id</th>   
            <th valign="middle" width="3%" class="lbl-b-08" align="center">Debit Account</th>                                                         
            <th valign="middle" width="3%" class="lbl-b-08" align="center">Bill No</th>                                    
            <th valign="middle" width="3%" class="lbl-b-08" align="right">Amount</th>
            <th valign="middle" width="3%" class="lbl-b-08" align="center">Vat</th>                  
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Due Date</th>                
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Lpc</th> 
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Total Bill</th>                                                         
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Comission</th>       
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Comission On Vat</th>  
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Revenewstamp</th>            
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Agent Doc Num</th>
            <th valign="middle" width="6%" class="lbl-b-08" align="left">Agent Msg</th>
            <th valign="middle" width="6%" class="lbl-b-08" align="left">Nesco Res Msg</th>
            <th valign="middle" width="3%" class="lbl-b-08" align="left">Create By</th> 
                         
          </tr>
          <%
					if(session.getAttribute("oNescoDayEndTransactionSettlementBO")!=null){
			NescoDayEndTransactionSettlementBO mainBO=(NescoDayEndTransactionSettlementBO)session.getAttribute("oNescoDayEndTransactionSettlementBO");
					ArrayList aDestinationBankList=mainBO.getUserIDGeneratedList();
					if(aDestinationBankList==null)aDestinationBankList=new ArrayList();
					for(int i=0;i<aDestinationBankList.size();i++){
			NescoDayEndTransactionSettlementBO bo=(NescoDayEndTransactionSettlementBO)aDestinationBankList.get(i);
                    String sReferenceId = bo.getsReferenceId();
                    String sDebitAcc = bo.getsDebitAcc();
                    String sBillNo = bo.getsBillNo();
                    String sAmount = bo.getsAmount();
                    String sVat = bo.getsVat();
                    String sDueDate = bo.getsDueDate();
                    String sLpc = bo.getsLpc();
                    String sTotalBill = bo.getsTotalBill();
                    String sComission = bo.getsComission();
                    String sComissionOnVat = bo.getsComissionOnVat();
                    String sRevenuestamp = bo.getsRevenuestamp();
                    String AgentDocNum = bo.getAgentDocNum();  
                    String sAgentMsg = bo.getsAgentMsg();
                    String NescoResMsg = bo.getNescoResMsg();  
                    String sCreateBy = bo.getsCreateBy();       																							
					%>
                  <tr bgcolor="#99CCFF">
                  <td valign="top" class="lbl-08" align="center"><%= bo.getsSl()%></td>  
                    <td valign="top" class="lbl-08" align="center"><%=sReferenceId%></td>                                      
                    <td align="center" valign="top" class="lbl-08"><%=sDebitAcc%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sBillNo%></td> 
                    <td align="center" valign="top" class="lbl-08"><%=sAmount%></td>                                       
                    <td align="center" valign="top" class="lbl-08"><%=sVat%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sDueDate%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sLpc%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sTotalBill%></td> 
                    <td align="center" valign="top" class="lbl-08"><%=sComission%></td>                                       
                    <td align="center" valign="top" class="lbl-08"><%=sComissionOnVat%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sRevenuestamp%></td>                        
                    <td align="center" valign="top" class="lbl-08"><%=AgentDocNum%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sAgentMsg%></td>
                    <td align="center" valign="top" class="lbl-08"><%=NescoResMsg%></td>
                    <td align="center" valign="top" class="lbl-08"><%=sCreateBy%></td>                         
                  </tr>
                  <%         
			  }
		      }
		    %>
       
       </table>
     </td>
  </tr>
    </table>
    
  </html:form>
  
  </body>
</html>


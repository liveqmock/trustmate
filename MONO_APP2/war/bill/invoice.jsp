<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<HTML>
	<HEAD>
		<TITLE>${merchantSettleBean.settleId }</TITLE>
		<link href="/assets/css/statement.css" rel="stylesheet" type="text/css">
		<script src="/assets/invoice/common.js" type="text/javascript"></script>
		<script src="/assets/invoice/iezn_embed_patch-unenfant.js" type="text/javascript"></script>		
		<script type="text/javascript" src="/assets/invoice/print.js"></script>

	</HEAD>
 
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="pDiv" align="center" valign="top"><!-- 프린트 데이터 시작-->
<table width="100%" border="0" cellspacing="0" cellpadding="0">    
    <!-- 검색된 결과 데이터 시작-->
	<tr>
		<td align="center" valign="top" colspan="3">
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr height="10">
					<td height="10" width="55%">&nbsp;</td>
					<td colspan=-10 class=xl15617227 width="45%" align="right" style='width:138pt'>&nbsp;</td>
				</tr> 
				<tr height=25 >
					<td valign="top" width="50%">
						<table width="100%"  border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="top_left_addr"><strong>Invoice No.&nbsp;&nbsp;  ${merchantSettleBean.settleId }&nbsp;(${fn:toUpperCase(mBean.merchantId)})</strong></td>
							</tr>
							<tr>
								<td class="top_left_addr">&nbsp;</td>
							</tr>
							<tr>
								<td class="top_left_addr"><strong>${fn:toUpperCase(mBean.name)}</strong></td>
							</tr>
							<tr>
								<td class="top_left_addr" align="left">${mBean.addr1}</td>
							</tr>
							<tr>
								<td class="top_left_addr" align="left">${mBean.addr2}</td>
							</tr>
							
						</table>
					</td>
					<td valign="top" width="50%">	
						<table width="100%"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="top_right_addr"><strong>${configBean.line1}</strong></td>
						</tr>
						<tr>
							<td class="top_right_addr">${configBean.line2}</td>
						</tr>
						<tr>
							<td  class="top_right_addr">${configBean.line3}</td>
						</tr>
						<tr>
							<td  class="top_right_addr">${configBean.line4}</td>
						</tr>
						<tr>
							<td  class="top_right_addr">${configBean.line5}</td>
						</tr>
						</table>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
	<tr>
		<td height="20" colspan="3"> </td>
	</tr>
	<tr>
		<td align="left" valign="top" colspan="3" border="0" cellpadding="0" cellspacing="0">
			<table width="95%" align="center" valign="top" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					<table width="95%" align="center" valign="top" border="0" cellpadding="0" cellspacing="0">
						<tr height="30">
							<td align="center"><font size="5"><strong>INVOICE</strong></font></td>
						</tr>
						<tr height="5">
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border:1 solid #0066CC">
									<tr>
										<td height="5" colspan="7"> </td>
									</tr>
									<tr>
										<td width="20"></td>
										<td width="150" height="18" align="center" class="td_title">* Period</td>
										<td colspan="5" class="xl14517227">${fn:substring(merchantSettleBean.startDay,0,4)}/${fn:substring(merchantSettleBean.startDay,4,6)}/${fn:substring(merchantSettleBean.startDay,6,8)}  
											&nbsp; - &nbsp;
											${fn:substring(merchantSettleBean.endDay,0,4)}/${fn:substring(merchantSettleBean.endDay,4,6)}/${fn:substring(merchantSettleBean.endDay,6,8)}
										</td>
									</tr>
									<tr>
										<td width="20"></td>
										<td width="150" height="18" align="center" class="td_title">* Account</td>
										<td colspan="5" class="xl14517227">${merchantSettleBean.bankInfo}　
											<c:if test="${!empty merchantSettleBean.account}">
												${fn:substring(merchantSettleBean.account,0,fn:length(merchantSettleBean.account)-3)}***
											</c:if>
										</td>
									</tr>
									<tr>
										<td width="20"></td>
										<td width="150" height="18" align="center" class="td_title">* Total Amount</td>
										<td width="120" class="xl14517227">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.totAmt }" pattern="#,###.###" />&nbsp;${payCurType}&nbsp;(A)</td>
										<td width="110" align="center" class="td_title">* Total Fees</td>
										<td width="120" class="xl14517227">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.totFee }" pattern="#,###.###" />&nbsp;${payCurType}&nbsp;(B)</td>
										<td width="110" align="center" class="td_title">* Deposit</td>
										<td class="xl14517227">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.totDeposit }" pattern="#,###.###" />&nbsp;${payCurType}&nbsp;(C)</td>
									</tr>
									<tr>
										<td width="20"></td>
										<td width="150" height="18" align="center" class="td_title"></strong></td>
										<td colspan="5" class="xl14517227"><strong><u><fmt:formatNumber type="number" value="${merchantSettleBean.totSettle }" pattern="#,###.###" />&nbsp;${payCurType} </u>&nbsp;(A)-(B)-(C)</strong></td>
									</tr>
									<tr>
										<td height="5" colspan="7"> </td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border:1 solid #0066CC">
									<tr>
										<td height="5"></td>
									</tr>
									<tr height="18" class="hiddentr">
										<td valign="top" class="td_title">&nbsp;&nbsp;&nbsp;I&nbsp;&nbsp;&nbsp;[Transactional Information]</td>
									</tr>
									<tr >
										<td valign="top">
										<table width="740" align="center" border="1" cellpadding="0" cellspacing="0" class="reference">
											<tr>
												<td width="20" class="td_emphasis">No</td>
												<td	width="345" class="td_emphasis">Period</td>
												<td	width="125" class="td_emphasis">Card Type</td>
												<td width="125" class="td_emphasis">Volume</td>
												<td width="125" class="td_emphasis">Amount</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">①</td>
												<td width="345" class="td_sub_title1">&nbsp;
													${fn:substring(merchantSettleBean.startDay,0,4)}/${fn:substring(merchantSettleBean.startDay,4,6)}/${fn:substring(merchantSettleBean.startDay,6,8)}  
													&nbsp; ~ &nbsp;
													${fn:substring(merchantSettleBean.endDay,0,4)}/${fn:substring(merchantSettleBean.endDay,4,6)}/${fn:substring(merchantSettleBean.endDay,6,8)}
												</td>
												<td width="125" class="td_noemp_num">VISA ・ MASTER</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnVmCnt }" pattern="##,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnVmAmt }" pattern="#,###,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">②</td>
												<td width="345" class="td_sub_title1">&nbsp;
													${fn:substring(merchantSettleBean.startDay,0,4)}/${fn:substring(merchantSettleBean.startDay,4,6)}/${fn:substring(merchantSettleBean.startDay,6,8)}  
													&nbsp; ~ &nbsp;
													${fn:substring(merchantSettleBean.endDay,0,4)}/${fn:substring(merchantSettleBean.endDay,4,6)}/${fn:substring(merchantSettleBean.endDay,6,8)}
												</td>
												<td width="125" class="td_noemp_num">JCB ・ AMEX</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnJaCnt }" pattern="##,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnJaAmt }" pattern="#,###,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">③</td>
												<td width="345" class="td_sub_title1">&nbsp;
													~ ${fn:substring(merchantSettleBean.startDay,0,4)}/${fn:substring(merchantSettleBean.startDay,4,6)}/${fn:substring(merchantSettleBean.startDay,6,8)}
												</td>
												<td width="125" class="td_noemp_num">VISA ・ MASTER</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.preTrnVmCnt }" pattern="##,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.preTrnVmAmt }" pattern="#,###,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">④</td>
												<td width="345" class="td_sub_title1">&nbsp;
													~ ${fn:substring(merchantSettleBean.startDay,0,4)}/${fn:substring(merchantSettleBean.startDay,4,6)}/${fn:substring(merchantSettleBean.startDay,6,8)}
												</td>
												<td width="125" class="td_noemp_num">JCB ・ AMEX</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.preTrnJaCnt }" pattern="##,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.preTrnJaAmt }" pattern="#,###,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_emphasis">⑤</td>
												<td width="345" class="td_emphasis1">&nbsp;Total Amount</td>
												<td width="125" class="td_emphasis">&nbsp; </td>
												<td width="125" class="td_sub_title3_emp_right">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnVmCnt + merchantSettleBean.trnJaCnt +merchantSettleBean.preTrnVmCnt + merchantSettleBean.preTrnJaCnt }" pattern="##,###" />&nbsp;</td>
												<td width="125" class="td_sub_title3_emp_right">(A)&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnVmAmt + merchantSettleBean.trnJaAmt+merchantSettleBean.preTrnVmAmt + merchantSettleBean.preTrnJaAmt}" pattern="#,###,###.###" />&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td height="5"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border:1 solid #0066CC">
									<tr>
										<td height="5"></td>
									</tr>
									<tr height="20" class="hiddentr">
										<td valign="top" class="td_title">&nbsp;&nbsp;&nbsp;II&nbsp;&nbsp;[CB&amp;Refund Statement]</td>
									</tr>
									<tr >
										<td valign="top">
										<table width="740" align="center" border="1" cellpadding="0" cellspacing="0" class="reference">
											<tr >
												<td width="20"  class="td_emphasis">No</td>
												<td width="345"	class="td_emphasis">Description</td>
												<td	width="125" class="td_emphasis">Card Type</td>
												<td width="125" class="td_emphasis"> Volume</td>
												<td width="125" class="td_emphasis">Amount</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">①</td>
												<td width="345"	class="td_sub_title1">&nbsp;Chargeback</font></td>
												<td width="125" class="td_noemp_num">&nbsp;</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbCnt }" pattern="#,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">②</td>
												<td width="345"	class="td_sub_title1">&nbsp;Refunded Card Payment</td>
												<td width="125" class="td_noemp_num">VISA ・ MASTER</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.refundVmCnt }" pattern="#,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.refundVmAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">③</td>
												<td width="345"	class="td_sub_title1">&nbsp;Refunded Card Payment</td>
												<td width="125" class="td_noemp_num">JCB ・ AMEX</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.refundJaCnt }" pattern="#,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.refundJaAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">④</td>
												<td width="345"	class="td_sub_title1">&nbsp;Refunded Card Payment from Previous Settlement</td>
												<td width="125" class="td_noemp_num">VISA ・ MASTER</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.preRefundVmCnt }" pattern="#,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.preRefundVmAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_noemp_num">⑤</td>
												<td width="345"	class="td_sub_title1">&nbsp;Refunded Card Payment from Previous Settlement</td>
												<td width="125" class="td_noemp_num">JCB ・ AMEX</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.preRefundJaCnt }" pattern="#,###" />&nbsp;</td>
												<td width="125" class="td_sub_title2_right" align="right"><fmt:formatNumber type="number" value="${merchantSettleBean.preRefundJaAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20" class="td_emphasis">⑥</td>
												<td width="345"	class="td_emphasis1">&nbsp;Total Amount</td>
												<td width="125" class="td_emphasis">&nbsp; </td>
												<td width="125" class="td_sub_title3_emp_right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbCnt + merchantSettleBean.refundVmCnt + merchantSettleBean.refundJaCnt + merchantSettleBean.preRefundVmCnt + merchantSettleBean.preRefundJaCnt }" pattern="#,###" />&nbsp;</td>
												<td width="125" class="td_sub_title3_emp_right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbAmt + merchantSettleBean.refundVmAmt + merchantSettleBean.refundJaAmt + merchantSettleBean.preRefundVmAmt + merchantSettleBean.preRefundJaAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td height="5" colspan="5"> </td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border:1 solid #0066CC">
									<tr>
										<td height="5"></td>
									</tr>
									<tr height="20" class="hiddentr">
										<td valign="top" class="td_title">&nbsp;&nbsp;&nbsp;III&nbsp;[Fees]</td>
									</tr>
									<tr>
										<td valign="top">
											<table width="740" align="center" border="1" cellpadding="0" cellspacing="0" class="reference">
												<tr>
													<td width="20"  class="td_emphasis">No</td>
													<td width="595" colspan="3" class="td_emphasis">Description</td>
													<td width="125" class="td_emphasis">Amount</td>
												</tr>
												<tr>
													<td width="20"  class="td_noemp_num">①</td>
													<td width="345" class="td_sub_title1">&nbsp;MDR [V／M]</td>
													<td width="125" class="td_noemp_num">(I-①)</td>
													<td width="125" class="td_sub_title2_right"  >&nbsp;×&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnVmRate * 100 }" pattern="#,###.###" />&nbsp;%</td>
													<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantSettleBean.trnVmFee +merchantSettleBean.preTrnVmFee}" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">②</td>
													<td width="345"  class="td_sub_title1">&nbsp;MDR [J／A]</td>
													<td width="125"  class="td_noemp_num">(I-②)</td>
													<td width="125"  class="td_sub_title2_right"  >&nbsp;×&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.trnJaRate * 100 }" pattern="#,###.###" />&nbsp;%</td>
													<td width="125"  class="td_sub_title2_right"  ><fmt:formatNumber type="number" value="${merchantSettleBean.trnJaFee + merchantSettleBean.preTrnJaFee  }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">③</td>
													<td width="345" class="td_sub_title1">&nbsp;Annual </td>
													<td width="125" class="td_noemp_num">&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.manageFee}" pattern="##,###.###" />&nbsp;${payCurType}</td>
													<td width="125" class="td_sub_title2_right">&nbsp;×&nbsp;&nbsp;&nbsp;
														<c:if test="${merchantSettleBean.manageFee !=0}">1</c:if>
														<c:if test="${merchantSettleBean.manageFee ==0}">0</c:if>
													</td>
													<td width="125" class="td_sub_title2_right"  ><fmt:formatNumber type="number" value="${merchantSettleBean.manageFee }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">④</td>
													<td width="345" class="td_sub_title1">&nbsp;Chargeback</td>
													<td width="125" class="td_noemp_num"><fmt:formatNumber type="number" value="${merchantSettleBean.cbCnt }" pattern="#,###" /></td>
													<td width="125" class="td_sub_title2_right"  >&nbsp;×&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.cbRate }" pattern="#,###" />&nbsp;${payCurType}</td>
													<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbFee }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">⑤</td>
													<td width="345" class="td_sub_title1">&nbsp;Refund</td>
													<td width="125" class="td_noemp_num"><fmt:formatNumber type="number" value="${merchantSettleBean.refundVmCnt + merchantSettleBean.refundJaCnt+merchantSettleBean.preRefundVmCnt + merchantSettleBean.preRefundJaCnt }" pattern="#,###" /></td>
													<td width="125" class="td_sub_title2_right">&nbsp;×&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.refundVmRate}" pattern="#,###.###" />&nbsp;${payCurType}</td>
													<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${(merchantSettleBean.refundVmCnt + merchantSettleBean.refundJaCnt+merchantSettleBean.preRefundVmCnt + merchantSettleBean.preRefundJaCnt) * merchantSettleBean.refundVmRate }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													 <td width="20" class="td_noemp_num">⑥</td>
                                                     <td width="345" class="td_sub_title1">&nbsp;Transaction</td>
                                                     <td width="125" class="td_noemp_num"><fmt:formatNumber type="number" value="${merchantSettleBean.vanCnt }" pattern="#,###" /></td>
                                                     <td width="125" class="td_sub_title2_right"  >&nbsp;×&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.vanRate }" pattern="#,###.##" />&nbsp;${payCurType}</td>
 													 <td width="125" class="td_sub_title2_right"  ><fmt:formatNumber type="number" value="${merchantSettleBean.vanFee }" pattern="#,###.###" />&nbsp;</td>  
 												</tr>
												<tr>
													 <td width="20" class="td_noemp_num">⑦</td>
													 <td width="345" class="td_sub_title1">&nbsp;${merchantSettleBean.etcName }</td>
													 <td width="125" class="td_noemp_num"><fmt:formatNumber type="number" value="${merchantSettleBean.etcAmt }" pattern="#,###.###" />&nbsp;${payCurType}</td>
													 <td width="125" class="td_sub_title2_right">&nbsp;×&nbsp;&nbsp;&nbsp;
													 	<c:if test="${merchantSettleBean.etcAmt !=0}">1</c:if>
														<c:if test="${merchantSettleBean.etcAmt ==0}">0</c:if>
													 </td>
													 <td width="125" class="td_sub_title2_right"  ><fmt:formatNumber type="number" value="${merchantSettleBean.etcAmt }" pattern="#,###.###" />&nbsp;</td>
												</tr>
											
												<tr>
													<td width="20" class="td_noemp_num" style="background-color:#99ccff;">⑧</td>
													<td width="345" class="td_sub_title1" style="background-color:#99ccff;">Total as Above</td>
													<td width="125" class="td_noemp_num" style="background-color:#99ccff;">Sum of ① and ⑦</td>
													<td width="125" class="td_noemp_num" style="background-color:#99ccff;">&nbsp;</td>
													<td width="125" class="td_sub_title2_right"  style="background-color:#99ccff;"><fmt:formatNumber type="number" value="${merchantSettleBean.trnVmFee + merchantSettleBean.preTrnVmFee+ merchantSettleBean.trnJaFee + merchantSettleBean.trnJaFee+merchantSettleBean.preTrnJaFee +merchantSettleBean.manageFee + merchantSettleBean.vanFee + merchantSettleBean.cbFee + merchantSettleBean.refundVmFee + merchantSettleBean.refundJaFee+ merchantSettleBean.preRefundVmFee + merchantSettleBean.preRefundJaFee + merchantSettleBean.etcAmt }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="365" class="td_noemp_num" colspan="2">VAT</td>
													<td width="125" class="td_noemp_num"><fmt:formatNumber type="number" value="${merchantBillBean.vat *100}" pattern="##.#" />&nbsp;%</td>
													<td width="125" class="td_noemp_num">&nbsp;</td>
													<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantSettleBean.vatAmt }" pattern="#,###.##" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">⑨</td>
													<td width="345" class="td_sub_title1">&nbsp;Remittance</td>
													<td width="125" class="td_noemp_num">1</td>
													<td width="125" class="td_sub_title2_right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.transfeFee }" pattern="#,###.###" />&nbsp;${payCurType}</td>
													<td width="125" class="td_sub_title2_right"  ><fmt:formatNumber type="number" value="${merchantSettleBean.transfeFee }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num" style="background-color:#99ccff;">⑩</td>
													<td width="345" class="td_sub_title1_emp" style="background-color:#99ccff;">&nbsp;Total Charging</td>
													<td width="125" class="td_noemp_num" style="background-color:#99ccff;">Sum of ① and ⑨</td>
													<td width="125" class="td_noemp_num" style="background-color:#99ccff;">&nbsp;</td>
													<td width="125" class="td_sub_title2_right" style="background-color:#99ccff;">
														<fmt:formatNumber type="number" value="${merchantSettleBean.trnVmFee + merchantSettleBean.preTrnVmFee+ merchantSettleBean.trnJaFee+merchantSettleBean.preTrnJaFee + merchantSettleBean.manageFee + merchantSettleBean.vanFee + merchantSettleBean.cbFee + merchantSettleBean.refundVmFee + merchantSettleBean.refundJaFee+ merchantSettleBean.preRefundVmFee + merchantSettleBean.preRefundJaFee + merchantSettleBean.etcAmt +merchantSettleBean.vatAmt }" pattern="#,###.###" />&nbsp;
													</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">⑪</td>
													<td width="345" class="td_sub_title1">&nbsp;Chargeback Amount</td>
													<td width="125" class="td_noemp_num">II-①</td>
													<td width="125" class="td_noemp_num">&nbsp;</td>
													<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbAmt }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">⑫</td>
													<td width="345" class="td_sub_title1">&nbsp;Refund deduction of carried over monthly [V／M]</td>
													<td width="125" class="td_noemp_num">II-④</td>
													<td width="125" class="td_noemp_num">&nbsp;</td>
													<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantSettleBean.preRefundVmAmt}" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_noemp_num">⑬</td>
													<td width="345" class="td_sub_title1">&nbsp;Refund deduction of carried over monthly [A／J]</td>
													<td width="125" class="td_noemp_num">II-⑤</td>
													<td width="125" class="td_noemp_num">&nbsp;</td>
													<td width="125" class="td_sub_title2_right"  ><fmt:formatNumber type="number" value="${merchantSettleBean.preRefundJaAmt }" pattern="#,###.###" />&nbsp;</td>
												</tr>
												
												<tr>
													<td width="20" class="td_emphasis">⑭</td>
													<td width="345" class="td_emphasis1">&nbsp;Total Deduction</td>
													<td width="125" class="td_emphasis">Sum of ⑪ and ⑬</td>
													<td width="125" class="td_emphasis">&nbsp;</td>
													<td width="125" class="td_sub_title3_emp_right"><fmt:formatNumber type="number" value="${merchantSettleBean.cbAmt + merchantSettleBean.preRefundVmAmt + merchantSettleBean.preRefundJaAmt}" pattern="#,###.###" />&nbsp;</td>
												</tr>
												<tr>
													<td width="20" class="td_emphasis">⑮</td>
													<td width="345" class="td_emphasis1">&nbsp;Total Charging &amp; Total Deduction</td>
													<td width="125" class="td_emphasis">Sum of ⑩ and ⑭</td>
													<td width="125" class="td_emphasis">&nbsp;</td>
													<td width="125" class="td_sub_title3_emp_right">(B)&nbsp;<fmt:formatNumber type="number" value="${merchantSettleBean.totFee}" pattern="#,###.###" />&nbsp;</td>
												</tr>
											</table>
										</td>
									<tr>
										<td height="5"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border:1 solid #0066CC">
									<tr>
										<td height="5"></td>
									</tr>
									<tr height="20" class="hiddentr">
										<td valign="top" class="td_title">&nbsp;&nbsp;&nbsp;IV&nbsp;[Deposit]</td>
									</tr>
									<tr >
										<td valign="top">
										<table width="740" align="center" border="1" cellpadding="0" cellspacing="0" class="reference">
											<tr >
												<td width="20"  class="td_emphasis">No</td>
												<td	width="595" colspan="3" class="td_emphasis">Description</td>
												<td width="125" class="td_emphasis">Amount</td>
											</tr>
											<tr>
												<td width="20"  class="td_noemp_num">①</td>
												<td width="345"	class="td_sub_title1">&nbsp;Accumulated Balance</td>
												<td width="125" class="td_noemp_num">&nbsp;</td>
												<td width="125" class="td_sub_title2_left">&nbsp;</td>
												<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantDepositBean.lastAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20"  class="td_noemp_num">②</td>
												<td width="345"	class="td_sub_title1">&nbsp;Current Deposit</td>
												<td width="125" class="td_noemp_num"></td>
												<td width="125" class="td_sub_title2_right">&nbsp;×&nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${merchantDepositBean.rate*100 }" pattern="#,###.###" />&nbsp;%</td>
												<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantDepositBean.curAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20"  class="td_noemp_num">③</td>
												<td width="345"	class="td_sub_title1">&nbsp;Release Deposit</td>
												<td width="125" class="td_noemp_num">&nbsp;</td>
												<td width="125" class="td_sub_title2_left">&nbsp;</td>
												<td width="125" class="td_sub_title2_right"><fmt:formatNumber type="number" value="${merchantDepositBean.payAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20"  class="td_emphasis">④</td>
												<td width="595"	class="td_emphasis" colspan="3">Balance( ① + ② - ③ )</td>
												<td width="125" class="td_sub_title3_emp_right"><fmt:formatNumber type="number" value="${merchantDepositBean.totAmt }" pattern="#,###.###" />&nbsp;</td>
											</tr>
											<tr>
												<td width="20"  class="td_emphasis">※</td>
												<td width="595"	class="td_emphasis" colspan="3">Total Deposit( ② - ③ )</td>
												<td width="125" class="td_sub_title3_emp_right">(C)&nbsp;<fmt:formatNumber type="number" value="${merchantDepositBean.curAmt - merchantDepositBean.payAmt}" pattern="#,###.###" />&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td height="5"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr> 
							<td height="5"></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td height="10"></td>
				</tr>
			</table>
			
		</td>
	</tr>
</table>
</div><!-- 프린트 데이터 END-->
<div id="pBtn">

<table width="100%" border="0" cellspacing="0" cellpadding="0">    
	<tr>
		<td align="center" valign="top">
			<table width="85.5%" border="0" cellpadding="0" cellspacing="0">
				<tr height="25">
					<td width="55%" height="20">&nbsp;</td>
					<td width="45%" align="right">
						
						<input onclick="javascript:printDivSX('${merchantSettleBean.settleId }');" class="btnClassic" type="button" value="print" title="The only possible printing is Explorer" />
						<input onclick="self.close();" class="btnClassic" type="button" value="Close Window"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>	
</table>
</div>
</body>
</html>
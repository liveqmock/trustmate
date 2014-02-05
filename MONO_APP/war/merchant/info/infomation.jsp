<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/merchant/include/header.jsp" />
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/merchant/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<div class="box">
					<!-- forms -->
					<!-- box / title -->
					
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h6> 1.MERCHANT INFOMATION </h6>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">MERCHANT ID</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"> <strong>${mBean.merchantId }&nbsp;</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">MERCHANT NAME</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${mBean.name }&nbsp;</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">PASSWORD</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<strong><a href="javascript:showloading(false);popup('passwordUpdate','/merchant/info.do?request=passwordModi',540,300,100,100);"><font color="red">Change Password</font></a></strong>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">PASSWORD UPDATE</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>
										<c:if test="${mBean.pwUpdate == 'Y'}">YES</c:if>
          								<c:if test="${mBean.pwUpdate == 'N'}">NO</c:if>&nbsp;</strong>
          							</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">PRODUCT</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.product}&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">HOMEPAGE </label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.homepage}&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">ADDRESS</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">[${mBean.zipCode }]&nbsp;${mBean.addr1 }${mBean.addr2 }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">TELEPHONE NUMBER</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.telNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">FAX NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.fax }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.email }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">IDENTIFICATION NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.identiNo }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">BANK NAME</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.settleBank }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">BANK ACCOUNT</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.settleBankCode }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">BANK ACCOUNT </label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.settleAccount }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">STATUS</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:if test="${mBean.active == '1'}">Active</c:if>
							          	<c:if test="${mBean.active == '2'}">Non-Active</c:if>
							          	<c:if test="${mBean.active == '3'}">Termination</c:if>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">SERVICE DATE</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${mBean.serviceDate }" pattern="yyyy/MM/dd/ HH:mm:ss" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">REGISTRATION DATE</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${mBean.regDate }" pattern="yyyy/MM/dd/ HH:mm:ss" />&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL40">
								<h6>2.CEO INFOMATION</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">NAME</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.ceoName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">NAME(ENGLISH)</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.ceoEngName }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">ADDRESS</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">[${mBean.ceoZipCode }] &nbsp; ${mBean.ceoAddr1 }${mBean.ceoAddr2 }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">TELEPHONE NUMBER</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.ceoTelNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">MOBILE_NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.ceoPhoneNo }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.ceoEMail }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">IDENTIFICATION NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.ceoIdentiNo }&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>3.BILLING MANAGER</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">NAME</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.settlePartName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.settlePartEMail }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">TELEPHONE NUMBER</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.settlePartTelNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>4.TECHNICAL MANAGER </h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">NAME</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.devPartName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.devPartEMail }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">TELEPHONE NUMBER</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.devPartTelNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>5.MERCHANT SERVICE INFOMATION</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">CURRENCY TYPE</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mngBean.curType }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">TRANSACITON TYPE</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<strong>
										<c:if test="${mngBean.demo =='Y' }">
											Test Mode
										</c:if>
										<c:if test="${mngBean.demo =='N' }">
											Active Mode
										</c:if></strong>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>6.DEPOSIT INFOMATION</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">Before</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${dBean.lastAmount }" pattern="#,##0.00" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">Current</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${dBean.currAmount }" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">Applicable percentage</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="percent" value="${dBean.currentRate / 100}" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">Balance</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${dBean.totalAmount }" pattern="#,##0.00" /></span>
								</div>
							</div>
							
						</div>
					</div>
					
					
				</div>	
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/merchant/include/footer.jsp" />
		<!-- end footert -->
		
	</body>
</html>

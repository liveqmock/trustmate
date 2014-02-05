<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/group/include/header.jsp" />
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/group/include/leftMenu.jsp" />
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
									<span class="m"> <strong>${merchantBean.merchantId }&nbsp;</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">MERCHANT NAME</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${merchantBean.name }&nbsp;</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">PRODUCT</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.product}&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">HOMEPAGE </label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.homepage}&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">ADDRESS</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">[${merchantBean.zipCode }]&nbsp;${merchantBean.addr1 }${merchantBean.addr2 }&nbsp;</span>
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
									<span class="m">${merchantBean.telNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">FAX NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.fax }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.email }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">IDENTIFICATION NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.identiNo }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">BANK NAME</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.settleBank }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">BANK CODE</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.settleBankCode }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">BANK ACCOUNT </label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.settleAccount }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">STATUS</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>
										<c:if test="${merchantBean.active == '1'}">Active</c:if>
							          	<c:if test="${merchantBean.active == '2'}">Non-Active</c:if>
							          	<c:if test="${merchantBean.active == '3'}">Termination</c:if>
							          	</strong>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">SERVICE DATE</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${merchantBean.serviceDate }" pattern="yyyy/MM/dd" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">REGISTRATION DATE</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${merchantBean.regDate }" pattern="yyyy/MM/dd/ HH:mm:ss" />&nbsp;</span>
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
									<span class="m">${merchantBean.ceoName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">NAME(ENGLISH)</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.ceoEngName }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">ADDRESS</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">[${merchantBean.ceoZipCode }] &nbsp; ${merchantBean.ceoAddr1 }${merchantBean.ceoAddr2 }&nbsp;</span>
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
									<span class="m">${merchantBean.ceoTelNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">MOBILE_NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.ceoPhoneNo }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.ceoEMail }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">IDENTIFICATION NUMBER</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.ceoIdentiNo }&nbsp;</span>
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
									<span class="m">${merchantBean.settlePartName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${merchantBean.settlePartEMail }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">TELEPHONE NUMBER</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.settlePartTelNo }&nbsp;</span>
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
									<span class="m"><strong>${merchantMngBean.curType }</strong>&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">Transaction</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>
										<c:if test="${merchantMngBean.demo =='Y' }">
											Test Mode
										</c:if>
										<c:if test="${merchantMngBean.demo =='N' }">
											Active Mode
										</c:if></strong>
									</span>
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
		<c:import url="/group/include/footer.jsp" />
		<!-- end footert -->
		
	</body>
</html>

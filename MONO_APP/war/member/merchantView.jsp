<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
	<!-- 상단 메인 링크 페이지 종료 -->
		<!-- content-->
		<div id="content">
			<!-- end content / left -->
			<div id="left">
				<c:import url="/include/leftMenu.jsp" />
			</div>
			<!-- end content / left -->
			<!-- content / right -->
			<div id="right">				
				<div class="box">
					<!-- forms -->
					<!-- box / title -->
					<div class="title">
						<h5>가맹점 정보</h5>
					</div>
					
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h6> 1.기본 정보 </h6>
								<ul class="links">
								<li><a href="/member.do?request=editForm&idx=${mBean.idx }">EDIT</a></li>
								</ul>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">가맹점 아이디</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"> <strong>${mBean.merchantId }&nbsp;</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">가맹점명</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${mBean.name }&nbsp;</strong></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">패스워드</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<strong><a href="javascript:showloading(false);popup('passwordUpdate','/member.do?request=passwordModi&merchantId=${mBean.merchantId }',540,250,100,100);"><font color="red">패스워드 변경</font></a></strong>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">패스워드 업데이트</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><strong>${mBean.pwUpdate }&nbsp;</strong></span>
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
									<span class="m">
										<c:if test="${mBean.homepage !='' }">
											<a href="http://${mBean.homepage}" target="_blank">${mBean.homepage}</a>
										</c:if>
										&nbsp;
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">주소</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<c:if test="${!empty mBean.zipCode}">
											[${mBean.zipCode }]
										</c:if>
										${mBean.addr1 }&nbsp;${mBean.addr2 }&nbsp;
										</span>
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
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.telNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">팩스번호</label>
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
									<label for="">사업자 번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.identiNo }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">은행명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.settleBank }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">은행코드</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.settleBankCode }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">계좌번호 </label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.settleAccount }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">상태</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:if test="${mBean.active == '0'}">예비</c:if>
										<c:if test="${mBean.active == '1'}">사용</c:if>
							          	<c:if test="${mBean.active == '2'}">중지</c:if>
							          	<c:if test="${mBean.active == '3'}">해지</c:if>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">서비스일자</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${mBean.serviceDate }" pattern="yyyy/MM/dd" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">등록일자</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${mBean.regDate }" pattern="yyyy/MM/dd HH:mm:ss" />&nbsp;</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL40">
								<h6>2.대표자 정보</h6>
								<ul class="links">
								<li><a href="/member.do?request=editForm&idx=${mBean.idx }">EDIT</a></li>
								</ul>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">성명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.ceoName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">성명(ENGLISH)</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.ceoEngName }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">주소</label>
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
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.ceoTelNo }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">휴대폰번호</label>
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
									<label for="">사업자번호</label>
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
								<h6>3.정산담당자 정보</h6>
								<ul class="links">
								<li><a href="/member.do?request=editForm&idx=${mBean.idx }">EDIT</a></li>
								</ul>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">성명</label>
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
									<label for="">전화번호</label>
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
								<h6>4.전산담당자 정보 </h6>
								<ul class="links">
								<li><a href="/member.do?request=editForm&idx=${mBean.idx }">EDIT</a></li>
								</ul>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">성명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.devPartName }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.devPartEMail }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.devPartTelNo }&nbsp;</span>
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
								<h6>5.가맹점 서비스 설정 정보</h6>
								<ul class="links">
								<li>
									<c:choose>
										<c:when test="${mngBean.idx == 0 }"><a href="/member.do?request=serviceAddForm&merchantId=${mBean.merchantId }">WRITE</a></c:when>
										<c:otherwise><a href="/member.do?request=serviceEditForm&idx=${mngBean.idx }">EDIT</a></c:otherwise>
									</c:choose>
								</li>
								</ul>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">통화 유형</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mngBean.curType }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">1회 거래 한도</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mngBean.onceLimit }" pattern="#,##0" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">일 거래 한도</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mngBean.dayLimit }" pattern="#,##0" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">월 거래 한도</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mngBean.monthLimit }" pattern="#,##0" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">월 카드 거래 한도</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mngBean.cardMonthLimit }" pattern="#,##0" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">중복 거래 제한 횟수</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mngBean.duplicationCount }" pattern="#,##0" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">월 카드 사용 제한 횟수</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mngBean.tempLimit }" pattern="#,##0" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">Transaction Type</label>
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
							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">VAN</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mngBean.van }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">VAN ID</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mngBean.vanId }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">인증서비스&nbsp;</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<c:if test="${mngBean.auth == 'Y' }">사용</c:if>
										<c:if test="${mngBean.auth == 'N' }">미사용</c:if>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">Report Email</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										${mngBean.reportEMail }&nbsp;
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">DM&nbsp;</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<c:if test="${mngBean.dm == 'Y' }">사용</c:if>
										<c:if test="${mngBean.dm == 'N' }">미사용</c:if>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										&nbsp;
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>6.가맹점 과금 설정 정보</h6>
								<ul class="links">
								<li>
									<c:choose>
										<c:when test="${mbBean.merchantId == '' }"><a href="/bill.do?request=addForm&merchantId=${mBean.merchantId }">WRITE</a></c:when>
										<c:otherwise><a href="/bill.do?request=editForm&merchantId=${mBean.merchantId }">EDIT</a></c:otherwise>
									</c:choose>
								</li>
								</ul>
							</div>
							<c:if test="${mbBean.merchantId != '' }">
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">정산방식</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										[${mbBean.period}]&nbsp;
										<c:choose>
											<c:when test="${mbBean.period == 'TYPE070' }">7 일 정산 (-28일~-22일 월요일 산출)</c:when>
											<c:when test="${mbBean.period == 'TYPE071' }">7 일 정산 (-21일~-15일 월요일 산출)</c:when>
											<c:when test="${mbBean.period == 'TYPE100' }">10 일 정산 (10일 마다 +3일 산출)</c:when>
											<c:when test="${mbBean.period == 'TYPE150' }">15 일 정산 (15일 마다 +3일 산출)</c:when>
											<c:when test="${mbBean.period == 'TYPE300' }">30 일 정산 (한달 마다 +3일 산출)</c:when>
											<c:otherwise>&nbsp;</c:otherwise>
										</c:choose>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">V/M 수수료</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.visamaster *100 }" pattern="###.###" />%</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">J/A 수수료</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.jcbamex *100}" pattern="###.###" />%</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">거래 수수료</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.transaction }" pattern="###.###" />&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">취소 수수료</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.refund }" pattern="###.###" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">CB 수수료</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.chargeback }" pattern="###.###" />&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">년 관리비</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.management }" pattern="###.##" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">송금 수수료</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.bankTransfer }" pattern="###.##" />&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">DEPOSIT</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.deposit *100 }" pattern="###.##" />%</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">VAT 부가세</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.vat *100}" pattern="###.##" />%</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">설정비</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><fmt:formatNumber type="number" value="${mbBean.setupFee }" pattern="###.##" />&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">정산여부</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:choose>
											<c:when test="${mbBean.active == 'Y' }">사용</c:when>
											<c:when test="${mbBean.active == 'N' }">중지</c:when>
											<c:otherwise>&nbsp;</c:otherwise>
										</c:choose>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">은행코드</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mbBean.bankCode }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">취급지점</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mbBean.branch }&nbsp;</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">계좌번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mbBean.account }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">계좌예금주</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mbBean.accountHolder }&nbsp;</span>
								</div>
							</div>
							</c:if>
						</div>
					</div>
					<div class="form">
						<div class="title">
							<h6>7.몰 정보</h6>
						</div>
						<div class="table">			
							<div class="padding10">&nbsp;</div>										
							<table>
									<thead>
										<tr>
											<th class="list_tit_w100">No</th>
											<th class="list_tit_w200">아이디</th>
											<th class="list_tit">연락처</th>
											<th class="list_tit">팩스번호</th>
											<th class="list_tit">이메일</th>
											<th class="list_tit">사이트주소</th>
											<th class="list_tit">상태</th>
											<th class="list_tit_w200">등록일자</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${fn:length(mallList) != 0}">
												<c:forEach var="list" items="${mallList}" varStatus="status">
												<tr>
													<td class="list">${status.count}</td>
													<td class="list"><strong>${list.mallId}</strong></td>
													<td class="list">${list.tel}&nbsp;</td>
													<td class="list">${list.fax}&nbsp;</td>
													<td class="list">${list.email}&nbsp;</td>
													<td class="list">${list.site}&nbsp;</td>
													<td class="list">
														<c:choose>
															<c:when test="${list.active == '0'}"><font color="#800080">예비</font>
															</c:when>
															<c:when test="${list.active == '1'}"><font color="blue">사용</font>
															</c:when>
															<c:when test="${list.active == '2'}"><font color="green">중지</font>
															</c:when>
															<c:when test="${list.active == '3'}"><font color="red">해지</font>
															</c:when>
															<c:otherwise>
																${list.active }
															</c:otherwise>
														</c:choose>
													</td>
													<td class="list">&nbsp;<fmt:formatDate type="both" value="${list.regDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr align="center" onMouseOver="this.style.backgroundColor='#B6D8EA'" onMouseOut="this.style.backgroundColor=''" style="height:16;word-break:break-all;">
													<td align="center" colspan="8"><font color="red"> NO RESULT</font></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>8.그룹 정보 </h6>
								<ul class="links">
								<li>
									<c:choose>
										<c:when test="${gBean.groupId == '' }"><a href="javascript:popup('group','/member.do?request=groupSelectForm&merchantId=${mBean.merchantId}',540,200,100,100);">WRITE</a></c:when>
										<c:otherwise><a href="javascript:popup('group','/member.do?request=groupSelectForm&merchantId=${mBean.merchantId}&groupId=${gBean.groupId }',540,200,100,100);">EDIT</a></c:otherwise>
									</c:choose>
								</li>
								</ul>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">그룹아이디</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${gBean.groupId }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">그룹명&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${gBean.name }&nbsp;</span>
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
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
		
	</body>
</html>

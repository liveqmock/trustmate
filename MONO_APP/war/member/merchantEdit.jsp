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
						<h5>가맹점 정보 수정</h5>
					</div>
					<form action="/member.do?request=edit" name="editForm" method="post">
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h6> 1.기본 정보 </h6>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">가맹점 아이디</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m">${mBean.merchantId } 
										<input type="hidden" name="idx" value="${mBean.idx }"/>
										<input type="hidden" name="merchantId" value="${mBean.merchantId }"/>
										<input type="hidden" name="commentIdx" value="${mBean.commentIdx }"/>
									</span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">가맹점명</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="name" value="${mBean.name }"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">패스워드</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										************
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">PRODUCT</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="product" value="${mBean.product }" style="width:200px;"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">HOMEPAGE</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="homepage" value="${mBean.homepage }" style="width:200px;"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">우편번호 </label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="zipCode" value="${mBean.zipCode }"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">주소</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="addr1" value="${mBean.addr1 }" style="width:200px;"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">상세 주소</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="addr2" value="${mBean.addr2 }" style="width:200px;"/></span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="telNo" value="${mBean.telNo }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">팩스번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="fax" value="${mBean.fax }"/></span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="email" value="${mBean.email }" onkeyup="onlyEnglish(this);" style="width:200px;"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">사업자 번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="identiNo" value="${mBean.identiNo }"/></span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">은행명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="settleBank" value="${mBean.settleBank }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">은행코드</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="settleBankCode" value="${mBean.settleBankCode }"/></span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">계좌번호 </label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="settleAccount" value="${mBean.settleAccount }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">상태</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="active">
											<option value="">-------</option>
											<option value="0" <c:if test="${mBean.active == '0' }">selected</c:if> >예비</option>
											<option value="1" <c:if test="${mBean.active == '1' }">selected</c:if> >사용</option>
											<option value="2" <c:if test="${mBean.active == '2' }">selected</c:if> >중지</option>
											<option value="3" <c:if test="${mBean.active == '3' }">selected</c:if> >해지</option>
										</select>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">서비스일자</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="serviceDate" value="<fmt:formatDate type="both" value="${mBean.serviceDate }" pattern="yyyyMMdd" />"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;
										<input type="hidden" name="pwUpdate" value="${mBean.pwUpdate}" />
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL40">
								<h6>2.대표자 정보</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">성명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="ceoName" value="${mBean.ceoName }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">성명(ENGLISH)</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="ceoEngName" value="${mBean.ceoEngName }" onkeyup="onlyEnglish(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">우편번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="ceoZipCode" value="${mBean.ceoZipCode }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">상세 주소</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<input type="text" name="ceoAddr1" value="${mBean.ceoAddr1 }" style="width:200px;"/>&nbsp;&nbsp;
										<input type="text" name="ceoAddr2" value="${mBean.ceoAddr2 }" style="width:200px;"/>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="ceoTelNo" value="${mBean.ceoTelNo }" onkeyup="onlyTelNo(this);" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">휴대폰번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="ceoPhoneNo" value="${mBean.ceoPhoneNo }" onkeyup="onlyTelNo(this);" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="ceoEMail" value="${mBean.ceoEMail }" onkeyup="onlyEnglish(this);" style="width:200px;"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">사업자번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="ceoIdentiNo" value="${mBean.ceoIdentiNo }" onkeyup="onlyTelNo(this);" /></span>
								</div>
							</div>
						</div>
					</div>
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>3.정산담당자 정보</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">성명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="settlePartName" value="${mBean.settlePartName }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="settlePartEMail" value="${mBean.settlePartEMail }" onkeyup="onlyEnglish(this);" style="width:200px;"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="settlePartTelNo" value="${mBean.settlePartTelNo }" onkeyup="onlyTelNo(this);" /></span>
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
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">성명</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="devPartName" value="${mBean.devPartName }"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="devPartEMail" value="${mBean.devPartEMail }" onkeyup="onlyEnglish(this);" style="width:200px;"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="devPartTelNo" value="${mBean.devPartTelNo }" onkeyup="onlyTelNo(this);" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">&nbsp;</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">&nbsp;</span>
								</div>
							</div>
						</div>
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="Edit" onclick="javascript:document.editForm.submit();"/>
							</div>
						</div>
					</div>
					</form>
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

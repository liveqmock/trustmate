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
						<h5>관리자 정보 수정</h5>
					</div>
					<form action="/member.do?request=mEdit" name="editForm" method="post">
					<div class="form">
						<div class="fields">	
							<div class="title">
								<h6> 기본 정보 </h6>
							</div>							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">관리자 아이디</label>
								</div>
								<div class="input" id="paddingL60">		
									<span class="m"> <strong>${mBean.memberId }&nbsp;</strong>
										<input type="hidden" name="idx" value="${mBean.idx }"/>
									</span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">관리자명</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="name" value="${mBean.name }" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="officeEmail" value="${mBean.officeEmail }" onkeyup="onlyEnglish(this);" style="width:200px;" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="phoneNo" value="${mBean.phoneNo }" onkeyup="onlyTelNo(this);"  /></span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">등급</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<select name="memberGrade">
											<option value="1" <c:if test="${mBean.memberGrade == '1'}">selected</c:if>>1등급</option>
											<option value="2" <c:if test="${mBean.memberGrade == '2'}">selected</c:if>>2등급</option>
											<option value="3" <c:if test="${mBean.memberGrade == '3'}">selected</c:if>>3등급</option>
											<option value="4" <c:if test="${mBean.memberGrade == '4'}">selected</c:if>>4등급</option>
											<option value="5" <c:if test="${mBean.memberGrade == '5'}">selected</c:if>>5등급</option>
										</select>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">상태</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="active">
											<option value="1" <c:if test="${mBean.active == '1'}">selected</c:if>>사용</option>
											<option value="2" <c:if test="${mBean.active == '2'}">selected</c:if>>중지</option>
										</select>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">부서</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<select name="notice">
											<option value="SYSTEM" <c:if test="${mBean.notice == 'SYSTEM'}">selected</c:if>>SYSTEM</option>
											<option value="ADMIN" <c:if test="${mBean.notice == 'ADMIN'}">selected</c:if>>ADMIN</option>
											<option value="USER" <c:if test="${mBean.notice == 'USER'}">selected</c:if>>USER</option>
										</select>
									</span>
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

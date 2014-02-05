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
						<h5>관리자 정보</h5>
					</div>
					<form name="editForm" action="/member.do?request=mEditForm&memberId=${mBean.memberId }" method="post">
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
									<span class="m"> <strong>${mBean.memberId }&nbsp;</strong></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">관리자명</label>
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
										*********
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">패스워드 변경</label>
								</div>
								<div class="input2" id="paddingL60">
									<div class="button">
									<input type="submit" value="변경" onclick="javascript:showloading(false);popup('passwordUpdate','/member.do?request=passwordModi2&memberId=${mBean.memberId }',540,300,100,100);"/> 
									</div>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${mBean.officeEmail }&nbsp;</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">${mBean.phoneNo }&nbsp;</span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">등급</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<c:if test="${mBean.memberGrade == '1'}">1등급</c:if>
							          	<c:if test="${mBean.memberGrade == '2'}">2등급</c:if>
							          	<c:if test="${mBean.memberGrade == '3'}">3등급</c:if>
							          	<c:if test="${mBean.memberGrade == '4'}">4등급</c:if>
							          	<c:if test="${mBean.memberGrade == '5'}">5등급</c:if>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">상태</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<c:if test="${mBean.active == '1'}">사용</c:if>
							          	<c:if test="${mBean.active == '2'}">중지</c:if>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">부서</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<c:if test="${mBean.notice == 'SYSTEM'}">SYSTEM</c:if>
							          	<c:if test="${mBean.notice == 'ADMIN'}">ADMIN</c:if>
							          	<c:if test="${mBean.notice == 'USER'}">USER</c:if>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">등록일자</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><fmt:formatDate type="both" value="${mBean.regDate }" pattern="yyyy/MM/dd/ HH:mm:ss" />&nbsp;</span>
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

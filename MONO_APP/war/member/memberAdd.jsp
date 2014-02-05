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
						<h5>관리자 정보 등록</h5>
					</div>
					<form action="/member.do?request=mAdd" name="editForm" method="post">
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
									<span class="m"><input type="text" name="memberId" value="" onkeyup="onlyEnglish(this);"/></span>							
								</div>
								<div class="label2" id="paddingL40">
									<label for="">관리자명</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="name" value="" /></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">패스워드</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="password" value="" onkeyup="onlyEnglish(this);" /></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="officeEmail" value="" onkeyup="onlyEnglish(this);" style="width:200px;"/></span>
								</div>							
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">전화번호</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<input type="text" name="phoneNo" value="${mBean.phoneNo }" onkeyup="onlyTelNo(this);" />
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">등급</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="memberGrade">
											<option value="1" >1등급</option>
											<option value="2" >2등급</option>
											<option value="3" >3등급</option>
											<option value="4" >4등급</option>
											<option value="5" >5등급</option>
										</select>
									</span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">상태</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<select name="active">
											<option value="1" >사용</option>
											<option value="2" >중지</option>
										</select>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">부서</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="notice">
											<option value="SYSTEM" >SYSTEM</option>
											<option value="ADMIN" >ADMIN</option>
											<option value="USER" >USER</option>
										</select>
									</span>
								</div>
							</div>
						</div>
						
						<div class="action">
							<div class="button">
								<input type="submit" name="submit" value="Regist" onclick="javascript:document.editForm.submit();"/>
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

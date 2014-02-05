<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
	<!-- 상단 메인 링크 페이지 -->
	<c:import url="/include/header.jsp" />
<script type="text/javascript">
	$(document).ready(function () {
	
		$("select").selectmenu({
	        style: 'dropdown',
	        width: 350,
	        menuWidth: 350,
	        icons: [
			    { find: '.locked', icon: 'ui-icon-locked' },
			    { find: '.unlocked', icon: 'ui-icon-unlocked' },
			    { find: '.folder-open', icon: 'ui-icon-folder-open' }
		    ]
	    });
    });
</script>
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
						<h5>가맹점 정보 등록</h5>
					</div>
					
					<form action="/member.do?request=serviceAdd" name="editForm" method="post">
					<div class="form">
						<div class="fields">						
							<div class="title" id="paddingL20">
								<h6>가맹점 서비스 설정 정보</h6>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">통화 유형&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">${merchantBean.curType }&nbsp;
										<input type="hidden" name="merchantId" value="${merchantId }" />
										<select name="curType">
											<option value="">----------</option>
											<option value="JPY">JPY</option>
											<option value="USD">USD</option>
											<option value="EUR">EUR</option>
											<option value="KRW">KRW</option>
										</select>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">1회 거래 한도&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="onceLimit" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">일 거래 한도&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="dayLimit" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">월 거래 한도&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="monthLimit" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">월 카드 거래 한도</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="cardMonthLimit" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">중복 거래 제한 횟수&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="duplicationCount" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">월 카드 사용 제한 횟수</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m"><input type="text" name="tempLimit" value="" onkeyup="onlyNumber(this);"/></span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">Transaction Type</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m">
										<select name="demo">
											<option value="Y">Test Mode</option>
											<option value="N">Active Mode</option>
										</select>
									</span>
								</div>
							</div>
							
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">VAN&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<select name="van">
											<option value="KSNET" selected>KSNET</option>
											<option value="INICIS">INICIS</option>
											<option value="KCP">KCP</option>
										</select>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">VAN ID&nbsp;<font color="red">*</font></label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="vanId" value="" onkeyup="onlyEnglish(this);"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">인증</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<select name="auth">
											<option value="N">미사용</option>
											<option value="Y">사용</option>
										</select>
									</span>
								</div>
								<div class="label2" id="paddingL40">
									<label for="">REPORT EMAIL</label>
								</div>
								<div class="input2" id="paddingL60">
									<span class="m"><input type="text" name="reportEMail" value="" style="width:200px;"/></span>
								</div>
							</div>
							<div class="field">
								<div class="label" id="paddingL40">
									<label for="">DM</label>
								</div>
								<div class="input" id="paddingL60">
									<span class="m">
										<select name="dm">
											<option value="N">미사용</option>
											<option value="Y">사용</option>
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

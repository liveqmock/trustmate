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
				<!-- table -->
				<!-- forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5>Transaction</h5>
					</div>
					<form name="trnsctnList" action="/merchant/trnsctn.do?request=list" method="post">
					<div class="form">
						<div class="fields">
							<div class="field">
								<div class="label">
									<label for="day">Transaction Id</label>
								</div>
								<div class="input">
									<input type="text" name="transactionId" id="transactionId" maxlength="16" class="transactionId" value="${trnsctnBean.transactionId}"/>
								</div>
								
								<div class="label2">
									<label for="qualification">Merchant Ref. </label>
								</div>
								<div class="input2">
									<input type="text" name="payNo" id="payNo" maxlength="16" class="payNo" value="${trnsctnBean.payNo}"/>
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="day">Approval Number </label>
								</div>
								<div class="input">
									<input type="text" name="approvalNo" id="approvalNo" maxlength="16" class="approvalNo" value="${trnsctnBean.approvalNo}"/>
								</div>
								
								<div class="label2">
									<label for="qualification">User Email </label>
								</div>
								<div class="input2">
									<input type="text" name="payEmail" id="payEmail" maxlength="16" class="payEmail" value="${trnsctnBean.payEmail}"/>
					             
								</div>
							</div>
							<div class="field">
								<div class="label">
									<label for="cardnum">Card Number</label>
								</div>
								<div class="input">
									<input type="text" name="temp1String" id="temp1String" maxlength="16" class="temp1String" value="${trnsctnBean.temp1String}"/>&nbsp;
								</div>
								
								<div class="label2">
									<label for="status">Status</label>
								</div>
								<div class="input2" >
									<select name="trnStatus" class="select2">
					              		<option value="">-----&nbsp;</option>
									    <option value="'00','01','02','03','04','05','06'">Approved</option>
									    <option value="'07','08','09','10','18','19','20','21'">collect</option>
									    <option value="'02','07','08','09','11','12','13','10'">Valid transaction</option>
									    <option value="'11','12','13'">Paid</option>
									    <option value="'06','14','18'">Canceled or Refund</option>
									    <option value="'17'">Ready for cancel</option>
									    <option value="'15'">Refund request</option>
									    <option value="'22','23'">Chargeback</option>
									    
					                </select>
					                <c:if test="${!empty trnsctnBean.trnStatus}"><script>document.trnsctnList.trnStatus.value="${trnsctnBean.trnStatus}"</script></c:if>
								</div>
								
							</div>
							<div class="field">
								<div class="label">
									<label for="day">Period</label>
								</div>
								<div class="input">
									<input type="text" id="startDate" name="startDate" class="date" value="${startDate}" onchange="onlyDate2(this);"/>
									<span class="m">-</span>
									<input type="text" id="endDate" name="endDate" class="date" value="${endDate }" onchange="onlyDate2(this);"/>
								</div>
								<div class="label2">
									<label for="">Search count</label>
								</div>
								<div class="input2">
									<select name="pageSize">
					              		<option value="50" <c:if test="${dao.pageSize == 50}">selected</c:if>>50</option>
					                	<option value="100" <c:if test="${dao.pageSize == 100}">selected</c:if>>100</option>
					                	<option value="200" <c:if test="${dao.pageSize == 200}">selected</c:if>>200</option>
					                	<option value="500" <c:if test="${dao.pageSize == 500}">selected</c:if>>500</option>
					                </select>
								</div>
								
							</div>
							
						</div>
						
						<!-- table action -->
						<div class="action">
							<div class="button">
								<input type="submit" name="format" value="excel"/>&nbsp;
								<input type="submit" name="submit" value="search"/>
							</div>
						</div>
						<!-- end table action -->			
					</div>
					</form>
				</div>
				<!-- end forms -->
				<div class="box">
					<!-- box / title -->
					<div class="title">
						<h5><strong>Search result</strong></h5>
					</div>
					<div style="height: 650px;"></div>
						
				</div>
				<!-- end table -->
			</div>
			<!-- end content / right -->
		</div>
		<!-- end content -->
		<!-- footer -->
		<c:import url="/include/footer.jsp" />
		<!-- end footert -->
	</body>
</html>


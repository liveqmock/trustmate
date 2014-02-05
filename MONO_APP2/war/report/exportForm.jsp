<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

			<!-- BEGIN PAGE HEADER-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">Export Data<small></small></h3>
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i><a href="/main.jsp">Home</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="#">Report</a><i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="javascript:getReloadPage();">Export Data</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN SEARCH FORM TABLE-->
					<div class="portlet box green ">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-search"></i> Search Form
							</div>
							<div class="tools">
								<a href="" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" role="form" data-form="true" id="searchForm" name="searchForm" action="/report.do" method="post">
								<div class="form-body">
									<div class="form-group">
										<label class="col-sm-2 control-label">Merchant ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm merchantId" data-provide="typeahead" name="merchantId" placeholder="merchantId" >
										</div>
										<label class="col-sm-2 control-label">Transaction ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="transactionId" placeholder="Transaction ID">
										</div>
										<label class="col-sm-2 control-label">Order No.</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="payNo" placeholder="Order No.">
										</div>
										<label class="col-sm-2 control-label">Approv No.</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="approvalNo" placeholder="Approv No." >
										</div>
										<label class="col-sm-2 control-label">E-Mail</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="payEmail" placeholder="E-Mail" >
										</div>
										<label class="col-sm-2 control-label">Pay Name</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="payName" placeholder="Pay Name">
										</div>
										<label class="col-sm-2 control-label">Van ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="temp2" placeholder="Van ID" >
										</div>
										<label class="col-sm-2 control-label">IP Address</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="ipAddress" placeholder="IP Address" >
										</div>
										<label class="col-sm-2 control-label">Card No.</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="temp1String" placeholder="Card No.">
										</div>
										<label class="col-sm-2 control-label">Status</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="trnStatus">
												<option value="">-----&nbsp;</option>
												<option value="'00','01','02','03','04','05','06'">Approved</option>
												<option value="'07','08','09','10','18','19','20','21'">Acquired</option>
												<option value="'02','07','08','09','11','12','13','10'">Valid Transaction</option>
												<option value="'11','12','13'">Settlement</option>
												<option value="'06','14','18'">Refunded</option>
												<option value="'17'">Request Cancel</option>
												<option value="'15'">Request Refund</option>
												<option value="'22','23'">Chargeback</option>
												
											</select>
										
										</div>
										<label class="col-sm-2 control-label">VAN</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="van">
												<option value="">-----&nbsp;</option>
												<option value="KCP">KCP</option>
												<option value="INICIS">INICIS</option>
												<option value="KSNET">KSNET</option>
											</select>
										
										</div>
										<label class="col-sm-2 control-label">VAN Transaction ID</label>
										<div class="col-sm-2">
											<input type="text" class="form-control input-sm" name="vanTransactionId" placeholder="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Brand</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="temp2String">
												<option value="">-----&nbsp;</option>
												<option value="VISA">VISA</option>
												<option value="MASTER">MASTER</option>
												<option value="JCB">JCB</option>
												<option value="AMEX">AMEX</option>
											</select>
										</div>
										<label class="col-sm-2 control-label">Currency</label>
										<div class="col-sm-2">
											<select class="form-control select2me input-sm" name="curType">
												<option value="">-----&nbsp;</option>
												<option value="USD">USD</option>
												<option value="JPY">JPY</option>
												<option value="EUR">EUR</option>
												<option value="CHY">CHY</option>
												<option value="KRW">KRW</option>
											</select>
										</div>
										<label class="col-sm-2 control-label">Period</label>
										<div class="col-sm-2">
											<div class="input-group date-picker input-daterange" data-date-format="yyyymmdd">
												<input type="text" class="form-control input-sm" name="startDate" value="${endDate}">
												<span class="input-group-addon">~</span>
												<input type="text" class="form-control input-sm" name="endDate" value="${endDate}">
												<input id="request" type="hidden" name="request" value="exportList" >
											    <input id="format" type="hidden" name="format" value="" >
											</div>
										</div>
									</div>
								</div>
								<div class="form-actions nobg right">
									<div class="">
										remove row ( click <i class='fa fa-times'></i> )!&nbsp;
										<button type="reset" class="btn dark btn-sm"><i class="fa fa-eraser"></i>&nbsp;Form Reset</button>
										<button type="button" class="btn purple btn-sm loading-btn" data-loading-text="Loading..." onclick="exportData();" ><i class="fa fa-search"></i>&nbsp;Search &amp; Append</button>
										<button type="button" class="btn green btn-sm" onclick="$('#format').val('excel');document.searchForm.submit();" ><i class="fa fa-file-text-o"></i>&nbsp;Excel</button>
										<button type="button" class="btn dark btn-sm" onclick="$('#sortTable > tbody').empty();"><i class="fa fa-eraser"></i>&nbsp;Result Reset&nbsp;&nbsp;</button>
										<button type="button" class="btn blue btn-sm" onclick="downloadExcel();" ><i class="fa fa-file-text-o"></i>&nbsp;Result Export</button>
										
									</div>
								</div>
								
							</form>
						</div>
					</div>
					<!-- END SEARCH FORM TABLE-->
					
					<!-- BEGIN SEARCH RESULT FORM-->
					<div class="portlet box blue" id="searchResult">	
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-reorder"></i>Search Result
							</div>
							<div class="tools">
							</div>
						</div>
						<div class="portlet-body flip-scroll" id="searchResult2">
							<div class="table-scrollable">
							<table class="table table-striped table-bordered table-hover flip-content" id="sortTable">
							<thead>
								<tr>
									<th>Edit</th>
									<th data-sort="float">No</th>
									<th data-sort="float">Transaction ID</th>
									<th data-sort="string">Merchant ID</th>
									<th>Mall ID</th>
									<th data-sort="string">Order No</th>
									<th data-sort="string">Status</th>
									<th data-sort="string">Currency</th>
									<th data-sort="float">Amount</th>
									<th data-sort="float">Card No</th>
									<th data-sort="string">Brand</th>
									<th data-sort="string">Approval No</th>
									<th data-sort="string">UserId</th>
									<th data-sort="string">UserName</th>
									<th data-sort="string">UserEmail</th>
									<th data-sort="string">TelNo</th>
									<th data-sort="string">ProductName</th>
									<th data-sort="string">IpAddress</th>
									<th data-sort="string">IpGeo</th>
									<th data-sort="string">Van</th>
									<th data-sort="string">Van ID</th>
									<th data-sort="string">URL</th>
									<th data-sort="string">Result Cd</th>
									<th data-sort="string">Result Msg</th>
									<th data-sort="string">Request Date</th>
									<th data-sort="string">Response Date</th>
									<th data-sort="string">Transaction Date</th>
								</tr>
							</thead>
							<tbody id="dataTable">
							</tbody>
							</table>
							</div>
						</div>
					</div>
					<!-- END SEARCH RESULT FORM-->
					
					<script>
					
					
					function exportData(){
						if($('#sortTable tr').length > 1){
							bootbox.dialog({
							  message: "Do you want Append or Search Again",
							  title: "Search",
							  buttons: {
							    success: {
							      label: "Append!",
							      className: "btn-success",
							      callback: function() {
							        exportTo('append');
							      }
							    },
							    main: {
							      label: "Search Again!",
							      className: "btn-primary",
							      callback: function() {
							       exportTo('');
							      }
							    }
							  }
							});
						}else{
							exportTo('research');
						}
												
					}
					
					
					function exportTo(type){
						var frm = document.searchForm;
						frm.format.value = type;
						var datas = $("#searchForm").serialize();
						if(type !='append'){
							$('#sortTable > tbody').empty();
						}
						$.ajax({
							url : $("#searchForm").attr("action"),
							type : 'POST',
							data : datas,
							success : function(msg){
								$(this).button('reset');
								if(msg.indexOf('MSG') == 0){
									bootbox.alert(msg.split("||")[1]);
								}else{
									var $table = $("#sortTable");
									
									var len = $('#sortTable tr').length-1;
									var rows = "";
								    $.each(msg, function(i, k) {
								    	if(!compare(k.transactionId)){
								    
								        rows += "<tr id='tr"+i+"'>"
								        +"<td><i class='fa fa-times' onclick=\"$('#tr"+i+"').remove();\"></i></td>"
								        +"<td>" + (len+i+1) +"</td>"
								        +"<td style='mso-number-format:\"\@\";'>" + k.transactionId + "</td>"
								        +"<td>" + k.merchantId + "</td>"
								        +"<td>" + k.mallId + "</td>"
								        +"<td style='mso-number-format:\"\@\";'>" + k.payNo + "</td>"
								        +"<td>" + k.trnStatus + "</td>"
								        +"<td>" + k.curType + "</td>"
								        +"<td>" + k.amount + "</td>"
								        +"<td>" + k.temp1String + "</td>"
								        +"<td>" + k.temp2String + "</td>"
								        +"<td>" + k.approvalNo + "</td>"
								        +"<td>" + k.payUserId + "</td>"
								        +"<td>" + k.payName + "</td>"
								        +"<td>" + k.payEmail + "</td>"
								        +"<td>" + k.payTelNo + "</td>"
								        +"<td>" + k.productName + "</td>"
								        +"<td>" + k.ipAddress + "</td>"
								        +"<td>" + k.temp3String + "</td>"
								        +"<td>" + k.van + "</td>"
								        +"<td>" + k.temp2 + "</td>"
								        +"<td>" + k.temp1 + "</td>"
								        +"<td>" + k.resultCd + "</td>"
								        +"<td>" + k.resultMsg + "</td>"
								        +"<td>" + k.trnReqDate + "</td>"
								        +"<td>" + k.trnResDate + "</td>"
								        +"<td>" + k.trnDate + "</td>"
								        +"</tr>";
								        }else{
								        	bootbox.alert(k.transactionId +' Duplicated TransactionID');
								        }
								    });
								    
								    $table.append(rows);
								    
									
									if(type == 'excel'){
										alert($("#searchResult").html());
										excelDown(msg);
									}
								}
								
							},
							error : function(result){
								bootbox.alert("Could not load the requested content.");
							}
						});
					}
					
					
					function downloadExcel(){
						if($('#sortTable tr').length > 1){
							excelDown($('#searchResult2').html());
						}else{
							bootbox.alert("export data is none");
						}
					}
					
					
					function compare(transactionId){
						var result;
						$('#sortTable > tbody  > tr').each(function() {
							if($(this).find('td:eq(2)').html() == transactionId){
								result = true;
								return false;
							}    
						});
						return result;
					}
					
					</script>
					
				</div>
			</div>

<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<div class="modal-content">
		<div class="modal-header">	
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		</div>
		<div class="modal-body">
			<div class="row">
			
				<!--  DEPOSIT DIV START -->
					
				<div class="portlet-body form">
					
					<div class="form-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">Settle ID</label>
							<label class="col-sm-3 control-label text-danger">${mdBean.settleId}</label>
							<label class="col-sm-3 control-label">Release Month</label>
							<label class="col-sm-3 control-label text-danger">${mdBean.payMonth}&nbsp;</label>
							<label class="col-sm-3 control-label">Accumulated Balance</label>
							<label class="col-sm-3 control-label text-danger"><fmt:formatNumber type="number" value="${mdBean.lastAmt}" pattern="#,##0.00" /></label>
							<label class="col-sm-3 control-label">Current Deposit</label>
							<label class="col-sm-3 control-label text-danger"><fmt:formatNumber type="number" value="${mdBean.curAmt}" pattern="#,##0.00" /></label>
							<label class="col-sm-3 control-label">Release Deposit</label>
							<label class="col-sm-3 control-label text-danger"><fmt:formatNumber type="number" value="${mdBean.payAmt}" pattern="#,##0.00" /></label>
							<label class="col-sm-3 control-label">Balance</label>
							<label class="col-sm-3 control-label text-danger"><fmt:formatNumber type="number" value="${mdBean.totAmt}" pattern="#,##0.00" /></label>
							<label class="col-sm-12 control-label">&nbsp;</label>
							<label class="col-sm-3 control-label text-warning">Release Month</label>
							
							<label class="col-sm-3 control-label text-danger">
								
									<input type="text" class="form-control input-sm" name="payMonth" id="payMonth" value="${mdBean.payMonth}">
									<input type="hidden" class="form-control input-sm" name="idx" id="idx" value="${mdBean.idx }">
							</label>
							<label class="col-sm-12 control-label">&nbsp;</label>
							
						</div>									
						<div class="form-group">
							<label class="col-sm-3 control-label text-left">comments</label>
							<label class="col-sm-9 control-label ">
							<textarea class="col-sm-9 form-control" rows="5" name="comments" id="comments"></textarea>
							</label>
						</div>
					</div>
					
				</div>
				<!--  DEPOSIT DIV END -->
				
					
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">Close</button>
			<button type="button" class="btn blue update" data-dismiss="modal">Update</button>
		</div>
	</div>
	<!-- /.modal-content -->

<!-- /.modal-dialog -->
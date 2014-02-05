<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">Change ${merchantId}'s Group &nbsp; <small class="error"></small></h4>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal form-row-seperated" role="form" data-form="true" id="modalForm" name="modalForm" action="/member.do" method="post">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-4">Group List<span class="required">*</span>
									</label>
									<div class="col-sm-6">
										<select name="groupId" class="select2me form-control" id="groupId">
											<option value="">-------------</option>
											<c:forEach var="list" items="${glist}" varStatus="status">
											<option value="${list.groupId }" <c:if test="${list.groupId == groupId}">selected</c:if>>${list.groupId }&nbsp;[${list.name }]</option>
											</c:forEach>
										</select>
										<input name="request" type="hidden" value="groupSelect" >
										<input name="merchantId" type="hidden" value="${merchantId}" >
										<input name="checkGroup" type="hidden" value="${groupId}" >
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<c:if test="${fn:length(glist) != 0}">
		    <button type="button" class="btn blue btn-sm update">Change Group</button>
		    </c:if>
			<button type="button" class="btn default" data-dismiss="modal">Close</button>
		</div>
		<script>
		if (jQuery().select2) {
        $('.select2me').select2({
            placeholder: "Select",
            allowClear: true,
			minimumResultsForSearch: Infinity,
			showSearchBox: false,
			showSearchInput: false
        });
	}
		</script>
	</div>
	
<!-- /.modal-dialog -->
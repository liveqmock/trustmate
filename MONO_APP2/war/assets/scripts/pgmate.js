


//KEEP SESSION
$( document ).ready(function() {
   	$('.keepSession').click(function() {
   		if(typeof App.refreshIntervalId === 'undefined' || App.refreshIntervalId == 0){
   			bootbox.alert('Start Reload Function');
    		App.refreshIntervalId=setInterval(function(){	
   			formSend('list');
	        },60000);
   		}else{
   			bootbox.alert('End Reload Function');
    		clearInterval(App.refreshIntervalId);
    		App.refreshIntervalId =0;
   		}
   });
   
   	if ($.cookie('page-sidebar-reversed')  == 1) {
   		$("body").addClass("page-sidebar-reversed");
   	}
   	
   	if ($.cookie('page-sidebar-fix') == 1) {
   		$("body").addClass("page-sidebar-fixed");
   	}
   	
});


function setPageInit(){
	if (jQuery().select2) {
        $('.select2me').select2({
            placeholder: "Select",
            allowClear: true,
			minimumResultsForSearch: Infinity,
			showSearchBox: false,
			showSearchInput: false
        });
	}
	if (jQuery().datepicker) {
		$('.date-picker').datepicker({
			rtl: App.isRTL(),
	        autoclose: true
	    });
	}
}

function setListInit(){
	if (!jQuery().dataTable) {
		var table = $("#sortTable").stupidtable({
	        "date": function(a,b) {
	          aDate = date_from_string(a);
	          bDate = date_from_string(b);
	          return aDate - bDate;
	        }
		});
	
		table.on("aftertablesort", function (event, data) {
			$("#sortTable").removeClass("disabled");
	
			var th = $(this).find("th");
			th.find(".arrow").remove();
			var dir = $.fn.stupidtable.dir;
	
			var arrow = data.direction === dir.ASC ? "&uarr;" : "&darr;";
			th.eq(data.column).append('<span class="arrow">' + arrow +'</span>');
		});
	}
}


function getPageContent(urls){
	historyPage= urls;
	var urld = urls.split("?");
	$.ajax({
		url : urld[0],
		type : 'POST',
		data : urld[1],
		success : function(msg){
			if(msg.indexOf('MSG') == 0){
				bootbox.alert(msg.split("||")[1], function() {
					document.location.href = "/sso/websso.jsp";
                });
			}else{
				$('.page-content').html(msg);	
				setPageInit();
			}
		},
		error : function(result){
			bootbox.alert("Could not load the requested content.");
		}
	});
}



function getReloadPage(){
	/*
	var page = '';
	if(historyPage.length > 0){
		page = historyPage[historyPage.length -1];
	}else{
		bootbox.alert('History Page is not exist');
		page = '/board.jsp';
	}
	*/
	
	getLoadPage(historyPage);
}

function getLoadPage(page){	
	$.ajax({
		url : page,
		type : 'POST',
		success : function(msg){
			if(msg.indexOf('MSG') == 0){
				bootbox.alert(msg.split("||")[1], function() {
					document.location.href = "/sso/websso.jsp";
                });
			}else{
				$('.page-content').html(msg);	
				setPageInit();
			}
		},
		error : function(result){
			bootbox.alert("Could not load the requested content.");
		}
	});
}


function ajaxFormSubmit(okUrl){
	ajaxFormSubmit(okUrl,2000);
}

function ajaxFormSubmit(okUrl,time){
	var datas = $("#form").serialize();
	console.log(datas);
	$.ajax({
		url : $("#form").attr("action"),
		type : 'POST',
		data : datas,
		success : function(msg){
			var res = msg.split("||");
			if(res[0] == "OK"){
				bootbox.alert(res[1]);
				if(okUrl != ''){
					setTimeout(function () {
						getPageContent(okUrl);
					},time);
				}
			}else{				
				bootbox.alert(res[1]);
			}
		},
		error : function(result){
			bootbox.alert("FUNCTION EXECUTE ERROR"+result);
		}
	});
}


function ajaxPaymentSubmit(){
	var datas = $("#payment").serialize();
	console.log(datas);
	jQuery.ajaxSetup({async:false});
	$.ajax({
		url : $("#payment").attr("action"),
		type : 'POST',
		data : datas,
		success : function(msg){
			$('#searchResult').html(msg);	
			jQuery.ajaxSetup({async:true});
		},
		error : function(result){
			jQuery.ajaxSetup({async:true});
			bootbox.alert("FUNCTION EXECUTE ERROR"+result);
		}
	});
}


function formSend(type){
	var frm = document.searchForm;
	frm.format.value = type;
	var datas = $("#searchForm").serialize();
	
	$.ajax({
		url : $("#searchForm").attr("action"),
		type : 'POST',
		data : datas,
		success : function(msg){
			$(this).button('reset');
			if(type == 'excel'){
				if(msg.indexOf('MSG') == 0){
					bootbox.alert(msg.split("||")[1]);
				}else{
					excelDown(msg);
				}
			}else{
				if(msg.indexOf('MSG') == 0){
					bootbox.alert(msg.split("||")[1], function() {
						document.location.href = "/sso/websso.jsp";
	                });
				}else{
					$('#searchResult').html(msg);
					setListInit();
				}
			}
		},
		error : function(result){
			bootbox.alert("Could not load the requested content.");
		}
	});
}

function excelDown(data){
	
		var form = document.createElement("form"); //created dummy form for submitting.
	    var element1 = document.createElement("input"); 
	    form.method = "POST";
	    form.action = "/ajax.do?request=toExcel";
	    element1.value=data; //its a json string I need to pass to server.
	    element1.name="message";
	    element1.type = 'hidden';
	    form.appendChild(element1);
	    document.body.appendChild(form);
	
	    form.submit();
}


function pagingSend(urls){
	App.reloadPage = urls;
	$.ajax({
		url : urls,
		type : 'GET',
		success : function(msg){
			if(msg.indexOf('MSG') == 0){
				bootbox.alert(msg.split("||")[1], function() {
					document.location.href = "/sso/websso.jsp";
                });
			}else{
				$('#searchResult').html(msg);
				setListInit();
			}
		},
		error : function(result){
			bootbox.alert("Could not load the requested content.");
		}
	});

}


function changePassword(role,id){
	var $modal = $('#pgmate-modal');
	$modal.attr("data-width","600px");
	$('body').modalmanager('loading');
	setTimeout(function(){
    	$modal.load('/member.do',{"request":"psForm","role":role,"id":id}, function(){
        $modal.modal();
    	});
   	}, 100);
		
	//여기 부분은 코딩해야함.
    $modal.on('click', '.update', function(){
    	
      if($('#modalForm').validate().form()){
    	  $modal.modal('loading');
    	  var datas = $("#modalForm").serialize();
    		$.ajax({
    			url : $("#modalForm").attr("action"),
    			type : 'POST',
    			data : datas,
    			success : function(msg){
    				var res = msg.split("||");
    				if(res[0] == "OK"){
    					bootbox.alert(res[1]);
    					setTimeout(function () {
    							$modal.modal('hide');
    					},500);
    					getReloadPage();
    				}else{
    					$modal.modal('removeLoading');
    					bootbox.alert(res[1]);
    				}
    			},
    			error : function(result){
    				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
    				$modal.modal('removeLoading');
    			}
    		});
      }
	});
    $modal.on('shown', function() {
		$(this).find("#password").focus();
	});
}




function getResultMsg(resultMsg){
	$.ajax({
		url : '/ajax.do?request=resultMsg&resultMsg='+resultMsg,
		type : 'POST',
		success : function(msg){
			bootbox.alert(msg);
		},
		error : function(result){
			bootbox.alert("Could not load the requested content.");
		}
	});

}



function printDiv(printDiv){
	if(printDiv == ''){
		printDiv = "printDiv";
	}
	console.log($("#"+printDiv).html());
	$("#"+printDiv).printThis({
		debug: false,              // show the iframe for debugging
		importCSS: true,           // import page CSS
		printContainer: true,      // grab outer container as well as the contents of the selector
		loadCSS: "/assets/css/print.css", // path to additional css file
		pageTitle: "PGMATE PRINT",             // add title to print page
		removeInline: false        // remove all inline styles from print elements
	});
}

function trnsctnView(transactionId){
	trnsctnDetail('#pgmate-modal',transactionId,"700px");
}

function trnsctnView2(transactionId,width){
	trnsctnDetail('#pgmate-modal',transactionId,width);
}

function trnsctnDetail(modalName,transactionId,width){
	var $modal = $(modalName);
	$modal.attr("data-width",width);
	$('body').modalmanager('loading');
	
	var url = '/trnsctn.do?request=view&transactionId='+transactionId;
	
	setTimeout(function(){
    	$modal.load(url, '', function(){
        $modal.modal();
    	});
   	}, 100);
		
	
    $modal.on('click', '.refund', function(){
      $modal.modal('loading');
      setTimeout(function(){
    	  jQuery.ajaxSetup({async:false});
    	  var datas = 'REQ_TYPE=refund&transactionId='+$('#transactionId').val()+'&comment='+encodeURIComponent($('#refundComment').val());
    	  $.ajax({
  			url : '/adminPayment.do',
  			type : 'POST',
  			data : datas,
  			success : function(msg){
  				var res = msg.split("||");
  				if(res[0] == "OK"){
  					bootbox.alert(res[1]);
  					setTimeout(function () {
  						$modal.load(url, '', function(){
  					        $modal.modal();
  					    });
  					},500);
  					formSend('list');
  				}else{
  					$modal.modal('removeLoading');
  					bootbox.alert(res[1]);
  				}
  				jQuery.ajaxSetup({async:true});
  			},
  			error : function(result){
  				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
  				$modal.modal('removeLoading');
  				jQuery.ajaxSetup({async:true});
  			}
  		});
    	  
      }, 1000);
	});
    
    
    $modal.on('click', '.chargeback', function(){
        $modal.modal('loading');
        setTimeout(function(){
      	  jQuery.ajaxSetup({async:false});
      	  var datas = 'REQ_TYPE=cbAdd&transactionId='+$('#transactionId').val()+'&comment='+encodeURIComponent($('#cbComment').val());
      	  $.ajax({
    			url : '/adminPayment.do',
    			type : 'POST',
    			data : datas,
    			success : function(msg){
    				var res = msg.split("||");
    				if(res[0] == "OK"){
    					bootbox.alert(res[1]);
    					setTimeout(function () {
    						$modal.load(url, '', function(){
    					        $modal.modal();
    					    });
    					},500);
    					formSend('list');
    				}else{
    					$modal.modal('removeLoading');
    					bootbox.alert(res[1]);
    				}
    				jQuery.ajaxSetup({async:true});
    			},
    			error : function(result){
    				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
    				$modal.modal('removeLoading');
    				jQuery.ajaxSetup({async:true});
    			}
    		});
      	  
        }, 1000);
  	});
}




function popup(id,url,width,height,px,py){
	var sw  = screen.availWidth ;
 	var sh  = screen.availHeight ;
	//px=(sw - width)/2 ;
	//py=(sh - height)/2 ;
 	
	var set  = 'top=' + py + ',left=' + px ;
	set += ',width=' + width + ',height=' + height + ',toolbar=0,resizable=0,status=0,scrollbars=yes,location=0,menubar=0,directories=0,copyhistory=no' ;
				
	window.open(url,id,set); 	
}



function monthlyChart(reqType) {
	
	var seriesData = '';
	var categoryData = '';
	var pointFormatData = '<tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0;text-align:right;"><b>{point.y}</b></td></tr>';
	if(reqType =='RATE'){
		pointFormatData = '<tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0;text-align:right;"><b>{point.y}%</b></td></tr>';
	}
	jQuery.ajaxSetup({async:false});
	$.post("/ajax.do",{request:"monthlyChart",type:reqType},function(data){
		seriesData = data.series;
		categoryData =  data.categories;
	});
	jQuery.ajaxSetup({async:true});
	$('#monthlyChart').highcharts({
	 legend: {
         layout: 'vertical',
         align: 'right',
         verticalAlign: 'middle',
         borderWidth: 0
     },
     title: {
    	 text: 'Monthly Transaction Statistics ('+reqType.toLowerCase()+')',
         x: -20 //center
     },
     subtitle: {
         text: 'Source: service.pgmate.com',
         x: -20
     },

     xAxis: {
    	 categories: categoryData
     },
     yAxis: {
         min: 0,
         title: {
             text: reqType
         },
         plotLines: [{
             value: 0,
             width: 1,
             color: '#808080'
         }]
     },
     tooltip: {
    	 valueDecimals: 2,
         headerFormat: '<span style="font-size:12px;">MONTH : {point.key}</span><table>',
         pointFormat: pointFormatData,
         footerFormat: '</table>',
         shared: true,
         useHTML: true
     },
     
     series: seriesData
  });
}



function monthlyChartToday(reqType) {
	
	var seriesData = '';
	var categoryData = '';
	var pointFormatData = '<tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0;text-align:right;"><b>{point.y}</b></td></tr>';

	jQuery.ajaxSetup({async:false});
	$.post("/ajax.do",{request:"monthlyTodayChart",type:reqType},function(data){
		seriesData = data.series;
		categoryData =  data.categories;
	});
	jQuery.ajaxSetup({async:true});
	$('#monthlyTodayChart').highcharts({
	 legend: {
         layout: 'vertical',
         align: 'right',
         verticalAlign: 'middle',
         borderWidth: 0
     },
     title: {
    	 text: 'Latest Transaction Statistics ('+reqType.toLowerCase()+')' ,
         x: -20 //center
     },

     xAxis: {
    	 categories: categoryData
     },
     yAxis: {
         min: 0,
         title: {
             text: reqType
         },
         plotLines: [{
             value: 0,
             width: 1,
             color: '#808080'
         }]
     },
     tooltip: {
    	 valueDecimals: 2,
         headerFormat: '<span style="font-size:12px;">MONTH : {point.key}</span><table>',
         pointFormat: pointFormatData,
         footerFormat: '</table>',
         shared: true,
         useHTML: true
     },
     
     series: seriesData
  });
}



function merchantChart(requestMerchant,reqType) {
	
	if(requestMerchant == ''){
		bootbox.alert('merchantId is missing');
		return
	}
	
	var seriesData = '';
	var categoryData = '';
	var pointFormatData = '<tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0;text-align:right;"><b>{point.y}</b></td></tr>';
	if(reqType =='RATE'){
		pointFormatData = '<tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0;text-align:right;"><b>{point.y}%</b></td></tr>';
	}
	jQuery.ajaxSetup({async:false});
	$.post("/ajax.do",{request:"merchantMonthlyChart",type:reqType,merchantId:requestMerchant},function(data){
		seriesData = data.series;
		categoryData =  data.categories;
	});
	jQuery.ajaxSetup({async:true});
	$('#merchantChart').highcharts({
		chart: {
            type: 'column'
        },
	 
     title: {
    	 text: requestMerchant+' Statistics ('+reqType.toLowerCase()+')',
         x: -20 //center
     },

     xAxis: {
    	 categories: categoryData
     },
     yAxis: {
         min: 0,
         title: {
             text: reqType
         },
         plotLines: [{
             value: 0,
             width: 1,
             color: '#808080'
         }]
     },
     tooltip: {
    	 valueDecimals: 2,
         headerFormat: '<span style="font-size:12px;">MONTH : {point.key}</span><table>',
         pointFormat: pointFormatData,
         footerFormat: '</table>',
         shared: true,
         useHTML: true
     },
     
     series: seriesData
  });
}



function todayChart() {
	
	
	var seriesData = '';
	
	jQuery.ajaxSetup({async:false});
	$.post("/ajax.do",{request:"todayChart"},function(data){
		seriesData = data;
	});
	jQuery.ajaxSetup({async:true});
	
	
	$('#todayChart').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: 'Today Tansaction',
            style: {
                display: 'none'
            }
        },
        tooltip: {
    	    pointFormat: '<b>{point.y} ({point.percentage:.1f})%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                	enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '{point.y:.2f}'
                },
                showInLegend: true
            }
        },
        series: seriesData
    });
	
}


function doSettle(settleId,status){
	
	bootbox.confirm("Are you sure?", function(result) {
		if(result){
			jQuery.ajaxSetup({async:false});
	    	  var datas = 'request=paidCheck&settleId='+settleId+'&status='+status;
	    	  $.ajax({
	  			url : '/bill.do',
	  			type : 'POST',
	  			data : datas,
	  			success : function(msg){
	  				var res = msg.split("||");
	  				bootbox.alert(res[1]);
	  				if(res[0] == "OK"){
	  					formSend('list');
	  				}
	  				jQuery.ajaxSetup({async:true});
	  			},
	  			error : function(result){
	  				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
	  				jQuery.ajaxSetup({async:true});
	  			}
	  		});
		}
	}); 
}


function doUnBlock(idx,active){
	
	bootbox.prompt("Are you sure? Please input your message", function(result) {
	if(result != '' && result){
			jQuery.ajaxSetup({async:false});
	    	  var datas = 'request=riskEdit&idx='+idx+'&active='+active+"&comments="+encodeURIComponent(result);
	    	  $.ajax({
	  			url : '/manage.do',
	  			type : 'POST',
	  			data : datas,
	  			success : function(msg){
	  				var res = msg.split("||");
	  				bootbox.alert(res[1]);
	  				if(res[0] == "OK"){
	  					formSend('list');
	  				}
	  				jQuery.ajaxSetup({async:true});
	  			},
	  			error : function(result){
	  				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
	  				jQuery.ajaxSetup({async:true});
	  			}
	  		});
		
	} 
	});
}

function deleteAlert(alertType,resultMsg,target,regId){
	
	bootbox.confirm("Are you sure?", function(result) {
		if(result){
			jQuery.ajaxSetup({async:false});
	    	  var datas = 'request=alertDelete&alertType='+alertType+'&resultMsg='+resultMsg+'&target='+target+'&regId='+regId;
	    	  $.ajax({
	  			url : '/manage.do',
	  			type : 'POST',
	  			data : datas,
	  			success : function(msg){
	  				var res = msg.split("||");
	  				bootbox.alert(res[1]);
	  				if(res[0] == "OK"){
	  					formSend('list');
	  				}
	  				jQuery.ajaxSetup({async:true});
	  			},
	  			error : function(result){
	  				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
	  				jQuery.ajaxSetup({async:true});
	  			}
	  		});
		}
	}); 
}



function depositEdit(idx){
	var $modal = $('#pgmate-modal');
	$modal.attr("data-width","600px");
	$('body').modalmanager('loading');
	
	var url = '/report.do?request=depositEditForm&idx='+idx;
	
	
	
	setTimeout(function(){
    	$modal.load(url, '', function(){
        $modal.modal();
    	});
   	}, 100);
		
	
    $modal.on('click', '.update', function(){
    	
      if($('#payMonth').val() =='' || $('#comments').val() ==''){
    	  bootbox.alert('Release Month or Comments IS NULL ');
    	  formSend('list');
    	  return ;
      } 	
    
      $modal.modal('loading');
      setTimeout(function(){
    	  jQuery.ajaxSetup({async:false});
    	  var datas = 'request=depositEdit&idx='+$('#idx').val()+'&payMonth='+$('#payMonth').val()+'&comments='+encodeURIComponent($('#comments').val());
    	  $.ajax({
  			url : '/report.do',
  			type : 'POST',
  			data : datas,
  			success : function(msg){
  				var res = msg.split("||");
  				if(res[0] == "OK"){
  					bootbox.alert(res[1]);
  					formSend('list');
  				}else{
  					bootbox.alert(res[1]);
  				}
  				jQuery.ajaxSetup({async:true});
  			},
  			error : function(result){
  				bootbox.alert("FUNCTION EXECUTE ERROR"+result);
  				jQuery.ajaxSetup({async:true});
  			}
  		});
    	  
      }, 1000);
	});
}



function loadCalendar(months){
	
	if (!jQuery().fullCalendar) {
        return;
    }
	
	var h = {};
    if (App.isRTL()) {
         if ($('#calendar').parents(".portlet").width() <= 720) {
            $('#calendar').addClass("mobile");
            h = {
                right: 'title, prev, next',
                center: '',
                right: ' month'
            };
        } else {
            $('#calendar').removeClass("mobile");
            h = {
                right: 'title',
                center: '',
                left: ' month, prev,next'
            };
        }                
    } else {
         if ($('#calendar').parents(".portlet").width() <= 720) {
            $('#calendar').addClass("mobile");
            h = {
                left: 'title, prev, next',
                center: '',
                right: 'month'
            };
        } else {
            $('#calendar').removeClass("mobile");
            h = {
                left: 'title',
                center: '',
                right: 'prev,next,month'
            };
        }
    }
	
	var seriesData = '';
	jQuery.ajaxSetup({async:false});
	$.post("/ajax.do",{request:"settleCalendar",type:"admin",month:months},function(data){
		seriesData = data;
	});
	jQuery.ajaxSetup({async:true});
	
	$('#calendar').fullCalendar('destroy'); // destroy the calendar
	$('#calendar').fullCalendar({ //re-initialize the calendar
        header: h,
        editable: false,
        height:650,
        events:seriesData
    });
	
	$('.fc-button-prev, .fc-button-next').click(function(){
		jQuery.ajaxSetup({async:false});
		$.post("/ajax.do",{request:"settleCalendar",type:"admin",month:getMonth($("#calendar").fullCalendar('getDate'))},function(data){
			seriesData = data;
		});
		jQuery.ajaxSetup({async:true});
		$("#calendar").fullCalendar('removeEvents'); 
		$("#calendar").fullCalendar('addEventSource', seriesData); 

 	});
 	 
}

function getMonth(date) {
	var today 	= date;
	var year 	= today.getYear();
	if (year < 2000){year += 1900;}
	var month 	= today.getMonth()+1;
	if(month < 10){month = "0"+month;
	}
	
	return year+"-"+month;
}


function setSideBar(){
	if ($("body").hasClass("page-sidebar-reversed")) {
		$("body").removeClass("page-sidebar-reversed");
		$.cookie('page-sidebar-reversed', 0,{ expires: 7 });
    } else {
    	$("body").addClass("page-sidebar-reversed");
    	$.cookie('page-sidebar-reversed', 1,{ expires: 7 });
    }
}

function setSideBarFix(){
	if ($("body").hasClass("page-sidebar-fixed")) {
		$("body").removeClass("page-sidebar-fixed");
		$.cookie('page-sidebar-fix', 0,{ expires: 7 });
    } else {
    	$("body").addClass("page-sidebar-fixed");
    	$.cookie('page-sidebar-fix', 1,{ expires: 7 });
    }
}





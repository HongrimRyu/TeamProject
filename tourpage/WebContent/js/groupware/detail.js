//디테일 뿌려주기
		$(document).ready(function() {
			$('.detailli').addClass('liselect');
		});		
		function printGroupDetail(){
			$.ajax({
				url: './GroupDetailAction.gw'
					, type: 'post'
					, dataType: 'json'
					, data: {"groupid": groupid}
					, success: function(result){
						var startdate = new Date(result.plan_startdate);
						var enddate = new Date(result.plan_startdate);
						enddate.setDate(startdate.getDate()+result.plan_days-1);
						$('.title').html(result.plan_name);
						$('.date').html(
								"날짜 : "+startdate.getFullYear()+"년 "+(startdate.getMonth()+1)+"월 "+startdate.getDate()+"일 ~ "+
								enddate.getFullYear()+"년 "+(enddate.getMonth()+1)+"월 "+enddate.getDate()+"일"
								);
						$('.peoples').html(result.plan_people);
						getDetailList(result.plan_detail);
						
						
					}
			});
		}
		function getDetailList(plan_detail){
			$.ajax({
				url: './GroupDetailListAction.gw'
					, type: 'post'
					, dataType: 'json'
					, data: {"plan_detail": plan_detail}
					, success: function(result){
						$('.dlist').html('');
						for(var i=0;i<result.length;i++){
							$('.dlist').append("<span class='titDay'>"+(i+1)+"일차</span><br>");
							$('.dlist').append("<span class='detail'>"+result[i]+"</span><br><hr>");
						}
						
					}
			});
		}
		
		function poepleMod(){
			var preVal = $('.peoples').html();
			if($('.peoples').children('#postVal').length==0){
				$('.peoples').html("<input type='text' class='modInp' id='postVal' value='"+preVal+"' onkeyup='peopleConfirm();'>");	
			}else{
				$.ajax({
					url: './GroupDetailPeopleAction.gw'
						, type: 'post'
						, data: {"plan_people": $('#postVal').val(),"groupid": groupid}
						, success: function(){
							$('.peoples').html($('#postVal').val());
						}
				});
			}
			
		}
		function peopleConfirm(){
			if(window.event.keyCode == 13){
				$.ajax({
					url: './GroupDetailPeopleAction.gw'
						, type: 'post'
						, data: {"plan_people": $('#postVal').val(),"groupid": groupid}
						, success: function(){
							$('.peoples').html($('#postVal').val());
						}
				});
				
			}
		}
		function changeDetail(){
			$('#maps').removeClass('active1');
			$('#gwCon').removeClass('active1');
			$('#gwMemo').removeClass('active1');
			$('.detailli').addClass('liselect');
			$('.mapli').removeClass('liselect');
			webSocket1.close();
		}
		   var webSocket1;
		function changeMap(){
//		   	 webSocket1 = new WebSocket('ws://127.0.0.1:8088/tourpage/GroupChat2.gw/'+groupid+"/"+nick);
		   	 webSocket1 = new WebSocket('ws://54.180.2.191:8080/tourpage/GroupChat2.gw/'+groupid+"/"+nick);
				webSocket1.onerror = function(event) {
				      onError(event)
				  };
				  webSocket1.onopen = function(event) {
				      onOpen1(event)
				  };
				  webSocket1.onmessage = function(event) {
				      onMessage1(event)
				  };
		    $('#maps').addClass('active1');
			$('#gwCon').addClass('active1');
			$('#gwMemo').addClass('active1');
			$('.detailli').removeClass('liselect');
			$('.mapli').addClass('liselect');
		}
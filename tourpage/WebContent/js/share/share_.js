/**
 * 
 */
$(document).ready(function(){
	
	$.ajax({
		url: './SharePlanBoard.sp'
		, type: 'post'
		, dataType: 'json'
		, success: function(data){
			var board="";
			$.each(data, function(index, item){
			 // 사진
			 //	제목 (조회수)
			 //	작성자  추천수
			 if(item.plan_days==1){
				 board += "<table class='share' data-idx='"+ item.plan_idx+"'><tr><td class='sImag'><img src='./img/makePlan/info/"+
				 item.image+"'></td></tr><tr><td class='sTit'>"+
				 item.plan_name+" <span class='sRC'>" + 
				 item.readcount + "</span></td></tr><tr><td class='sName'>" +
				 item.nick + " " +
				 item.best + "</td></tr></table>";			
				 $('#sb').html(board);
			 }
			});
			 
			 $('.share[data-idx]').on('click',function(){
		            var plan_idx = $(this).attr('data-idx');
		            location.href="./SharePlanContent.sp?idx="+plan_idx;
		         });
	},
	error : function(log){alert("실패");},
	});
});
		
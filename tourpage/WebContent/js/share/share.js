/**
 * 
 */

function getFormatDate(date){
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;     
	var day = date.getDate();                   
	day = day >= 10 ? day : '0' + day;      
	return  year + '년 ' + month + '월 ' + day +'일';
}

$(document).ready(function(){
	
	$.ajax({
		url: './SharePlanAdd.sp',
		type: 'post',
		dataType: 'json',
		data : {"plan_idx" : document.getElementById('plan_idx').value},
		success : function(data){
			console.log(data);
			var Allplace ="";
			var comment = "";
			$.each(data, function(index, item){
				Allplace += item.plan_detail + ", "
				if(data.length == item.plan_days){
					Allplace = Allplace.substring(0, Allplace.length-1);
					$('#subject').text(item.plan_name);
					$('#allplace').text(Allplace);
					
					var prestartdate = item.plan_startdate.split("-");
					var startdate = new Date(prestartdate[0],prestartdate[1]-1,prestartdate[2]); // 출발날짜
					var startdate2 = new Date(prestartdate[0],prestartdate[1],prestartdate[2]); // 56번 코드줄을 계산할때 1일에 해당하는 계산단위를 계산할수없어서 만든 변수 
					
					var enddate = new Date();
					enddate.setDate((startdate2.getDate())+(item.plan_days-1));	
					
					startdate = getFormatDate(startdate);
					enddate = getFormatDate(enddate);			
					
					var cal_day = item.plan_days;
					if(item.plan_days < 10){
						cal_day =  "0" +item.plan_days;
					}
					
					$('#date').text("날짜 : " + startdate + " ~ " + enddate + "("+ cal_day +"일)");
				}		
				comment += "<div class='scCon" + (index+1) + "'></div><div class='scConSt" + (index+1) + "'>" + (index+1) + "일"+ "</div><div class='scCont" + (index+1) + "'><br><input type='text' name='content" + (index+1) + "' maxlength='30' size='100' placeholder='30자 내 코멘트 입력'/></div>";			
			})
			
			$('#target').html(comment);
		},
		error : function(log){
		history.back();
        alert("이미 공유된 일정입니다.");},
	})
});

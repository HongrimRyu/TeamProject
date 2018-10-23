/**
 * 
 */
var place=[];

function getFormatDate(date){
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;     
	var day = date.getDate();                   
	day = day >= 10 ? day : '0' + day;      
	return  year + '년 ' + month + '월 ' + day +'일';
}


function printDetail(idx){

    $('#contentBoxB').attr('plan_idx',idx);
    $('.dlist *').remove();
    for(var i=0;i<place.length;i++){
    	if(place[i].plan_idx==parseInt(idx)){
		    $('.titB').text(place[i].plan_name); //titB는 제목
			
			var prestartdate = place[i].plan_startdate.split("-");
			var startdate = new Date(prestartdate[0],prestartdate[1]-1,prestartdate[2]);
			var startdate2 = new Date(prestartdate[0],prestartdate[1],prestartdate[2]);
			console.log("place[i].maxDay : " + place[i].maxDay);
			
			var enddate = new Date();
			enddate.setDate((startdate2.getDate())+(place[i].maxDay-1));	
			
			startdate = getFormatDate(startdate);
			enddate = getFormatDate(enddate);
		
			$('.date').text("날짜 : " + startdate + " ~ " + enddate);
			break;
    	}
    }
	var str_p="";
	for(var i=0,max=place.length ; i<max ; i+=1){
		if(place[i].plan_idx==parseInt(idx)){
			str_p += place[i].plan_detail;
			str_p += ","
			$('.dlist').append('<span class=\"titDay\">'+place[i].plan_days+' 일차</span><br>');
			var temp_str= place[i].plan_detail.split(",");
			var t="";
			for(var j=0,max1=temp_str.length ; j < max1 ; j+=1){
				t+=temp_str[j];
				if(j!=(temp_str.length-1)) t+=">";
			}		
			$('.dlist').append('<span class="detail">'+t+'</span><br><hr>');
		}
	}
	str_p=str_p.substring(0, (str_p.length-1));
	$('.plist').text(str_p);

}


$(document).ready(function(){
	$.ajax({
		url: './MyPageAction.mp'
		, type: 'post'
		, dataType: 'json'
		, success: function(data){
			$('#target *').remove();
			console.log(data);
			place=data;
			$.each(data, function(index, item){
				   var datesplit = item.plan_startdate.split("-");
	               var stdDate = new Date(datesplit[0],datesplit[1]-1, datesplit[2]);
	               var today = new Date();
	               var gapDate = stdDate.getTime() - today.getTime();
	               var gapDay = Math.ceil(gapDate / (60*1000*60*24));
	               
	               if(gapDay == 0) {
	                  gapDay = "D-day";
	               }else if(gapDay<0){
	            	   gapDay = "D+"+Math.abs(gapDay);
	               }else{
	            	  gapDay = "D-"+gapDay;
	               }

				if(item.plan_days==1){
				let node =
				 "<li>"+
				 "<div id='contentBox1' onClick='jQuery(this).addClass(\"active\")' data-idx="+item.plan_idx+">"+
				 "<div class='planTxt'>"+
				 "<span class='titA'>"+item.plan_name+"</span>"+
				 "<br>"+
				 "<span id='placeinfo'>"+item.plan_detail+"</span>"+
				 "<br>"+
				 "<span id='days'>"+ (item.maxDay-1) + "박 "+item.maxDay+"일</span>"+
			 "</div>"+
				 "<div class='dday' name='dday'>" + gapDay + "</div>"+
				 "</div>"+
				 "</li>";
				$("#target").append(node);}
			});
			$('#contentBox1[data-idx]').on('click',function(){
				var idx = $(this).attr('data-idx');
				$("#shbtn").on('click', function(){
					document.hifr.plan_idx.value=idx;
					$('#hifr').attr("action","./ShareForm.sp");
					$('#hifr').submit();
				});
				$("#shbtn2").off('click').on('click', function(){
					if(confirm("정말 삭제하시겠습니까?")){
						document.hifr.plan_idx.value=idx;
						$('#hifr').attr("action","./MyPlanDelAction.mp");
						$('#hifr').submit();
					}

				});

				printDetail(idx);
			});
		
			$("#contentBoxALayout li").each(function(){
				$(this).on('click',function(){
					$(this).toggleClass("active");
					$(this).siblings().removeClass("active");
					$("#contentBoxB").addClass("visible");
				});
			});
		},error: function(error){
			alert('요청 실패');
		}
	});
	$("#contentBoxB").each(function(){
		$(this).addClass("hidden");
	});
		$('#modifyPlan').click(function(){
				$('#plan_idx').val($('#contentBoxB').attr('plan_idx'));
				$('#hifr').attr("action","./modifyPlan.pl");
					$('#hifr').submit();
		});
		$('#gwbtn').click(function(){
			$('#gwJoin,.page_cover,body').addClass('open');
			$('#plan_idx').val($('#contentBoxB').attr('plan_idx'));
			window.location.hash='#gwCreate';
		});
	    $('#login_cover').not('#login_form').click(function(){
	    	history.back();
	    })
		$('#lg_btn').click(function(){
			
			$.ajax({
			url: './VariableLoginAction.me'
			, type: 'post'
			, dataType: 'json'
			, data : {"id": $('#loginid').val(),"pass":$('#passwd').val()}
			, success: function(data){
				console.log(data.chk);
				if(data.chk){
					myPlan();
					$('#login_cover').remove();
					$('#login_form').remove();
				}else{
					alert("아이디나 비밀번호가 틀렸습니다.")
				}
					
			}
			});
		});
});
window.onhashchange = function(){
	if (location.hash != "#gwCreate") {
		$("#gwJoin,.page_cover,body").removeClass('open');
	}
};
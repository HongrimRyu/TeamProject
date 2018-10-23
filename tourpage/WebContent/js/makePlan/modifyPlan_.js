/**
 *  여행 일정만들기(2018-07-17)일정만들기.
 *  %목표%
 *  1.전역변수 최대한쓰지 않기
 */
/*전역변수*/
	/*일정관련*/
	var day_num=0,	
	/*마커관련*/
	    day_m=[],//여행기간,
	    s_date="",
        searchBox="",
	    placeInfo=[]; //Object Array , 일정에 대한 계획
	var map={}; //GoogleMap객체생성(설정)
	var PlanCoordinates= []; // Polyline Array
	var flightPath; //Polyline Object
	var infoWindow; //Marker('click event')
	var markers=[]; // marker arrary
	var marker; //마커 객체
	var place=[]; //장소 객체 배열
	var markerCluster;
	var cluster;
	var g_days=0;
	var bounds;
/*전역변수*/

	var c_marker=null;
	var c_icon="";
	var placeInfo;
	var g_idx=0;
	var g_d_idx=0;

var listen1,
    marker1;
window.addEventListener('load', function(){
	listPrint();
	var t_da=$('#startdate').val().split('-');
	s_date=new Date(t_da[0],parseInt(t_da[1])-1,t_da[2]);
	g_days= parseInt($('#days').val(),10);
	setplan(s_date); 
	for(var i = 0 ; i<g_days ; i++){
		var plan=[];
		day_m.push(plan);
	}
	g_idx=$('.tour_item').length;
	map.setCenter(new google.maps.LatLng( parseFloat($('.tour_item[data=0]').attr('lat')) , parseFloat($('.tour_item[data=0]').attr('lng')) ));
    bounds = map.getBounds().toJSON();
 	map.setZoom(14);
 	for(var i=0 ; i < g_days ; i +=1){
 		day_num=i;
	 $('.tour_item[data-day='+i+']').each(function(i,e){
		var point = new google.maps.LatLng(
		        parseFloat($(this).attr('lat'),10),
		        parseFloat($(this).attr('lng'),10)
		        );//위도 경도 객체로 저장.
		var infowincontent = '<div><strong>'+$(this).attr('name')+'</strong><br>'+
							  $(this).attr('addr')+'<br>'+
							  '<img src="./img/makePlan/add.jpg" class="add_btn"'+
							  											'data-prod="'+$(this).attr('data-idx')+'"></div>';
		planMarker(point,$(this).attr('type'),infowincontent,$(this).attr('data-idx'));
	 });
 	}
 	day_num=0;
 	drawPath();
	
})


/**
 * 로그인 관한
 */

function modifyPlan(){
	var planinfo="";
	for(var i = 0; i < g_days  ; i+=1  ){
	$('.tour_item[data-day='+i+']').each(function(i,e){
		planinfo +=$(this).attr('prod')+",";
	});
	planinfo=planinfo.substring(0,(planinfo.length-1));
	planinfo += "/";
    }
	console.log(planinfo);
	
	
	var yy=s_date.getFullYear();
	var mm=s_date.getMonth()+1;
	var dd=s_date.getDate();
	document.getElementById("planInfo").value=planinfo;
	document.getElementById("period").value=g_days;
	document.getElementById("plan_start").value=yy+'-'+mm+'-'+dd;
	$.ajax({
  	  url:"./modifyPlanAction.pl",
                data: {"plan_title":document.getElementById("plan_title").value,"planInfo":planinfo,"plan_start":document.getElementById("plan_start").value,"plan_idx":document.getElementById("plan_idx").value},
                type: 'POST',
          	  dataType: "json",
                success: function(result){
              	  if(result.chk){
              			location.href="./MyPage.mp"
              	}else{
            		alert("로그인이 필요한서비스입니다.");
            		$('.login').addClass('open');
            		$('#login_cover').addClass('open');
            		
            		$('#login_cover').on('click',function(){
            			$('.login').removeClass('open');
            			$('#login_cover').removeClass('open');
            		});
                  	 
                    }
                }
   });

}

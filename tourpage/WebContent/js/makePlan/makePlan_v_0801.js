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
function savePlan(){
	var planinfo="";
	for(var i = 0; i < g_days  ; i+=1  ){
	$('.tour_item[data-day='+i+']').each(function(i,e){
		planinfo +=$(this).attr('prod')+",";
	});
	planinfo=planinfo.substring(0,(planinfo.length-1));
	planinfo += "/";
    }
	if(planinfo.length<=1){
		alert("일정을 추가하여야 합니다.!");
		return ;
	}
	if($('#plan_title').val().trim().length<1){
		alert("제목을 설정해주세요!");
		return ;
	}
	var yy=s_date.getFullYear();
	var mm=s_date.getMonth()+1;
	var dd=s_date.getDate();
	document.getElementById("planInfo").value=planinfo;
	document.getElementById("period").value=g_days;
	document.getElementById("plan_start").value=yy+'-'+mm+'-'+dd;
	$.ajax({
  	  url:"./store.pl",
                data: {"plan_title":document.getElementById("plan_title").value,"planInfo":planinfo,"plan_start":document.getElementById("plan_start").value},
                type: 'POST',
          	  dataType: "json",
                success: function(result){
              	  if(result.chk){
              			location.href="./MyPage.mp"
              	}else{
            		alert("로그인이 필요한서비스입니다.");
            		loginOpen();
                  	 
                    }
                }
   });

}

//제이쿼리
$(document).ready(function(){

    $('.init_cls').click(function(){
		var lat =37.5642135;
		var lng = 127.0016985;
    	var idat1 = document.getElementById("date1").value.split("-");
    	var idat2 = document.getElementById("date2").value.split("-");
    	s_date = new Date(parseInt(idat1[0]), parseInt(idat1[1]-1), parseInt(idat1[2]));
    	var date2 = new Date(idat2[0], idat2[1]-1, idat2[2]);
    	var diff = date2 - s_date;    
    	var currDay = 24 * 60 * 60 * 1000;
    	g_days=(diff/currDay)+1;
    	console.log("날짜"+g_days);
    	console.log("시작");
    	console.log(g_days);
    	for(var i=0;i<g_days;i++) {
    	
    	    var plan_m=[];
    		day_m.push(plan_m);
    	}
    	setplan(s_date);
    	
    	$('#s_date').val(document.getElementById("date1").value);
    	$('#s_day').val(g_days);
    	$('#plan_title').val(" ");
    	$('#init').remove();
    	setting(lat,lng);
	  });
    $('.sub_btn').click(function(){
    	if($('#subject').val().trim().length<1){
    		alert("제목을 설정해주세요!");
    		return ;
    	}
    	
    	var cty = $(this).attr('data-city');
    	if(cty=='seoul'){
    		var lat =37.5642135;
    		var lng = 127.0016985;
    	}else if(cty=='busan'){
    		var lat =35.198362;
    		var lng = 129.053922;
    	}else if(cty=='gangreung'){
    		var lat =37.765259;
    		var lng = 128.886610;
    	}else if(cty=='tongyoung'){
    		var lat =34.853218;
    		var lng =128.433340;
    	}else if(cty=='namhae'){
    		var lat =34.839422;
    		var lng =127.892247;
    	}else if(cty=='jeju'){
    		var lat =33.495550;
    		var lng =126.519734;
    	}else if(cty=='gyeongju'){
    		var lat =35.853399;
    		var lng =129.207098	;
    	}else if(cty=='deagu'){
    		var lat =35.798838	;
    		var lng =128.583052	;
    	}else if(cty=='jeonju'){
    		var lat =35.827025;
    		var lng =127.113919;
    	}
    	var idat1 = document.getElementById("date1").value.split("-");
    	var idat2 = document.getElementById("date2").value.split("-");
    	s_date = new Date(parseInt(idat1[0]), parseInt(idat1[1]-1), parseInt(idat1[2]));
    	var date2 = new Date(idat2[0], idat2[1]-1, idat2[2]);
    	var diff = date2 - s_date;    
    	var currDay = 24 * 60 * 60 * 1000;
    	g_days=(diff/currDay)+1;
    	console.log("날짜"+g_days);
    	console.log("시작");
    	console.log(g_days);
    	for(var i=0;i<g_days;i++) {
    	
    	    var plan_m=[];
    		day_m.push(plan_m);
    	}
    	setplan(s_date);
    	
    	$('#s_date').val(document.getElementById("date1").value);
    	$('#s_day').val(g_days);
    	$('#plan_title').val($('#subject').val());
    	$('#title_show>h1').html("▶"+$('#subject').val());
    	
    	$('#init').remove();
    	setting(lat,lng);

    });
	
});


function setting(lat,lng){
    bounds = map.getBounds().toJSON();
 	map.setCenter(new google.maps.LatLng(lat, lng));
 	map.setZoom(14);
}

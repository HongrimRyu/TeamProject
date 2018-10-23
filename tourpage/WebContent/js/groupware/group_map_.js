/**
 * 
 */
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


	var center="";
	var isFirst=true;
   function onMessage1(event) {

    	console.log(event.data);
    	var str = event.data.split("/");
    	if(str[0]=="add"){
    		var plan_m=[];
    		day_m.push(plan_m);
    		day_num=day_m.length-1;
    		g_days=day_m.length;
    		var date = document.getElementById('date'),value
    		listPrint();
    		drawPath();
    		setplan(s_date)
    		return ;
    	}else if(str[0]=="sort"||str[0]=="action"){
    		var place=JSON.parse(str[1]);
    		console.log(str[2]);
    		var day = str[2].split("@");
    		console.log('길이 : '+ day.length);
    		$('.plan_listA *').remove();
    		for(var i =0, max=day.length ; i < max ; i+=1){
    			var day_plan=day[i].split(",");
    			console.log(day_plan);
    			for(var j=0,mx=day_plan.length ; j < mx ; j+=1){
    	     	$('.plan_listA').append('<li class="tour_item ui-state-default" data="'+g_idx+'"data-idx="'+place[day_plan[j]].prod+'" data-day="'+i+'"'+
    	     			'lat="'+place[day_plan[j]].lat+'" lng="'+place[day_plan[j]].lng+'" name="'+place[day_plan[j]].name+'" addr="'+place[day_plan[j]].address+'"'+
    	     			'prod="'+place[day_plan[j]].prod+'" type="'+place[day_plan[i]].type+'" style="cursor:all-scroll;"></li>');
    	    	var str_list = '<div class="img_box fl"><img src="./img/makePlan/info/'+place[day_plan[j]].image+'">'+
    					'</div><div class="info_box fl">'+place[day_plan[j]].name+
    	 				'<div class="control_box"><img src="./img/makePlan/del_btn.png" class="del_btn" onclick="deletePlan('+g_idx+','+day_num+')"></div>'+
    	 				'</div><div id="clear"></div>';
    	    	$('.tour_item[data='+g_idx+']').append(str_list);
    	    	if(i!=day_num) $('.tour_item[data='+g_idx+']').css("display","none");
    	    	
    	    	g_idx +=1;
    			}
    			printGroupDetail();
    		}
    		drawPath();
    		return ;
    	}else if(str[0]=="delete"){
    		alert("삭제됩니다");
    		Array.prototype.removeElement = function(index)
    		{

    			this.splice(index,1);
    			return this;

    		};
    		$('.tour_item[data-day='+str[2]+']').each(function(i,e){
    			console.log($(this).attr('data')+','+str[1]);
    			if($(this).attr('data')==str[1]){
    				day_m[str[2]][i].setMap(null);
    				day_m[str[2]].removeElement(i);
    			}
    		});
    		$('.tour_item[data='+str[1]+']').remove();
    		drawPath();
    		return;
    	}else if(str[0]=="open"){
    		bounds="";
    		var plan=JSON.parse(str[1]);
    		var place=JSON.parse(str[2]);
    		$('#plan_start').val(plan.plan_startdate);
    		var day = plan.plan_detail.split("@");
    		var t_da=$('#plan_start').val().split('-');
    		s_date=new Date(t_da[0],parseInt(t_da[1])-1,t_da[2]);
    		g_days=plan.plan_days;
    		setplan(s_date);
    		for(var i = 0 ; i<g_days ; i++){
    			var plan=[];
    			day_m.push(plan);
    		}
    		$('.plan_listA *').remove();
    		for(var i =0, max=day.length ; i < max ; i+=1){
    			var day_plan=day[i].split(",");
    			console.log(day_plan);
    			for(var j=0,mx=day_plan.length ; j < mx ; j+=1){	
    	     	$('.plan_listA').append('<li class="tour_item ui-state-default" data="'+g_idx+'"data-idx="'+place[day_plan[j]].prod+'" data-day="'+i+'"'+
    	     			'lat="'+place[day_plan[j]].lat+'" lng="'+place[day_plan[j]].lng+'" name="'+place[day_plan[j]].name+'" addr="'+place[day_plan[j]].address+'"'+
    	     			'prod="'+place[day_plan[j]].prod+'" type="'+place[day_plan[j]].type+'" style="cursor:all-scroll;"></li>');
    	    	var str_list = '<div class="img_box fl"><img src="./img/makePlan/info/'+place[day_plan[j]].image+'">'+
    					'</div><div class="info_box fl">'+place[day_plan[j]].name+
    	 				'<div class="control_box"><img src="./img/makePlan/del_btn.png" class="del_btn" onclick="deletePlan('+g_idx+','+day_num+')"></div>'+
    	 				'</div><div id="clear"></div>';
    	    	$('.tour_item[data='+g_idx+']').append(str_list);
    	    	if(i!=day_num) $('.tour_item[data='+g_idx+']').css("display","none");
    	    	g_idx +=1;
    		}
    		}
    		if(isFirst==true){
    			console.log("dd")
    			map.setCenter(new google.maps.LatLng( parseFloat($('.tour_item[data=0]').attr('lat')) , parseFloat($('.tour_item[data=0]').attr('lng')) ));
    		isFirst=false;
    		}else{
    		map.setCenter(center);}
    		center=map.getCenter();
    		console.log(map.getCenter().toJSON());
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
    		
    		
    		
    	}
    	
    
    }
   
   function onOpen1(event) {
       webSocket1.send(nick + "님이 참여/open");
   }
   
   


/**
 * 일정만들기 통합 JS
 */
  $( function() {
       $( "#sortable" ).sortable({
          stop: function( event, ui ) {
             drawPath();
            }
       });
       $( "#sortable" ).disableSelection();
       $( "#sortable1" ).sortable({
          stop: function( event, ui ) {
             $('.dayBox').each(function(i,e){
                console.log($(this).attr('data-idx')+","+i);
                var idx = $(this).attr('data-idx');
                  $('.tour_item[data-day='+idx+']').attr('data-day',i+"t");
                
             });
             $('.tour_item').each(function(i,e){
                var temp=$(this).attr('data-day');
                temp=temp.substring(0, temp.length-1);
                $(this).attr('data-day',temp);
             });
             setplan(s_date);
             listPrint();
             drawPath();
            }
       });
       $( "#sortable1" ).disableSelection();
        $(document).ready(function(){
           $('.icon').click(function(){
             $('.placeLayout').toggleClass('rightbar');
           });
        });
         $("#title_show").click(function(){
            $('#in_title').val($('#plan_title').val());
            $('#title_modify').css('display','block');
            $('#title_show').css('display','none');
            
         });
         $('#m_btn').click(function(){
            $('#plan_title').val($('#in_title').val());
            $('#title_show>h1').html("▶"+$('#in_title').val());
            $('#title_modify').css('display','none');
            $('#title_show').css('display','block');
         });   
       
  });

/* 구글맵 초기화 함수*/
function initMap() {

   map = new google.maps.Map(document.getElementById('googleMap'), {
       center: new google.maps.LatLng(35.907756999, 127.766922000), //나중에 LatLng 정보 받아와서 좌표 바귀게끔 설정 35.90775699999999, 127.76692200000002)->한국
       disableDefaultUI: true,
       scrollwheel:true,
       zoom: 6,
       minZoom:6
    });

   
   var mcOptions = {zoomOnClick:false,maxZoom: 14, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'};
     markerCluster = new MarkerClusterer(map, markers,mcOptions);
     markerCluster.setAverageCenter(true);
     markerCluster.setMinimumClusterSize(1);
     markerCluster.setGridSize(200);
   //Set polyline(init)
   var lineSymbol = {path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW}; // 선을 화살표로 바꿈
   flightPath = new google.maps.Polyline({ //좌표사이 직선그리기 객체 생성
     path:PlanCoordinates, // 다수의 좌표 (객체 배열)
     geodesic: true,//측지선 설정 
     icons: [{ // 선모양에 관한 설정 100px간격으로 화살표를 설정
         icon: lineSymbol,
         offset: '0',
         repeat: '100px'
     }],
     strokeColor: '#FF0000',
     strokeOpacity: 1.0,
     strokeWeight: 2
   });
   infoWindow = new google.maps.InfoWindow; // 마커 위에 정보를 출력하는 객체(마커 클릭시 생성)

   //맵 초기 설정 끝.
   map.addListener('zoom_changed',function(){
	   center=map.getCenter();
      if(map.getZoom() < 12){
         markerCluster.clearMarkers();
         for(var mi = 0 ; mi < markers.length ; mi++){markers[mi].setMap(null);}
         for(var i = 0 ; i< day_m.length ; i++){
            for(var j = 0 ; j< day_m[i].length ; j++){
               day_m[i][j].setMap(null);
            }
         }
         flightPath.setMap(null);
         bounds=map.getBounds().toJSON();
         
         place=[];
         markers=[];
         bounds.zoom=map.getZoom();
         loadCity(bounds);
      }else if(map.getZoom()>11 && map.getZoom() < 14){
         markerCluster.clearMarkers();
         flightPath.setMap(null);
         for(var mi = 0 ; mi < markers.length ; mi++){markers[mi].setMap(null);}
         for(var i = 0 ; i< day_m.length ; i++){
            for(var j = 0 ; j< day_m[i].length ; j++){
               day_m[i][j].setMap(null);
            }
         }
         bounds=map.getBounds().toJSON();
         place=[];
         markers=[];
         bounds.zoom=map.getZoom();
         clustering(bounds);
      
      }else{
         markerCluster.clearMarkers();
         for(var mi = 0 ; mi < markers.length ; mi++){markers[mi].setMap(null);}
         bounds=map.getBounds().toJSON();
         place=[];
         markers=[];
         bounds.zoom=map.getZoom();
         loadMarker(bounds);
         
      }
   });
   bounds=map.getBounds();
   map.addListener('mouseup',function(){
      var maps=map.getCenter().toJSON();
      center=map.getCenter();
      if(maps.lat < bounds.south || maps.lat > bounds.north){
         for(var mi = 0 ; mi < markers.length ; mi++){
         markers[mi].setMap(null);
      }
         bounds=map.getBounds().toJSON();
         bounds.zoom=map.getZoom();
         place=[];
         markers=[];
         if(map.getZoom() < 12) loadCity(bounds);
         else if(map.getZoom() > 13) loadMarker(bounds);
         else clustering(bounds);
      }
      else if(maps.lng > bounds.east || maps.lng < bounds.west){
         for(var mi = 0 ; mi < markers.length ; mi++){markers[mi].setMap(null);}
         bounds=map.getBounds().toJSON();
         bounds.zoom=map.getZoom();
         place=[];
         markers=[];
         if(map.getZoom() < 12) loadCity(bounds);
         else if(map.getZoom() > 13) loadMarker(bounds);
         else clustering(bounds);
      }
   });
         
}

/**************************/
/* 맵동작과 관련하여 마커동작 함수들*/
/************************/
function clustering(latlng){
   $.ajax({
        method: "POST",
        data:latlng,
        url: "./getMarkerAction.pl",
        dataType: "json", 
        success: function(result){
             place=result;
             var locations=[];
             for(var idx=0,max=place.length ; idx<max ; idx+=1){
            var point = new google.maps.LatLng(
                    parseFloat(place[idx].lat),
                    parseFloat(place[idx].lng));//위도 경도 객체로 저장.
                var marker = new google.maps.Marker({
                    position: point
                  });
                markers.push(marker);
             }
            markerCluster.addMarkers(markers);
            markerCluster.setMap(map);
            cluster = markerCluster.getClusters();
            google.maps.event.addListener(markerCluster,'click',function(c){
               map.setCenter(c.getCenter());
               map.setZoom(14);
             });
           
         }
             
   });
}
function loadMarker(latlng){
   $.ajax({
        method: "POST",
        data:latlng,
        url: "./getMarkerAction.pl",
        dataType: "json", 
        success: function(result){
             place=result;
            printList(result);
             for(var idx=0,max=place.length ; idx<max ; idx+=1){
            var point = new google.maps.LatLng(
                    parseFloat(place[idx].lat),
                    parseFloat(place[idx].lng));//위도 경도 객체로 저장.
            var infowincontent = '<div>'+'<strong>'+place[idx].name+'</strong><br>'+
                             place[idx].address+'<br>'+
                            '<img src="./img/makePlan/add_btn.png" class="add_btn"'+
                                            'data-prod="'+place[idx].prod+'" data-idx="'+idx+'"></div>';
            place[idx].markId=markers.length;
            addMarker(point,place[idx].type,infowincontent,(idx));//마커 생성하기
             }
             drawMarker();
        }
        });
   
}

function loadCity(latlng){
   
   $.ajax({
        method: "POST",
        data:latlng,
        url: "./getCityAction.pl",
        dataType: "json", 
        success: function(result){
             place=result;
             
             for(var idx=0 ; idx<place.length;idx++){
            var point = new google.maps.LatLng(
                    parseFloat(place[idx].lat),
                    parseFloat(place[idx].lng));//위도 경도 객체로 저장.
            var infowincontent = document.createElement('div'); //div태그 생성하는 변수저장.
            var strong = document.createElement('strong'); //strong 태그
            strong.setAttribute("id", "p_name");//strong변수의 id설정
            strong.textContent = place[idx].c_name;//strong안의 텍스트 작성
            infowincontent.appendChild(strong); // 변수로 만든 div태그안에 넣기.
            infowincontent.appendChild(document.createElement('br'));//br태그
            place[idx].markId=markers.length;
             var marker = new google.maps.Marker({
                  map: null,
                     position: point,
                   label : place[idx].c_name
               });
             markers.push(marker);
             marker.addListener('click', function() {
                map.setZoom(14);
                map.setCenter(this.getPosition());
                
             });
             }
             drawMarker();
        }
        });   
}


//Add marker! 맵안에 마커 찍는거
function addMarker(point,type,infowincontent,idx){
   
     var icon = {
           rest: {
             icon: './img/makePlan/category/rest.png'
           },
           shop: {
                icon: './img/makePlan/category/shop.png'
              },
           expr: {
                icon: './img/makePlan/category/expr.png'
              },
           history: {
                icon: './img/makePlan/category/history.png'
              },
           sights: {
              icon:'./img/makePlan/category/sights.png'
           },
           nature: {
               icon:'./img/makePlan/category/nature.png'
            },
           custom: {
              icon: './img/makePlan/category/custom.png'
           }
         };//아이콘설정
      var icons=icon[type].icon;
    var marker = new google.maps.Marker({
         map: null,
            position: point,
          icon: icons
          });
    markers.push(marker);
    marker.addListener('click', function() {          
        infoWindow.setContent(infowincontent);
        infoWindow.open(map, this);
        $('.add_btn').on('click',function(){
        	   if(c_marker!=null){
                   c_marker.setIcon(c_icon);
                }
           g_idx+=1;
           planPrint(idx);
           addPlan();
           infoWindow.close();
           $('#detail_form').css("visibility","hidden");
      
        })
           });
//     marker.addListener('mouseout', function() {
//        
//         
//         infoWindow.close();
//         
//    });
     marker.addListener('click', function() {
        showDetail(idx);
     });
    return null;
   }


/*화면출력에관한 함수들 ▽*/

function printList(result){
   $('.placeBox *').remove();
    console.log(result);
   for(var i=0, max=result.length < 9 ? result.length : 9 ; i<max ; i++){
      console.log(max);
       $('.placeBox').append('<div class="re pointer" style="color: white; background: url(\'./img/makePlan/info/'+result[i].image+'\');'+
         'background-size:318px 100px; height:100px; width: 318px;"'+
         'data-lat="'+result[i].lat+'" data-lng="'+result[i].lng+'" data-idx="'+i+'">'+
         '<p style="text-align: center; font-size: 30px; ">'+result[i].name+'</p><br>'+
         '<p style="text-align: center;">좋아요 '+result[i].plike+'</p>'+
         '</div>');
   }
     $('.re').on('click',function(){
          var point = new google.maps.LatLng(
                 parseFloat($(this).attr('data-lat'),10),
                 parseFloat($(this).attr('data-lng'),10));//위도 경도 객체로 저장.
          map.setCenter(point);
          showDetail(parseInt($(this).attr('data-idx'),10));
          if(c_marker!=null){
             c_marker.setIcon(c_icon);
          }
          for(var j=0,max=markers.length ; j < max ; j += 1){
             if(markers[j].getPosition().toJSON().lat==point.toJSON().lat && markers[j].getPosition().toJSON().lng==point.toJSON().lng){
                c_marker=markers[j];
                c_icon=markers[j].getIcon();
                markers[j].setIcon('./img/makePlan/category/cilp.png');
             }
          }
       });
}


function drawMarker(){
   for(var mi = 0 ; mi < markers.length ; mi++){
      markers[mi].setMap(map);
   }
   return null;
}
/*
 * 일정출력에 대한 함수
 */
var placeInfo;

function planPrint(number){
         var t_day=parseInt(day_num)+1;
         $('.planRightTop').text((t_day)+" 일차");
      
              $('.plan_listA').append('<li class="tour_item ui-state-default" data="'+g_idx+'"data-idx="'+number+'" data-day="'+day_num+'"'+
                    'lat="'+place[number].lat+'" lng="'+place[number].lng+'" name="'+place[number].name+'" addr="'+place[number].address+'"'+
                    'prod="'+place[number].prod+'" type="'+place[number].type+'" style="cursor:all-scroll;"></li>');
             var str_list = '<div class="img_box fl"><img src="./img/makePlan/info/'+place[number].image+'">'+
                  '</div><div class="info_box fl">'+place[number].name+
                   '<div class="control_box"><img src="./img/makePlan/del_btn.png" class="del_btn" onclick="deletePlan('+g_idx+','+day_num+')"></div>'+
                   '</div><div id="clear"></div>';
             $('.tour_item[data='+g_idx+']').append(str_list);
             drawPath()

      return null;
}//화면출력



/*화면출력에관한 함수들 △*/





/**************************/
/* 맵동작과 관련하여 마커동작 함수들*/
/************************/
function addPlan(){
    for(var j= 0,max=day_m[day_num].length ; j<max ;j++ ){day_m[day_num][j].setMap(null);}
    day_m[day_num]=[];
    $('.tour_item[data-day="'+day_num+'"').each(function(i,e){
      var point = new google.maps.LatLng(
              parseFloat($(this).attr('lat'),10),
              parseFloat($(this).attr('lng'),10)
              );//위도 경도 객체로 저장.
      var infowincontent = '<div><strong>'+$(this).attr('name')+'</strong><br>'+
                       $(this).attr('addr')+'<br>'+
                       '<img src="./img/makePlan/add_btn.png" class="add_btn"'+
                                                        'data-prod="'+$(this).attr('data-idx')+'"></div>';
      planMarker(point,$(this).attr('type'),infowincontent,$(this).attr('data-idx'));
    });
    
   return null;
}


function deletePlan(placeNum,day){//일정 삭제
   alert("삭제됩니다");
   Array.prototype.removeElement = function(index)
   {

      this.splice(index,1);
      
      return this;

   };
   $('.tour_item[data-day='+day+']').each(function(i,e){
      console.log($(this).attr('data')+','+placeNum);
      if($(this).attr('data')==placeNum){
         day_m[day][i].setMap(null);
         day_m[day].removeElement(i);
         
      }
   });
   $('.tour_item[data='+placeNum+']').remove();
   drawPath();
   
}//일정삭제


/**
 * 일정선 출력 & 마커 등록
 */
function drawPath(){
    var planLine=[];
    $('.tour_item[data-day="'+day_num+'"').each(function(i,e){
      var plan_list = {};
      plan_list.lat=parseFloat($(this).attr('lat'));
      plan_list.lng=parseFloat($(this).attr('lng'));
      planLine.push(plan_list);
    });

   flightPath.setPath(planLine)
   flightPath.setMap(map);
}
function planMarker(point,type,infowincontent,idx){
     var icon = {
           rest: {
             icon: './img/makePlan/category/rest.png'
           },
           shop: {
                icon: './img/makePlan/category/shop.png'
              },
           expr: {
                icon: './img/makePlan/category/expr.png'
              },
           history: {
                icon: './img/makePlan/category/history.png'
              },
           sights: {
              icon:'./img/makePlan/category/sights.png'
           },
           custom: {
              icon: './img/makePlan/category/custom.png'
           }
         };//아이콘설정
      var icons=icon[type].icon;
    var marker = new google.maps.Marker({
         map: map,
            position: point,
          icon: icons
          });
    day_m[day_num].push(marker);
    marker.addListener('click', function() {          
       infoWindow.setContent(infowincontent);
       infoWindow.open(map, this);
       $('.add_btn').on('click',function(){
    	   if(c_marker!=null){
               c_marker.setIcon(c_icon);
            }
          g_idx+=1;
          planPrint(idx);
          addPlan();
          infoWindow.close();
          $('#detail_form').css("visibility","hidden");
     
       })
          });
//    marker.addListener('mouseout', function() {
//       
//        
//        infoWindow.close();
//        
//   });
    marker.addListener('click', function() {
       showDetail(idx);
    });
    return null;
}

  
function showDetail(num){

   	 $.ajax({
   		 type: "POST",
   		 url: "./likeCheck.pl",
   		 data: {"prod":place[num].prod},
   		 cache:false,
   		 dataType: "json", 
   		 success: function(html){
   			 if(html.chk){
   				 if(html.like=="like"){
   					 $('.like_img').attr('src','./img/makePlan/like.png');
   					 $('.like_img').attr('like','like');
   				 }else if(html.like=="unlike"){
   	   				 $('.like_img').attr('src','./img/makePlan/unlike.png');
   	   				 $('.like_img').attr('like','unlike');
   				 }
   			 }else{
	   				 $('.like_img').attr('src','./img/makePlan/unlike.png');
	   				 $('.like_img').attr('like','unlike');
   			 }
   		 },error:function(err){
   		 }
   	 });

   $('#detail_form').attr("style","z-index:999;");
   $('#detail_form').css("visibility","visible");
   $('.detail_item').html(place[num].info);
   $('.detail_name').html(place[num].name);
   $('.detail_address').html("<h4>ADDRESS</h4>"+place[num].address);
   $('.detail_o_time').html("<h4>OPENING HOURS</h4>"+place[num].o_time);
   $('.detail_phone').html("<h4>CONTACT</h4>"+place[num].phone);
   $('.detail_img').attr("src","./img/makePlan/info/"+place[num].image);
   $('.like_img').attr("data-idx",place[num].prod);
   $('.close').click(function() {
      $('#detail_form').attr("style","z-index:0;");
      $('#detail_form').css("visibility","hidden");
      if(c_marker!=null){
          c_marker.setIcon(c_icon);
       }
   });
   $('header').click(function() {
      $('#detail_form').attr("style","z-index:0;");
      $('#detail_form').css("visibility","hidden");
      if(c_marker!=null){
          c_marker.setIcon(c_icon);
       }
   });
   $('.mapIcon').click(function() {
      $('#detail_form').attr("style","z-index:0;");
      $('#detail_form').css("visibility","hidden");
      if(c_marker!=null){
          c_marker.setIcon(c_icon);
       }
   });

   $('#detail_btn').off().on('click',function(){
            if(c_marker!=null){
             c_marker.setIcon(c_icon);
          }
          g_idx+=1;
          planPrint(num);
          addPlan();
          infoWindow.close();
          $('#detail_form').css("visibility","hidden");
   });
}




function setplan(dt){
   $('.leftlist').empty();
   var yy=dt.getFullYear();
   var mm=dt.getMonth()+1;
   var dd=dt.getDate();
   var maxdays=getEndOfMonthDay(yy,mm);

   for(var p =0,max=g_days ; p < max ; p++){
      $('.leftlist').append('<li class="dayBox" idx="'+g_d_idx+'" data-idx="'+p+'"><div class="Abox"  style="cursor:pointer;"  data-day="'+p+'" >'
            +'<span id="dayel"  >'+(p+1)+' 일차</span><br><span id="date">'+yy+'년'+mm+'월'+dd+'일'+'</span> <br> '
            +'<img alt="빼기" src="./img/makePlan/iconMinus.png" onclick="removeDay('+g_d_idx+');" style="cursor:pointer;"></div></li>');
      g_d_idx +=1;
      if(dd==maxdays){
         if(mm==12){
            yy=yy+1;
            mm=1;
            dd=1;
         }else{
            mm=mm+1;
            dd=1;
         }
         maxdays=getEndOfMonthDay( yy,mm);
      }else{
         dd=dd+1;
      }
   }   
   $('.Abox[data-day='+day_num+']').not('.Abox[data-day='+day_num+']').css('border','solid 3px black');
   $('.Abox[data-day='+day_num+']').css('border','solid 3px red');
   
   $('.Abox[data-day]').on('click',function(){
      $('.Abox').not(this).css('border','solid 3px black');
      $(this).css('border','solid 3px red');
      day_num=$(this).attr('data-day');
      listPrint();
      drawPath();
   });
}


function listPrint(){
   $('.planRightTop').text((parseInt(day_num)+1)+" 일차");
    $('.tour_item').each(function(i,e){
         if($(this).attr('data-day')==day_num){
            $(this).css('display','block');
         }else{
            $(this).css('display','none');
         }
    });
}




function today(){
   var today = new Date();
   return today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
   
}

//년월 입력시 마지막 일자
function  getEndOfMonthDay( yy, mm )
{
    var max_days=0;
    if(mm == 1) {
        max_days = 31 ;
    } else if(mm == 2) {
        if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))
            max_days = 29;
        else
            max_days = 28;
    }
    else if (mm == 3)   max_days = 31;
    else if (mm == 4)   max_days = 30;
    else if (mm == 5)   max_days = 31;
    else if (mm == 6)   max_days = 30;
    else if (mm == 7)   max_days = 31;
    else if (mm == 8)   max_days = 31;
    else if (mm == 9)   max_days = 30;
    else if (mm == 10)  max_days = 31;
    else if (mm == 11)  max_days = 30;
    else if (mm == 12)  max_days = 31;
    else                return '';
    return max_days;
}
$(function() {
   var dateFormat = "mm/dd/yy",
      from = $( "#date1" )
        .datepicker({
          defaultDate: "+1w",
          dateFormat: 'yy-mm-dd',
     
        })
        .on( "change", function() {
          to.datepicker( "option", "minDate", getDate( this ) );
        }),
      to = $( "#date2" ).datepicker({
        defaultDate: "+1w",
        dateFormat: 'yy-mm-dd',
      })
      .on( "change", function() {
        from.datepicker( "option", "maxDate", getDate( this ) );
      });
 
    function getDate( element ) {
      var a =  element.value.split("-");
      var b =  a[1]+'/'+a[2]+'/'+a[0];
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat,b);
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
    
    
    
});


function removeDay(g_d_idx){
   alert("dd");
   day_num=0;
   day_m.pop();
   g_days-=1;
   console.log($('.dayBox[idx='+g_d_idx+']').attr('data-idx'));
   $('.tour_item[data-day='+$('.dayBox[idx='+g_d_idx+']').attr('data-idx')+']').remove();
   $('.dayBox[idx='+g_d_idx+']').remove();
   
   if(g_days<1){
      var plan_m=[];
      day_m.push(plan_m);
      g_days=1;
      listPrint();
      setplan(s_date);
      return null;
   }
   listPrint();
   setplan(s_date);

   
}

/////////////////////////
//장소 추가에 관한 메서드
/////////////////////////

function addMap() {
	initiateMap();
var input = document.getElementById('pac-input');
searchBox = new google.maps.places.SearchBox(input);
searchBox.addListener('places_changed', function() {
var places = searchBox.getPlaces();
document.getElementById("latlng").value=places[0].geometry.location

});

$('#place_add').attr("style","z-index:999;");
$('#place_add').css("visibility","visible");
$('#place_add').css("overflow","auto");

var position=map.getCenter().toJSON();  
var point = new google.maps.LatLng(
parseFloat(position.lat),
parseFloat(position.lng));//위도 경도 객체로 저장.
marker1 = new google.maps.Marker({
map: map,
draggable: true,
animation: google.maps.Animation.DROP,
position: point
});
listen1=map.addListener('bounds_changed', function() {
searchBox.setBounds(map.getBounds());
});
searchBox.addListener('places_changed', function() {
var places = searchBox.getPlaces();
marker1.position=places[0].geometry.location
toggleBounce(geocoder,marker1.position);
marker1.setPosition(marker1.position);
marker1.setMap(map);


var bounds = new google.maps.LatLngBounds();
if (places[0].geometry.viewport) {
// Only geocodes have viewport.
bounds.union(places[0].geometry.viewport);
} else {
bounds.extend(places[0].geometry.location);
}

map.fitBounds(bounds);

});

// Listen for the event fired when the user selects a prediction and retrieve
// more details for that place.

var geocoder = new google.maps.Geocoder;
// marker.addListener('click', toggleBounce(geocoder));
marker1.addListener('dragend', function() {

toggleBounce(geocoder,marker1.position);
    });

}

function toggleBounce(geocoder,place) {
geocoder.geocode({'location' : place}, function(results,status){
if(status === 'OK'){
if(results[0]){
document.getElementById("pac-input").value=results[0].formatted_address;
document.getElementById("latlng").value=place;

}
}
});


if (marker1.getAnimation() !== null) {
marker1.setAnimation(null);
} else {
marker1.setAnimation(google.maps.Animation.BOUNCE);
}
}

function initiateMap(){
    $('#place_add').attr("style","z-index:0;");
    $('#place_add').css("visibility","hidden");
    $("#pac-name").val("");
    $("#pac-input").val("");
    $("#detail").val("");
    $("#type").val("");
    $("#latlng").val("");
    $("#o_time").val("");
    $("#phone").val("");
    $("#price").val("");
    $("#traffic").val("");
    $("#homepage").val("");
    $('#image-upload').val("");
    $('#image-preview').css('background-image','');
    $('#image-label').html("Choose File");
    $(".class_item").css('filter','grayscale(100)');
google.maps.event.removeListener(listen1);
if(marker1!=null){
marker1.setMap(null);
}
}
function e_key(){
	 if(window.event.keyCode==13){
         $('#plan_title').val($('#in_title').val());
         $('#title_show>h1').html("▶"+$('#in_title').val());
         $('#title_modify').css('display','none');
         $('#title_show').css('display','block');
	 }
}

//제이쿼리
$(document).ready(function(){
/*
* 출발일 변경하는 캘린더 출력.
* 날짜 선택에 관하여 아직 하지 않음.
*/

   
   $( ".picker-popup" ).datepicker({
      dateFormat: 'yy.mm.dd',
      onSelect: function (dateText, inst) {
         
         var idat1 = dateText.split(".");
         s_date = new Date(idat1[0], idat1[1]-1, idat1[2]);
         console.log(idat1[0]+','+idat1[1])
         console.log(s_date.getFullYear());
         console.log(s_date.getMonth());
         console.log(s_date.getDate());
         setplan(s_date);
         $( ".picker-popup" ).css('display','none');
      }
   });
$( ".picker-popup" ).css('display','none');
   $('.planButton2').click(function(){
   
      $( ".picker-popup" ).css('display','block');
   });
   $('#planRight').click(function(){
      $( ".picker-popup" ).css('display','none');
   })
   $('header').click(function(){
      $( ".picker-popup" ).css('display','none');
   })

   $('.planAddButton').click(function(){
      var plan_m=[];
      day_m.push(plan_m);
      day_num=day_m.length-1;
      g_days=day_m.length;
      var date = document.getElementById('date'),value
      listPrint();
      drawPath();
      setplan(s_date);
      
   });   

  $.datepicker.setDefaults({        
      prevText: '이전 달',
      nextText: '다음 달',
      monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
      monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
      dayNames: ['일', '월', '화', '수', '목', '금', '토'],
      dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
      dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
      showMonthAfterYear: true,
      yearSuffix: '년'
  });
   $('#date1').val(today());   
   $('#date2').val(today());   

         
     $( "#place_add" ).draggable({
        axis: "x"
     });
   
     $('.cls_btn').click(function(){
    	 initiateMap();
         $('#place_add').attr("style","z-index:0;");
         $('#place_add').css("visibility","hidden");
         $("#pac-name").val("");
         $("#pac-input").val("");
         $("#detail").val("");
         $("#type").val("");
         $("#latlng").val("");
         $("#o_time").val("");
         $("#phone").val("");
         $('#image-upload').val("");
         $('#image-preview').css('background-image','');
         $('#image-label').html("Choose File");
         
     });
     $('.addPlace_btn').click(function(){
         var form = $('#fr')[0];
         var formData = new FormData(form);
         formData.append("pac-name", $("#pac-name").val());
         formData.append("pac-input", $("#pac-input").val());
         formData.append("detail", $("#detail").val());
         formData.append("type", $("#type").val());
         formData.append("latlng", $("#latlng").val());
         formData.append("traffic", $("#traffic").val());
         formData.append("price", $("#price").val());
         formData.append("phone", $("#phone").val());
         formData.append("homepage", $("#homepage").val());
         formData.append('image-upload', $('#image-upload')[0].files[0]);
         formData.append('share', document.getElementById('sharechk').checked);
   
         if($("#pac-name").val().trim().length<1){
            alert("장소이름을 입력해주세요");
            $( "#pac-name" ).focus();
            return null;
         }
         if($("#latlng").val().trim().length<1){
            alert("검색된주소를 사용하거나 지도에서 위치를 지정해주세요.");
            return null;
         }

         if($("#type").val().trim().length<1){
            alert("카테고리를 설정해주세요");
            return null;
         }
         $.ajax({
            url:"./addPlaceAction.pl",
                     processData: false,
                     contentType: false,
                     data: formData,
                     type: 'POST',
                     success: function(result){
                    	 alert("와우");
                    	 console.log(result);
                    	 var str= result.split("/");
                    	 var chk= JSON.parse(str[0]);
                    	 console.log(chk);
                        if(chk.chk){
                        	var p=JSON.parse(str[1]);
                        	place.push(p);
                        	 for(var mi = 0 ; mi < markers.length ; mi++){markers[mi].setMap(null);}
                            var point = new google.maps.LatLng(
                                    parseFloat(p.lat),
                                    parseFloat(p.lng));//위도 경도 객체로 저장.
                            var infowincontent = '<div>'+'<strong>'+p.name+'</strong><br>'+
                                             p.address+'<br>'+
                                            '<img src="./img/makePlan/add_btn.png" class="add_btn"'+
                                                            'data-prod="'+p.prod+'" data-idx="'+place.length+'"></div>';
                            p.markId=markers.length;
                            addMarker(point,p.type,infowincontent,place.length);//마커 생성하기
                            drawMarker();
                            alert("업로드 성공!!");
                            
                         }else{
                            alert("로그인이 필요한 서비스입니다.");
                         }
                     }
        });
         initiateMap();

     });
     
     
     $(".class_item[data-name]") .click(function(){
        $(".class_item").not(this).css('filter','grayscale(100)');
        $(this).css('filter','grayscale(0)');
        document.getElementById("type").value=$(this).attr('data-name');
     });
     
     $('.like_img[like]').click(function(){
    	 var like=$(this).attr('like');
    	 var url="";
    	 if(like=='like'){
    		 url="./unlikeAction.pl"
    	 }else if(like=='unlike'){
    		 url="./likeAction.pl"
    	 }
    	 $.ajax({
    		 type: "POST",
    		 url: url,
    		 data: {"prod":$(this).attr("data-idx")},
    		 cache:false,
    		 dataType: "json", 
    		 success: function(html){
    			 console.log(html);
    			 if(html.chk){
    				 if(like=="unlike"){
    					 $('.like_img').attr('src','./img/makePlan/like.png');
    					 $('.like_img').attr('like','like');
    				 }else if(like=="like"){
    					 $('.like_img').attr('src','./img/makePlan/unlike.png');
    					 $('.like_img').attr('like','unlike');
    				 }
    			 }else{
    				 alert("로그인해주세요.");
    			 }
    		 },error:function(err){
    		 }
    	 });
     });
     
     $('.planButton2_1').click(function(){
    	if($('.tour_item').length>0){
    		if(confirm("저장되지 않은 일정은 삭제됩니다 괜찮습니까?")){
    			location.reload(); 
    		}else{
    			return ;
    		}
    	} 
    	location.reload(); 
     });
     $.uploadPreview({
          input_field: "#image-upload",   // Default: .image-upload
          preview_box: "#image-preview",  // Default: .image-preview
          label_field: "#image-label",    // Default: .image-label
          label_default: "Choose File",   // Default: Choose File
          label_selected: "Change File",  // Default: Change File
          no_label: false                 // Default: false
        });
     

});


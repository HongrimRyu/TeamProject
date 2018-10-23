<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
  
    <meta charset="utf-8">
    
    <title>Marker Animations</title>
    
    <style> 
      #map {
        height: 300px;
        padding: 15px;
      }
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
    
    <link rel="stylesheet" type="text/css" href="./css/plan.css">
    <script src="./js/jquery-3.3.1.min.js"></script>
    <script src="./tour/tourClip_v_180711.js"></script>
  </head>
  <body>
  <div style="width: 500px; margin: auto; border: 1px solid black;">
  <input type="hidden" id="type">
	  <div style="padding: 15px;">
	  	   -위치 이름-<br>
		   <input id="pac-name" class="controls" type="text" placeholder="Search Box" style="width: 100%; height: 50px; padding-bottom: 5px"><br>
		   -위치 주소-<br>
		   <input id="pac-input" class="controls" type="text" placeholder="Search Box" style="width: 100%; height: 50px; padding-bottom: 5px"><br>
		   <input id="latlng" class="controls" type="text" readonly style="width: 100%; height: 50px;">
	  </div>
	  <div id="map"></div>
	  <div class="cat_box">
	   <div>카테고리</div>
	   <div class="cat_img">
	   	<img  class="class_item" src="./img/rest_cat_off.png" data-name="rest">
	   	<img  class="class_item" src="./img/bar_cat_off.png"  data-name="bar">
	   </div>
	  </div>
	  <div id="clear"></div>
	 
	  <div class="btn_area">
	  		<img class="add_btn" src="./img/add_btn.png">
	  </div>
  </div>
  
        <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0RaRBOgbRI5gID0YDXFW41VUh2rfBLqE&libraries=places&callback=initMap">
    </script>
  </body>
</html>
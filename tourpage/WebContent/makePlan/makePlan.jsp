<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   
   <link rel="stylesheet" type="text/css" href="./css/commons/defaultCSS.css">
   <link rel="stylesheet" type="text/css" href="./css/makePlan/map.css">
   <link rel="stylesheet" type="text/css" href="./css/makePlan/plan_v_180718_map.css">
   <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic"  rel="stylesheet">
   <link rel="shortcut icon" href="./img/commons/favicon15.ico">
   <link rel="stylesheet"  href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
   <link rel="stylesheet" href="//code.jquery.com/resources/demos/style.css">
     <!-- Link Swiper's CSS -->
  <link rel="stylesheet" href="./css/makePlan/swiper.min.css">
   
   <script src="./js/commons/jquery-3.3.1.js"></script>
   <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <script src="./js/makePlan/makePlan_v_0801.js"></script>
   <script src="./js/makePlan/commons_plan_js_.js"></script>
   <script src="./js/makePlan/jquery.uploadPreview.js"></script>
   <script src="./js/makePlan/markerclusterer_packed.js"></script>
   
<title>make your page, TOURPAGE</title> 
</head>
<body>
   <!-- 
      *************************************************************
      시작 화면  시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 
      *************************************************************
    -->
   <div id="init">
      <div id="swiper">
         <div class="swiper-container">
         <div class="xbox init_cls"><img src="img/commons/xbox.svg"/></div><br><br>
         <p class="tit">추천여행지</p>
             <div class="swiper-wrapper">
               <div class="swiper-slide"><img src="./img/makePlan/city/busan.jpg" class="sub_btn" style="cursor: pointer; " data-city="busan"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/seoul.jpg" class="sub_btn" style="cursor: pointer;" data-city="seoul"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/jeju.jpg" class="sub_btn" style="cursor: pointer;" data-city="jeju"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/gyeongju.jpg" class="sub_btn" style="cursor: pointer;" data-city="gyeongju"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/daegu.jpg" class="sub_btn" style="cursor: pointer;" data-city="deagu"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/jeonju.jpg" class="sub_btn" style="cursor: pointer;" data-city="jeonju"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/gangreung.jpg" class="sub_btn" style="cursor: pointer;" data-city="gangreung"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/tongyoung.jpg" class="sub_btn" style="cursor: pointer;" data-city="tongyoung"></div>
               <div class="swiper-slide"><img src="./img/makePlan/city/namhae.jpg" class="sub_btn" style="cursor: pointer;" data-city="namhae"></div>
             </div> <!-- swiper wraapper -->
            <!--  Add Pagination -->
             <div class="swiper-pagination"></div>
            <!--  Add Arrows -->
             <div class="swiper-button-next"></div>
             <div class="swiper-button-prev"></div>
         </div> <!-- swiper container -->
         <div class="swiper-input">
              <p><img src="./img/commons/dot.png"> 여행기간 <input type="text" id="date1"> ~ <input type="text" id="date2"> </p>
          <br>
         <div id="initForm" style="text-align: center"  >
            <img src="./img/commons/dot.png"> 여행 제목
            <input type="text" id="subject" name="subject" style="width: 500px;">
            <input type="hidden" id="s_date" name="s_date">   
            <input type="hidden" id="s_day" name="s_day">   
         </div>
         </div>
      </div><!-- swiper -->
   </div><!-- init --> 
   <!-- 
      *************************************************************
      시작 화면  시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 시작 화면 
      *************************************************************
    -->


<div id="wrap">
   <!-- header menu -->
   <%@include file="../inc/header.jsp" %>
   <!-- header menu -->
   <article> 
   <aside>
      <div id="planTitle">
         <div id="title_show"><h1>제목을 입력하세요 </h1><img src="./img/makePlan/icon/icon_edit.png" alt="edit"></div>
         <div id="title_modify"><input type="text" id="in_title" onkeyup="e_key();"><div id="m_btn"><img src="./img/makePlan/icon/icon_edit.png" alt="edit"></div></div>      
      </div>
      <div id="place_add">
       <div class="addNewPlace">
        <form action="placeAddAction.pl" method="post" name="fr" id="fr"  enctype="multipart/form-data">
           <input type="hidden" id="type" name="type">
                 <img src="./img/commons/xbox.svg" width="30px" class="xbox cls_btn">
             <span class="npTit">신규 장소 등록</span>
              <!-- 장소추가 input -->
                 <div class="npInput">
                  <img src="./img/commons/menuicon.png" width="5px"> 이미지<br>
                  <div id="image-preview"  style="border: 1px dotted #aeaeae; border-radius: 5px; padding-bottom: 5px">
                    <label for="image-upload" id="image-label">Choose File</label>
                    <input type="file" name="image" id="image-upload" />
                  </div><!-- image-preview -->
                    <img src="./img/commons/menuicon.png" width="5px"> 장소 이름*<br>
                  <input id="pac-name" name="pac-name"class="controls" type="text"><br>
                 <img src="./img/commons/menuicon.png" width="5px">  장소 주소*<br>
                  <input id="pac-input" name="pac-input" class="controls" type="text" placeholder="Search Box"><br>
                  <input id="latlng" name="latlng" type="hidden">
                  <div id="map"></div>
                 <img src="./img/commons/menuicon.png" width="5px">  장소 설명<br>
                  <textarea name="detail" id="detail" rows="10" cols="65"></textarea>
                  <img src="./img/commons/menuicon.png" width="5px"> 운영시간<br>
                  <input id="o_time" name="o_time" class="controls" type="text" ><br>
                 <img src="./img/commons/menuicon.png" width="5px">  연락처<br>
                  <input id="phone" name="phone" class="controls" type="text"><br>
                  <img src="./img/commons/menuicon.png" width="5px"> 가격정보<br>
                  <input id="price" name="price" class="controls" type="text"><br>
                  <img src="./img/commons/menuicon.png" width="5px"> 교통정보<br>
                  <input id="traffic" name="traffic" class="controls" type="text" ><br>
                  <img src="./img/commons/menuicon.png" width="5px"> 홈페이지<br>
                  <input id="homepage" name="homepage" class="controls" type="text"><br>
                  </div><!-- npInput -->
         </form>    
         <div class="cat_box">
            <div><img src="./img/commons/menuicon.png" width="5px"> 카테고리*</div>
            <div class="cat_img"> <!-- iconbox -->
               <img  class="class_item" src="./img/makePlan/icon/icon_shop.svg" width="40px" data-name="shop" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_restaurant.svg" width="40px" data-name="rest" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_culture.svg" width="40px" data-name="expr" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_drive.svg" width="40px" data-name="history" style="cursor: pointer; filter:grayscale(100);">
               <img  class="class_item" src="./img/makePlan/icon/icon_hotel.svg" width="40px" data-name="nature" style="cursor: pointer; filter:grayscale(100);">
            </div>
           </div><!-- cat_box -->
                      <img src="./img/commons/menuicon.png" width="5px"> 공유하기 <input type="checkbox" value="share" id="sharechk" name="share">
           <div id="clear"></div>
           <div class="btn_area"><!-- 추가하기 -->
                 <img class="addPlace_btn" src="./img/makePlan/add_btn.png">
           </div><!-- btn_area -->
       </div><!-- .addNewPlace -->
       </div><!-- #placeAdd -->
         
      <div id="detail_form">
      	  <img class="like_img" src="./img/makePlan/unlike.png" like="unlike">
          <img class="close xbox" src="./img/commons/xbox.svg">
          <img class="detail_img">
       <div class="dForm">   
          <div class="detail_name"></div>
          <div class="detail_item"></div><br>
          <div class="detail_address"></div><br>
          <div class="detail_o_time"></div><br>
          <div class="detail_phone"></div><br>
      </div>
      <img src="./img/makePlan/add_btn.png" id="detail_btn">
      </div><!-- detail_form -->
         
         
      <div id="planLeft">
         <!-- 일정추가 -->
         <input type="button" class="planButton2" value="날짜변경하기">
            <div class="picker-popup" style="z-index: 9999; position: absolute;"></div> 
         <input type="button" class="planAddButton" value="일정추가">
         <!-- 일정추가 -->
         <div class="planLeftA">
            <ul class="leftlist" id="sortable1"></ul>
         </div><!-- 날짜 출력 -->
         <div class="planLeftB">
            <input type="button" class="planButton2_1" value="일정 전체 삭제">
            <input type="button" class="planSaveButton" value="일정저장" onclick="savePlan();">
         </div>
      </div><!-- planLeft -->
      <div id="planRight">
         <div class="planRightTop"> 1 일차</div>
         <div class="planRightA" >
            <ul class="plan_listA" id="sortable">
             </ul>
         </div>
         <div class="planRightB">
            <!-- 장소추가버튼 -->
            <input type="text" id="pInput" class="planInput">
            <br>
            <input
               type="button" value="장소추가하기" onclick="addMap();"
               class="planButton3"><br>
         </div>
      </div><!-- planRight -->
   </aside> 
   <!-- 본문 지도부분 -->      
   <section>
      <div id="sLayout">
         <div class="mapLayout">
            <div class="mapIcon">
               <!-- 아이콘 이미지 들어갈 예정 -->
               <ul>
                  <li><img src="./img/makePlan/icon/icon_shop.svg" alt="쇼핑" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_culture.svg" alt="문화" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_restaurant.svg" alt="식당" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_drive.svg" alt="교통" width="40px"></li>
                  <li><img src="./img/makePlan/icon/icon_hotel.svg" alt="숙박" width="40px"></li>
               </ul>
            </div>
            <!-- map들어갈곳 -->
            <div id="googleMap"></div>         
            <script async defer   src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0RaRBOgbRI5gID0YDXFW41VUh2rfBLqE&libraries=places&callback=initMap"></script>
            <!-- map들어갈곳 -->
         </div>
         
         <!-- 우측 박스  -->
         <div class="placeLayout">
         <div class="icon">◀</div>
            <div class="RightBox">
               <div class="searchBox">
                <h3><img src="./img/commons/menuicon.png" width="5px"> 장소검색</h3>
                  <input type="text" id="search">
               </div>
               <div class="placeBox"></div>
            </div>
         </div><!-- placeLayout -->
         
      </div><!-- sLayout -->
   </section> 
   </article>
   <!-- 본문 끝   -->
      
   <!-- footer -->
   <jsp:include page="../inc/footer.jsp"/>
   <!-- footer -->
   
   <form action="store.pl" method="post" id="myform">
      <input type="hidden" id="plan_idx" name="plan_idx">
      <input type="hidden" id="plan_title" name="plan_title">
      <input type="hidden" id="planInfo" name="planInfo">
      <input type="hidden" id="plan_start" name="plan_start">
      <input type="hidden" id="period" name="period">
   </form>
   <!-- Swiper JS -->
       <script src="./js/makePlan/swiper.min.js"></script>

     <!-- Initialize Swiper -->
     <script>
     var swiper = new Swiper('.swiper-container', {
          slidesPerView: 3,
          spaceBetween: 15,
          slidesPerGroup: 3,
          loop: true,
          loopFillGroupWithBlank: true,
          pagination: {
            el: '.swiper-pagination',
            clickable: true,
          },
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
          },
        });
     </script>
     
</div><!-- wrap -->     
</body>
</html>
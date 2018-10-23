<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="css/shop/shop.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic"
			rel="stylesheet">
		<link rel="shortcut icon" href="./img/commons/favicon15.ico">
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<title>shop</title>
		
		<script type="text/javascript">
		
			/* $(document).ready(function() {

			  $('.shopInp').keydown(function(event) {
			    // enter has keyCode = 13, change it if you want to use another button
			    if (event.keyCode == 13) {
			      $('.shopBtn').click();
			      return false;
			    }
			  });

			});
			function getXMLHttpRequest(){
				var httpRequest = null;
			    if(window.ActiveXObject){
			        try{
			            httpRequest = new ActiveXObject("Msxml2.XMLHTTP");    
			        } catch(e) {
			            try{
			                httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
			            } catch (e2) { httpRequest = null; }
			        }
			    }
			    else if(window.XMLHttpRequest){
			        httpRequest = new window.XMLHttpRequest();
			    }
			    return httpRequest;    
			}	
			function Scallback(){
				if(httpRequest.readyState == 4){
					/* alert(httpRequest.getAllResponseHeaders());
			        var resultText = httpRequest.responseText;
			        
			      	if(resultText==0){
			      		alert("검색 결과가 없습니다.");
			      	}else{
			      		var searchResult = new Array();
			      		searchResult = JSON.parse(resultText);
			      		for(var i=0;i<searchResult.length;i++){
				        	$('#Sresult').html($('#Sresult').html()+"<tr><td>제목: "+searchResult[i].title+"</td><td>링크: "+searchResult[i].link+"</td></tr>");
				      	}
			      	}
			    }
			}
			function Search(){
				if($('.shopInp').val()==""){
					alert("검색어를 입력해주세요.");
					$('.shopInp').focus();
				}else{
					$('#Sresult').html('');
					var keyword = $('.shopInp').val();
					var param="keyword="+keyword;
			        httpRequest = getXMLHttpRequest();
			        httpRequest.onreadystatechange = Scallback;
			        httpRequest.open("POST", "searchAction.shop", true);    
			        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8'); 
			        httpRequest.send(param);
				}
			} */
			
			function chk(){
				if($('.shopInp').val() == ""){
					alert("검색어를 입력해주세요.");
					$('.shopInp').focus();
					return false;
				}
			}
		</script>
	
	</head>
<body>
   <div id="wrap">
      <!-- header menu -->
      <jsp:include page="../inc/header.jsp"/>   
      <!-- header menu -->
      <article>
      <div id="shopLayout">
         <div id="shopAd" onclick="location.href='./searchAction.sh?keyword=하와이 여행 투어'"></div>
         <div id="shopSearch">
        	 <span>
				<a href="./searchAction.sh?keyword=캐리어">캐리어</a> | 
				<a href="./searchAction.sh?keyword=튜브">튜브</a> | 
				<a href="./searchAction.sh?keyword=여권커버">여권커버</a> | 
				<a href="./searchAction.sh?keyword=스페인 남부 투어">스페인 남부 투어</a>
			</span>
            <div class="search">
               <form action="searchAction.sh" method="post">
                  <input type="text" class="shopInp" name="keyword">
                  <input type="submit" class="shopBtn">
               </form>
            </div>
         </div>
         <div id="shopList">
            <div class="A"><a href="./searchAction.sh?keyword=이탈리아 남부투어"><img src="img/shop/shop1.jpg"></a></div>
            <div class="B"><a href="./searchAction.sh?keyword=여행용 파우치"><img src="img/shop/shop2.jpg"></a></div>
            <div class="C"><a href="./searchAction.sh?keyword=경주 한옥 펜션"><img src="img/shop/shop3.jpg"></a></div>
            <div class="D"><a href="./searchAction.sh?keyword=하와이안 셔츠"><img src="img/shop/shop4.jpg"></a></div>
           <!--  <div id="shopBanner">
            Banners?
            </div> -->
         </div>
      </div>
      </article>
      	<div class="top"><a href="#">▲top</a></div>
      <!-- footer -->
      <jsp:include page="../inc/footer.jsp" />
      <!-- footer -->
      
   </div>
</body>
</html>
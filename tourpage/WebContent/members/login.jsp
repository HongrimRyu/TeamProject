<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- 구글 -->
		<meta name="google-signin-client_id" 
					content="1017895204039-i3mq47pubcaj0tql6t85jcs34rn2i4qo.apps.googleusercontent.com">
					
		<link rel="stylesheet" type="text/css" href="css/members/member.css">
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
		<link rel="shortcut icon" href="./img/members/favicon15.ico">
		
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<!-- 자체 로그인 -->
		<script src="./js/members/login_180720.js"></script>
		<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
		
		<!-- 카카오 -->
		<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
		<!-- 네이버 -->
		<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js"></script>
		<!-- 구글 -->
		  <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>

		<title>make your page, TOURPAGE</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$('section[data-type="background"]').each(function(){
					var $bgobj = $(this);
					$(window).scroll(function(){
						var yPos = -($window.scrollTop() / $bgobj.data('speed'));
						var coords = '20%'+yPos+'px';
						$bgobj.css({backgroundPosition:coords});
					});
				});
			});
		</script>
	</head>
	<body>
	<%
		if(session.getAttribute("id")!=null){
			response.sendRedirect("./tourpage.pl");
		}
	
	%>
	<div id="wrap">
	<img src="img/members/back_cl.svg" class="cloud floating">
		<section id="main" data-type="background" data-speed="10">
		<article>
			<div>
			<img src="img/members/font.svg" width="400px"> 
			<img src="img/members/main1.svg" width="300px">
			<a href="#main4">skip</a>
			</div>
		</article>
		</section>
	<section id="main2" data-type="background" data-speed="10">
		<article>
			<div>
			<img src="img/members/font2.svg" width="500px"><br><br>
			<img src="img/members/main_img.jpg" width="800px" class="main_img"> 
			</div>
		</article>
		</section>
	<section id="main3" data-type="background" data-speed="10">
		<article>
			<div>
			<img src="img/members/font3.svg" width="400px" class="fl">
			<img src="img/members/main_img2.jpg" class="main_img fr">
			<img src="img/members/main_img1.jpg" class="main_img fl">
			<br><br><br><br>
			<img src="img/members/font4.svg" width="480px" class="fr">
			</div>
		</article>
		</section>
	<section id="main4" data-type="background" data-speed="10">
		<article>
			<div>
			<img src="img/members/mainPageLogo.svg" width="600px"><br><br>
			<a href="#lgPopup">시작하기</a>
			</div>
		</article>
		</section>
	<section id="login" data-type="background" data-speed="10">	
		<article>
			<div class="page_cover" onclick="history.back();"></div>
			<div id="lgPopup">
			<div><img src="img/commons/logo_bk.svg" width="120px"></div>
				<div class="txt">login</div>
				<div class="lgForm">
					<form action="./LoginAction.me" method="post" name="lgFr" autocomplete="off"> <br> 
					<input type="text" name="id" class="lgInp" /><br> 
					<input type="password" name="passwd" class="lgInp"  autocomplete="off"/><br> 
					<input type="submit" value="로그인" class="lgSbm"/><br>
					</form>
					
					<input type="button" value="회원가입" class="lgBtn" onclick="location.href='./Join.me';" /> 
					<input type="button" value="비밀번호 찾기" class="lgBtn2" onclick="passfind();"/>
					<hr>
					
					<input type="button" value="Guest로 입장" class="lgGuest" onclick="location.href='./tourpage.pl';" /><br>
					<div class="snsBtn">
						<div id="naverIdLogin" value="N" class="snsN"></div>
						<br>
						<input type="button" value="facebook으로 로그인" class="snsF" onclick="fbLoginAction();" />
						<br>
						<div id="my-signin2" class="snsG"></div>
						<form action="./MemberJoinCheckAction.me" method="post" name="fr" class="form">
							<input type="hidden" name="name"> 
							<input type="hidden" name="email"> 
							<input type="hidden" name="nickname">
							<input type="hidden" name="platform">
						</form>
					</div><!-- snsBtn -->
				</div><!-- lgForm -->
			</div><!-- lgPopup -->
			<
		</article>
		</section><!-- login section -->
		<!-- 비밀번호찾기 팝업 -->		
			<div id="pfPopup">
				<div class="txt">
					비밀번호 찾기
				</div>
				<div class="pfForm">
						<label>이메일을 입력하세요</label><br>
						<input type="text" id="id" class="lgInp"/><br>
						<label id="idChkResult" class="smallLabel"></label><br>
						<input type="button" value="비밀번호 재설정" class="pfSbm" onclick="sendPass();"/><br>
						<p class="pftxt">※ '비밀번호 재설정' 버튼을 누를 시 해당 이메일로 새로운 비밀번호가 바로 전송됩니다.</p>
				</div>
			</div>
	
	<!-- 비밀번호찾기 팝업 -->
		
<%-- 	<!-- footer -->
	<%@include file="../inc/footer.jsp" %>
	<!-- footer --> --%>
	</div><!-- wrap -->
	<!-- 플랫폼 자바스크립트 -->
	<script src="./js/members/platform_js.js"></script>
	<!-- 플랫폼 자바스크립트 -->
	</body>
</html>
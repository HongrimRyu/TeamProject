<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.Session"%>
<%@page import="tourpage.member.action.GoogleAuthentication"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="tourpage.member.db.MemberDAO" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" type="text/css" href="css/members/member.css">
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">	
		<link rel="shortcut icon" href="img/members/favicon15.ico">
		
		<title>make your page, TOURPAGE</title>
		
		<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
		<script src="./js/members/join_180720.js"></script>
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		
	</head>
	<body>
	<img src="img/members/back_cl.svg" class="cloud floating">
		<article id="join">
			<div id="jiPopup">
				<div class="xbox"><img alt="x" src="./img/commons/xbox.svg" onclick="history.back()"></div>
				<div class="jiTxt">회원가입</div>
				<div class="jiForm">
					<form action="./JoinAction.me" method="post" name="jiFr" onsubmit="return chk();">
						<label>이메일</label>
						<!-- 이메일을 입력해주세요 -->
						<label id="emailChkResult" class="smallLabel"></label>
						<br>
						<input type="text" id="id" name="id" class="jiInp2" maxlength="30" required onkeyup="emailChk();"/>
						@ <input type="text" id="email" class="jiInp2" name="email" readonly="readonly" maxlength="30" required onkeyup="emailChk();" >
						<select name="emailSelect" id="emailSelect" class="jiInp2" onchange="selectEmail(this.value);emailChk();"> 
							<option value="">선택</option>
							<option value="naver.com">naver.com</option>
							<option value="hanmail.net">hanmail.net</option>
							<option value="gmail.com">gmail.com</option>
							<option value="nate.com">nate.com</option>
							<option value="1">직접입력</option>
						</select>
						
						<!------------- 이메일 중복체크 결과 레이블 --------------->
						<input type="button" id="emailauth" class="btn" onclick="emailAuth();" value="인증하기"/>
						<label id="authResult" class="smallLabel"></label>
						<br>
						
						<label>비밀번호</label><br>
						<input type="password" id="pass1" name="pass" class="jiInp" maxlength="20" required onkeyup="passChk();"/><br>
						<label>비밀번호확인</label><br>
						<input type="password" id="pass2" name="pass2" class="jiInp" maxlength="20" required onkeyup="passChk();"/>
						<!------------- 패스워드 체크 결과 레이블 --------------->
						<br>
						<label id="passChkResult" class="smallLabel"></label>
						<br>
						
						<label>닉네임</label><br>
						<input type="text" id="nick" name="nick" class="jiInp" maxlength="10" required onkeyup="nickChk();"/> 
						<label id="nickChkResult" class="smallLabel"></label>
						<br>
						<label>생년월일</label><br>
						<select class="jiInp2" name="birth1">
						<%
						//연도
							for(int i=1930;i<2018;i++){
						%>
							<option value=<%=i %>><%=i %></option>
						<%} %>
						</select>
						<select class="jiInp2" name="birth2">
						<%
							//월
							for(int i=1;i<=12;i++){
						%>
							<option value=<%=i %>><%=i %></option>
						<%}	%>
						</select>
						<select class="jiInp2" name="birth3">
						<%
							//일
							for(int i=1;i<=31;i++){
						%>
							<option value=<%=i %>><%=i %></option>
						<%}	%>
						</select>
						<br>
						<label>성별</label><br>
						<select class="jiInp" name="gender">
							<option value="남">남</option>
							<option value="여">여</option>
						</select><br>
						
						<label>연락처</label><br>
						<input type="text" id="phone1" name="phone1" class="jiInp2" maxlength="3" onkeyup="phoneChk();"/> -
						<input type="text" id="phone2" name="phone2" class="jiInp2" maxlength="4" onkeyup="phoneChk();"/> -
						<input type="text" id="phone3" name="phone3" class="jiInp2" maxlength="4" onkeyup="phoneChk();"/>
						<label id="phoneChkResult" class="smallLabel"></label><br>
						<span id="guide" style="color:#999"></span>
						
						<input type="hidden" name="platform" value="me"> <!-- 일반회원가입 platform설정-->
						<input type="hidden" name="profile" value="profile.png"> <!-- 일반회원가입 profile설정-->
						
						<input type="submit" value="join" class="jiSbm"/><br>
					</form>
				</div><!-- jiForm -->
			</div><!-- jiPopup -->
	</article>
<%-- 	<!-- footer -->
	<%@include file="../inc/footer.jsp" %>
	<!-- footer --> --%>
	</body>
</html>
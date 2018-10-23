<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.Session"%>
<%@page import="tourpage.member.action.GoogleAuthentication"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="tourpage.member.db.MemberDAO" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<!-- CSS -->
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="css/members/member.css">
		<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
		<link rel="shortcut icon" href="img/members/favicon15.ico">
		
		<!-- JQUERY -->
		<script src="./js/members/platform_chk_js.js"></script>
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		
		<title>make your page, TOURPAGE</title>
		
		
	</head>
	<body>
		<!-- 
			SNS통한 회원가입 창이니까 만약 이메일을 가져오지 못한다면 월래 페이지로 이동하게끔 한다 
			또한 넘어온 이메일,닉네임(이름) 값을 받는다
		-->
		<%
			request.setCharacterEncoding("utf-8");
		
			String email = (String)session.getAttribute("id");
			String nick = (String)session.getAttribute("nick");
			
			// SNS를 통한 회원가입을 하기전 MemberJoinCheckAction에서 가져온 값
			String platform = (String)session.getAttribute("platform");
			System.out.println("email:"+email);
			
			session.invalidate();
			
			if(email==null){ response.sendRedirect("./join.jsp");}
		%>
		<div id="wrap">
		<article>
				<div id="jiPopup">
					<div class="txt">회원가입</div>
					<div class="jiForm">
					<form action="./JoinAction.me" method="post" name="jiFr" onsubmit="return chk();">
						<!-- 
						지금 SNS를 통한 회원가입창이니까 submit이 되면 디비처리를 하려고 컨트롤러(./JoinAction.me)를 통해서 MemberJoinAction으로 가게 된다 
						passChk()함수를 통해 패스워드 조건(패스워드일치,8~20,영문+숫자)확인
						nickChk()함수를 통해 중복확인(MemberNickCheckAction)
						phoneChk()함수를 통해 중복확인(MemeberPhoneCheckAction)
						-->
						<input type="hidden" name="platform" value=<%=platform%>>
					
						<label>이메일</label><br>
						<input type="text" id="id" name="id" class="jiInp" maxlength="30" readonly value="<%=email.split("@")[0]%>" required/>
						@ <input type="text" id="email" class="jiInp" name="email" readonly value="<%=email.split("@")[1]%>" maxlength="30">
						<br>
						
						<label>비밀번호</label><br>
						<input type="password" id="pass1" name="pass" class="jiInp" maxlength="20" required onkeyup="passChk();"/><br>
						<label>비밀번호확인</label><br>
						<input type="password" id="pass2" name="pass2" class="jiInp" maxlength="20" required onkeyup="passChk();"/>
						<!------------- 패스워드 체크 결과 레이블 --------------->
						<label id="passChkResult" class="#"></label>
						<br>
						<%System.out.println(nick); %>
						<label>닉네임</label><br>
						<input type="text" id="nick" name="nick" value="<%=nick%>" class="jiInp" maxlength="10" required onkeyup="nickChk();"/>
						<label id="nickChkResult"></label>
						
						
						
						
						<!-- <input type="button" value="중복체크" class="btn" onclick="winopen()"> -->
						<br>
						<label>생년월일</label><br>
						<select class="jiInp" name="birth1">
							<option value="">선택 안함</option>
						<%
						//연도
							for(int i=1930;i<2018;i++){
						%>
							<option value=<%=i %>><%=i %></option>
						<%} %>
						</select>
						<select class="jiInp" name="birth2">
							<option value="">선택 안함</option>
						<%
							//월
							for(int i=1;i<=12;i++){
						%>
							<option value=<%=i %>><%=i %></option>
						<%}	%>
						</select>
						<select class="jiInp" name="birth3">
							<option value="">선택 안함</option>
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
							<option value="">선택 안함</option>
							<option value="남">남</option>
							<option value="여">여</option>
						</select><br>
						
						<label>연락처</label><br>
						<input type="text" id="phone1" name="phone1" class="jiInp2" maxlength="3" onkeyup="phoneChk();"/> -
						<input type="text" id="phone2" name="phone2" class="jiInp2" maxlength="4" onkeyup="phoneChk();"/> -
						<input type="text" id="phone3" name="phone3" class="jiInp2" maxlength="4" onkeyup="phoneChk();"/><br>
						<label id="phoneChkResult"></label><br>
						<span id="guide" style="color:#999"></span>
						<input type="submit" value="join" class="jiSbm"/><br>
					</form>
					</div>
			</div>
	</article>
	<!-- footer -->
	<%@include file="../inc/footer.jsp" %>
	<!-- footer -->
	</div><!-- wrap -->
	</body>
</html>
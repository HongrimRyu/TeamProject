<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script src="http://54.180.2.191:8080/tourpage//js/commons/jquery-3.3.1.js"></script>
		<link rel="stylesheet" type="text/css" href="http://54.180.2.191:8080/tourpage/css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="http://54.180.2.191:8080/tourpage/css/groupware/groupware.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic"
			rel="stylesheet">
		<link rel="shortcut icon" href="http://54.180.2.191:8080/tourpage/img/commons/favicon15.ico">
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="http://54.180.2.191:8080/tourpage//js/group/create.js"></script>
	</head>
	<body>
		<div id="wrap">
		<!-- header menu -->
		<%@include file="../inc/header.jsp"%>
		<!-- header menu -->
		<!-- content -->
		<article> 
		<div id="gWare">
		
			<div id="gwJoin">
				<p>
					<img alt="tourpage groupware" src="http://54.180.2.191:8080/tourpage/img/logo_gw_.svg" width="200px">
					<br>Join
				</p>
				<form action="./CreateGroupAction.gw" method="post" name="gwFr">
					<label>아이디</label><br>
					<!-- 이메일을 입력해주세요 -->
					<input type="text" id="id" class="gwInp" maxlength="15"/><br>
					<label id="idChkResult" class="smallLabel"></label><br>
					<label>비밀번호</label><br>
					<input type="password" id="pass1" name="pass1" class="gwInp" maxlength="20"/><br>
					<label>비밀번호확인</label><br>
					<input type="password" id="pass2" name="pass2" class="gwInp" maxlength="20"/><br>
					<!------------- 패스워드 체크 결과 레이블 --------------->
					<label id="passChkResult" class="smallLabel"></label>
					<br>
					<input type="button" value="create" class="gwSbm" onclick="idChk();"/><br>
				</form>
			
			</div>
		</div>
		
		</article>
		<!-- footer -->
		<jsp:include page="../inc/footer.jsp" />
		<!-- footer -->
	</div>
	</body>
</html>
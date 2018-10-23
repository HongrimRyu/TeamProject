<%@page import="tourpage.group.db.GroupDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>make your page, TOURPAGE</title>
		<link rel="stylesheet" type="text/css" href="http://54.180.2.191:8080/tourpage/css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="http://54.180.2.191:8080/tourpage/css/groupware/groupware.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic" rel="stylesheet">
		<link rel="shortcut icon" href="http://54.180.2.191:8080/tourpage/img/commons/favicon15.ico">
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="http://54.180.2.191:8080/tourpage/js/commons/jquery-3.3.1.js"></script>
		<%
		request.setCharacterEncoding("utf-8");
		if(request.getAttribute("id")!=null){
			GroupDAO gd = new GroupDAO();
			boolean chk = gd.idCheck((String)request.getAttribute("id"));
			if(!chk){
				%>
				<script type="text/javascript">
					alert("해당 링크가 존재하지 않습니다.");
					location.href = "/tourpage/GroupLogin.gw";
				</script>
				<%
			}
		}
		%>

	</head>
	<body>
		<div id="wrap">
		<!-- header menu -->
		<%@include file="../inc/header.jsp"%>
		<!-- header menu -->
		<!-- content -->
		<article> 
		<div id="gWare">
			<div id="gwLogin">
				<p>
					<img alt="tourpage groupware" src="http://54.180.2.191:8080/tourpage/img/logo_gw_.svg" width="200px">
					<br>Login
				</p>
				
				<%
				if(request.getAttribute("id")==null){
				%>
				<form action="./GroupLoginAction.gw" method="post">
					<label>아이디</label><br>
					<input type="text" name="id" id="id" class="gwInp"><br>
					<label>패스워드</label><br>
					<input type="password" name="pass" id="pass" class="gwInp"><br>
					<input type="submit" value="로그인" class="gwSbm">
				</form>
				<%}else{ %>
				<form action="/tourpage/GroupLoginAction.gw" method="post">
					<input type="hidden" name="id" value="<%=request.getAttribute("id")%>">
					<label>패스워드</label><br>
					<input type="password" name="pass" id="pass" class="gwInp"><br>
					<input type="submit" value="로그인" class="gwSbm">
				</form>
				<%} %>
	
			</div>

		</div>
		</article>
		<!-- footer -->
		<jsp:include page="../inc/footer.jsp" />
		<!-- footer -->
	</div>

	</body>
</html>
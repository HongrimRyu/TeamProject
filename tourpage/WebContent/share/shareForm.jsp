<%@page import="com.google.gson.JsonParser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link rel="stylesheet" type="text/css" href="css/commons/defaultCSS.css">
		<link rel="stylesheet" type="text/css" href="css/share/share.css">
		<link href="https://fonts.googleapis.com/css?family=Jua|Nanum+Gothic" rel="stylesheet">
		<link rel="shortcut icon" href="./img/commons/favicon15.ico">
		<script src="//code.jquery.com/jquery.min.js"></script>
		
		<title>share</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="./js/commons/jquery-3.3.1.js"></script>
		<script src="./js/share/share.js"></script>
	</head>
	<body>
	<%
	 request.setCharacterEncoding("utf-8");
	%>
	<div id="wrap">
		<!-- header menu -->
		<jsp:include page="../inc/header.jsp" />
		<!-- header menu -->
		<article>
		<form action="./SharePlanAddAction.sp" method="post" name="fr">
			<div id="shareLayout">
				<div id="shareImg">share board img</div>
				
				<div id="shareBoardLayout">
						<div id="shareContent">
							<div class="scTit">
							<span id="subject"></span><br>
							<span id="allplace"></span><br>
							<span id="date"></span><br>
	 						<br>
	 						</div>
 	
							<div id="target">
								<div class="scCon">
								</div>
								<div class="scConSt">
								<!-- 일 -->
								</div>
								<div class="scCont">
								<br><input type="text" name="content" maxlength="30" size="100" placeholder="30자 내 코멘트 입력"/>
								</div>
							</div>
					
							<div class="scBtns">
						 	<input type="submit" value="일정공유하기" class="reBtn">
							</div>
						
						</div><!-- shareContent -->	
						<input type="hidden" name ="plan_idx" id="plan_idx" value="<%=request.getParameter("plan_idx")%>">
				</div>
			</div>
		</form>
		</article>
		<!-- footer -->
		<jsp:include page="../inc/footer.jsp" />
		<!-- footer -->
	</div>
</body>
</html>
<%@page import="java.util.ArrayList"%>
<%@page import="tourpage.share.db.SharePlanBean"%>
<%@page import="java.util.List"%>
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
		
		<title>share</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="./js/commons/jquery-3.3.1.js"></script>
	
		<style type="text/css">
			.pdetail{
				background-image:url(./img/share/backline.png);
 				background-repeat-x:repeat;
				background-position:center;
			}
		</style>
	<script type="text/javascript">
	function nullChk(){
		var check;
		$('.target').each(function(){
			check = $(this).find('input').val();
		});
		if(check==null || $.trim(check)==""){
			alert('코멘트를 입력해주세요');
		}else{
			document.fr.submit();	
		}
		
	}
	</script>
	</head>
	<body>
	<%
	 request.setCharacterEncoding("utf-8");
	 String id = (String)session.getAttribute("id");
	 List<SharePlanBean> planList = (ArrayList)request.getAttribute("planList");
	 SharePlanBean spb = null;
	 String allplace = "";
	 String[] place;
	 for(int i=0; i<planList.size(); i++){
 		 spb = planList.get(i);
		 allplace += spb.getPlan_detail()+", ";
	 }
	%>
	<div id="wrap">
		<!-- header menu -->
		<jsp:include page="../inc/header.jsp" />
		<!-- header menu -->
		<article>
		<form action="./SharePlanUpdateAction.sp" method="post" name="fr">
			<div id="shareLayout">
				<div id="shareImg">share board img</div>
				
				<div id="shareBoardLayout">
						<div id="shareContent">
							<div class="scTit">
							<span id="subject"><%=spb.getPlan_name()%></span><br>
							<span id="allplace"><h3>여행 장소 :</h3> <%=allplace.substring(0, allplace.length()-2)%></span><br>
							<span id="date"><h3>여행 기간 :</h3> <%=spb.getTravel_period()%></span><br>
	 						<br>
	 						</div>
	 						<div class="scCon">
								<img alt="pic" src="img/share/back.jpg">
							</div>
							<%
								for(int i=0; i<planList.size(); i++){
							        place = planList.get(i).getPlan_detail().split(", ");
								    spb = planList.get(i);
							%>
							<div class="target">
								<div class="scConSt"><%=spb.getPlan_days()%>일차</div>
								<div class="scCont">
									<span class="pdetail">
												<%
													for(int b=0; b<place.length; b++){
												%>
							                   		 <span class="pd"><%=place[b]%></span>
							                    <%
							                    	}
							                    %>
						                 	</span>	
									<div class="scConCo">
									<input type="text" idx="<%=spb.getPlan_days()%>" class="content" name="content<%=spb.getPlan_days()%>" maxlength="30" size="100" value="<%=spb.getComment()%>"/>
									</div>
								</div>
							</div>
							<%
								}
							%>
							<div class="scBtns">
						 	<input type="button" value="일정수정하기" class="reBtn" onclick="nullChk()">
							</div>
						
						</div><!-- shareContent -->	
						<input type="hidden" name ="plan_idx" id="plan_idx" value="<%=request.getParameter("plan_idx")%>">
				</div>
			</div>
		</form>
		</article>
		<!-- footer -->
<%-- 		<jsp:include page="../inc/footer.jsp" /> --%>
		<!-- footer -->
	</div>
</body>
</html>
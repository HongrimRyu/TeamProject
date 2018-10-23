<%@page import="tourpage.share.db.ShareCommentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="tourpage.share.db.SharePlanBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<script src="./js/share/share_content.js"></script>
	
		<style type="text/css">
			.pdetail{
				background-image:url(./img/share/backline.png);
				background-repeat:repeat-x;
				background-position:center;
			}
		</style>

	</head>
	<body>
		<%
			request.setCharacterEncoding("utf-8");
			Map map =  (HashMap)request.getAttribute("map");
			List<SharePlanBean> planList = (ArrayList)map.get("planList");
			List<ShareCommentBean> commentList = (ArrayList)map.get("commentList");
			String writer= planList.get(1).getId();
			SharePlanBean spb = null;
			String allplace = "";
			String[] place;
			for(int i=0; i<planList.size(); i++){
				spb = planList.get(i);
				allplace += spb.getPlan_detail()+", ";
			}
		%>
		<input type="hidden" id="plan_idx" value="<%=request.getParameter("idx")%>">
		<div id="wrap">
			<!-- header menu -->
			<%@include file="../inc/header.jsp" %>
			<!-- header menu -->
			<article>
			<div id="shareCLayout">
					<div id="shareBoardLayout">
						<div id="shareContent">
							<div class="scTit">
								<span id="subject"><%=spb.getPlan_name()%></span><br>
								<span id="allplace"><h3><img src="./img/commons/dot.png"> 여행 장소 </h3> <%=allplace.substring(0, allplace.length()-2)%></span><br>
								<span id="date"><h3><img src="./img/commons/dot.png"> 여행 기간 </h3> <%=spb.getTravel_period()%></span><br>
		 						<span class="titSub">조회수:<%=spb.getReadcount()%></span>
	 						</div>
							<div class="scCon">
								<img alt="pic" src="img/share/back.jpg">
							</div>
							<%
								for(int i=0; i<planList.size(); i++){
							        place = planList.get(i).getPlan_detail().split(", ");
								    spb = planList.get(i);
							%>
								    <div id="target"> <!-- 기존에서 이 div태그 추가 -->
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
											<div class="scConCo">한줄 코멘트 : <%=spb.getComment()%></div>
										</div>
									</div>
							<%
								}
							%>
							<!-- 작성자에게만 보이게 -->
							<%
								if(ssid.equals(writer)){
							%>
							<div class="scBtns">
								<input type="button" value="일정수정하기" class="reBtn scUpd" plan_idx='<%=spb.getPlan_idx()%>'>
								<input type="button" value="일정삭제하기" class="reBtn scDel" plan_idx='<%=spb.getPlan_idx()%>'>
							</div>
							<%
								}
							%>
							<br>
							<button id="best_btn"><img src="./img/makePlan/like.png"><span id="best_value"> 공감 <%=spb.getBest()%></span></button>
						</div><!-- shareContent -->
						
						<!-- 댓글 -->
						<div class="scReply">
							댓글<br>
							<textarea rows="5" cols="60" id="content"></textarea>
							<input type="hidden" id="board_idx" value="<%=Integer.parseInt(request.getParameter("idx"))%>"/>
							<input type="button" value="reply" class="scReBtn" id="reply">
						</div>	
						<div class="scReList paginated" id="scReList">
						<%for(int j=0; j<commentList.size(); j++){%>	
							<div class='scRe' idx="<%=commentList.get(j).getIdx()%>">
								<%if(commentList.get(j).getRe_lev() >= 1){
								%> 
								<span class="reNum">
								<img src="./img/share/level.gif" width="50" height="10">
								<img src="./img/share/re.gif">
								</span>
								<span class="reName"><img src="img/myPage/<%=commentList.get(j).getProfile()%>" width="30px">&nbsp;&nbsp;<%=commentList.get(j).getNick()%></span>
								<span class="reCon">
									<%
										System.out.println(commentList.get(j).getRe_lev());
										if(commentList.get(j).getRe_lev()==2){
									%>
										[<%=commentList.get(j).getRef_nick() %>]
									<%
										}
									%>
										<%=commentList.get(j).getContent().replace("\n", "<br>")%>
								</span>
								<span class="reBtns"><input type="button" value="대댓" class="reBtn" id="rep" idx="<%=commentList.get(j).getIdx()%>">
													 <%if(ssid.equals(commentList.get(j).getId()) || commentList.get(j).getId() == "skyrhl@naver.com"){
														%>
														<input type="button" value="수정" class="reBtn" id="upd" idx="<%=commentList.get(j).getIdx()%>">
													 	<input type="button" value="삭제" class="reBtn" id="del" idx="<%=commentList.get(j).getIdx()%>">
													 <%}%>
							    </span>
							    
							    <%}else{%>
							   
							    <span class="reNum"></span>
								<span class="reName"><img src="img/myPage/<%=commentList.get(j).getProfile()%>" width="30px">&nbsp;&nbsp;<%=commentList.get(j).getNick()%></span>
								<span class="reCon">
									<%
										System.out.println(commentList.get(j).getRe_lev());
										if(commentList.get(j).getRe_lev()==2){
									%>
										[<%=commentList.get(j).getRef_nick() %>] 
									<%
										}
									%>
										<%=commentList.get(j).getContent().replace("\n", "<br>")%>
								</span>
								<span class="reBtns"><input type="button" value="대댓" class="reBtn" id="rep" idx="<%=commentList.get(j).getIdx()%>">
													 <%if(ssid.equals(commentList.get(j).getId()) || commentList.get(j).getId() == "skyrhl@naver.com"){
														%>
														<input type="button" value="수정" class="reBtn" id="upd" idx="<%=commentList.get(j).getIdx()%>">
													 	<input type="button" value="삭제" class="reBtn" id="del" idx="<%=commentList.get(j).getIdx()%>">
													 <%}%>
				 
							    </span>
							    
							    <%}%>
							</div>
							
							<!-- 대댓글 등록 창 -->
							<div class="RR scReply reRe" idx="<%=commentList.get(j).getIdx()%>">
								대댓글<br>
								<div id="cls_repbtn" idx="<%=commentList.get(j).getIdx()%>" style="cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;">X</div>
								<textarea rows="5" cols="60" class="reContent" idx="<%=commentList.get(j).getIdx()%>"></textarea>
								<input type="button" value="reply" class="scReBtn" id="reReply" idx="<%=commentList.get(j).getIdx()%>" 
								                     ref="<%=commentList.get(j).getRe_ref()%>" lev="<%=commentList.get(j).getRe_lev()%>">
								<div style="clear: both;"></div>
							</div>
							<!-- 댓글 수정 -->
							<div class="MM scReply upRe" idx="<%=commentList.get(j).getIdx()%>">
								댓글 수정<br>
								<div id="cls_updbtn" idx="<%=commentList.get(j).getIdx()%>" style="cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;">X</div>
								<textarea rows="5" cols="60" class="upContent" idx="<%=commentList.get(j).getIdx()%>"></textarea>
								<input type="hidden" id="board_idx" value="<%=Integer.parseInt(request.getParameter("idx"))%>"/>
								<input type="button" value="reply" class="scReBtn" id="upReply" idx="<%=commentList.get(j).getIdx()%>">
								<div style="clear: both;"></div>
							</div>
							<%}%>
						</div><!-- scReList -->
					</div><!-- shareBoardLayout -->
					</div>
			</article>
			
			<!-- footer -->
			<jsp:include page="../inc/footer.jsp" />
			<!-- footer -->
		</div>
	</body>
	<script type="text/javascript">
	page();
	</script>
</html>
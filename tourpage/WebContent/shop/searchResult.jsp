
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
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
	<link rel="shortcut icon" href="img/favicon15.ico">
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<title>make your page, TOURPAGE</title>
	<%
	request.setCharacterEncoding("utf-8");
	
	//searchAction에서 가져온 검색결과 저장 
	JSONArray items = (JSONArray)request.getAttribute("items");
	
	//페이징 관련 변수 선언
	int count = Integer.parseInt(request.getAttribute("count").toString());
	int pageNum = 0;
	String keword = request.getAttribute("keword").toString();
	if(request.getAttribute("pageNum")!=null){
		pageNum = Integer.parseInt(request.getAttribute("pageNum").toString());	
	}
	
	int pageCount = count / 10 + (count % 10 == 0 ? 0 : 1);

	int startPage = ((pageNum - 1) / 10) * 10 + 1;
	int endPage = startPage + 9;
	if (endPage > pageCount) {
		endPage = pageCount;
	}
 	
	//정렬 방식 선언
	String[] sortType = {"sim","date","asc","dsc"};
	String[] sortName = {"정확도순","최신순","낮은 가격순","높은 가격순"};
 	String sort = (String)request.getAttribute("sort");
	%>
	<script type="text/javascript">
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
					<form action="./searchAction.sh" method="get" onsubmit="return chk();">
						<input type="text" class="shopInp" name="keyword" placeholder="검색어를 입력해주세요.">
						<input type="submit" class="shopBtn" value="검색">
					</form>
				</div>
			</div>
			
			<div id="searchList">
					<!-- 분류카테고리 -->
					<nav id="searchCategory">
						<ul>
						<%
						for(int i=0;i<sortType.length;i++){
						if(sortType[i].equals(sort)){
						%>
						<li><b><%=sortName[i] %></b></li>
						<%}else{%>
						<li><a href="./searchAction.sh?keyword=<%=keword%>&sort=<%=sortType[i]%>"><%=sortName[i] %></a></li>
						<%}} %>
						</ul>
					</nav><!-- searchCategory -->
					<!-- 상품리스트 -->
				<div id="itemListLayout">	
					
						<%if(count!=0){
						String countS = Integer.toString(count);
						StringBuilder csb = new StringBuilder(countS); 
						if(((countS.length()-1)/3)>=1){
							int cycle = ((countS.length()-1)/3);
							for(int j=1;j<=cycle;j++){
								countS = csb.insert(countS.length()-(j-1)-(3*j), ",").toString();
							}
						}%>
						<span>
						<img src="./img/commons/menuicon.png" width="5px"> 검색 결과 수 : <%=countS %>건
						</span>
					<ul id="itemList">
					<%
					for(int i=0;i<items.size();i++){
						JSONObject item = (JSONObject)items.get(i);
						%>
						<li>
							<div id="itemimg">
								<a href="<%=item.get("link")%>" target="_blank"><img src="<%=item.get("image")%>" style="width: 200px;height: 200px;"></a>
							</div>
							<div id="itemtitle">
								<span class="stitle"><a href="<%=item.get("link")%>" target="_blank"><%=item.get("title") %></a></span>
								
								<span class="sprice">
									<%
									if(item.get("productType").equals("1")){
										out.print("최저 ");
									}
									String price = item.get("lprice").toString();
									StringBuilder sb = new StringBuilder(price);
									if(((price.length()-1)/3)>=1){
										int cycle = ((price.length()-1)/3);
										for(int j=1;j<=cycle;j++){
											price = sb.insert(price.length()-(j-1)-(3*j), ",").toString();
										}
									}
									%>
									<%=price %>원
								</span>
							</div>
							<div id="itemprice">
								<%
								if(item.get("productType").equals("1")){
									out.print(item.get("mallName")+" 쇼핑");
								}else{
									out.print(item.get("mallName"));
								} %>
							</div>
						</li>
						<%
					}
				}else{
				%>
					결과없음<br>
				<%
				}
				%>
					
				</ul>
				</div>
				<div class="pageNum">
				<%
					// 페이지 처리출력
					if(count != 0){
						// 이전
						if(startPage>10){
							%>
							<a href="./searchAction.sh?pageNum=<%=startPage-10%>&keyword=<%=keword%>&sort=<%=sort%>">[이전]</a>
							<%
						}
						// 1~10   11~20   21~30 .... 
						for(int i=startPage;i<=endPage;i++){
							if(i==pageNum){
							%>
								<a href="./searchAction.sh?pageNum=<%=i%>&keyword=<%=keword%>&sort=<%=sort%>" style="font-weight: bold;">[<%=i %>]</a>
							<%
							}else{
							%>
								<a href="./searchAction.sh?pageNum=<%=i%>&keyword=<%=keword%>&sort=<%=sort%>">[<%=i %>]</a>
							<%		
							}
						}
						// 다음
						if(endPage < pageCount){
							%>
							<a href="./searchAction.sh?pageNum=<%=startPage+10%>&keyword=<%=keword%>&sort=<%=sort%>">[다음]</a>		
							<%
						}
					}					
					%>
				</div><!-- pageNum -->
				
			</div>
		</article>
		<div class="top"><a href="#">▲top</a></div>
	<div class="fixedfooter"><jsp:include page="../inc/footer.jsp"/></div> 
	</div>
	 
</body>
</html>
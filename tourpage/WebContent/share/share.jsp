<%@page import="tourpage.share.db.SharePlanDAO"%>
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


<script type="text/javascript">
function sendData(plan_idx){
	   document.hifr.idx.value = plan_idx;
	   document.hifr.submit();
	}

	$(document).ready(function(){
		
		$.ajax({
			url: './SharePlanBoard.sp'
			, type: 'post'
			, dataType: 'json'
			, success: function(data){
				var board="";
				$.each(data, function(index, item){
				 // 사진
				 //	제목 (조회수)
				 //	작성자  추천수
				 if(item.plan_days==1){
					 console.log("???");
					 board += "<div class='share' data-idx='"+item.plan_idx+"'>"+
		             "<img src='./img/makePlan/info/"+item.image+"'>"+
		             "<div><span class='sTit'>"+item.plan_name+"</span><span class='sRC'>"+item.readcount+"</span>"+
		             "<span class='sName'>"+item.nick+"</span><span class='sCon'>"+item.best+"</span></div></div>";		
					 $('#sb').html(board);
				 }
				});
				page();
				 
				 $('.share[data-idx]').on('click',function(){
			            var plan_idx = $(this).attr('data-idx');
			            console.log("data-idx : "+plan_idx);
			            sendData(plan_idx);
			         });
		},
		error : function(log){alert("실패");},
	});
		
		$('#search').on('click', function(){
			$("#pagingnum").html(""); //search할떄 pagenum이 append형식으로 되어있기 떄문에 초기화해준다.
			$.ajax({
				url: './SharePlanBoard.sp'
					, type: 'post'
					, dataType: 'json'
					, data : {"standard" : document.getElementsByName('searchoption')[0].value,
						  	"search" : document.getElementsByName('shareBSearch')[0].value}
					, success: function(data){
						var board = "";
						$.each(data, function(index, item){
							if(item.plan_days==1){
								board += "<div class='share' data-idx='"+item.plan_idx+"'>"+
					             "<img src='./img/makePlan/info/"+item.image+"'>"+
					             "<div><h3>"+item.plan_name+"</h3><p>"+item.readcount+"</p>"+
					             "<p>"+item.nick+"&nbsp;"+item.best+"</p></div></div>";		
								 $('#sb').html(board);
							 }		
						});
						$('#tbl').html(board);
						page();
						
						$('.share[data-idx]').on('click',function(){
				            var plan_idx = $(this).attr('data-idx');
				            console.log("data-idx : "+plan_idx);
				            sendData(plan_idx);
				         });
				},
				error : function(log){alert("실패");},
			});
		});
		
	
		
});

	
	
	function page(){ 
		var reSortColors = function($div) { //even 짝수 odd 홀수
		  $('#sb div:odd div', $div).removeClass('even').addClass('odd');
		  $('#sb div:even div', $div).removeClass('odd').addClass('even');
		 };
		 $('div.paginated').each(function() {
			 
		  
			 
		  // alert();
		  var pagesu = 12;  //페이지 번호 갯수
		  var currentPage = 0;
		  var numPerPage = 12;  //목록의 수
		  var $div = $(this); // div.paginated
		  
		  //length로 원래 리스트의 전체길이구함
		  var numRows = $div.find(".share").length;
		  //Math.ceil를 이용하여 반올림
		  var numPages = Math.ceil(numRows / numPerPage);
		  //리스트가 없으면 종료
		  if (numPages==0) return;
		  //pager라는 클래스의 div엘리먼트 작성
		  var $pager = $('<div align="center" id="remo"><div class="pager"></div></div>');
		  
		  var nowp = currentPage;
		  var endp = nowp+10;
		  
		  //페이지를 클릭하면 다시 셋팅
		  $div.bind('repaginate', function() {
		  //기본적으로 모두 감춘다, 현재페이지+1 곱하기 현재페이지까지 보여준다
		  
		   $div.find('.share').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
		   $("#remo").html("");//위에서 설정한 값

		   if (numPages > 1) {     // 한페이지 이상이면
		    if (currentPage < 5 && numPages-currentPage >= 5) {   // 현재 5p 이하이면
		     nowp = 0;     // 1부터 
		     endp = pagesu;    // 10까지
		    }else{
		     nowp = currentPage -5;  // 6넘어가면 2부터 찍고
		     endp = nowp+pagesu;   // 10까지
		     pi = 1;
		    }
		    
		    if (numPages < endp) {   // 10페이지가 안되면
		     endp = numPages;   // 마지막페이지를 갯수 만큼
		     nowp = numPages-pagesu;  // 시작페이지를   갯수 -10
		    }
		    if (nowp < 1) {     // 시작이 음수 or 0 이면
		     nowp = 0;     // 1페이지부터 시작
		    }
		   }else{       // 한페이지 이하이면
		    nowp = 0;      // 한번만 페이징 생성
		    endp = numPages;
		   }
		   
		   // [처음]
		   $('<br /><span class="page-number" cursor: "pointer">[처음]</span>').bind('click', {newPage: page},function(event) {
		          currentPage = 0;   
		          $div.trigger('repaginate');  
		          $($(".page-number")[2]).addClass('active').siblings().removeClass('active');
		      }).appendTo($pager).addClass('clickable');
		   
		    // [이전]
		      $('<span class="page-number" cursor: "pointer">&nbsp;&nbsp;&nbsp;[이전]&nbsp;</span>').bind('click', {newPage: page},function(event) {
		          if(currentPage == 0) return; 
		          currentPage = currentPage-1;
		          $div.trigger('repaginate'); 
		    $($(".page-number")[(currentPage-nowp)+2]).addClass('active').siblings().removeClass('active');
		   }).appendTo($pager).addClass('clickable');
		      
		    // [1,2,3,4,5,6,7,8]
		   for (var page = nowp ; page < endp; page++) {
		    $('<span class="page-number" cursor: "pointer" style="margin-left: 8px;"></span>').text(page + 1).bind('click', {newPage: page}, function(event) {
		     currentPage = event.data['newPage'];
		     $div.trigger('repaginate');
		     $($(".page-number")[(currentPage-nowp)+2]).addClass('active').siblings().removeClass('active');
		     }).appendTo($pager).addClass('clickable');
		   } 
		    // [다음]
		      $('<span class="page-number" cursor: "pointer">&nbsp;&nbsp;&nbsp;[다음]&nbsp;</span>').bind('click', {newPage: page},function(event) {
		    if(currentPage == numPages-1) return;
		        currentPage = currentPage+1;
		        $div.trigger('repaginate'); 
		     $($(".page-number")[(currentPage-nowp)+2]).addClass('active').siblings().removeClass('active');
		   }).appendTo($pager).addClass('clickable');
		    // [끝]
		   $('<span class="page-number" cursor: "pointer">&nbsp;[끝]</span>').bind('click', {newPage: page},function(event) {
		           currentPage = numPages-1;
		           $div.trigger('repaginate');
		           $($(".page-number")[endp-nowp+1]).addClass('active').siblings().removeClass('active');
		   }).appendTo($pager).addClass('clickable');
		     
		     $($(".page-number")[2]).addClass('active');
		reSortColors($div);
		  });
		   $pager.insertAfter('#pagingnum').find('span.page-number:first').next().next().addClass('active');   
		   $pager.appendTo('#pagingnum');
		   $div.trigger('repaginate');
		 });
		}	
	
	
	
	
	
	
</script>
</head>
<body>
<%
request.setCharacterEncoding("utf-8");
%>

<form name="hifr" method="post" action="./SharePlanContent.sp">
	<input type="hidden" name="idx" id="idx">
</form>

	<div id="wrap">
		<!-- header menu -->
		<jsp:include page="../inc/header.jsp" />
		<!-- header menu -->
		<article>
			<div id="shareLayout">
				<div id="shareImg"></div>
				<div id="shareBoardSearch">
						<select name="searchoption" class="shareSel">
							<option value="1">제목</option>
							<option value="2">내용</option>
							<option value="3">제목+내용</option>
							<option value="4">닉네임</option>
						</select>
						<input type="text" name="shareBSearch" class="shareInp">
						<input type="button" value="search" class="shareSbm" id="search">
				</div><!-- shareBoardSearch -->
				
				<div id="shareBoardLayout">
					<div id="shareBoard">
					<div id="sb" class="paginated">	
					</div>
					<div id="pagingnum" class="paginated"></div>
					</div><!-- shareBoard -->
					<!-- <input type="hidden" name="idx" id="idx"> -->
				</div><!-- shareBoardLayout -->
				
		</article>
		<!-- footer -->
		<div class="fixedfooter"><jsp:include page="../inc/footer.jsp" /></div>
		<!-- footer -->
	</div>
</body>
</html>
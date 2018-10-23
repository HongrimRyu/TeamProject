  /**
 * 
 */
function getFormatDate(date){
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;     
	var day = date.getDate();                   
	day = day >= 10 ? day : '0' + day;      
	return  year + '년 ' + month + '월 ' + day +'일';
}
	$(document).ready(function(){
		var repopen = false; //대댓입력창 확인을 위한 변수
		var updopen = false; //댓글수정창 확인을 위한 변수
		var isRun = false; //버튼중복을 막기위한 변수
		//댓글
		$(document).on('click', '#reply', function(){
			if(isRun == true){
				return;
			}
			isRun = true;
			$.ajax({
				url: './ShareCommentAddAction.sp',
				type: 'post',
				data:{"board_idx" : document.getElementById('board_idx').value,
					  "content" : document.getElementById('content').value},
				success:function(data){	
						var reply = data;
						$('.scReList').html(reply);
						$('#content').val("");
						page();
						isRun = false;
				},error:function(request,status,error){alert("일시적인 오류입니다. 잠시후 다시 시도해주세요");}
				});	
		});
		//대댓
		$(document).on('click', '#reReply[idx]',function(){
			if(isRun == true){
				return;
			}
			isRun = true;
			$.ajax({
				url: './ShareCommentReAddAction.sp',
				type: 'post',
				data:{"reContent" : $(this).attr('idx'),
	                  "board_idx" : $('#plan_idx').val(),
	                  "re_ref"    : $(this).attr('ref'),
	                  "content"   : $('.reContent[idx='+$(this).attr('idx')+']').val(),
	                  "lev"       : $(this).attr('lev'),
	           },
				success:function(data){
					var reReply = data;
						$('.scReList').html(reReply);
						$('.RR[idx='+$(this).attr('idx')+']').addClass('reRe');
						alert("대댓등록 성공");
						page();
						isRun = false;
				},error:function(request,status,error){}
			});
		});
		//댓글 삭제
		$(document).on('click','#del[idx]' ,function(){
			if(isRun == true){
				return;
			}
			isRun = true;
			$.ajax({
				url: './ShareCommentDeleteAction.sp',
				type: 'post',
				data:{'idx':$(this).attr("idx"),
					  'board_idx':document.getElementById("board_idx").value
				      },
				success:function(data){
					var reply = data;
					$('.scReList').html(reply);
					alert("댓글삭제 성공");	
					page();
					isRun = false;
				},
				error:function(request,status,error){alert("일시적인 오류입니다. 잠시후 다시 시도해주세요");}
			});
			
		});
		//댓글 수정
		$(document).on('click','#upReply[idx]' ,function(){
			if(isRun == true){
				return;
			}
			isRun = true;
			console.log("콘솔 idx : "+$(this).attr('idx'));
			console.log("콘솔 board_idx : "+document.getElementById('board_idx').value);
			console.log("콘솔 upContent : "+$('.upContent[idx='+$(this).attr('idx')+']').val());
			$.ajax({
				url:'./ShareCommentUpdateAction.sp',
				type:'post',
				data:{'idx':$(this).attr('idx'),
					  'board_idx': document.getElementById('board_idx').value,
					  'upContent': $('.upContent[idx='+$(this).attr('idx')+']').val()},
			    success:function(data){
			    	var reply = data;
					$('.scReList').html(reply);
					$('.MM[idx='+$(this).attr('idx')+']').addClass('upRe');
					alert("댓글수정 성공");
					page();
					isRun = false;
			    },error:function(request,status,error){alert("일시적인 오류입니다. 잠시후 다시 시도해주세요");}
			});
			
		});
	$(document).on('click','#rep[idx]' ,function(){
		$('.MM[idx='+$(this).attr('idx')+']').addClass('upRe');
		$('.RR[idx='+$(this).attr('idx')+']').removeClass('reRe');
	});
	//대댓 등록창 닫기
	$(document).on('click','#cls_repbtn[idx]' ,function(){
		$('.RR[idx='+$(this).attr('idx')+']').addClass('reRe');
	});
	//댓글 수정 입력창
	$(document).on('click','#upd[idx]' ,function(){
		$('.RR[idx='+$(this).attr('idx')+']').addClass('reRe');
		$('.MM[idx='+$(this).attr('idx')+']').removeClass('upRe'); 
	});
	//댓글 수정 입력창 닫기
	$(document).on('click','#cls_updbtn[idx]' ,function(){
		$('.MM[idx='+$(this).attr('idx')+']').addClass('upRe');
	});
	//일정 삭제 [세션과 작성자가 일치할 경우만 버튼이 보임]
	$(document).on('click', '.scDel[plan_idx]', function(){
		var result = confirm('일정을 삭제하시겠습니까?');
		if(result){
			//yes
			location.href='./SharePlanDeleteAction.sp?plan_idx='+$(this).attr('plan_idx');
		}else{
			//no
		}
	});
	//일정 수정[세션과 작성자가 일치할 경우만 버튼이 보임]
	$(document).on('click', '.scUpd[plan_idx]', function(){
		var result = confirm('일정을 수정하시겠습니까?');
		if(result){
			//yes
			location.href='./SharePlanUpdate.sp?plan_idx='+$(this).attr('plan_idx');
		}else{
			//no
		}
	});
	$(document).on('click', '#best_btn',function(){
		var count = 0;
		if(count >= 5) {
			alert("더 이상 좋아요를 할수가 없습니다");
		}else{
			$.ajax({
				url: './SharePlanLikeAction.sp',
				type: 'post',
				data:{ "plan_idx" : $('#plan_idx').val(),
				},
				success:function(data){
					document.getElementById("best_value").innerHTML = "공감 " + data;
					count += 1;
				},error:function(request,status,error){alert("일시적인 오류입니다. 잠시후 다시 시도해주세요");}
			});
		}
	});
});

	function page(){ 
		var reSortColors = function($div) { //even 짝수 odd 홀수
		  $('.scReList div:odd div', $div).removeClass('even').addClass('odd');
		  $('.scRe div:even div', $div).removeClass('odd').addClass('even');
		 };
		 $('div.paginated').each(function() {
		  // alert();
		  var pagesu = 10;  //페이지 번호 갯수
		  var currentPage = 0;
		  var numPerPage = 10;  //목록의 수
		  var $div = $(this); // div.paginated
		  
		  //length로 원래 리스트의 전체길이구함
		  var numRows = $div.find(".scRe").length;
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
		  
		   $div.find('.scRe').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
		   $("#remo").html("");

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
		   $pager.insertAfter($div).find('span.page-number:first').next().next().addClass('active');   
		   $pager.appendTo($div);
		   $div.trigger('repaginate');
		 });
		}
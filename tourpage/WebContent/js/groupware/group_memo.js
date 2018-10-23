/**
 * 메모 동작 처리
 */
	    $( function() {
	       	 $( ".gwMemoContent[data-idx]" ).draggable({ containment: "#gwMemoBox", scroll: false });
		        $( ".gwMemoContent" ).draggable({
		        	snap: true,
		        	  stop: function( event, ui ) {
		        		  var idx=$(this).attr("data-idx");
		        		  $.ajax({
		 	        		    method: "GET",
		 	        	        data:{'top':ui.position.top,'left':ui.position.left,'mode':'drag','idx':idx},
		 	        	        url: "./updateMemoAction.gw",
		 	        	        success: function(result){
		 	        	        }
		        	      });//ajax
		        	  	}
		        	});
	       	 $( ".gwMemoContent[data-idx]" ).resizable({
	       		 stop: function( event, ui ) {
		        		  var idx=$(this).attr("data-idx");
		        		 var width=$(this).css('width');
		        		 var height=$(this).css('height');
		        		  $.ajax({
		 	        		    method: "GET",
		 	        	        data:{'width':width,'height':height,'mode':'size','idx':idx},
		 	        	        url: "./updateMemoAction.gw",
		 	        	        success: function(result){
		 	        	        	console.log(result);
		 	        	        }
		        	      });//ajax
	       		 }
	       	 }
	       	 );
        	 $('.configure[data-idx]').on('click',function(){
	        		 var idx=$(this).attr('data-idx');
	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).append('<div class="con_cover"><div class="modiMemo">수정</div><div class="delMemo">삭제</div>'+
	 	     				'<div class="bottomMemo">맨밑으로</div><div class="topMemo">맨위로</div></div>');
	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".modiMemo").on('click',function(){
	 	     			$(".gwMemoContent[data-idx="+idx+"]" ).append('<textarea class="tt" rows="5" cols="5" id="gwMemoW" data-idx='+idx+'></textarea>');
	 	     			$('.tt[data-idx='+idx+']').css('width',$(".gwMemoContent[data-idx="+idx+"]").css('width'))
	 	     			$('.tt[data-idx='+idx+']').css('height',$(".gwMemoContent[data-idx="+idx+"]").css('height'))
	 	     			$('.tt[data-idx='+idx+']').css('margin','0px');
	 	     			$(".con_cover").remove();
	 	     			$('.tt[data-idx='+idx+']').on('focusout',function(){
	 	     				var comment=$(this).val();
	 	     				$(this).remove();
  		        		  $.ajax({
  		 	        		    method: "GET",
  		 	        	        data:{'comment':comment,'mode':'commodi','idx':idx},
  		 	        	        url: "./updateMemoAction.gw",
  		 	        	        success: function(result){
  		 	        	        	$(".gwMemoContent[data-idx="+idx+"]" ).find('.m_comment').text(comment);
  		 	        	        }
  		        	      });//ajax
	 	     				
	 	     			});
	 	     		});
	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".delMemo").on('click',function(){
		        		  $.ajax({
		 	        		    method: "GET",
		 	        	        data:{'idx':idx},
		 	        	        url: "./deleteMemoAction.gw",
		 	        	        success: function(result){
		 	        	        	$(".gwMemoContent[data-idx="+idx+"]" ).remove();
		 	        	        }
		        	      });//ajax
	 	     			
	 	     		});
	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".bottomMemo").on('click',function(){
	 	     			$(".gwMemoContent[data-idx="+idx+"]" ).css('z-index','95');
	 	     			$(".con_cover").remove();
	 	     		});
	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".topMemo").on('click',function(){
	 	     			$(".gwMemoContent[data-idx="+idx+"]" ).css('z-index','105');
	 	     			$(".con_cover").remove();
	 	     		});
	 	     	});
	       	 
	       	 
	       	 
	       	 
	       	 
	        $( "#gwMemoBox" ).off('dblcilck').on('dblclick',function(event,ui){
	        	$(".con_cover").remove();
	        	$( "#memoCover" ).append('<textarea class="gwMemoContent tt"'
	        			+'style="z-index:140; padding:10px; margin:10px; position:absolute; left: 0px; top: 0px;font-size: 2ex;border: solid 2px;'
	        			+'border-radius: 20px;width: 200px;height: 200px;" rows="5" cols="5" id="gwMemoW"></textarea>');
	        	$('#memoCover').css("z-index","130");
	        	  $( "#memoCover").on('dblclick',function(event,ui){
	  	        	$.ajax({
	  	        		 method: "GET",
	  	        	        data:{'comment':$('.tt').val(),'width':$('.tt').css('width'),'height':$('.tt').css('height')},
	  	        	        url: "./addMemoAction.gw",
	  	        	        success: function(result){
	  	        	        	var res=result;
	  	        	        	var res1 = res.substr(0,4);
	  	        	        	var res2 = res.substr(4,);
	  	        	        	
	  	        	        	if(res1=="true"){
	  	        	        		console.log(res2);
	  		        	        	 $( "#gwMemoBox" ).append(res2);
	  		        	        	 $( ".gwMemoContent[data-idx]" ).draggable({ containment: "#gwMemoBox", scroll: false });
	  		        	 	        $( ".gwMemoContent" ).draggable({
	  		        		        	  stop: function( event, ui ) {
	  		        		        		  var idx=$(this).attr("data-idx");
	  		        		        		  $.ajax({
	  		        		 	        		    method: "GET",
	  		        		 	        	        data:{'top':ui.position.top,'left':ui.position.left,'mode':'drag','idx':idx},
	  		        		 	        	        url: "./updateMemoAction.gw",
	  		        		 	        	        success: function(result){
	  		        		 	        	        	console.log(result);
	  		        		 	        	        }
	  		        		        	      });//ajax
	  		        		        	  	}
	  		        		        	});
	  		        	        	 $( ".gwMemoContent[data-idx]" ).resizable({
	  		        	        		 stop: function( event, ui ) {
	  		        		        		  var idx=$(this).attr("data-idx");
	  		        		        		 var width=$(this).css('width');
	  		        		        		 var height=$(this).css('height');
	  		        		        		  $.ajax({
	  		        		 	        		    method: "GET",
	  		        		 	        	        data:{'width':width,'height':height,'mode':'size','idx':idx},
	  		        		 	        	        url: "./updateMemoAction.gw",
	  		        		 	        	        success: function(result){
	  		        		 	        	        }
	  		        		        	      });//ajax
	  		        	        		 }
	  		        	        	 }
	  		        	        	 );
	  		        	        	 $('#memoCover').css("z-index","0");
	  		        	        	 $('.configure[data-idx]').on('click',function(){
	  		        	        		 var idx=$(this).attr('data-idx');
	  		        	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).append('<div class="con_cover"><div class="modiMemo">수정</div><div class="delMemo">삭제</div></div>');
	  		        	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".modiMemo").on('click',function(){
	  		        	 	     			$(".gwMemoContent[data-idx="+idx+"]" ).append('<textarea class="tt" rows="5" cols="5" id="gwMemoW" data-idx='+idx+'></textarea>');
	  		        	 	     			$('.tt[data-idx='+idx+']').css('width',$(".gwMemoContent[data-idx="+idx+"]").css('width'))
	  		        	 	     			$('.tt[data-idx='+idx+']').css('height',$(".gwMemoContent[data-idx="+idx+"]").css('height'))
	  		        	 	     			$('.tt[data-idx='+idx+']').css('margin','0px');
	  		        	 	     			$(".con_cover").remove();
	  		        	 	     			$('.tt[data-idx='+idx+']').on('focusout',function(){
	  		        	 	     				var comment=$(this).val();
	  		        	 	     				$(this).remove();
	  			        		        		  $.ajax({
	  			        		 	        		    method: "GET",
	  			        		 	        	        data:{'comment':comment,'mode':'commodi','idx':idx},
	  			        		 	        	        url: "./updateMemoAction.gw",
	  			        		 	        	        success: function(result){
	  			        		 	        	        	$(".gwMemoContent[data-idx="+idx+"]" ).find('.m_comment').text(comment);
	  			        		 	        	        }
	  			        		        	      });//ajax
	  		        	 	     				
	  		        	 	     			});
	  		        	 	     		});
	  		        	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".delMemo").on('click',function(){
	  		        		        		  $.ajax({
	  		        		 	        		    method: "GET",
	  		        		 	        	        data:{'idx':idx},
	  		        		 	        	        url: "./deleteMemoAction.gw",
	  		        		 	        	        success: function(result){
	  		        		 	        	        	$(".gwMemoContent[data-idx="+idx+"]" ).remove();
	  		        		 	        	        }
	  		        		        	      });//ajax
	  		        	 	     			
	  		        	 	     		});
		  		      	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".bottomMemo").on('click',function(){
			  		  	 	     			$(".gwMemoContent[data-idx="+idx+"]" ).css('z-index','95');
			  		  	 	     			$(".con_cover").remove();
		  		      	 	     		});
		  		      	 	     		$(".gwMemoContent[data-idx="+idx+"]" ).find(".topMemo").on('click',function(){
			  		  	 	     			$(".gwMemoContent[data-idx="+idx+"]" ).css('z-index','105');
			  		  	 	     			$(".con_cover").remove();
		  		      	 	     		});
	  		        	 	     	});
	  	        	        		
	  	        	        	}
	  	        	        	$('.tt').remove();
	  	        	        }
	  	        	});//비동기요청끝
	  	        });
	  	      } );
	        });	        
	      
	    
	    	

	    
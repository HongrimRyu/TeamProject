/**
 * 
 */
function loginOpen(){
	   $('.login').addClass('open');
	   $('#login_cover').addClass('open');
	   $('#login_cover').on('click',function(){
	      $('.login').removeClass('open');
	      $('#login_cover').removeClass('open');
	   });
	}

$(function(){

	$('.lgSbm').click(function(){
	       console.log( $("#id").val()+','+$("#pass").val());
	      $.ajax({
	          url:"./LoginActionInMain.me",

	                   data: {"id":$("#id").val(),"pass":$("#pass").val()},
	                   type: 'POST',
	                  dataType: "json",
	                   success: function(result){
	                      if(result.chk){
	                          $('.login,#login_cover').removeClass('open');
	                          $('#headerul').html(result.tag)
	                          $('#headerLayout ul').append('<li><a href="./MyPage.mp">마이페이지</a></li>');
	                    }else{
	                            alert("아이디나 비밀번호가 틀렸습니다.");
	                            if($("#id").val()==""){
	                               $("#id").focus();
	                            }else if($("#pass").val()==""){
	                               $("#pass").focus();
	                            }
	                         
	                       }
	                   }
	      });
	   });
});
function enterKey(){
	   if(window.event.keyCode==13){
	      $('.lgSbm').click();
	   }
}

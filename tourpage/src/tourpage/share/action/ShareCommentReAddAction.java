package tourpage.share.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tourpage.member.db.MemberDAO;
import tourpage.share.db.ShareCommentBean;
import tourpage.share.db.ShareCommentDAO;

public class ShareCommentReAddAction implements Action{

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      System.out.println("ShareCommentReAddAction_execute()");
      request.setCharacterEncoding("utf-8");
      HttpSession session = request.getSession();
      String id = (String)session.getAttribute("id");
      System.out.println(id);
      if(id==null || id.equals("guest")){
          ActionForward forward = new ActionForward();
            forward.setPath("./Login.me");
            forward.setRedirect(true);
            return forward;
      }
      System.out.println("board_idx : " + request.getParameter("board_idx"));
      System.out.println("reContent : " + request.getParameter("reContent"));
      int board_idx = Integer.parseInt(request.getParameter("board_idx"));
      int reContent = Integer.parseInt(request.getParameter("reContent"));
      int ref = Integer.parseInt(request.getParameter("re_ref"));
      int lev = Integer.parseInt(request.getParameter("lev"));
      System.out.println("ref : "+ ref);
      String content = request.getParameter("content");
      System.out.println(content);
      MemberDAO mdao = new MemberDAO();
      String nick = mdao.getNick(id);
      String profile = mdao.getProfile(id);
      if(profile == null){
         profile = "./img/myPage/tan.jpg";
      }
      if(lev == 0){
    	  lev = 1;
        }else{
      	lev = 2;
      }
      ShareCommentBean scb = new ShareCommentBean();
      ShareCommentDAO scdao = new ShareCommentDAO();
      int seq = scdao.getComment(ref);
      scb.setBoard_idx(board_idx);
      scb.setRe_ref(ref);
      scb.setRe_seq(seq);
      scb.setRe_lev(lev);
      scb.setId(id);
      scb.setNick(nick);
      scb.setProfile(profile);
      scb.setContent(content);
      scb.setIp(request.getRemoteAddr());
      scb.setRef_nick(scdao.getNick(reContent));
      boolean check = scdao.replyComment(scb);
      String comment = "";
      if(check){
    	  List<ShareCommentBean> commentList = scdao.getCommentList(board_idx);
    	  for(int a = 0; a < commentList.size(); a++) {
    		  String enter = "";
    		  if(commentList.get(a).getRe_lev() == 1){
    			  enter = commentList.get(a).getContent().replace("\n", "<br>");
	    		  comment += "<div class='scRe'>"+
				    		 "<span class='reNum'>" +
				    		 "<img src='./img/share/level.gif' width='50' height='10'>"+
				    		 "<img src='./img/share/re.gif'>" +
				    		 "</span>"+
				    		 "<span class='reName'><img src='./img/myPage/"+commentList.get(a).getProfile()+"' width='30px'>&nbsp;&nbsp;"+commentList.get(a).getNick()+"</span>"+
				    		 "<span class='reCon'>"+enter+"</span>"+
				    		 "<span class='reBtns'><input type='button' value='대댓' class='reBtn' id='rep' idx='"+commentList.get(a).getIdx()+"'>";
				    		  if(commentList.get(a).getId().equals(id) || commentList.get(a).getId() == "skyrhl@naver.com"){
				    			  comment +=  "<input type='button' value='수정' class='reBtn' id='upd' idx='"+commentList.get(a).getIdx()+"'>"+
										 	  "<input type='button' value='삭제' class='reBtn' id='del' idx='"+commentList.get(a).getIdx()+"'>";
				    		  }
	    		  comment+="</span>"+
				    		 "</div>"+
				    		 "<div class='RR scReply reRe' idx='"+commentList.get(a).getIdx()+"'>"+
			                    "	댓글<br>"+
			                    "<div id='cls_repbtn' idx='"+commentList.get(a).getIdx()+"' style='cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;'>X</div>"+
							    "<textarea rows='5' cols='60' class='reContent' idx='"+commentList.get(a).getIdx()+"'></textarea>"+
							    "<input type='button' value='reply' class='scReBtn' id='reReply' idx='"+commentList.get(a).getIdx()+"'"+ 
							    "              ref='"+commentList.get(a).getRe_ref()+"' lev='"+commentList.get(a).getRe_lev()+"'>"+
							    "<div style='clear: both;'></div>"+
						        "</div>"+
						        "<div class='MM scReply upRe' idx='"+commentList.get(a).getIdx()+"'>"+
							    "댓글 수정<br>"+
							    "<div id='cls_updbtn' idx='"+commentList.get(a).getIdx()+"' style='cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;'>X</div>"+
							    "<textarea rows='5' cols='60' class='upContent' idx='"+commentList.get(a).getIdx()+"'></textarea>"+
							    "<input type='hidden' id='board_idx' value='"+commentList.get(a).getBoard_idx()+"'/>"+
							    "<input type='button' value='reply' class='scReBtn' id='upReply' idx='"+commentList.get(a).getIdx()+"'>"+
							    "<div style='clear: both;'></div>"+
						        "</div>";
				}else if(commentList.get(a).getRe_lev() == 2){
					enter = commentList.get(a).getContent().replace("\n", "<br>");
					comment += "<div class='scRe'>"+
							 "<span class='reNum'>" +
						 	 "<img src='./img/share/level.gif' width='50' height='10'>"+
						 	 "<img src='./img/share/re.gif'>" +
						 	 "</span>"+
					         "<span class='reName'><img src='./img/myPage/"+commentList.get(a).getProfile()+"' width='30px'>&nbsp;&nbsp;"+commentList.get(a).getNick()+"</span>"+
							 "<span class='reCon'>"+"["+commentList.get(a).getNick()+"] "+enter+"</span>"+
							 "<span class='reBtns'><input type='button' value='대댓' class='reBtn' id='rep' idx='"+commentList.get(a).getIdx()+"'>";
							 if(commentList.get(a).getId().equals(id) || commentList.get(a).getId() == "skyrhl@naver.com"){
								comment +=  "<input type='button' value='수정' class='reBtn' id='upd' idx='"+commentList.get(a).getIdx()+"'>"+
										 	"<input type='button' value='삭제' class='reBtn' id='del' idx='"+commentList.get(a).getIdx()+"'>";
							 }
					comment+= "</span>"+
					          "</div>"+
					          "<div class='RR scReply reRe' idx='"+commentList.get(a).getIdx()+"'>"+
			                    "	댓글<br>"+
			                    "<div id='cls_repbtn' idx='"+commentList.get(a).getIdx()+"' style='cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;'>X</div>"+
							    "<textarea rows='5' cols='60' class='reContent' idx='"+commentList.get(a).getIdx()+"'></textarea>"+
							    "<input type='button' value='reply' class='scReBtn' id='reReply' idx='"+commentList.get(a).getIdx()+"'"+ 
							    "              ref='"+commentList.get(a).getRe_ref()+"' lev='"+commentList.get(a).getRe_lev()+"'>"+
							    "<div style='clear: both;'></div>"+
						        "</div>"+
						        "<div class='MM scReply upRe' idx='"+commentList.get(a).getIdx()+"'>"+
							    "댓글 수정<br>"+
							    "<div id='cls_updbtn' idx='"+commentList.get(a).getIdx()+"' style='cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;'>X</div>"+
							    "<textarea rows='5' cols='60' class='upContent' idx='"+commentList.get(a).getIdx()+"'></textarea>"+
							    "<input type='hidden' id='board_idx' value='"+commentList.get(a).getBoard_idx()+"'/>"+
							    "<input type='button' value='reply' class='scReBtn' id='upReply' idx='"+commentList.get(a).getIdx()+"'>"+
							    "<div style='clear: both;'></div>"+
						        "</div>";
				}else{
					enter = commentList.get(a).getContent().replace("\n", "<br>");
					comment += "<div class='scRe'>"+
					 			"<span class='reNum'></span>"+
					 			"<span class='reName'><img src='./img/myPage/"+commentList.get(a).getProfile()+"' width='30px'>&nbsp;&nbsp;"+commentList.get(a).getNick()+"</span>"+
					 			"<span class='reCon'>"+enter+"</span>"+
					 			"<span class='reBtns'><input type='button' value='대댓' class='reBtn' id='rep' idx='"+commentList.get(a).getIdx()+"'>";
								if(commentList.get(a).getId().equals(id) || commentList.get(a).getId() == "skyrhl@naver.com"){
								comment +=  "<input type='button' value='수정' class='reBtn' id='upd' idx='"+commentList.get(a).getIdx()+"'>"+
											"<input type='button' value='삭제' class='reBtn' id='del' idx='"+commentList.get(a).getIdx()+"'>";
								}
					comment+=   "</span>"+
			                    "</div>"+
			                    "<div class='RR scReply reRe' idx='"+commentList.get(a).getIdx()+"'>"+
			                    "	댓글<br>"+
			                    "<div id='cls_repbtn' idx='"+commentList.get(a).getIdx()+"' style='cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;'>X</div>"+
							    "<textarea rows='5' cols='60' class='reContent' idx='"+commentList.get(a).getIdx()+"'></textarea>"+
							    "<input type='button' value='reply' class='scReBtn' id='reReply' idx='"+commentList.get(a).getIdx()+"'"+ 
							    "              ref='"+commentList.get(a).getRe_ref()+"' lev='"+commentList.get(a).getRe_lev()+"'>"+
							    "<div style='clear: both;'></div>"+
						        "</div>"+
						        "<div class='MM scReply upRe' idx='"+commentList.get(a).getIdx()+"'>"+
							    "댓글 수정<br>"+
							    "<div id='cls_updbtn' idx='"+commentList.get(a).getIdx()+"' style='cursor:pointer; border: 1px solid black;width: 20px;height:  20px;position:  absolute;top: 10px;right: 2%;'>X</div>"+
							    "<textarea rows='5' cols='60' class='upContent' idx='"+commentList.get(a).getIdx()+"'></textarea>"+
							    "<input type='hidden' id='board_idx' value='"+commentList.get(a).getBoard_idx()+"'/>"+
							    "<input type='button' value='reply' class='scReBtn' id='upReply' idx='"+commentList.get(a).getIdx()+"'>"+
							    "<div style='clear: both;'></div>"+
						        "</div>";
				}
    	  }
    	  response.setCharacterEncoding("utf-8");
    	  response.getWriter().write(comment);
    	  return null;
      }
      return null;
   }
   
}
package tourpage.share.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tourpage.share.db.ShareCommentBean;
import tourpage.share.db.ShareCommentDAO;

public class ShareCommentListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShareCommentListAction_exeute()");
		  request.setCharacterEncoding("utf-8");
	      HttpSession session = request.getSession();
	      String id = (String)session.getAttribute("id");
	      if(id==null){
	         ActionForward forward = new ActionForward();
	         forward.setPath("./Login.me");
	         forward.setRedirect(true);
	         return forward;
	      }
	      int board_idx = Integer.parseInt(request.getParameter("board_idx"));
	      ShareCommentDAO scdao = new ShareCommentDAO();
	      List<ShareCommentBean> commentList = scdao.getCommentList(board_idx);
	      Gson gson = new Gson();
	      String commentJson = gson.toJson(commentList);
	      response.setCharacterEncoding("utf8");
	      response.getWriter().write(commentJson);
		return null;
	}

}

package tourpage.share.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.share.db.ShareCommentDAO;
import tourpage.share.db.SharePlanDAO;

public class ShareGetContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ShareGetContentAction");
		System.out.println(request.getParameter("idx"));
		SharePlanDAO sdao = new SharePlanDAO();
		int plan_idx = Integer.parseInt(request.getParameter("idx"));
		ShareCommentDAO scdao = new ShareCommentDAO();
		List board = sdao.getBoardList(plan_idx);
		List comment = scdao.getCommentList(plan_idx);
		Map map = new HashMap();
		map.put("board", board);
		map.put("comment", comment);
		request.setAttribute("content", map);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./share/shareContent.jsp");
		forward.setRedirect(false);
		return forward;
	}

}

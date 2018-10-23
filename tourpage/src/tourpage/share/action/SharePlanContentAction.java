package tourpage.share.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;
import tourpage.share.db.ShareCommentBean;
import tourpage.share.db.ShareCommentDAO;
import tourpage.share.db.SharePlanBean;
import tourpage.share.db.SharePlanDAO;

public class SharePlanContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SharePlanContentAction_execute()");
	      request.setCharacterEncoding("utf-8");
	      HttpSession session = request.getSession();
	      String id = (String)session.getAttribute("id");
      
	      int idx = Integer.parseInt(request.getParameter("idx"));
	      System.out.println("SharePlanContentAction idx : " + idx);
	     
	      SharePlanDAO spdao = new SharePlanDAO();
	      //조회수 +1 업데이트하기
	      spdao.updateReadCount(idx);
	      
	      List<SharePlanBean> planList = spdao.getBoardList(idx);
	     
	      ShareCommentDAO scdao = new ShareCommentDAO();
	      List<ShareCommentBean> commentList = scdao.getCommentList(idx);
	      
	      Map<String, List<?>> map = new HashMap<String, List<?>>();
	      map.put("planList", planList);
	      map.put("commentList", commentList);
	      request.setAttribute("map", map);
	      ActionForward forward = new ActionForward();
	      forward.setPath("/ShareContent.sp?idx="+idx);
	      forward.setRedirect(false);
	      return forward;
	}
}

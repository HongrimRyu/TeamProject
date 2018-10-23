package tourpage.share.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.share.db.SharePlanBean;
import tourpage.share.db.SharePlanDAO;

public class SharePlanUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SharePlanDeleteAction_execute()");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}
		request.setCharacterEncoding("utf-8");
		int plan_idx =  Integer.parseInt(request.getParameter("plan_idx"));
		SharePlanDAO spdao = new SharePlanDAO();
		List<SharePlanBean> planList = spdao.getBoardList(plan_idx);
		request.setAttribute("planList", planList);
		forward = new ActionForward();
		forward.setPath("./share/shareUpdate.jsp");
		forward.setRedirect(false);
		return forward;
	}
	
}

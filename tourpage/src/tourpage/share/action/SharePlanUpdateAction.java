package tourpage.share.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.member.db.MemberDAO;
import tourpage.share.db.SharePlanBean;
import tourpage.share.db.SharePlanDAO;

public class SharePlanUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SharePlanDeleteAction_execute()");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}
		SharePlanBean spb = new SharePlanBean();
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		SharePlanDAO spdao = new SharePlanDAO();
		List<SharePlanBean> planList = spdao.getBoardList(plan_idx);
		for(int i=0; i<planList.size(); i++){
			spb = planList.get(i);
			spb.setComment(request.getParameter("content"+spb.getPlan_days()));
			spdao.updateSharePlan(spb);
		}
		planList = spdao.getBoardList(plan_idx);
		request.setAttribute("planList", planList);
		forward = new ActionForward();
		forward.setPath("./SharePlanContent.sp?idx="+plan_idx);
		forward.setRedirect(true);
		return forward;
	}
	
}

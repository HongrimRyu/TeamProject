package tourpage.myPage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlanDAO;

public class MyPlanDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		if(session.getAttribute("id")==null){
			forward.setPath("./Login.me");
			forward.setRedirect(false);
			return forward;
		}
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		PlanDAO dao = new PlanDAO();
		dao.deletePlan(plan_idx);
		
		forward.setPath("./myPage/myPage.jsp");
		forward.setRedirect(false);
		return forward;
	}

}

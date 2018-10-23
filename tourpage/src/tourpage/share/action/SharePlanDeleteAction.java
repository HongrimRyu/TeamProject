package tourpage.share.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlanDAO;
import tourpage.share.db.SharePlanDAO;

public class SharePlanDeleteAction implements Action{

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
		System.out.println("SharePlanDeleteAction : " + plan_idx);
		
		SharePlanDAO spdao = new SharePlanDAO();
		boolean check = spdao.deleteSharePlan(plan_idx);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(check) {
			out.println("<script>");
			out.println("alert('일정 삭제 완료');");
			out.println("location.href='./SharePlan.sp';");
			out.println("</script>");
			out.close();
			return null;
		}
		out.println("<script>");
		out.println("alert('일정 삭제 실패');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
		return null;
	}
	
}

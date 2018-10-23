package tourpage.group.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.db.GroupDAO;

public class GroupDetailPeopleAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String groupid = request.getParameter("groupid");
		String plan_people = request.getParameter("plan_people");
		if(plan_people==null)	plan_people="";
		
		GroupDAO dao = new GroupDAO();
		dao.updatePoeple(groupid, plan_people);
		
		return null;
	}
}

package tourpage.group.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.db.GroupDAO;

public class CreateGroupAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CreateGroupAction execute");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		GroupDAO gd = new GroupDAO();
		gd.insertGroup(id,pass,plan_idx);
		
		return null;
	}
}

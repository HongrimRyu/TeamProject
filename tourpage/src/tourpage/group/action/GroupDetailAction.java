package tourpage.group.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tourpage.group.db.GroupBean;
import tourpage.group.db.GroupDAO;

public class GroupDetailAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GroupDetailAction excute");
		String groupid = request.getParameter("groupid");
		GroupDAO dao = new GroupDAO();
		GroupBean gb = dao.getGroupPlan(groupid);
		
		Gson gson = new Gson();
		String json = gson.toJson(gb);
		System.out.println(json);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		
		return null;
	}
}

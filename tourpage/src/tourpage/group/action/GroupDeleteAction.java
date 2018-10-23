package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.db.GroupDAO;

public class GroupDeleteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getParameter("group_id")==null){
			ActionForward f = new ActionForward();
			f.setPath("./MyGroupware.gw");
			f.setRedirect(true);
			return f;
		}
		String group_id = request.getParameter("group_id");
		
		response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
		GroupDAO dao = new GroupDAO();
		int chk = dao.deleteGroup(group_id);
		
		if(chk==1)	out.println("1"); 
		else		out.println("0");
		
		return null;
	}
}

package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.db.GroupDAO;

public class GroupIdCheckAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GroupIdCheckAction execute");
		String id = request.getParameter("id");
		GroupDAO gd = new GroupDAO();
		boolean result = gd.idCheck(id);
		PrintWriter out = response.getWriter();
		
		if(result) out.println("0"); // 아이디 중복
        else out.println("1");
        out.close();
		return null;
	}
}

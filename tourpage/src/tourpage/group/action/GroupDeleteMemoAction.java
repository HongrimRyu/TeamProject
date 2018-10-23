package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.db.GmemoDAO;

public class GroupDeleteMemoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GroupDeleteMemoAction excute()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		GmemoDAO gdao= new GmemoDAO();
		if(gdao.deleteMemo(idx)){
	    	response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("true");
	      	out.close();
		}
		return null;
	}

}

package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tourpage.group.db.GmemoBean;
import tourpage.group.db.GmemoDAO;

public class GroupUpdateMemoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mode= request.getParameter("mode");
		System.out.println(mode);
		int idx = Integer.parseInt(request.getParameter("idx"));
		String top="",left="",width="",height="",comment="";
		GmemoBean gb= new GmemoBean();
		gb.setIdx(idx);
		if(mode.equals("drag")){
		top = request.getParameter("top");
		left = request.getParameter("left");
		gb.setGw_top(top+"px");
		gb.setGw_left(left+"px");
		}else if(mode.equals("size")){
			width = request.getParameter("width");
			height = request.getParameter("height");
			gb.setGw_width(width);
			gb.setGw_height(height);
		}else if(mode.equals("commodi")){
			comment=request.getParameter("comment");
			gb.setGw_comment(comment);
		}
		
		GmemoDAO gdao = new GmemoDAO();
		if(gdao.updateMemo(gb,mode)){
	    	response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("true");
	      	out.close();
		}
		
		
		
		
		
		return null;
	}

}

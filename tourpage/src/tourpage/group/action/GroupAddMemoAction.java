package tourpage.group.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.group.db.GmemoBean;
import tourpage.group.db.GmemoDAO;

public class GroupAddMemoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GroupAddMemoAction  execute");
		String comment=request.getParameter("comment");
		String width=request.getParameter("width");
		String height=request.getParameter("height");
		HttpSession session = request.getSession();
		String nick=(String)session.getAttribute("nick");
		String groupid=(String)session.getAttribute("group_id");
		System.out.println(comment);
		System.out.println(width);
		System.out.println(height);
		System.out.println(nick);
		System.out.println(groupid);
		GmemoBean gb = new GmemoBean();
		gb.setGroupid(groupid);
		gb.setGw_comment(comment);
		gb.setGw_width(width);
		gb.setGw_height(height);
		gb.setGw_nick(nick);
		GmemoDAO gdao = new GmemoDAO();
		
		if(gdao.insertMemo(gb)){
			int idx = gdao.getMaxidx();
			String divmemo="<div class='gwMemoContent stickerMemo' data-idx='"+idx+"'"+
	        	           "style='position: absolute; width:"+width+"; height:"+height+"; left: 0px; top: 0px;font-size: 2ex;border: solid 2px;border-radius: 20px;'>"+
	        	           "<div class='inner_memo'><span>"+nick+"</span><div class='m_comment'>"+comment+"</div>"+
	        	           "<div class='configure'data-idx='"+idx+"'"
	        	           		+ " style='cursor: pointer; position: absolute; padding:  10px;bottom:  2%;  right: 2%;'><img alt='@' src='./img/commons/dot.png'></div></div>"+
	        	           "</div>";
	    	response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("true"+divmemo);
	      	out.close();
		}
		
		
		
		return null;
	}

}

package tourpage.myPage.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import tourpage.group.db.GroupBean;
import tourpage.group.db.GroupDAO;

public class MyGroupwareAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("MyGroupwareAction excute");
		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		
		if(session.getAttribute("id")==null){
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}
		
		String id = (String)session.getAttribute("id");
		
		GroupDAO dao = new GroupDAO();
		List<GroupBean> arr = dao.getMyGroupList(id);
		Gson gson = new Gson();
		
		JSONParser jp = new JSONParser();
		JSONArray jarr = (JSONArray) jp.parse(gson.toJson(arr));
		request.setAttribute("myGroupList",jarr );
		
		forward.setPath("./myPage/myGroupList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}

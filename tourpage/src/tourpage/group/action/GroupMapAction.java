package tourpage.group.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.group.db.GroupBean;
import tourpage.group.db.GroupDAO;
import tourpage.makePlan.db.PlaceDAO;
import tourpage.makePlan.db.PlaceDTO;
import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;

public class GroupMapAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GroupMapAction exceute()");
//		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		String group_id="skyrhl";
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println(id);
		
		GroupBean gb = new GroupBean();
		GroupDAO gdao = new GroupDAO();
		gb = gdao.getGroupPlan(group_id);
		System.out.println(gb.getPlan_detail());
		String[] detail=gb.getPlan_detail().split("@");
		
		String plan_detail = "";
		for(int i=0, max=detail.length ; i < max ; i+=1){
			plan_detail += detail[i]+",";
		}
		plan_detail=plan_detail.substring(0, plan_detail.length()-1);
		System.out.println("일정"+plan_detail);
		PlaceDAO pldao= new PlaceDAO();
		Map<Integer, PlaceDTO> map = pldao.getPlaceList(plan_detail);
		request.setAttribute("placeinfo",map);
		request.setAttribute("plan", gb);
		ActionForward forward = new ActionForward();
		forward.setPath("./group/modifyPlan.jsp");
		forward.setRedirect(false);	
		return forward;
	}

}

package tourpage.group.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tourpage.makePlan.db.PlaceDAO;
import tourpage.makePlan.db.PlaceDTO;

public class GroupDetailListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("GroupDetailListAction excute");
		String[] days = request.getParameter("plan_detail").split("@");
		List<String> result = new ArrayList<>();
		PlaceDAO dao = new PlaceDAO();
		for(int i=0;i<days.length;i++){
			Map<Integer,PlaceDTO> map = dao.getPlaceList(days[i]);
			String[] detail = days[i].split(",");
			String tmp = "";
			for(int j=0;j<detail.length;j++){
				if(j==detail.length-1){
					tmp+=map.get(Integer.parseInt(detail[j])).getName();
				}else{
					tmp+=map.get(Integer.parseInt(detail[j])).getName()+">";
				}
			}
			result.add(tmp);
		}
		Gson gson = new Gson();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(gson.toJson(result));
		
		return null;
	}
}

package tourpage.shop.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tourpage.shop.action.ActionForward;

public class searchAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("searchAction execute");
		request.setCharacterEncoding("utf-8");
		
		//검색어
		String query = request.getParameter("keyword");
		//글시작지점
		int start = 1;
		//현재 페이지
		if(request.getParameter("pageNum")!=null){
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			start = (pageNum-1)*10 +1;
		}
		//정렬방식
		String sort = "sim";
		if(request.getParameter("sort")!=null){
			sort = (String)request.getParameter("sort");
		}
		System.out.println("검색어: "+query);
		String clientId = "eBfAKVzMAGh91h2hbm8T";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "z4vGIQDf10";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/shop?query="+ text+"&start="+start+"&sort="+sort; // json 결과
        
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            
            int responseCode = con.getResponseCode();
            BufferedReader br;
            
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer responseStringBuffer = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                responseStringBuffer.append(inputLine);
            }
            JSONParser jp = new JSONParser();
            JSONObject jo = (JSONObject)jp.parse(responseStringBuffer.toString());
            int total = Integer.parseInt(jo.get("total").toString());
            JSONArray arr = (JSONArray)jo.get("items");
            
            request.setAttribute("count", total);
            request.setAttribute("items", arr);
            request.setAttribute("keword", query);
            request.setAttribute("pageNum", request.getParameter("pageNum"));
            request.setAttribute("sort", sort);
            br.close();
            System.out.println("결과:"+responseStringBuffer.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        ActionForward forward = new ActionForward();
		forward.setPath("/searchResult.sh");
		forward.setRedirect(false);
		return forward;
	}
	
}

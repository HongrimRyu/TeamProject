package tourpage.makePlan.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import tourpage.makePlan.db.PlaceDAO;
import tourpage.makePlan.db.PlaceDTO;

public class addPlaceAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setCharacterEncoding("utf-8");
//	     System.out.println("데이터받아오기 성공");
	    HttpSession session = request.getSession();
	    String ssid = (String) session.getAttribute("id");
	    if(ssid==null){
        	response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println("{\"chk\": false}"+"/");
          	out.close();
	    	return null;
	    }
	    ServletContext context = request.getServletContext();
	    String saveName="";
		String realPath=context.getRealPath("/upload");
		int maxSize = 10 * 1024 * 1024; // 10MB
		MultipartRequest multi = new MultipartRequest(
			request,
			realPath,
			maxSize,
			"utf-8",
			new DefaultFileRenamePolicy()	
			);
		String fileName="";
		System.out.println("공유설정 : "+ multi.getParameter("share"));
		if(multi.getFilesystemName("image-upload")==null){
			saveName="default.png";
		}else{
			fileName= multi.getFilesystemName("image-upload").toLowerCase(); 
		    String savePath="upload";
			String sDownloadPath=context.getRealPath(savePath);//C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Bs_Travel/upload/logo.jpg
			String sFilePath="";
			String datagroup="";

			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yy_mm_dd_hh_mm_ss");
			String str = dayTime.format(new Date(time));
			sFilePath = sDownloadPath+"/"+fileName;
			saveName= str+"_"+fileName;
			int data=0;
			int cnt=0;
			try {
					InputStream fis = new FileInputStream(sFilePath);
					OutputStream fos= new FileOutputStream("/var/lib/tomcat8/webapps/tourpage/img/makePlan/info"+saveName);
					while((data=fis.read()) != -1){
						fos.write(data);
				        cnt++;
					}
					fos.close();
			
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}
		}		
	    String latlng=multi.getParameter("latlng");
	   	String lat=latlng.split(",")[0].substring(1);
	   	String lng=latlng.split(",")[1].substring(1,latlng.split(",")[1].length()-1);
	    
	    PlaceDAO dao= new PlaceDAO();
	    PlaceDTO dto= new PlaceDTO();
	    //저장할정보 객체에 저장

	    dto.setName(multi.getParameter("pac-name"));
	    dto.setAddress(multi.getParameter("pac-input"));
	    dto.setInfo(multi.getParameter("detail"));
	    dto.setLat(lat);
	    dto.setLng(lng);
	    dto.setType(multi.getParameter("type"));
	    dto.setAuthor((String)session.getAttribute("id"));
	    dto.setA_ip(getClientIpAddr(request));
	    dto.setPhone(multi.getParameter("phone"));
	    dto.setO_time(multi.getParameter("o_time"));
	    dto.setPrice(multi.getParameter("price"));
	    dto.setHomepage(multi.getParameter("homepage"));
	    dto.setTraffic(multi.getParameter("traffic"));
	    dto.setSharechk(multi.getParameter("share"));
	    dto.setImage(saveName);
	   
	    dao.insertPlaceinfo(dto); 

	    
	    dto= dao.getMyPlace((String)session.getAttribute("id"));
		Gson gson = new Gson();
		String json=gson.toJson(dto);
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.println("{\"chk\": true}"+"/"+json);
      	out.close();
		return null;
	}
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}

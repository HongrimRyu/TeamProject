package tourpage.group.db;

import java.sql.Timestamp;

public class GmemoBean {
	private int idx;
	private String groupid;
	private String gw_comment;
	private Timestamp w_time;
	private String gw_nick;
	private String gw_top;
	private String gw_left;
	private String gw_width;
	private String gw_height;
	private String passwd;
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getGw_width() {
		return gw_width;
	}
	public void setGw_width(String gw_width) {
		this.gw_width = gw_width;
	}
	public String getGw_height() {
		return gw_height;
	}
	public void setGw_height(String gw_height) {
		this.gw_height = gw_height;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGw_comment() {
		return gw_comment;
	}
	public void setGw_comment(String gw_comment) {
		this.gw_comment = gw_comment;
	}
	public Timestamp getW_time() {
		return w_time;
	}
	public void setW_time(Timestamp w_time) {
		this.w_time = w_time;
	}
	public String getGw_nick() {
		return gw_nick;
	}
	public void setGw_nick(String gw_nick) {
		this.gw_nick = gw_nick;
	}
	public String getGw_top() {
		return gw_top;
	}
	public void setGw_top(String gw_top) {
		this.gw_top = gw_top;
	}
	public String getGw_left() {
		return gw_left;
	}
	public void setGw_left(String gw_left) {
		this.gw_left = gw_left;
	}
	
}

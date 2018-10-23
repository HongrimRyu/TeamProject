package tourpage.share.db;

import java.sql.Date;

public class SharePlanBean {
	private int idx;
	private String plan_name;
	private int plan_idx;
	private int best;
	private int readcount;
	private String travel_period;
	private int plan_days;
	private String comment;
	private String plan_detail;
	private Date regdate; // 등록한 날짜, 수정한 날짜
	private String writer_ip;
	private String id;
	private String nick;
	private String image;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public int getPlan_idx() {
		return plan_idx;
	}
	public void setPlan_idx(int plan_idx) {
		this.plan_idx = plan_idx;
	}
	public int getBest() {
		return best;
	}
	public void setBest(int best) {
		this.best = best;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getTravel_period() {
		return travel_period;
	}
	public void setTravel_period(String travel_period) {
		this.travel_period = travel_period;
	}
	public int getPlan_days() {
		return plan_days;
	}
	public void setPlan_days(int plan_days) {
		this.plan_days = plan_days;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPlan_detail() {
		return plan_detail;
	}
	public void setPlan_detail(String plan_detail) {
		this.plan_detail = plan_detail;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date date) {
		this.regdate = date;
	}
	public String getWriter_ip() {
		return writer_ip;
	}
	public void setWriter_ip(String writer_ip) {
		this.writer_ip = writer_ip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}

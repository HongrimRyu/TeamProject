package tourpage.group.db;

import java.sql.Timestamp;

public class GroupBean {
	
	private String group_id;
	private String group_pass;
	private int plan_idx;
	private String plan_name;
	private String plan_startdate;
	private int plan_days;
	private String plan_detail;
	private String plan_writer;
	private Timestamp plan_time;
	private String writer_id;
	private String plan_people;
	
	
	public String getPlan_people() {
		return plan_people;
	}
	public void setPlan_people(String plan_people) {
		this.plan_people = plan_people;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_pass() {
		return group_pass;
	}
	public void setGroup_pass(String group_pass) {
		this.group_pass = group_pass;
	}
	public int getPlan_idx() {
		return plan_idx;
	}
	public void setPlan_idx(int plan_idx) {
		this.plan_idx = plan_idx;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public String getPlan_startdate() {
		return plan_startdate;
	}
	public void setPlan_startdate(String plan_startdate) {
		this.plan_startdate = plan_startdate;
	}
	public int getPlan_days() {
		return plan_days;
	}
	public void setPlan_days(int plan_days) {
		this.plan_days = plan_days;
	}
	public String getPlan_detail() {
		return plan_detail;
	}
	public void setPlan_detail(String plan_detail) {
		this.plan_detail = plan_detail;
	}
	public String getPlan_writer() {
		return plan_writer;
	}
	public void setPlan_writer(String plan_writer) {
		this.plan_writer = plan_writer;
	}
	public Timestamp getPlan_time() {
		return plan_time;
	}
	public void setPlan_time(Timestamp plan_time) {
		this.plan_time = plan_time;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	
	
}

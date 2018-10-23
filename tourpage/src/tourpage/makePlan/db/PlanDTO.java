package tourpage.makePlan.db;

import java.util.Date;

public class PlanDTO {
	private int plan_idx;
	private String plan_name;
	private int plan_days;
	private String plan_detail;
	private String plan_writer;
	private String plan_startdate;
	private int maxDay;
	public int getMaxDay() {
		return maxDay;
	}
	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}
	private Date plan_time;
	public Date getPlan_time() {
		return plan_time;
	}
	public void setPlan_time(Date plan_time) {
		this.plan_time = plan_time;
	}
	private String id;
	private String priv;
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPriv() {
		return priv;
	}
	public void setPriv(String priv) {
		this.priv = priv;
	}
	public String getPlan_startdate() {
		return plan_startdate;
	}
	public void setPlan_startdate(String plan_startdate) {
		this.plan_startdate = plan_startdate;
	}
}

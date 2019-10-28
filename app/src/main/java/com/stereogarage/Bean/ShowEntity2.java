package com.stereogarage.Bean;

public class ShowEntity2 {
	private String id;
	private String total_number;
	private String rest_number;

	public ShowEntity2(String id, String total_number, String  rest_number){
		this.id = id;
		this.total_number = total_number;
		this.rest_number = rest_number;
	}
	
	public String getid() {return id;}
	public void setid(String id) {
		this.id = id;
	}
	public String gettotal_number() {
		return total_number;
	}
	public void settotal_number(String total_number) {
		this.total_number = total_number;
	}
	public String getrest_number() {
		return rest_number;
	}
	public void setrest_number(String rest_number) {this.rest_number = rest_number;}

}

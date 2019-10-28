package com.stereogarage.Bean;

public class ShowEntity{
	private String current_addr;
	private String car_price;
	private String car_time;
	private String order_id;

	public ShowEntity (String current_addr, String car_price,String car_time,String order_id){
		this.car_price = car_price;
		this.car_time = car_time;
		this.current_addr = current_addr;
		this.order_id=order_id;
	}
	
	public String getcar_price() {return car_price;}
	public void setcar_price(String car_price) {
		this.car_price = car_price;
	}
	public String getcar_time() {
		return car_time;
	}
	public void setcar_time(String car_time) {
		this.car_time = car_time;
	}
	public String getcurrent_addr() {
		return current_addr;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setcurrent_addr(String current_addr) {this.current_addr = current_addr;

	}

}

package com.mkyong.common.model;

import java.util.*;

public class Shop {

	
	public List<Point> arrList = new ArrayList<Point>();
	public int ClientID;
	
	public int getClientID()
	{return ClientID;}
	
	public void setClientID(int ClientID)
	{
		this.ClientID = ClientID;
	}
	
	public Shop() {
	} 
	
}
package com.mkyong.common.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

/**
 * 
 * @author Neshant
 *<Description> establishes connection to general purpose login table</Description>
 *<Database>MySql 5.6 on port 3306</Database> 
 *<Schema>test</Schema> 
 *<Table>tab</Table>
 *
 */

public class dbcon_select 
{
	private Connection con;
	private ResultSet rs;
	
	
	public dbcon_select()
	{
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			
		}
		catch(Exception ex)
		{
			
			System.out.println("Some Exception has occoured DBConnect_select_1 constructor "+ex);
		}
	}
	
	public String getdata(String name)
	{
		
		System.out.println(name);
		try
		{
			PreparedStatement prepStatSelection = null;
			prepStatSelection = (PreparedStatement) con.prepareStatement("select password from tab where user = (?)");
			
			prepStatSelection.setString(1, name);
			//rs = stat.executeQuery(query);
			rs=prepStatSelection.executeQuery();
			System.out.println("Records from the database");
			while(rs.next())
			{
				//String user= rs.getString("user");
				String password = rs.getString("password");
				System.out.println(" Password :"+password);
				return password;
				
			}
			
			
			
		}
		catch(Exception ex)
		{
			
			System.out.println("The error from the  DBConnect_select_1 getdata function"+ex);
		}
		return null;
	}
	
	public boolean inserData(String name,String password)
	{
		System.out.println("Insert datam function "+name+" "+password);
		
		try 
		{
			PreparedStatement prepStatInsertion = null;
			prepStatInsertion = (PreparedStatement) con.prepareStatement("insert into tab values (?,?)");
			
			prepStatInsertion.setString(1, name);
			prepStatInsertion.setString(2, password);
			
			int check = prepStatInsertion.executeUpdate();
			if(check>0)
			{
				System.out.println("Successful update");
				return true;
			}
			else
			{
				System.out.println("Unsuccessful update");
				return false;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

}

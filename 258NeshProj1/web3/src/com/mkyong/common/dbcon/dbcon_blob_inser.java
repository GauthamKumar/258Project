package com.mkyong.common.dbcon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 
 * @author Neshant
 *<Description> This class is used to insert a blob into the database, it extends 
 *specific blob functionalities and is utilized only after login</Description>
 *<Database>MySql 5.6 on port 3306</Database> 
 *<Schema>test</Schema> 
 *<Table>picLab</Table>
 *
 */
public class dbcon_blob_inser 
{
	//<Information>The private variables of class dbcon_blob_insert</Information>
	private Connection con;
	private ResultSet Rs;
	
	public dbcon_blob_inser()
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
	
	/**
	 * 
	 * @return boolean value
	 * <Definition>The insert function specific for insertion of blob data</Definition>
	 */
	public boolean insertData_BLOB(String name,File file)
	{
		System.out.println("Insert datam function ");
		
		try 
		{
			PreparedStatement prepStatInsertion = (PreparedStatement) con.prepareStatement("insert into picLab values (?,?)");
			FileInputStream fis = null;
			//<Information>The try-catch block that surrounds the file input stream</Information>
			try 
			{
				
				fis = new FileInputStream(file);
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				//<Information>Return false and don't proceed with insertion of data</Information>
				return false;
			}
			
			prepStatInsertion.setString(1, name);
			prepStatInsertion.setBinaryStream(2, fis, (int) file.length());
			
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

package com.mkyong.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.log4j.Logger;








import com.mkyong.common.dbcon.dbcon_select;
import com.mkyong.common.dbcon.dbcon_blob_inser;
//import com.mkyong.common.dbcon.DBConnect_select_1;
import com.mkyong.common.model.Description;
import com.mkyong.common.model.FileManager;
import com.mkyong.common.model.Point;
import com.mkyong.common.model.PointsCloud;
import com.mkyong.common.model.UserStats;
import com.mkyong.common.model.Shop;
import com.mkyong.common.model.allPassword;
import com.mkyong.common.model.registrationNumber;
import com.mkyong.common.model.testMe;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Controller
@RequestMapping("/kfc/brands")
public class JSONController {

	//<Information>Local Variables to the Controller module</Information>
	Shop shop = new Shop();
	/**
	 * <Definition>The shop object declaration, this object holds the list of points </Definition>
	 */
	Shop sta[] = new Shop[10];
	/**
	 * <Definition>The count is used to identify each client and register the view</Definition>
	 */
	static int Count =0;
	/**
	 * <Definition>The Cloud memory management element: Primitive garbage collection logic</Definition>
	 */
	int cloud_limit = 0;//The limit always starts from zero
	/**
	 * <Definition>The actual points cloud: this class simulates a cloud upon which we can store data</Definition>
	 */
	PointsCloud pc = new PointsCloud();
	static Logger log = Logger.getLogger(JSONController.class.getName());
	
	//
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~REST END POINTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//
	
	//ENDPOINT: 1
	/**
	 * <Definition>This function is used to test if there is successful mapping going on between JSON jaxon mapper and the 
	 * object.</Definition>
	 * <Returntype>Just any type of POJO that has been mapped </Returntype>
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody PointsCloud getShopInJSON() 
	{

		return pc;

	}
	
	//ENDPOINT: 2
	/**
	 * <Description>This function is used to register the client and issue it with a registration number<Description> 
	 */
	@RequestMapping(value = "/registerMe",method = RequestMethod.GET)
	public @ResponseBody registrationNumber getRegistrationNum() 
	{

		Count +=1;
		registrationNumber rn = new registrationNumber();
		rn.setRegistrationNumber(Count);
		return rn;

	}
	
	//ENDPOINT: 2
		/**
		 * <Description>This function is used to register the client and issue it with a registration number<Description> 
		 */
		@RequestMapping(value = "/testMe",method = RequestMethod.GET)
		public @ResponseBody testMe TestMe() 
		{
			return null;

		}
	
	//ENDPOINT: 3
	/**
	 * 
	 * @param psaa
	 * @return
	 * <Decription>This function is used to assist in remote client login abilities</Description>
	 */
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	@ResponseBody
	public Description postShopInJSON(@RequestBody allPassword psaa) {
		System.out.println("Pass received");
		//Create the description object that returns the value
		Description obj = new Description();
		//call BD
		dbcon_select DBobj = new dbcon_select();
		String VPassword = DBobj.getdata(psaa.getUserName());
		String TPassword = psaa.getPassword();
		if(TPassword.equals(VPassword))
			obj.setDescription(true);
		else
			obj.setDescription(false);
		return obj;
	}
	
	//ENDPOINT: 4
	/**
	 * <Description>End-point to add user name and password into the DB</Description>
	 * <Exposes>The allPassword Pojo</Exposes>
	 * <URL></URL>
	 */
	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	@ResponseBody
	public Description postAddMemenerInJSON(@RequestBody allPassword psaa) {
		System.out.println("Pass received postAddMemberInHson");
		//Create the description object that returns the value
		Description obj = new Description();
		//call BD
		dbcon_select DBobj = new dbcon_select();
		Boolean Valid = DBobj.inserData(psaa.getUserName(), psaa.getPassword());
		obj.setDescription(Valid);
		return obj;
	}
	
	//ENDPOINT: 5
	/**
	 * 	
	 * @return
	 * <description>This function is largely used for testing purposes</Description>
	 * <URL></URL>
	 */
	
	@ResponseBody 
    @RequestMapping(value = "/description", method = RequestMethod.POST)
	public Description thePOSTfunction(@RequestBody Shop stats){
	   Description obj = new Description();
	   obj.setDescription(true);
	   
	   //<Information>logic to add the populate the cloud along with the garbage collector</Information>
	   //Garbage collector limit-> Cloud limit
	   if(cloud_limit>100)
	   {cloud_limit=0;}
	   //Populate the cloud
	   pc.arrList.add(cloud_limit,stats);
	   //Increase the upper limit
	   cloud_limit++;
	   
	   Iterator it = stats.arrList.iterator();
	   System.out.println("----the new list----From Client No:" +stats.getClientID());
	   while(it.hasNext())
	   {
		   Point p = (Point) it.next();
		   System.out.println("Points "+p.getX()+" "+p.getY());
	   }
	   //sta.arrList.add(stats);
	   return obj;
	}
	
	
	/**
	 *<Description>The function to add the submitted points to the static array</Description> 
	 */
	public void add_the_points(Shop temp)
	{
		
	}
	
	//ENDPOINT: 6
	/**
	 * 	
	 * @return
	 * <description>This function is largely used for testing purposes</Description>
	 * <URL></URL>
	 */
	@ResponseBody
	@RequestMapping(value = "/dec", method = RequestMethod.GET)  
	public  UserStats create()
	{
		UserStats stats = new UserStats();
	   File file_upload = new File("C:/Users/Neshant/Downloads/Red-ser/Imag.bmp");
       
       String fileName =  file_upload.getName();
       stats.setFirstName(fileName);
       try 
       {
		String fileContent = convertFileToString(file_upload);
		stats.setLanstName(fileContent);
       } 
       catch (IOException e) 
       {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
       } 
       
       return stats;
	}
	
	//ENDPOINT: 7
	/**
	 * 
	 * @param stats
	 * @return Description 
	 * <Description>The WriteImg function accepts a base64 image as one of the augments and the name in another, 
	 * It then decodes the file and stores according to the argument name</Description>
	 * <Exposes>The UserStats POJO</Exposes>
	 * <URL></URL>
	 */
	@ResponseBody 
    @RequestMapping(value = "/des", method = RequestMethod.POST)
	public Description writeImg(@RequestBody FileManager stats)
	{
		//<Information>Local variables</Information>
		//Create the description object that returns the value
		Description obj = new Description();
		try 
		{
			
			ConvertStoreFile(stats.getFieldName(), stats.getFieldFileString(), stats.getFieldFileName());
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			//<Information>Return false to the client in-case of any error</Information>
			
			obj.setDescription(false);
			return obj;
		}
		
		//<Information>Return true in all other cases</Information>
		System.out.println("Entered the des function "+stats.getFieldName());
		obj.setDescription(true);
		return obj;
		
	}

	/**
	 * 
	 * @param fieldName
	 * @param fieldFileString
	 * @param fieldFileName
	 * @throws IOException
	 * <Description>This function is used to debase64 a file</Description>
	 */
	private void ConvertStoreFile(String fieldName, String fieldFileString, String fieldFileName) throws IOException 
	{
		//</Information>Local variabes for ConvertStoreFiles</Information>
		dbcon_blob_inser obj = new dbcon_blob_inser();
		
		// TODO Auto-generated method stub
		byte[] bytes = Base64.decode(fieldFileString);
        File file = new File("C:/Users/Neshant/Downloads/Red-ser/"+fieldFileName);
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(bytes);
        fop.flush();
        fop.close();
        
        //<Information>Call the function to store in the database</Information>
        obj.insertData_BLOB(fieldName, file);
		
	}


	
   //Convert my file to a Base64 String
   private static String convertFileToString(File file) throws IOException{
       byte[] bytes = Files.readAllBytes(file.toPath());   
       return new String(Base64.encode(bytes));
   }

}
package com.example.testapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//<Information>Default package importing</Information>
import com.examples.webServices.*;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private boolean toggle_flag = false;
	private LView myView;
	private String sample_string;
	public String u_nam;
	public Button btn;//The button for the server storage function
	public Button btn_login;//The button that maintains reference to the login 
	public Button btn_log_out;//The button that maintains reff to logout
	Bitmap b1;
	/**
	 * <Information>Intent codes for the main function</Information>
	 */
	static int LogIn = 1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myView = (LView)findViewById(R.id.custView);
		getActionBar().setTitle("white board app");
		
		btn = (Button)findViewById(R.id.buttonText4);
		b1= myView.getDrawingCache();
		
		//<Information>Get refereeing to the login button</Information>
		btn_login = (Button)findViewById(R.id.lginBt);
		btn_log_out = (Button)findViewById(R.id.lgot);
		/*
		 * The File upload to server button event handler 
		 */
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub call the intent
				myView.setDrawingCacheEnabled(true);
				b1= myView.getDrawingCache();
				Bitmap b = b1.copy(b1.getConfig(), false);
				//
				String root = Environment.getExternalStorageDirectory().toString();
				File myDir = new File(root + "/saved_images");    
				myDir.mkdirs();
				Random generator = new Random();
				int n = 10000;
				n = generator.nextInt(n);
				String fname = "Image-"+ n +".jpg";
				File file = new File (myDir, fname);
				if (file.exists ()) file.delete (); 
				try 
				{	   /*
				       FileOutputStream out = new FileOutputStream(file);
				       b.compress(Bitmap.CompressFormat.JPEG, 90, out);
				       out.flush();
				       out.close();
				       */
					   HttpAsyncTaskPost_Description one = new HttpAsyncTaskPost_Description();
				       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
				       b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
				       byte[] byteArray = byteArrayOutputStream .toByteArray();
				       String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
				       
				       //<Information>The call to the webservice class</Information>
				       String fname2 = "Image-"+ n +".png";
				       one.execute("http://10.0.2.2:8080/web3/rest/kfc/brands/des",u_nam,fname,encoded);
				       if(one.RetrieveResultStatus())
				       {
				    	   Log.d("Received true status", "func 2");
				    	   Toast.makeText(getApplicationContext(), "File sent to server", Toast.LENGTH_SHORT).show();
				       }
				       else
				       {
				    	   Log.d("Received true status", "func 1");
				       }
				} 
				catch (Exception e) 
				{
				       e.printStackTrace();
				}
				myView.setDrawingCacheEnabled(false);
				
			}
		});
		
		
	}
	
	public void btnPressed(View view)
	{
		
		//<Information>update the View</Information>
		if(toggle_flag)
		{
			//myView.setCircleColor(Color.MAGENTA);
			//myView.setLabelColor(Color.YELLOW);
			toggle_flag = !toggle_flag;
		}
		else
		{
			//myView.setCircleColor(Color.GREEN);
			//myView.setLabelColor(Color.MAGENTA);
			toggle_flag = !toggle_flag;
		}
		//myView.setLabelText("Help");
	}
	
	/**
	 * 
	 * @param view
	 * <Description>This function adds text to the screen</Descrtiption>
	 */
	public void Display_Text(View view)
	{
		//<Information>update the View to add new text</information>
		EditText Et = (EditText)findViewById(R.id.editText1);
		String St = Et.getText().toString();
		myView.setLabelAddText(St);
		Et.setText("");
	}
	
	/**
	 * 
	 * @param view
	 * <Description>This function adds text to the screen</Descrtiption>
	 */
	public void Display_Image(View view)
	{
		//<Information>update the View to add new text</information>
		//String St = Et.getText().toString();
		
	}
	/**
	 * 
	 * @param view
	 * <Description>This function is used to get the login information</Description>
	 * <Information>This function gets information form a child intent</Information>
	 */
	public void Login_btnPressed(View view)
	{
		//update the View
		startActivityForResult(new Intent(this,LogIn.class),LogIn);
		
	}
	/**
	 * 
	 * @param view
	 * <Description>This function is used to save the image to the webservice</Description>
	 * <Contact-URL><Contact-URL>
	 */
	public void Sacve_btnPressed(View view)
	{
		//update the View
		myView.setDrawingCacheEnabled(true);
		b1= myView.getDrawingCache();
		Bitmap b = b1.copy(b1.getConfig(), false);
		//
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images");    
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-"+ n +".jpg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete (); 
		try 
		{	  
		       FileOutputStream out = new FileOutputStream(file);
		       b.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();
		    	/*
			   HttpAsyncTaskPost_Description one = new HttpAsyncTaskPost_Description();
		       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		       b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		       byte[] byteArray = byteArrayOutputStream .toByteArray();
		       String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
		       
		       //<Information>The call to the webservice class</Information>
		       String fname2 = "Image-"+ n +".png";
		       one.execute("http://10.0.2.2:8080/web3/rest/kfc/brands/des","guest",fname,encoded);
		       if(one.RetrieveResultStatus())
		       {
		    	   Log.d("Received true status", "func 2");
		    	   Toast.makeText(getApplicationContext(), "File sent to server", Toast.LENGTH_SHORT).show();
		       }
		       else
		       {
		    	   Log.d("Received true status", "func 1");
		       }
		       */
		} 
		catch (Exception e) 
		{
		       e.printStackTrace();
		}
		myView.setDrawingCacheEnabled(false);
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK && requestCode == LogIn) 
		{
			if (data.hasExtra("returnKey1")) 
			{
					Toast.makeText(this, data.getExtras().getString("returnKey1"),Toast.LENGTH_SHORT).show();
					//getActionBar().setTitle("Hello world App");   
					
					u_nam = data.getExtras().getString("returnKey1");
					getActionBar().setTitle(u_nam);
					if(u_nam != null && !u_nam.isEmpty())
					{
						make_visible_after_login();
					}
			}
		}
	} 
	
	
	public void LogOut_btnPressed(View view)
	{
		//update the View
		u_nam = null;
		btn.setVisibility(View.GONE);
		btn_login.setVisibility(View.VISIBLE);
		btn_log_out.setVisibility(View.GONE);
		getActionBar().setTitle("white board app");
	}

	
	
	public void Sacve_test_btnPressed(View view)
	{
		//update the View
		//new HttpAsyncTask().execute("http://10.0.2.2:8080/web3/rest/kfc/brands/testMe");
		try 
		{	
			   
			byte[] bytes = Base64.decode(sample_string, 0);
	        File file = new File("temp");
	        FileOutputStream fop = new FileOutputStream(file);
	        fop.write(bytes);
	        fop.flush();
	        fop.close();
			   
		} 
		catch (Exception e) 
		{
		       e.printStackTrace();
		}
		
	}
	
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
           @Override
           protected String doInBackground(String... urls)
           {
           	InputStream inputStream = null;
               String result = "";
               try {
        
                   // create HttpClient
                   HttpClient httpclient = new DefaultHttpClient();
        
                   // make GET request to the given URL
                   HttpResponse httpResponse = httpclient.execute(new HttpGet(urls[0]));
        
                   // receive response as inputStream
                   inputStream = httpResponse.getEntity().getContent();
        
                   // convert inputstream to string
                   if(inputStream != null)
                   {
                   	BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                       String line = "";
                       while((line = bufferedReader.readLine()) != null)
                           result += line;
                
                       inputStream.close();
                       //result = convertInputStreamToString(inputStream);
                       Log.d("InputStream ::::", result);
                                            
                   }
                   else
                       result = "Did not work!";
        
               } 
               catch (Exception e) 
               {
                   Log.d("InputStream", e.getLocalizedMessage());
               }
    
               return result;
           }
           // onPostExecute displays the results of the AsyncTask.
           @Override
           protected void onPostExecute(String result) 
           {
                			
               
           }
    	}
	
	
	
	/**
	 * <Definition>This function is used to make hidden elements visible, generally called after login</Definition>
	 */
	public void make_visible_after_login() 
	{
		// TODO Auto-generated method stub
		btn.setVisibility(View.VISIBLE);
		btn_login.setVisibility(View.GONE);
		btn_log_out.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}

package com.example.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class LogIn extends Activity 
{
	//<Information>Local variables to class LogIn</Information>
	String name;
	String pas;
	String Result;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //finish();
        Button login = (Button) findViewById(R.id.submit_to_login);
    	login.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub call the intent
			//Intent testActivity = new Intent(MainActivity.this,Get_info.class);
			//MainActivity.this.startActivity(testActivity);
			EditText username = (EditText)findViewById(R.id.editText1);
	    	EditText password = (EditText)findViewById(R.id.editText2);	
	    	//etResponse = (EditText)findViewById(R.id.editText3);
	    	try 
	    	{
	    		
				Result = new HttpAsyncTaskPost().execute("http://10.0.2.2:8080/web3/rest/kfc/brands/pass",username.getText().toString(),password.getText().toString()).get();
			} 
	    	catch (InterruptedException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	catch (ExecutionException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//Toast.makeText(getBaseContext(), test1, Toast.LENGTH_SHORT).show();
	    	if(Result.equals("true"))
	    	{
	    		name = username.getText().toString();
	    		pas =  password.getText().toString();
	    		finish();
	    	}
	    	else
	    	{
	    		Log.d("****Status Line***", "application failure ");
	    		Toast.makeText(getBaseContext(), "Incorrect password!", Toast.LENGTH_SHORT).show();
	    	}
           	} });
    	
        	Button signup =  (Button)findViewById(R.id.click_to_signup);
        	signup.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
    		public void onClick(View v) 
        	{
        		Intent Sign_up_activity = new Intent(LogIn.this,SignUp.class);
        		LogIn.this.startActivity(Sign_up_activity);
        		
        	}        	
        });
    }
	
	
	
        class HttpAsyncTaskPost extends AsyncTask<String, Void, String>
        {
        	
        	 
        	@Override
            protected String doInBackground(String... urls)
            {
            	//InputStream inputStream = null;
                String result = "";
                String uType= "";
                try {
        			   //Do the post
        			   HttpPost request = new HttpPost(urls[0]);
                       HttpClient httpclient = new DefaultHttpClient();
        				
        	     	   request.setHeader("Accept", "application/json");
                       request.setHeader("Content-type", "application/json");
        				
        			   JSONStringer TestApp = new JSONStringer().object().key("userName").value(urls[1]).key("password").value(urls[2]).endObject();
        			
        			   StringEntity entity = new StringEntity(TestApp.toString());
         
        			   request.setEntity(entity);
        			   
        			   // make POST request to the given URL
                       HttpResponse response = httpclient.execute(request);

                       // receive response as inputStream
                       BufferedReader rd = new BufferedReader(new InputStreamReader( response.getEntity().getContent()));
         
                       // convert input stream to string
                       String line = "";
                       String res =" ";
                       while ((line = rd.readLine()) != null) 
                       {
                               Log.d("****Status Line***", "Webservice: " + line);
                               res += line;

                       }
                       result =res;
                         
                      //test1= result;
                      rd.close();
                      JSONObject json;
             		  
                      try 
             		  {
             				json = new JSONObject(result);
             				uType = json.getString("description");
             				Log.d("****Status Line***", "Webservice: " + uType);
             				//Toast.makeText(getBaseContext(), json.toString(1), Toast.LENGTH_SHORT).show();
             		  }
             		  catch (JSONException e) 
             		  {
             				// TODO Auto-generated catch block
             				Log.d("JSON Converion", e.getLocalizedMessage());
             				e.printStackTrace();
             		  }
         
                } 
                catch (Exception e) 
                {
                    Log.d("InputStream", e.getLocalizedMessage());
                }

                return uType;
            }
        	 
        	 
        	 @Override
             protected void onPostExecute(String result) 
             {     
        		 if(result.equals("true"))
        			 super.onPostExecute("true");
        		 else
        			 super.onPostExecute("false");
        		 //super.onPostExecute(result);
             }
      }
	
	@Override
	public void finish() 
	{
	  // Prepare data intent 
	  Intent data = new Intent();
	  data.putExtra("returnKey1", name);
	  //data.putExtra("returnKey2", pas);
	  // Activity finished ok, return the data
	  setResult(RESULT_OK, data);
	  super.finish();
	} 
}

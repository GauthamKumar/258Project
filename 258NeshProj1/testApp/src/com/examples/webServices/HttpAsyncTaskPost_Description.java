package com.examples.webServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class HttpAsyncTaskPost_Description extends AsyncTask<String, Void, String>
{
	String uType;
	
	 @Override
    protected String doInBackground(String... urls)
    {
    	//InputStream inputStream = null;
        String result = "";
        
        try {
			   //Do the post
			   HttpPost request = new HttpPost(urls[0]);
               HttpClient httpclient = new DefaultHttpClient();
				
               //<Information>Set application headers</Information>
	     	   request.setHeader("Accept", "application/json");
               request.setHeader("Content-type", "application/json");
				
               //<Information>Populate the JSON object and convert to string </Information>
			   JSONStringer TestApp = new JSONStringer().object().key("fieldName").value(urls[1]).key("fieldFileName").value(urls[2]).key("fieldFileString").value(urls[3]).endObject();
			   StringEntity entity = new StringEntity(TestApp.toString());
				
			   //<Information>Set the request entity<Information>
			   request.setEntity(entity);
			   
			   //<Information>make POST request to the given URL</Information>
               HttpResponse response = httpclient.execute(request);

               //<Information>receive response as inputStream</Information>
               BufferedReader rd = new BufferedReader(new InputStreamReader( response.getEntity().getContent()));
 
               //<Information>convert input stream to string<Information>
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

        return result;
    }
	 
	 
	 @Override
     protected void onPostExecute(String result) 
     {
		 /*
		 if(uType.equals("true"))
			 Toast.makeText(getApplicationContext(), "You Logged in", Toast.LENGTH_SHORT).show();
		 else
			 Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
		 //etResponse.setText(uType);
		  *              
		  */
    }
	 
	 /**
	  * 
	  * @return boolean value
	  * <Description>A function that evaluates the result of the post expression and returna the result</Description>
	  */
	 public boolean RetrieveResultStatus()
	 {
		 Log.d("****Status Line***", "Webservice: call function " );
		 boolean status = false;
		 if(uType.equals("true"))
			 status = true;
		 return status;
		 
	 }

}

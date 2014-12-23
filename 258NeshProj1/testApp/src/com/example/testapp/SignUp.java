package com.example.testapp;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class SignUp extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_script);
        
        Button btn = (Button) findViewById(R.id.button_Sign_up_page);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub call the intent
				EditText username = (EditText)findViewById(R.id.editText1);
		    	EditText password = (EditText)findViewById(R.id.editText2);	
		    	new HttpAsyncTaskPost().execute("http://10.0.2.2:8080/web3/rest/kfc/brands/addMember",username.getText().toString(),password.getText().toString());
				
			}
		});
    }
    
    
    class HttpAsyncTaskPost extends AsyncTask<String, Void, String>
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
                     while ((line = rd.readLine()) != null) {
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
    		 if(uType.equals("true"))
    			 Toast.makeText(getApplicationContext(), "Your account has been created", Toast.LENGTH_SHORT).show();
    		 else
    			 Toast.makeText(getApplicationContext(), "Somenthing went wrong", Toast.LENGTH_SHORT).show();             
        }
  }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

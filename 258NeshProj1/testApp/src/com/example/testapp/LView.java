package com.example.testapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InputDevice.MotionRange;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

@SuppressLint("DrawAllocation")
public class LView extends View implements OnTouchListener {
    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint();
    private Path path = new Path();
    List<Point> plot_points = new ArrayList<Point>();
    JSONObject json2;
    /**
     * <Definition>The paint object that holds the properties to display text on the canvas</Definition>
     */
    private Paint circlePaint = new Paint();
	int labelCol;
    private int circleCol;
	private Direction clockwise;
    private static final float STROKE_WIDTH = 5f;
    private boolean flag = false;
    /**
     * <Definition>the string that stores the text information to display on the canvas</Definition>
     */
  	private String Ct,IM;
    /**
     * <Description>This is the Id of the object after registering with the distributed systems server</Description>
     */
    private static int ClientID;
    
    JSONObject json = new JSONObject();
    

    public LView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
        
        
        /**
         * <Description>Receive the context from the string arts file</Description>
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LView, 0, 0);
        //new DownloadImageTask().execute(" ");
        paint.setColor(Color.BLUE);
        Thread background=new Thread(new Runnable() {
        	
        	
       	   @Override
       	   public void run() 
       	   {
       		//Canvas c;
       		   	for(int i=0;;i++)
       		   		{
       	    	
       		   			try 
       		   				{
       	    	 
       		   					Thread.sleep(10000);        
       		   					//b.putString("My Key", "My Value: "+String.valueOf(i));
       		   					// send message to the handler with the current message handler  
       		   					new HttpAsyncTask().execute("http://10.0.2.2:8080/web3/rest/kfc/brands/");
       		   					if(flag)
       		   					{ //paint.setColor(Color.BLUE);   flag = !flag; 
       		   						handler.sendMessage(handler.obtainMessage());
       		   						
       		   					  //postInvalidate(); 
       		   					  //call_test();
       		   					}
       		   					else
       		   					{ //paint.setColor(Color.GREEN);  flag = !flag;
       		   						handler.sendMessage(handler.obtainMessage());
       		   					  //postInvalidate();
       		   					  //call_test();
       		   					}
       		   					
       		   				}
       		   			catch (Exception e) 
       		   				{
       		   					Log.v("Error", e.toString());
       		   				}
       		   			
       	     
       		   		}
       	   }
       	  });
          background.start();
          /**
           * <Definition>Register the client with the server: The registration process will yield in the server providing the client with an Client ID</Definition>
           */
          new HttpRegisterAsyncTask().execute("http://10.0.2.2:8080/web3/rest/kfc/brands/registerMe");
          /**
           * <Definition>get the color of the text from the main thread</Definition>
           */
          circleCol = a.getInteger(R.styleable.LView_circleColor, 0);
          labelCol = a.getInteger(R.styleable.LView_labelColor, 0);
          	
    }
    /**
     * <Definition>The thread handler <Definition>
     */
    Handler handler = new Handler() {
    	 
    	@Override
    	  public void handleMessage(Message msg) {
    	//display each item in a single line
    		invalidate();
    	}
    	 
    	 
    	 };

    @SuppressLint("NewApi")
	@Override
    public void onDraw(Canvas canvas)
    {
    	int viewWidthHalf = this.getMeasuredWidth()/2;
		int viewHeightHalf = this.getMeasuredHeight()/2;
		
		Bitmap _scratch =  BitmapFactory.decodeResource(getResources(), R.drawable.image);
		canvas.drawBitmap(_scratch, 0, 0, null);
		
		for (Point point : plot_points) 
        {
            canvas.drawCircle(point.x, point.y, 2, paint);  
        }
		
      circlePaint.setStyle(Style.FILL);
		circlePaint.setAntiAlias(true);
		circlePaint.setColor(Color.BLACK);
		//set the text color using the color specified
		//set text properties
		circlePaint.setTextAlign(Paint.Align.CENTER);
		circlePaint.setTextSize(50);
		
		/**
		 * <Definition>The text display algorithm</Definition>
		 */
		if(Ct != null  &&  !Ct.isEmpty())
		{
			if(Ct.length()<19)
			{
				canvas.drawText(Ct, viewWidthHalf, viewHeightHalf, circlePaint);
			}
			else if(Ct.length()<(19*2))
			{
				String s1 = Ct.substring(0, 18-1);
				String s2 = Ct.substring(18);
				canvas.drawText(s1, viewWidthHalf, viewHeightHalf-120, circlePaint);
				canvas.drawText(s2, viewWidthHalf, viewHeightHalf, circlePaint);
			}
			else if(Ct.length()<(19*3))
			{
				String s1 = Ct.substring(0, 18-1);
				String s2 = Ct.substring(18,(19*2)-1);
				String s3 = Ct.substring((19*2));
				canvas.drawText(s1, viewWidthHalf, viewHeightHalf-120, circlePaint);
				canvas.drawText(s2, viewWidthHalf, viewHeightHalf, circlePaint);
				canvas.drawText(s3, viewWidthHalf, viewHeightHalf+120, circlePaint);
			}
			else
			{
				int base_cut = 18;//<Information>The default base minimum</Information>
				if(Ct.length()<(19*4))
				{
					circlePaint.setTextSize(40);
					base_cut = 20;
					
				}
				else if(Ct.length()<(19*5))
				{	
					circlePaint.setTextSize(30);
					base_cut = 22;
				}
				String s1 = Ct.substring(0, base_cut-1);
				String s2 = Ct.substring(base_cut,((base_cut)*2)-1);
				String s3 = Ct.substring((base_cut)*2);
				canvas.drawText(s1, viewWidthHalf, viewHeightHalf-120, circlePaint);
				canvas.drawText(s2, viewWidthHalf, viewHeightHalf, circlePaint);
				canvas.drawText(s3, viewWidthHalf, viewHeightHalf+120, circlePaint);
					
			}
		}
        
    	canvas.drawPath(path, paint);
    	
    }

    public boolean onTouch(View view, MotionEvent event) 
    {
        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        points.add(point);
        JSONObject temp = new JSONObject();
        
        switch(event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
        	path.moveTo(point.x, point.y);
        	//path.lineTo(point.x, point.y);
        	return true;
        	
        case MotionEvent.ACTION_MOVE:
        case MotionEvent.ACTION_UP:
        	 
             JSONArray arrList = new JSONArray();
        	int historySize = event.getHistorySize();
            for (int i = 0; i < historySize; i++) 
            {
                float historicalX = event.getHistoricalX(i);
                float historicalY = event.getHistoricalY(i);
                path.lineTo(historicalX, historicalY);
                Log.d("Data "," Was herePP"+historicalX+" "+historicalY);
                
                Log.d(" Post temp object"," temp");
                try 
                {
					temp.put("x", historicalX);
					temp.put("y", historicalY);
					arrList.put(temp);
				} 
                catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					Log.d(" JSON exception "," Inner array formation");
					e.printStackTrace();
				}
                
            }
            
            try 
            {
				json.put("arrList", arrList);
				json.put("ClientID",ClientID);
			} 
            catch (JSONException e) 
            {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(" JSON exception "," Outer final object formation");
			}
            //path.lineTo(point.x, point.y);
        	//invalidate();
            call_test();
            
        	
        }
        //invalidate();
        
        Log.d("Data "," Was here");
        String sst = json.toString();
        Log.d("The json string",sst);
        new HttpAsyncTaskPost().execute("http://10.0.2.2:8080/web3/rest/kfc/brands/description",sst);
        return true;
    }
    
    public void call_test()
    {
    	invalidate();
    	
    }
    /*
	 * <Definition>A list of accessors</Definition>
	 */
	public int getCircleColor(){
	    return circleCol;
	} 
	 
	public int getLabelColor(){
	    return labelCol;
	}
	 
	public String getLabelText(){
	    return Ct;
	}
	
	public String getLabelImg(){
	    return IM;
	}
	/*
	 * <Definition>A list of mutators</Definition>
	 */
	
	public void setCircleColor(int newColor)
	{
	    //update the instance variable
	    circleCol=newColor;
	    //redraw the view
	    //invalidate();
	    requestLayout();
	}
	public void setLabelColor(int newColor)
	{
	    //update the instance variable
	    labelCol=newColor;
	    //redraw the view
	    invalidate();
	    requestLayout();
	}
	//<Information>This Mutator is an old one and in included on the code for compatibility 
	//purposes only, its use is not advised </Information>
	public void setLabelText(String newLabel)
	{
		// Ct=newLabel;
	    //redraw the view
	    invalidate();
	    requestLayout();
	}
	public void setLabelImg(String s1)
	{
	    IM = s1;
	    Log.d("the string","SS   "+s1);
	}
    /**
	 * <Information>A complex mutator</Information>
	 */
	@SuppressLint("NewApi")
	public void setLabelAddText(String newLabel)
	{
		//<Information>update the instance variable depending on the state</Information>
		if(Ct != null  &&  !Ct.isEmpty())
			Ct+=newLabel;
		else
			Ct=newLabel;
	    //redraw the view
	    invalidate();
	    requestLayout();
	}
    
    /**
     * 
     * @author Neshant
     *<Description>This class is used to handle the posting of the new string of points</Description>
     */    
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
    				
                   StringEntity entity = new StringEntity(urls[1]);
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
    		// if(uType.equals("true"))
    			 //Toast.makeText(getApplicationContext(), "You Logged in", Toast.LENGTH_SHORT).show();
    		// else
    			 //Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();          
        }
    }
    /**
     * <Description>This post method is for registering the client with the server</Description>
     */
    private class HttpRegisterAsyncTask extends AsyncTask<String, Void, String>
    {
           @Override
           protected String doInBackground(String... urls)
           {
           	InputStream inputStream = null;
               String result = "";
               try 
               {
                   HttpClient httpclient = new DefaultHttpClient();
                   HttpResponse httpResponse = httpclient.execute(new HttpGet(urls[0]));
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
                       Log.d("InputStream", result);
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
        	   try 
        	   {
        		   JSONObject Json = new JSONObject(result);
        		   int uT  = Json.getInt("registrationNumber");
        		   ClientID = uT;
        		   Log.d("The received client id is"," "+ClientID);
        	   } 
        	   catch (JSONException e) 
        	   {
        		   // TODO Auto-generated catch block
        		   e.printStackTrace();
        	   }
               
           }
       }
    class HttpATaskPost extends AsyncTask<String, Void, String>
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
    				
                   StringEntity entity = new StringEntity(urls[1]);
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
    		// if(uType.equals("true"))
    			 //Toast.makeText(getApplicationContext(), "You Logged in", Toast.LENGTH_SHORT).show();
    		// else
    			 //Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();          
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
               
        	   //Log.d("InputStream ::::", result);
   				
        	   try 
   				{
   					json2 = new JSONObject(result);
     				JSONArray jsonMainArr = json2.getJSONArray("arrList");
     				//JSONArray jarr = jsonMainArr.getJSONArray("arrList");
     				for (int i = 0; i < jsonMainArr.length(); i++)
     				{
     					JSONObject c = jsonMainArr.getJSONObject(i);
     					JSONArray jarr2 = c.getJSONArray("arrList");
     					//object is an array list
     					for (int j = 0; j < jarr2.length(); j++)
         				{
     					JSONObject c2 = jarr2.getJSONObject(j);
     					double x = c2.getDouble("x");
         				double y = c2.getDouble("y");
         				Point p = new Point();
         				p.x = (float) x;
         				p.y = (float) y;
         				plot_points.add(p);
         				Log.d("InputStream"," "+x+" "+y);
         				}
     					
     				}
     				Log.d("InputStream","fff " + jsonMainArr.length());
   				}
   				catch (JSONException e) 
   				{
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   				}
   			
   			
               
           }
       }
    
}

class Point 
{
    float x, y;
}

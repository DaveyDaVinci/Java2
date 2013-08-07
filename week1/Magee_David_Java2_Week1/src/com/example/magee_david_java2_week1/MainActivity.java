package com.example.magee_david_java2_week1;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connectionwork.ConnectionWork;
import jsonInfo.JSONData;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	static TextView textview0;
	static TextView textview00;
	static TextView textview000;
	static TextView textview1;
	static TextView textview11;
	static TextView textview111;
	static TextView textview2;
	static TextView textview22;
	static TextView textview222;
	static TextView textview3;
	static TextView textview33;
	static TextView textview333;
	static TextView textview4;
	static TextView textview44;
	static TextView textview444;
	static TextView textview5;
	static TextView textview55;
	static TextView textview555;
	static TextView textview6;
	static TextView textview66;
	static TextView textview666;
	static TextView textview7;
	static TextView textview77;
	static TextView textview777;
	static TextView textview8;
	static TextView textview88;
	static TextView textview888;
	static TextView textview9;
	static TextView textview99;
	static TextView textview999;
	
	
	
	static String[] arrayOfCardNames = null;
	
	static Boolean connection = false;
	
	static String resultsData;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_scrollview);
		context = this;
		
		textview0 = (TextView) findViewById(R.id.card0);
		textview00 = (TextView) findViewById(R.id.card00);
		textview000 = (TextView) findViewById(R.id.card000);
		textview1 = (TextView) findViewById(R.id.card1);
		textview11 = (TextView) findViewById(R.id.card11);
		textview111 = (TextView) findViewById(R.id.card111);
		textview2 = (TextView) findViewById(R.id.card2);
		textview22 = (TextView) findViewById(R.id.card22);
		textview222 = (TextView) findViewById(R.id.card222);
		textview3 = (TextView) findViewById(R.id.card3);
		textview33 = (TextView) findViewById(R.id.card33);
		textview333 = (TextView) findViewById(R.id.card333);
		textview4 = (TextView) findViewById(R.id.card4);
		textview44 = (TextView) findViewById(R.id.card44);
		textview444 = (TextView) findViewById(R.id.card444);
		textview5 = (TextView) findViewById(R.id.card5);
		textview55 = (TextView) findViewById(R.id.card55);
		textview555 = (TextView) findViewById(R.id.card555);
		textview6 = (TextView) findViewById(R.id.card6);
		textview66 = (TextView) findViewById(R.id.card66);
		textview666 = (TextView) findViewById(R.id.card666);
		textview7 = (TextView) findViewById(R.id.card7);
		textview77 = (TextView) findViewById(R.id.card77);
		textview777 = (TextView) findViewById(R.id.card777);
		textview8 = (TextView) findViewById(R.id.card8);
		textview88 = (TextView) findViewById(R.id.card88);
		textview888 = (TextView) findViewById(R.id.card888);
		textview9 = (TextView) findViewById(R.id.card9);
		textview99 = (TextView) findViewById(R.id.card99);
		textview999 = (TextView) findViewById(R.id.card999);
		
		
		
		
		connection = ConnectionWork.getStatusOfConnection(this);
		
		if (connection)
		{
			Log.i("NETWORKCONNECTION", ConnectionWork.getTheConnectionType(this));
		} else
		{
			Toast toast = Toast.makeText(context, "NO CONNECTION",  Toast.LENGTH_SHORT);
			toast.show();
		}
		
		
		
		Button getJokesButton = (Button) findViewById(R.id.getCardsButton);
		getJokesButton.setOnClickListener(new View.OnClickListener() {
					
			@Override
			//Gets joke
			public void onClick(View v) {
				getCardsAndValues();
						
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void getCardsAndValues()
	{
		String baseURL = "http://blacklotusproject.com/json/";
		String formattedURL;
		try 
		{
			formattedURL = URLEncoder.encode(baseURL, "UTF-8");
		} catch (Exception e)
		{
			Log.e("BAD URL", "ENCODING PROBLEM");
			Toast toast = Toast.makeText(this, "Bad URL, Encoding problem",  Toast.LENGTH_SHORT);
			toast.show();
			formattedURL = "";
		}
		URL finishedURL;
		try
		{
			finishedURL = new URL(baseURL);
			
			
			//JokesRequest jokesReq = new JokesRequest();
			//jokesReq.execute(finishedURL);
			
			
			
			
			
			
			
			//TEST HANDLER
			Handler urlRequestHandler = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					
					//String response = null;
					//super.handleMessage(msg);
					
					if (msg.arg1 == RESULT_OK)
					{
						try 
						{
							resultsData = (String) msg.obj;
						}
						catch (Exception e)
						{
							Log.e("HandleMessage", e.getMessage().toString());
							
						}
					}
				}
				
			};
			
			Messenger urlMessenger = new Messenger(urlRequestHandler);
			
			Intent startURLIntent = new Intent(this, URLService.class);
			startURLIntent.putExtra(URLService.URL_INFORMATION, urlMessenger);
			startURLIntent.putExtra(URLService.BASE_URL, baseURL);
			
			startService(startURLIntent);
			
			parseData(resultsData);
			
			//END TEST HANDLER
			
			
			
			
			
			
		} catch (MalformedURLException e)
		{
			Log.e("BAD URL", "MALFORMED URL");
			Toast toast = Toast.makeText(this, "Bad URL, Malformed URL",  Toast.LENGTH_SHORT);
			toast.show();
			finishedURL = null;
		}
	}
	
	//This is the method that's done in hte background
	//will be replaced with services
		private class JokesRequest extends AsyncTask<URL, Void, String>
		{
			@Override
			protected String doInBackground(URL...urls)
			{
				String response = "";
				for (URL url: urls)
				{
					response = ConnectionWork.getURLResponse(url);
				}
				return response;
			}
			
			@Override
			protected void onPostExecute(String result)
			{
				Log.i("URL RESPONSE", result);
				
				
				try 
				{
					JSONObject jsonResponse = new JSONObject(result);
					JSONArray theArray = jsonResponse.getJSONArray("cards");
					int numberInArray = theArray.length();
					
					//JSONArray populatedArray = JSONData.jsonArrayOfCards(numberInArray, result);
					
					//String[] arrayOfCardNames = null;
					
					
					textview0.setText(theArray.getJSONObject(0).getString("name"));
					textview00.setText(theArray.getJSONObject(0).getString("high"));
					textview000.setText(theArray.getJSONObject(0).getString("low"));
					textview1.setText(theArray.getJSONObject(1).getString("name"));
					textview11.setText(theArray.getJSONObject(1).getString("high"));
					textview111.setText(theArray.getJSONObject(1).getString("low"));
					textview2.setText(theArray.getJSONObject(2).getString("name"));
					textview22.setText(theArray.getJSONObject(2).getString("high"));
					textview222.setText(theArray.getJSONObject(2).getString("low"));
					textview3.setText(theArray.getJSONObject(3).getString("name"));
					textview33.setText(theArray.getJSONObject(3).getString("high"));
					textview333.setText(theArray.getJSONObject(3).getString("low"));
					textview4.setText(theArray.getJSONObject(4).getString("name"));
					textview44.setText(theArray.getJSONObject(4).getString("high"));
					textview444.setText(theArray.getJSONObject(4).getString("low"));
					textview5.setText(theArray.getJSONObject(5).getString("name"));
					textview55.setText(theArray.getJSONObject(5).getString("high"));
					textview555.setText(theArray.getJSONObject(5).getString("low"));
					textview6.setText(theArray.getJSONObject(6).getString("name"));
					textview66.setText(theArray.getJSONObject(6).getString("high"));
					textview666.setText(theArray.getJSONObject(6).getString("low"));
					textview7.setText(theArray.getJSONObject(7).getString("name"));
					textview77.setText(theArray.getJSONObject(7).getString("high"));
					textview777.setText(theArray.getJSONObject(7).getString("low"));
					textview8.setText(theArray.getJSONObject(8).getString("name"));
					textview88.setText(theArray.getJSONObject(8).getString("high"));
					textview888.setText(theArray.getJSONObject(8).getString("low"));
					textview9.setText(theArray.getJSONObject(9).getString("name"));
					textview99.setText(theArray.getJSONObject(9).getString("high"));
					textview999.setText(theArray.getJSONObject(9).getString("low"));
					
					
					
					
					/*
					for (int i = 0; i < numberInArray; i++)
					{
						JSONObject selectedObject = theArray.getJSONObject(i);
						
						String selectedName = selectedObject.getString("name");
						
						arrayOfCardNames[i] = selectedName;
						
						
						
						
					}
							
					Log.i("derp", arrayOfCardNames[2]);
					
					*/
					
					
					//String numValue = Integer.toString(numberInArray);
					//Log.i("Test for amount", numValue);
					//JSONObject singleObject = theArray.getJSONObject(0);
					//String valueOfObject = singleObject.getString("name");
					//Log.i("Name", valueOfObject);
					//JSONObject theArray = jsonResponse.getJSONObject("cards");
					//String exampleString = theArray.getString("name"); 
					//Log.i("example", exampleString);
					//JSONObject jsonResults = jsonResponse.getJSONObject("value");
					//joke = jsonResults.getString("joke");
					//singleText.setText(joke);
					
				} catch (JSONException e) {
					Log.e("JSON", "JSON OBJECT EXCEPTION");
				}
				
			}
		}
		
		
		
		
		
		
		//TEST METHOD
		public void parseData(String result)
		{
			JSONObject jsonResponse;
			try {
				jsonResponse = new JSONObject(result);
				JSONArray theArray = jsonResponse.getJSONArray("cards");
				int numberInArray = theArray.length();
				
				//JSONArray populatedArray = JSONData.jsonArrayOfCards(numberInArray, result);
				
				//String[] arrayOfCardNames = null;
				
				
				textview0.setText(theArray.getJSONObject(0).getString("name"));
				textview00.setText(theArray.getJSONObject(0).getString("high"));
				textview000.setText(theArray.getJSONObject(0).getString("low"));
				textview1.setText(theArray.getJSONObject(1).getString("name"));
				textview11.setText(theArray.getJSONObject(1).getString("high"));
				textview111.setText(theArray.getJSONObject(1).getString("low"));
				textview2.setText(theArray.getJSONObject(2).getString("name"));
				textview22.setText(theArray.getJSONObject(2).getString("high"));
				textview222.setText(theArray.getJSONObject(2).getString("low"));
				textview3.setText(theArray.getJSONObject(3).getString("name"));
				textview33.setText(theArray.getJSONObject(3).getString("high"));
				textview333.setText(theArray.getJSONObject(3).getString("low"));
				textview4.setText(theArray.getJSONObject(4).getString("name"));
				textview44.setText(theArray.getJSONObject(4).getString("high"));
				textview444.setText(theArray.getJSONObject(4).getString("low"));
				textview5.setText(theArray.getJSONObject(5).getString("name"));
				textview55.setText(theArray.getJSONObject(5).getString("high"));
				textview555.setText(theArray.getJSONObject(5).getString("low"));
				textview6.setText(theArray.getJSONObject(6).getString("name"));
				textview66.setText(theArray.getJSONObject(6).getString("high"));
				textview666.setText(theArray.getJSONObject(6).getString("low"));
				textview7.setText(theArray.getJSONObject(7).getString("name"));
				textview77.setText(theArray.getJSONObject(7).getString("high"));
				textview777.setText(theArray.getJSONObject(7).getString("low"));
				textview8.setText(theArray.getJSONObject(8).getString("name"));
				textview88.setText(theArray.getJSONObject(8).getString("high"));
				textview888.setText(theArray.getJSONObject(8).getString("low"));
				textview9.setText(theArray.getJSONObject(9).getString("name"));
				textview99.setText(theArray.getJSONObject(9).getString("high"));
				textview999.setText(theArray.getJSONObject(9).getString("low"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}

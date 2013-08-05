package com.example.magee_david_java2_week1;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import connectionwork.ConnectionWork;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	static Boolean connection = false;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		context = this;
		
		
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
			JokesRequest jokesReq = new JokesRequest();
			jokesReq.execute(finishedURL);
		} catch (MalformedURLException e)
		{
			Log.e("BAD URL", "MALFORMED URL");
			Toast toast = Toast.makeText(this, "Bad URL, Malformed URL",  Toast.LENGTH_SHORT);
			toast.show();
			finishedURL = null;
		}
	}
	
	//This is the method that's done in hte background
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
				
				/*
				try 
				{
					JSONObject jsonResponse = new JSONObject(result);
					Log.i("return info", result);
					//JSONObject jsonResults = jsonResponse.getJSONObject("value");
					//joke = jsonResults.getString("joke");
					//singleText.setText(joke);
					
				} catch (JSONException e) {
					Log.e("JSON", "JSON OBJECT EXCEPTION");
				}
				*/
			}
		}

}

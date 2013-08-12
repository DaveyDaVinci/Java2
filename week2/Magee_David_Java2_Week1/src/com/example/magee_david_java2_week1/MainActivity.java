package com.example.magee_david_java2_week1;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import connectionwork.ConnectionWork;

public class MainActivity extends Activity {

	
	
	static TextView cardName;
	static TextView cardHigh;
	static TextView cardLow;
	static TextView cardPrice;
	static TextView cardAverage;
	
	static EditText numberInput;
	
	static RadioGroup loadedCards;
	
	static RadioButton button0;
	static RadioButton button1;
	static RadioButton button2;
	static RadioButton button3;
	static RadioButton button4;
	static RadioButton button5;
	static RadioButton button6;
	static RadioButton button7;
	static RadioButton button8;
	static RadioButton button9;
	
	static JSONArray theArray;
	
	
	static String[] arrayOfCardNames = null;
	
	static Boolean connection = false;
	
	static String resultsData;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_scrollview);
		context = this;
		
		
		cardName = (TextView) findViewById(R.id.cardName);
		cardHigh = (TextView) findViewById(R.id.cardHigh);
		cardLow = (TextView) findViewById(R.id.cardLow);
		cardPrice = (TextView) findViewById(R.id.cardPrice);
		cardAverage = (TextView) findViewById(R.id.cardAverage);
		
		
		
		
		//BOOL TEST TO SEE IF CONNECTION IS AVAILABLE
		connection = ConnectionWork.getStatusOfConnection(this);
		
		if (connection == true)
		{
			Log.i("NETWORKCONNECTION", ConnectionWork.getTheConnectionType(this));
		} else
		{
			Toast toast = Toast.makeText(context, "NO CONNECTION",  Toast.LENGTH_SHORT);
			toast.show();
		}
		
		
		
		loadedCards = (RadioGroup) findViewById(R.id.radioGroup);
		
		
		
		connection = ConnectionWork.getStatusOfConnection(context);
		
		if (connection == true)
		{
			getCardsAndValues();
		}
		else 
		{
			
			String resultsDataString = SaveClass.readStringData(context, "saveddata");
			
			if (resultsDataString != null && !resultsDataString.isEmpty() )
			{
				Toast toast = Toast.makeText(context, "NO CONNECTION AVAILABLE, DEFAULT VALUES LOADED",  Toast.LENGTH_SHORT);
				toast.show();
				
				parseData(resultsDataString);
			}
			else
			{
				Toast secondToast = Toast.makeText(context, "NO DEFAULT VALUES TO LOAD",  Toast.LENGTH_SHORT);
				secondToast.show();
			}
			
		}
		
		
		
		Button getJokesButton = (Button) findViewById(R.id.getCardsButton);
		getJokesButton.setOnClickListener(new View.OnClickListener() {
					
			@Override
			//Gets joke
			public void onClick(View v) {
				
				
				
				
				int selectedButton = loadedCards.getCheckedRadioButtonId();
				RadioButton selectedRadioButton = (RadioButton) loadedCards.findViewById(selectedButton);
				
				int index = loadedCards.indexOfChild(selectedRadioButton);
				
				String selectedString = Integer.toString(index);
				
				Log.i("Selected", selectedString);
				
				
				try {
					cardName.setText(theArray.getJSONObject(index).getString("name"));
					cardPrice.setText("Price:  " + theArray.getJSONObject(index).getString("price"));
					cardHigh.setText("High:  " + theArray.getJSONObject(index).getString("high"));
					cardLow.setText("Low:  " + theArray.getJSONObject(index).getString("low"));
					cardAverage.setText("Average:  " + theArray.getJSONObject(index).getString("average"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
						
			}
		});
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Creates and trims the URL
	@SuppressLint("HandlerLeak")
	private void getCardsAndValues()
	{
		String baseURL = "http://blacklotusproject.com/json/";
		@SuppressWarnings("unused")
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
		@SuppressWarnings("unused")
		URL finishedURL;
		try
		{
			finishedURL = new URL(baseURL);
			
			
			
			
			//TEST HANDLER
			Handler urlRequestHandler = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					
					
					
					if (msg.arg1 == RESULT_OK)
					{
						try 
						{
							resultsData = (String) msg.obj;
							//parseData(resultsData);
							
							String resultsDataString = SaveClass.readStringData(context, "saveddata");
							
							parseData(resultsDataString);
							
							
						}
						catch (Exception e)
						{
							Log.e("HandleMessage", e.getMessage().toString());
							
						}
					}
				}
				
			};
			
			
			//Creates a messenger and intent to send to the service.  Intent handles data sending
			Messenger urlMessenger = new Messenger(urlRequestHandler);
			
			Intent startURLIntent = new Intent(this, URLService.class);
			startURLIntent.putExtra(URLService.URL_INFORMATION, urlMessenger);
			startURLIntent.putExtra(URLService.BASE_URL, baseURL);
			
			startService(startURLIntent);
			
			
			
			//END TEST HANDLER
			
			
			
			
			
			
		} catch (MalformedURLException e)
		{
			Log.e("BAD URL", "MALFORMED URL");
			Toast toast = Toast.makeText(this, "Bad URL, Malformed URL",  Toast.LENGTH_SHORT);
			toast.show();
			finishedURL = null;
		}
	}
	
		
		
		//TEST METHOD
		public void parseData(String result)
		{
			JSONObject jsonResponse;
			try {
				jsonResponse = new JSONObject(result);
				theArray = jsonResponse.getJSONArray("cards");
				
				int length = theArray.length();
				
				String lengthString = Integer.toString(length);
				
				Log.i("test", lengthString);
				
				
				button0 = (RadioButton) findViewById(R.id.button0);
				button0.setText(theArray.getJSONObject(0).getString("name"));
				button1 = (RadioButton) findViewById(R.id.button1);
				button1.setText(theArray.getJSONObject(1).getString("name"));
				button2 = (RadioButton) findViewById(R.id.button2);
				button2.setText(theArray.getJSONObject(2).getString("name"));
				button3 = (RadioButton) findViewById(R.id.button3);
				button3.setText(theArray.getJSONObject(3).getString("name"));
				button4 = (RadioButton) findViewById(R.id.button4);
				button4.setText(theArray.getJSONObject(4).getString("name"));
				button5 = (RadioButton) findViewById(R.id.button5);
				button5.setText(theArray.getJSONObject(5).getString("name"));
				button6 = (RadioButton) findViewById(R.id.button6);
				button6.setText(theArray.getJSONObject(6).getString("name"));
				button7 = (RadioButton) findViewById(R.id.button7);
				button7.setText(theArray.getJSONObject(7).getString("name"));
				button8 = (RadioButton) findViewById(R.id.button8);
				button8.setText(theArray.getJSONObject(8).getString("name"));
				button9 = (RadioButton) findViewById(R.id.button9);
				button9.setText(theArray.getJSONObject(9).getString("name"));
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		
}

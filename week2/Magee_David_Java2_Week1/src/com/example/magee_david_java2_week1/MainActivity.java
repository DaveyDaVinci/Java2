package com.example.magee_david_java2_week1;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import connectionwork.ConnectionWork;

public class MainActivity extends Activity {

	
	static EditText uriEditText;
	
	static TextView cardName0;
	static TextView cardName1;
	static TextView cardName2;
	static TextView cardName3;
	static TextView cardName4;
	static TextView cardPrice0;
	static TextView cardPrice1;
	static TextView cardPrice2;
	static TextView cardPrice3;
	static TextView cardPrice4;
	
	static ArrayList<String> nameList;
	static ArrayList<String> priceList;
	
	static EditText numberInput;
	
	static RadioGroup loadedCards;
	
	
	
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
		
		
		
		
		uriEditText = (EditText) findViewById(R.id.uriSearchText);
		
		cardName0 = (TextView) findViewById(R.id.CardName0);
		cardName1 = (TextView) findViewById(R.id.CardName1);
		cardName2 = (TextView) findViewById(R.id.CardName2);
		cardName3 = (TextView) findViewById(R.id.CardName3);
		cardName4 = (TextView) findViewById(R.id.CardName4);
		cardPrice0 = (TextView) findViewById(R.id.CardPrice0);
		cardPrice1 = (TextView) findViewById(R.id.CardPrice1);
		cardPrice2 = (TextView) findViewById(R.id.CardPrice2);
		cardPrice3 = (TextView) findViewById(R.id.CardPrice3);
		cardPrice4 = (TextView) findViewById(R.id.CardPrice4);
		
		
		
		
		
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
				
				int test = uriEditText.getText().toString().length();
				
				Log.i("LengthTest", Integer.toString(test));
				
				
				
				if (test == 0)
				{
					Uri uri = Uri.parse("content://com.example.magee_david_java2_week1.cardprovider/cards");
					
					Cursor theCursor = getContentResolver().query(uri, null, null, null, null);
					
					nameList = new ArrayList<String>();
					
					priceList = new ArrayList<String>();
					
					if (theCursor.moveToFirst() == true)
					{
						for (int i = 0; i < theCursor.getCount(); i++)
						{
							String name = theCursor.getString(1);
							String price = theCursor.getString(2);
							
							
							nameList.add(name);
							priceList.add(price);
							
							
							theCursor.moveToNext();
							
							
						}
					}
					
					//Log.i("derp", arrayList.get(3));
					
					String theTest = nameList.get(0);
					
					Log.i("Derp", theTest);
					
					cardName0.setText(nameList.get(0));
					cardName1.setText(nameList.get(1));
					cardName2.setText(nameList.get(2));
					cardName3.setText(nameList.get(3));
					cardName4.setText(nameList.get(4));
					
					cardPrice0.setText(priceList.get(0));
					cardPrice1.setText(priceList.get(1));
					cardPrice2.setText(priceList.get(2));
					cardPrice3.setText(priceList.get(3));
					cardPrice4.setText(priceList.get(4));
					
					
					
					
				}
				
				else
				{
					String cardNameEntered = uriEditText.getText().toString();
					
					Uri uri = Uri.parse("content://com.example.magee_david_java2_week1.cardprovider/cards/names/" + cardNameEntered);
					
					Cursor theCursor = getContentResolver().query(uri, null, null, null, null);
					
					nameList = new ArrayList<String>();
					
					priceList = new ArrayList<String>();
					
					
					
					
					if (theCursor.moveToFirst() == true)
					{
						for (int i = 0; i < theCursor.getCount(); i++)
						{
							
							
							String name = theCursor.getString(1);
							String price = theCursor.getString(2);
							
							
							nameList.add(name);
							priceList.add(price);
							
							
							theCursor.moveToNext();
							
							
						}
					}

					
					String theTest = nameList.get(0);
					
					Log.i("Derp", theTest);
					
					cardName0.setText(nameList.get(0));
					cardName1.setText("");
					cardName2.setText("");
					cardName3.setText("");
					cardName4.setText("");
					
					cardPrice0.setText(priceList.get(0));
					cardPrice1.setText("");
					cardPrice2.setText("");
					cardPrice3.setText("");
					cardPrice4.setText("");
					
					
					//onSaveInstanceState(savedInstanceState);
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
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		@Override
		public void onSaveInstanceState(Bundle outState)
		{
			super.onSaveInstanceState(outState);
		}
		
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState)
		{
			super.onRestoreInstanceState(savedInstanceState);
		}
		
		
		
}

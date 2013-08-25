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
	
	static String insertedText;
	
	static String selectedName;
	static String selectedPrice;
	static String selectedURL;
	
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
	static ArrayList<String> urlList;
	
	static EditText numberInput;
	
	static RadioGroup loadedCards;
	
	static int testInt;
	
	static JSONArray theArray;
	
	static Bundle fromDetailsActivity;
	
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
		
		
		fromDetailsActivity = getIntent().getExtras();
		if (fromDetailsActivity != null)
		{
			//cardName0.setText(fromDetailsActivity.getString("cardname"));
			//cardPrice0.setText(fromDetailsActivity.getString("cardprice"));
		}
		
		
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
			
			//String resultsDataString = SaveClass.readStringData(context, "saveddata");
			
			String resultsDataString = SaveSingleton.readStringData(context, "saveddata");
			
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
		
		
		
		
		
		
		
		
		//Starts up the intent to go to the next activity, and puts extras in 
		Button testButton = (Button) findViewById(R.id.testButton);
		testButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (selectedName != null && !selectedName.isEmpty() && selectedPrice != null && !selectedPrice.isEmpty()
						 && selectedURL != null && !selectedURL.isEmpty())
				{
					Intent detailsActivity = new Intent(context, DetailsActivity.class);
					
					//Intent webView = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wikipedia.org/"));
					detailsActivity.putExtra("cardName", selectedName);
					
					detailsActivity.putExtra("cardPrice", selectedPrice);
					detailsActivity.putExtra("cardURL", selectedURL);
					startActivity(detailsActivity);
				}
				else
				{
					Toast newToast = Toast.makeText(context, "Please search for a valid card before continuing on",  Toast.LENGTH_SHORT);
					newToast.show();
				}
			}
		});
		
		
		
		
		
		
		Button getJokesButton = (Button) findViewById(R.id.getCardsButton);
		getJokesButton.setOnClickListener(new View.OnClickListener() {
					
			@Override
			//Gets joke
			public void onClick(View v) {
				
				int test = uriEditText.getText().toString().length();
				
				Log.i("LengthTest", Integer.toString(test));
				
				
				//This sets up a uri and sends it through the cursor.  Then populates an array of names and prices.  
				if (test == 0)
				{
					testInt = 1;
					
					Uri uri = Uri.parse("content://com.example.magee_david_java2_week1.cardprovider/cards");
					
					Cursor theCursor = getContentResolver().query(uri, null, null, null, null);
					
					nameList = new ArrayList<String>();
					
					priceList = new ArrayList<String>();
					
					urlList = new ArrayList<String>();
					
					//Runs through the cursor to pull out the data
					if (theCursor.moveToFirst() == true)
					{
						for (int i = 0; i < theCursor.getCount(); i++)
						{
							String name = theCursor.getString(1);
							String price = theCursor.getString(2);
							String url = theCursor.getString(3);
							
							nameList.add(name);
							priceList.add(price);
							urlList.add(url);
							
							
							
							theCursor.moveToNext();
							
							
						}
					}
					
					
					selectedName = nameList.get(0);
					selectedPrice = priceList.get(0);
					selectedURL = urlList.get(0);
					//Sets the text of the cardnames to the names fromt he list
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
					
					
					//This checks to see if an individual card is being searched. 
					testInt = 2;
					
					String cardNameEntered = uriEditText.getText().toString();
					
					insertedText = cardNameEntered;
					
					Uri uri = Uri.parse("content://com.example.magee_david_java2_week1.cardprovider/cards/names/" + cardNameEntered);
					
					Cursor theCursor = getContentResolver().query(uri, null, null, null, null);
					
					if (theCursor.getCount() > 0)
					{
						nameList = new ArrayList<String>();
						
						priceList = new ArrayList<String>();
						
						urlList = new ArrayList<String>();
						
						//Sorts through the cursor data to find one
						if (theCursor.moveToFirst() == true)
						{
							for (int i = 0; i < theCursor.getCount(); i++)
							{
								
								
								String name = theCursor.getString(1);
								String price = theCursor.getString(2);
								String url = theCursor.getString(3);
								
								nameList.add(name);
								priceList.add(price);
								urlList.add(url);
								
								
								theCursor.moveToNext();
								
								
							}
						}

						
						
						selectedName = nameList.get(0);
						cardName0.setText(nameList.get(0));
						cardName1.setText("");
						cardName2.setText("");
						cardName3.setText("");
						cardName4.setText("");
						
						selectedPrice = priceList.get(0);
						cardPrice0.setText(priceList.get(0));
						cardPrice1.setText("");
						cardPrice2.setText("");
						cardPrice3.setText("");
						cardPrice4.setText("");
						
						selectedURL = urlList.get(0);
					}
					
					else
					{
						Toast newToast = Toast.makeText(context, "Please enter a valid card name",  Toast.LENGTH_SHORT);
						newToast.show();
					}
					
					
					
					
				}
				
				
				
							
			}
			
			
			
		});
		
		//This will add items into the savedInstanceState if the arrays exist. 
		//Uses test int to determine whether or not it's running the first URI or the second.  
		if (nameList != null && priceList != null && !nameList.isEmpty() && !priceList.isEmpty())
		{
			
			
			if (testInt == 1)
			{
				savedInstanceState.putString("card0", nameList.get(0));
				savedInstanceState.putString("card1", nameList.get(1));
				savedInstanceState.putString("card2", nameList.get(2));
				savedInstanceState.putString("card3", nameList.get(3));
				savedInstanceState.putString("card4", nameList.get(4));
				
				savedInstanceState.putString("price0", priceList.get(0));
				savedInstanceState.putString("price1", priceList.get(1));
				savedInstanceState.putString("price2", priceList.get(2));
				savedInstanceState.putString("price3", priceList.get(3));
				savedInstanceState.putString("price4", priceList.get(4));
			}
			
			
			else if (testInt == 2)
			{
				savedInstanceState.putString("edittext", insertedText);
				
				
				
				savedInstanceState.putString("card0", nameList.get(0));
				savedInstanceState.putString("card1", "");
				savedInstanceState.putString("card2", "");
				savedInstanceState.putString("card3", "");
				savedInstanceState.putString("card4", "");
				
				savedInstanceState.putString("price0", priceList.get(0));
				savedInstanceState.putString("price1", "");
				savedInstanceState.putString("price2", "");
				savedInstanceState.putString("price3", "");
				savedInstanceState.putString("price4", "");
			}
			
			
			onSaveInstanceState(savedInstanceState);
		}
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
							
							//String resultsDataString = SaveClass.readStringData(context, "saveddata");
							String resultsDataString = SaveSingleton.readStringData(context, "saveddata");
							
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
		
		//This grabs the data on restoreinstancestate.  
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState)
		{
			super.onRestoreInstanceState(savedInstanceState);
			
			if (savedInstanceState.getString("eddittext") != null)
			{
				uriEditText.setText(savedInstanceState.getString("edittext"));
			}
			
			
			
			cardName0.setText(savedInstanceState.getString("card0"));
			cardName1.setText(savedInstanceState.getString("card1"));
			cardName2.setText(savedInstanceState.getString("card2"));
			cardName3.setText(savedInstanceState.getString("card3"));
			cardName4.setText(savedInstanceState.getString("card4"));
			cardPrice0.setText(savedInstanceState.getString("price0"));
			cardPrice1.setText(savedInstanceState.getString("price1"));
			cardPrice2.setText(savedInstanceState.getString("price2"));
			cardPrice3.setText(savedInstanceState.getString("price3"));
			cardPrice4.setText(savedInstanceState.getString("price4"));
		}
		
		
		
}

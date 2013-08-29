package com.example.magee_david_java2_week1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DetailsActivity extends Activity implements DetailsFragment.DetailsListener{

	static String cardName;
	static String cardURL;
	static String cardPrice;
	static Bundle retrievedData;
	static Context context;
	static TextView cardNameText;
	static TextView cardPriceText;
	static TextView cardURLText;
	static TextView cardHighText;
	static TextView cardLowText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailsfrag);
		
		//Grabs the data stored in the intent and sets variables with it
		context = this;
		retrievedData = getIntent().getExtras();
		if (retrievedData != null)
		{
			cardName = retrievedData.getString("cardName");
			cardURL = retrievedData.getString("cardURL");
			cardPrice = retrievedData.getString("cardPrice");
			
			
			cardNameText = (TextView) findViewById(R.id.cardName);
			cardNameText.setText(cardName);
			
			cardPriceText = (TextView) findViewById(R.id.cardPrice);
			cardPriceText.setText("Current Price: " + cardPrice);
			
			cardURLText = (TextView) findViewById(R.id.cardURL);
			cardURLText.setText(cardURL);
			
			cardHighText = (TextView) findViewById(R.id.cardHigh);
			
			cardLowText = (TextView) findViewById(R.id.cardLow);
			
			
			//Searches through the provider to pull additional content
			Uri uri = Uri.parse("content://com.example.magee_david_java2_week1.cardprovider/cards/names/" + cardName);
			
			Cursor theCursor = getContentResolver().query(uri, null, null, null, null);
			
			if (theCursor.getCount() > 0)
			{
				if (theCursor.moveToFirst() == true)
				{
					for (int i = 0; i < theCursor.getCount(); i++)
					{
						cardHighText.setText("High: " + theCursor.getString(4));
						cardLowText.setText("Low: " + theCursor.getString(5));
						
						theCursor.moveToNext();
					};
				};
			};
			
			
		}
		
		
		//STarts web activity
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public void onWebButtonPress() {
		// TODO Auto-generated method stub
		if (cardURL != null && !cardURL.isEmpty())
		{
			Intent webView = new Intent(Intent.ACTION_VIEW, Uri.parse(cardURL));
			
			startActivity(webView);
		}
	}
	
	

}

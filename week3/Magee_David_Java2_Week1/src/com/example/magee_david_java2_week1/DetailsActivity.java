package com.example.magee_david_java2_week1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	static String cardName;
	static String cardURL;
	static String cardPrice;
	static Bundle retrievedData;
	static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_list);
		
		context = this;
		retrievedData = getIntent().getExtras();
		if (retrievedData != null)
		{
			cardName = retrievedData.getString("cardName");
			cardURL = retrievedData.getString("cardURL");
			cardPrice = retrievedData.getString("cardPrice");
			
			
			TextView cardNameText = (TextView) findViewById(R.id.cardName);
			cardNameText.setText(cardName);
			
			TextView cardPriceText = (TextView) findViewById(R.id.cardPrice);
			cardPriceText.setText("Current Price: " + cardPrice);
			
			TextView cardURLText = (TextView) findViewById(R.id.cardURL);
			cardURLText.setText(cardURL);
			
			TextView cardHighText = (TextView) findViewById(R.id.cardHigh);
			
			TextView cardLowText = (TextView) findViewById(R.id.cardLow);
			
			
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
		
		
		Button webPageButton = (Button) findViewById(R.id.webPageButton);
		webPageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cardURL != null && !cardURL.isEmpty())
				{
					Intent webView = new Intent(Intent.ACTION_VIEW, Uri.parse(cardURL));
					
					startActivity(webView);
				}
			}
		});
		
		Button goBackButton = (Button) findViewById(R.id.goBackButton);
		goBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (cardName != null && !cardName.isEmpty() && cardPrice != null && !cardPrice.isEmpty())
				{
					Intent newIntent = new Intent(context, MainActivity.class);
					newIntent.putExtra("cardname", cardName);
					newIntent.putExtra("cardprice", cardPrice);
					
					startActivity(newIntent);
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
	
	

}

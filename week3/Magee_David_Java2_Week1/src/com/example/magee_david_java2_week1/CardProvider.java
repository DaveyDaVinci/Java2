package com.example.magee_david_java2_week1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class CardProvider extends ContentProvider{

	public static final String AUTHORITY = "com.example.magee_david_java2_week1.cardprovider";
	
	
	//Custom class that holds content
	public static class CardData implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/cards");
		
		public static final String ALL_CONTENT = "vnd.android.cursor.dir/vnd.example.magee_david_java2_week1.item";
		public static final String SORTED_CONTENT = "vnd.android.cursor.item/vnd.example.magee_david_java2.week1.item";
		
		//This defines columns
		public static final String CARD_NAME_COLUMN = "Card";
		public static final String CARD_PRICE_COLUMN = "Price";
		public static final String CARD_URL = "Url";
		
		public static final String[] PROJECTION = {"_Id", CARD_NAME_COLUMN, CARD_PRICE_COLUMN, CARD_URL};
		
		private CardData() {};
	}
	
	public static final int ALLCARDS = 1;
	public static final int CARDNAMES = 2;
	
	private static final UriMatcher theMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	
	
	
	//Adds URI
	static {
		theMatcher.addURI(AUTHORITY, "cards/", ALLCARDS);
		theMatcher.addURI(AUTHORITY, "cards/names/*", CARDNAMES);
	}
	
	
	
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	
	
	//Grabs the type entered through the matcher
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch (theMatcher.match(uri))
		{
		case ALLCARDS:
			return CardData.ALL_CONTENT;
			
		case CARDNAMES:
			return CardData.SORTED_CONTENT;
		}
		return null;
	}

	
	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	//This is the query method that actually grabs the URI and checks it against the matcher.  THen 
	//filters out the data according to the URI 
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		MatrixCursor result = new MatrixCursor(CardData.PROJECTION);
		
		String savedJSONData = SaveClass.readStringData(getContext(), "saveddata");
		
		JSONObject theObject = null;
		JSONArray cardsArray = null;
		JSONObject results = null;
		
		try {
			theObject = new JSONObject(savedJSONData);
			cardsArray = theObject.getJSONArray("cards");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (cardsArray == null)
		{
			return result;
		}
		
		switch (theMatcher.match(uri))
		{
		case ALLCARDS:
			
			for (int i = 0; i<cardsArray.length();i++)
			{
				try {
					results = cardsArray.getJSONObject(i);
					result.addRow(new Object[]{ i + 1, results.get("name"), results.get("price"), results.get("url")});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		break;
			
		
		//This one checks for a card name entered
		case CARDNAMES:
			
			String cardNameRequest = uri.getLastPathSegment();
			
			for (int i = 0; i < cardsArray.length(); i++)
			{
				try
				{
					results = cardsArray.getJSONObject(i);
					
					if (results.getString("name").contentEquals(cardNameRequest))
					{
						result.addRow(new Object[]{ i + 1, results.get("name"), results.get("price"), results.get("url")});
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		break;
			
		}
		return result;
	}

	
	
	
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}

package com.example.magee_david_java2_week1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	private DetailsListener listener;
	static TextView nameView;
	static TextView priceView;
	static TextView URLView;
	static TextView highView;
	static TextView lowView;
	
	public interface DetailsListener
	{
		public void onWebButtonPress();
	}
	
	@Override
	public View onCreateView(LayoutInflater theInflater, ViewGroup viewContainer, Bundle savedInstanceState)
	{
		super.onCreateView(theInflater, viewContainer, savedInstanceState);
		
		ScrollView view = (ScrollView) theInflater.inflate(R.layout.details_list, viewContainer, false);
		
		nameView = (TextView) view.findViewById(R.id.cardName);
		priceView = (TextView) view.findViewById(R.id.cardPrice);
		URLView = (TextView) view.findViewById(R.id.cardURL);
		highView = (TextView) view.findViewById(R.id.cardHigh);
		lowView = (TextView) view.findViewById(R.id.cardLow);
		
		Button webPageButton = (Button) view.findViewById(R.id.webPageButton);
		webPageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				listener.onWebButtonPress();
			}
		});
		
		
		
		return view; 
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			listener = (DetailsListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + "must first implement");
		}
	}
}

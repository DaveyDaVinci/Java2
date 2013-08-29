package com.example.magee_david_java2_week1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;

public class MainFragment extends Fragment {
	
	private MainListener listener;
	static Button getJokesButton;
	
	public interface MainListener
	{
		public void onMoreDetailsClick();
		public void onGetJSONData();
	}
	
	@Override
	public View onCreateView(LayoutInflater theInflater, ViewGroup viewContainer, Bundle savedInstanceState)
	{
		super.onCreateView(theInflater, viewContainer, savedInstanceState);
		
		ScrollView view = (ScrollView) theInflater.inflate(R.layout.main_scrollview, viewContainer, false);
		
		//Starts up the intent to go to the next activity, and puts extras in 
		Button testButton = (Button) view.findViewById(R.id.testButton);
		testButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				listener.onMoreDetailsClick();
			}
		});
		
		getJokesButton = (Button) view.findViewById(R.id.getCardsButton);
		getJokesButton.setOnClickListener(new View.OnClickListener() {
					
			@Override
			//Gets joke
			public void onClick(View v) {
				
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	             imm.hideSoftInputFromWindow(getJokesButton.getWindowToken(), 0);
				
				listener.onGetJSONData();
								
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
			listener = (MainListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + "must first implement");
		}
	}
}

package com.example.magee_david_java2_week1;
import java.net.MalformedURLException;
import java.net.URL;
import com.example.magee_david_java2_week1.SaveClass;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import connectionwork.ConnectionWork;

//Service that handles the intent coming in.  Sets up the URL and opens the connection through ConnectionWork.  Returns and
//saves string
public class URLService extends IntentService{

	public static final String URL_INFORMATION = "";
	public static final String BASE_URL = null;
	static URL finishedURL = null;
	
	public URLService() {
		super("URLService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle savedExtras = intent.getExtras();
		Messenger messenger = (Messenger) savedExtras.get(URL_INFORMATION);
		String baseURL = savedExtras.getString(BASE_URL);
		
		
		try {
			finishedURL = new URL(baseURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK;
		
		String resultsString = ConnectionWork.getURLResponse(URLService.finishedURL);
		
		SaveClass.storeJSONStringData(this, "saveddata", resultsString);
		
		message.obj = ConnectionWork.getURLResponse(URLService.finishedURL);
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}


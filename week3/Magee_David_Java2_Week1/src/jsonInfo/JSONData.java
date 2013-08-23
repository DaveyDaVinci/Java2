package jsonInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONData {
	static JSONArray theArray = null;
	
	
	public static JSONArray jsonArrayOfCards(int numberOfObjects, String jsonData){
		
		JSONObject jsonResponse = null;
		JSONObject loopedObject = null;
		try {
			jsonResponse = new JSONObject(jsonData);
			try {
				JSONArray arrayOfCards = jsonResponse.getJSONArray("cards");
				for (int i = 0; i < numberOfObjects; i++)
				{
					loopedObject = arrayOfCards.getJSONObject(i);
					
					theArray.put(loopedObject);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return theArray;
	}

}

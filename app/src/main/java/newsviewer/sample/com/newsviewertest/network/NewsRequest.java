package newsviewer.sample.com.newsviewertest.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import newsviewer.sample.com.newsviewertest.model.News;

/**
 * Created by Jade Byfield on 1/10/2015.
 */
public class NewsRequest {

    private static final String TAG = "NewsRequest";

    private static final String API_KEY = "5a33c8bbb741454d178bfe7adead038d:11:70708734";
    private static final String NEWS_URL = "http://api.nytimes.com/svc/topstories/v1/home.json?api-key=" + API_KEY;

    private Context mContext;
    static JSONObject result = new JSONObject();
    private JSONArray resultArray = new JSONArray();

    private List<News> mNewsList = new ArrayList<News>();


    //A reference to context is not really necessary here, but is useful in this case for
    //showing toast in case of an error
    public NewsRequest(Context c) {
        this.mContext = c;
    }

    public JSONObject getNews() {


        try {
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, NEWS_URL, null, new Response.Listener<JSONObject>() {


                //If the request was successful and a valid response is returned
                //Save the corresponding JSON dictionary for later mapping to data model
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "onResponse");

                    getNewsList(response);

                    Log.d(TAG, "Response: " + response.toString());


                }
                //Handle the error and notify the user
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                    Toast.makeText(mContext, "Sorry, error retrieving the news!", Toast.LENGTH_LONG).show();
                }
            });

            //Add the request to the request queue and execute it
            Volley.newRequestQueue(mContext).add(jsonRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public void getNewsList(JSONObject res) {

        //Iterate through the JSON returned and construct a News item for reach item in the dictionary

        try {


            Log.d(TAG, "Results -> " + res.toString());


            JSONArray jArray = res.getJSONArray("results");


            for (int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                News newsItem = new News(obj);
                mNewsList.add(newsItem);
            }

            Log.d(TAG, "News List Size -> " + mNewsList.size());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public List<News> getResults() {

        return mNewsList;
    }
}

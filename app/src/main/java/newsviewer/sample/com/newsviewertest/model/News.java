package newsviewer.sample.com.newsviewertest.model;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jade Byfield on 1/8/2015.
 */


//
public class News {


    private String mUrl;
    private String mThumbnailUrl;
    private String mTitle;
    private static final String TAG = "News";


    public News(JSONObject obj) {

        //Construct a News object out of the JSON data

        try {
            setUrl(obj.getString("url"));
            setTitle(obj.getString("title"));
            Log.d(TAG, "Title-> " + obj.getString("title"));
            Log.d(TAG, "URL-> " + obj.getString("url"));


            //The thumbnail URL is stored in a JSONArray named "multimedia"
            //So we have to dig a bit deeper to get that item

            //The Times API seems to sometime return an empty string for the thumbnail url
            if (obj.get("multimedia").equals("")) {
                setThumbnailUrl("");
            } else {
                JSONArray jArray = obj.getJSONArray("multimedia");

                //Array contains thumbnail images of different sizes
                //In this case we'll do just fine with the first one
                JSONObject jObj = jArray.getJSONObject(0);
                setThumbnailUrl(jObj.getString("url"));
                Log.d(TAG, "Thumb URL-> " + jObj.getString("url"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public News() {

    }


    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setThumbnailUrl(String url) {
        this.mThumbnailUrl = url;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }


}

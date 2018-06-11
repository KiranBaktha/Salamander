package edu.uchicago.cs.prowebservice;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class Loading extends AppCompatActivity {

    private String query;
    private String key = "a14dc8d28153433e8e50ad8f28d65cd6"; // API key
    private ArrayList<String> titles;
    private ArrayList<String> imageURLS;
    private ArrayList<String> descriptions;
    public String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent intent = getIntent(); // Get the intent that started it
        query = intent.getStringExtra("query_term");
        URL = "https://newsapi.org/v2/everything?q="+query+"&sortBy=popularity&apiKey=" + key;
        new FetchCodesTask().execute(URL);

    }

    private class FetchCodesTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            return new JSONParser().getJSONFromUrl(params[0]);
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            titles = new ArrayList<String>();
            imageURLS = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                JSONArray array = (JSONArray) jsonObject.get("articles");
                for (int i=0;i<array.length();i++){
                    titles.add(array.getJSONObject(i).getString("title"));
                    imageURLS.add(array.getJSONObject(i).getString("urlToImage"));
                    descriptions.add(array.getJSONObject(i).getString("description"));
                }
                Intent nextIntent = new Intent(Loading.this, Display.class);
                nextIntent.putStringArrayListExtra("titles_arraylist",titles); // Send the Titles
                nextIntent.putStringArrayListExtra("imgs",imageURLS); // Send the Image URLs
                nextIntent.putStringArrayListExtra("descps",descriptions); // Send the Descriptions
                nextIntent.putExtra("in_fav",false); // If the Display activity is showing the favourite articles
                startActivity(nextIntent);
                finish();
            } catch (JSONException e) {
                Toast.makeText(
                        Loading.this,
                        "There's been a JSON exception: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                e.printStackTrace();
                finish();
            }

        }
    }




}

package edu.uchicago.cs.prowebservice;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Start extends AppCompatActivity {

    private Button search;
    private Button previous_search;
    private Button favourite;
    private EditText textbox;
    private Database_Adapter db_adapter;
    private ArrayList<String> titles;
    private ArrayList<String> imageURLS;
    private ArrayList<String> descriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // Hide the logo when rotated because it takes up a lot of space.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            View my_view = findViewById(R.id.image_main);
            my_view.setVisibility(View.GONE);
        }

        search = (Button) findViewById(R.id.search_button);
        previous_search = (Button) findViewById(R.id.load_previous);
        favourite = (Button) findViewById(R.id.load_favourites);
        textbox = (EditText) findViewById(R.id.Search_Term);
        textbox.setText(PrefsMgr.getSearch(this,"entered_text"));

       // Show only logo in the main App
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.salamander_logo);


        // Set onclick for Search Button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term = textbox.getText().toString();
                if (term.equalsIgnoreCase("")){ // If the search term is empty
                    Toast toast  = Toast.makeText(Start.this,"Please enter something...",Toast.LENGTH_SHORT);
                    View view  = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.Black));
                    toast.show();
                }
                else{
                PrefsMgr.setBoolean(Start.this, "already_loaded", true);
                PrefsMgr.setString(Start.this, "query", term);
                Intent intent = new Intent(Start.this,Loading.class);
                intent.putExtra("query_term",term);
                startActivity(intent);  // Go to Loading Activity
            }}
        });

        // Set onclick for Display Previous Search Button
        previous_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bAlreadyLoaded = PrefsMgr.getBoolean(Start.this, "already_loaded", false);
                if (!bAlreadyLoaded){
                    Toast toast  = Toast.makeText(Start.this,"First Application load..please type a new search",Toast.LENGTH_SHORT);
                    View view  = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.Black));
                    toast.show();
                }
                else{
                    String query = PrefsMgr.getSearch(Start.this,"query");
                    Intent intent = new Intent(Start.this,Loading.class);
                    intent.putExtra("query_term",query);
                    startActivity(intent);  // Go to Loading Activity
                }
            }
        });


        // Set onclick for Display Favourites Button
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            db_adapter = new Database_Adapter(Start.this); // Learn: Start.this because only this implies a view.
            db_adapter.open();
            //db_adapter.createEntry("How to Get Started With Your Brand-New Samsung Galaxy S9","https://i.kinja-img.com/gawker-media/image/upload/s--DFHHaPNJ--/c_fill,fl_progressive,g_center,h_450,q_80,w_800/jrd6v3x7b6tszld09qn5.jpg","Happy launch day, new Samsung Galaxy S9 and or S9+ owners! Now that you have your brand-new device (or are still refreshing its shipping status every five minutes while waiting for it to arrive),");
            //db_adapter.createEntry("How to Get Started With Your Brand-New Samsung Galaxy S9","https://i.kinja-img.com/gawker-media/image/upload/s--DFHHaPNJ--/c_fill,fl_progressive,g_center,h_450,q_80,w_800/jrd6v3x7b6tszld09qn5.jpg","Happy launch day, new Samsung Galaxy S9 and or S9+ owners! Now that you have your brand-new device (or are still refreshing its shipping status every five minutes while waiting for it to arrive),");
            //db_adapter.createEntry("How to Get Started With Your Brand-New Samsung Galaxy S9","https://i.kinja-img.com/gawker-media/image/upload/s--DFHHaPNJ--/c_fill,fl_progressive,g_center,h_450,q_80,w_800/jrd6v3x7b6tszld09qn5.jpg","Happy launch day, new Samsung Galaxy S9 and or S9+ owners! Now that you have your brand-new device (or are still refreshing its shipping status every five minutes while waiting for it to arrive),");
            //Toast.makeText(Start.this, "Created Database Entry", Toast.LENGTH_LONG).show();
            Cursor cursor = db_adapter.fetchAll();
            titles  = new ArrayList<String>();
            imageURLS  = new ArrayList<String>();
            descriptions  = new ArrayList<String>();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {  // for loop to start cursor from beginning to end
                   titles.add(cursor.getString(cursor.getColumnIndexOrThrow(Database_Adapter.COL_TITLE)));
                   imageURLS.add(cursor.getString(cursor.getColumnIndexOrThrow(Database_Adapter.COL_IMAGE_URL)));
                   descriptions.add(cursor.getString(cursor.getColumnIndexOrThrow(Database_Adapter.COL_DESCRIPTION)));
                }
                Intent nextIntent = new Intent(Start.this, Display.class);
            if (titles.size()==0){  // Warn if no favourites yet
                Toast toast  = Toast.makeText(Start.this,"No Favourites as yet",Toast.LENGTH_SHORT);
                View view  = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.Black));
                toast.show();
            }
            else{
                nextIntent.putStringArrayListExtra("titles_arraylist",titles); // Send the Titles
                nextIntent.putStringArrayListExtra("imgs",imageURLS); // Send the Image URLs
                nextIntent.putStringArrayListExtra("descps",descriptions); // Send the Descriptions
                nextIntent.putExtra("in_fav",true); // If the Display activity is showing the favourite articles
                startActivity(nextIntent);
            }
            }
        });


    }

    // Handle instance states for edit text
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("entered_text",textbox.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textbox.setText(savedInstanceState.getString("entered_text"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_menu, menu);
        return true;
    }



    // Lets make the action bar menu have only exit
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }

}
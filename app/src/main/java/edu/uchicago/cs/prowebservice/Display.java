package edu.uchicago.cs.prowebservice;

import android.app.Dialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Display extends AppCompatActivity {

    private ListView content;
    private ArrayList<String> titles;
    private  ArrayList<String> images;
    private ArrayList<String> descriptions;
    private Database_Adapter db_adapter = new Database_Adapter(this);
    private Boolean infav;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent previous_intent = getIntent(); // Get the intent that started it
        titles = previous_intent.getStringArrayListExtra("titles_arraylist");
        images = previous_intent.getStringArrayListExtra("imgs");
        descriptions = previous_intent.getStringArrayListExtra("descps");
        infav =previous_intent.getBooleanExtra("in_fav",false);
        content = (ListView) findViewById(R.id.main_content);
        adapter = new ListAdapter(Display.this,titles,images);
        //content.setAdapter(arrayAdapter);
        content.setAdapter(adapter);
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fireArticle(position);
            }
        });

    }

    private void fireArticle(final int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        TextView viewer = (TextView) dialog.findViewById(R.id.text_in_scroll);
        Button savebutton = (Button) dialog.findViewById(R.id.save_me);
        if (infav){
            savebutton.setText("Delete the Article");
        }
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_adapter.open();
                if (!infav){
                    db_adapter.createEntry(titles.get(position),images.get(position),descriptions.get(position));
                  toast(); // Notify user that it has been saved
                  dialog.cancel();
                }
                else{
                    db_adapter.deleteArticle(titles.get(position));
                    toast(); // Notify user that it has been deleted
                    titles.remove(position); // Remove the values
                    images.remove(position);
                    descriptions.remove(position);
                    dialog.cancel();
                    adapter.notifyDataSetChanged(); // Refresh the view
                }
            }
        });
        viewer.setText(descriptions.get(position));
        dialog.show();
    }

    private void toast(){
      Toast to = new Toast(Display.this);
        ImageView view = new ImageView(Display.this);
        view.setImageResource(R.drawable.check);
        to.setView(view);
        to.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_menu, menu);
        return true;
    }

    // Lets make the action bar menu have only exit
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_goback:
                finish();
                return true;
            default:
                return false;
        }
    }

}

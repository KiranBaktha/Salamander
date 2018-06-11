package edu.uchicago.cs.prowebservice;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{

    private Context con;
    private ArrayList<String> title_values;
    private ArrayList<String> image_urls;

    // The ViewHolder Class
    static class ViewHolder{
        TextView text;
        ImageView image;
    }

    public ListAdapter(Context context, ArrayList<String> titles, ArrayList<String> images){
        title_values = titles;
        image_urls = images;
        con = context;
    }

    @Override
    public int getCount() {
        return title_values.size();
    }

    @Override

    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override

    public View getView(int position, View view, ViewGroup viewGroup) {

        // create a ViewHolder reference
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = ((Activity) con).getLayoutInflater();
            view = inflater.inflate(R.layout.list_row, viewGroup, false);


            holder.text = (TextView) view.findViewById(R.id.row_text);
            holder.image = (ImageView) view.findViewById(R.id.row_image);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        String stringItem = title_values.get(position);
        if (stringItem != null) {
            //set the item name on the TextView
            holder.text.setText(stringItem);
            // Picasso helps in displaying images as they are loaded avoiding having to use async for this.
            Picasso.get().load(image_urls.get(position)).resize(60,60).into(holder.image);
        } else {
            holder.text.setText("Unknown");
        }
        return view;
    }


}

package com.alfredoalmenares.memorandum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// Adapter for listview item
public class MyListAdapter extends ArrayAdapter<CustomTask> {
    IRemover r;
    boolean isEmpty;
    // Constructor takes memorandum tasks as parameter
    public MyListAdapter(@NonNull Context context, @NonNull ArrayList<CustomTask> eachTask, IRemover r, boolean isEmpty) {
        super(context, R.layout.row_item, eachTask);
        this.isEmpty=isEmpty;
        this.r=r;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater lin = LayoutInflater.from(getContext());
        View myView = lin.inflate(R.layout.row_item, parent, false);

        //Reference to description using position
        String eachTitle = getItem(position).getTitle();
        TextView myDesc = (TextView) myView.findViewById(R.id.textview_description);
        myDesc.setText(eachTitle);

        // Button event to remove item from list
        ImageView imageView = myView.findViewById(R.id.image_close);
        if(isEmpty)
            imageView.setColorFilter(parent.getResources().getColor(R.color.divider));
        else
            imageView.setColorFilter(parent.getResources().getColor(R.color.remove_item));
        
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                                "Removed "+getItem(position).getTitle()+"...",
                                Toast.LENGTH_SHORT)
                        .show();
                r.remover(getItem(position).getId());
            }
        });

        return myView;
    }


}

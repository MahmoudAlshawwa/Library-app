package com.example.project333.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project333.BooksActivity;
import com.example.project333.R;
import com.example.project333.model.Category;
import java.util.ArrayList;

public class catagoryAdapter extends RecyclerView.Adapter<catagoryAdapter.ViewHolder> {

    private ArrayList<Category> mData;
    private LayoutInflater mInflater;
    Context context;

    // data is passed into the constructor
    public catagoryAdapter(Context context, ArrayList<Category> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_library_catagory, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.catagoryID.setText(Integer.toString(mData.get(position).getId()));
        } catch (Exception e) {
            Log.e("mah", "CatID error ");
        }
        holder.catagory_title_txt.setText(mData.get(position).getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catagory_title_txt;
        TextView catagoryID;

        ViewHolder(View itemView) {
            super(itemView);
            catagory_title_txt = itemView.findViewById(R.id.catagory_title_txt);
            catagoryID = itemView.findViewById(R.id.catagory_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, BooksActivity.class);
            intent.putExtra("catagoryID", catagoryID.getText().toString());
            intent.putExtra("catagoryName", catagory_title_txt.getText().toString());
            context.startActivity(intent);
        }
    }

}
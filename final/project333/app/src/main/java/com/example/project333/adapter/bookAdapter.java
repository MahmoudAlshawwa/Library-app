package com.example.project333.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project333.BookDetailsActivity;
import com.example.project333.R;
import com.example.project333.model.Book;

import java.util.ArrayList;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {
    private ArrayList<Book> mData;
    private LayoutInflater mInflater;
    String catTitle = "catTitle";
    Context context;

    public bookAdapter(Context context, ArrayList<Book> data, String catTitle) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.catTitle = catTitle;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.book_id.setText(Integer.toString(mData.get(position).getId()));
        holder.book_title.setText(mData.get(position).getName());
        holder.book_author.setText(mData.get(position).getAuthorName());
        holder.bookImage.setImageURI(Uri.parse(mData.get(position).getImageURL()));
        holder.book_release.setText(mData.get(position).getReleaseYear() + "");
        holder.book_category.setText(catTitle);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView book_id, book_title, book_author, book_pages, book_release, book_category;
        ImageView bookImage;

        ViewHolder(View itemView) {
            super(itemView);
            book_id = itemView.findViewById(R.id.book_id_txt);
            book_title = itemView.findViewById(R.id.book_title_txt);
            book_author = itemView.findViewById(R.id.book_author_txt);
            bookImage = itemView.findViewById(R.id.bookImage);
            book_pages = itemView.findViewById(R.id.book_pages_txt);
            book_release = itemView.findViewById(R.id.book_release_txt);
            book_category = itemView.findViewById(R.id.book_category_txt);
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override

                        public void onClick(View view) {

                            Intent intent = new Intent(context, BookDetailsActivity.class);
                            intent.putExtra("id", book_id.getText().toString() + "");
                            intent.putExtra("category", book_category.getText().toString());
                            context.startActivity(intent);
                        }
                    }
            );
        }

        @Override
        public void onClick(View view) {
        }
    }

}



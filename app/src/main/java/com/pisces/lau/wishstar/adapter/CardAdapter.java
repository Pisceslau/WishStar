package com.pisces.lau.wishstar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pisces.lau.wishstar.R;
import com.pisces.lau.wishstar.bean.Book;

import java.util.ArrayList;

/**
 * Created by Liu Wenyue on 2015/8/18.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    ArrayList<Book> books;
    Context context;

    public CardAdapter(Context context,ArrayList<Book> books) {
        this.context=context;
        this.books = books;


    }

    public CardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {


        if (books == null) {
            Log.v("kkk", "0");
            return 0;
        } else
            Log.v("kkk", "非空");
        return books.size();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        Book bookItem = books.get(i);
        viewHolder.tvNature.setText(bookItem.getId());
        viewHolder.tvDesNature.setText(bookItem.getTitle());
        viewHolder.imgThumbnail.setImageResource(R.drawable.us);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvNature = (TextView) itemView.findViewById(R.id.tv_nature);
            tvDesNature = (TextView) itemView.findViewById(R.id.tv_des_nature);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getLayoutPosition();
            Book book = books.get(position);

        }
    }
}


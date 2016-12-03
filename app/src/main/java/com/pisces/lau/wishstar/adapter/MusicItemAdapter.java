package com.pisces.lau.wishstar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pisces.lau.wishstar.R;
import com.pisces.lau.wishstar.bean.Song;

import java.util.List;

/**
 * Created by E440 on 2016/5/16.
 * ListView  适配器
 */
public class MusicItemAdapter extends ArrayAdapter<Song> {
    List<Song> songs;
    Context context;


    public MusicItemAdapter(Context context, int resource,List<Song> songs) {
        super(context, resource);
        this.context = context;
        this.songs = songs;
    }

    //内部类ViewHolder优化ListView
    private static class ViewHolder {
        //歌曲的名字和歌手名
        private TextView nameTextView;
        private TextView singerTextView;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = songs.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, parent, false);
            viewHolder = new ViewHolder();
            //ViewHolder中的控件初始化
            viewHolder.singerTextView = (TextView) convertView.findViewById(R.id.song_singer);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.song_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.singerTextView.setText(song.getSinger());
        viewHolder.singerTextView.setTextColor(Color.parseColor("#2196F3"));
        viewHolder.nameTextView.setText(song.getTitle());
        viewHolder.nameTextView.setTextColor(Color.parseColor("#2196F3"));
        viewHolder.nameTextView.setTextSize(19f);
        return convertView;
    }
}

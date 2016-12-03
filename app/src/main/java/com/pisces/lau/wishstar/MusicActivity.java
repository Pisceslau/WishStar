package com.pisces.lau.wishstar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pisces.lau.wishstar.adapter.MusicItemAdapter;
import com.pisces.lau.wishstar.bean.Song;
import com.pisces.lau.wishstar.util.AudioUtil;

import java.util.List;

/**
 * Created by E440 on 2016/5/26.
 */
public class MusicActivity extends Activity {
    ListView listView;
    ProgressDialog dialog;
    List<Song> songs;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    Handler musicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                listView.setAdapter(new MusicItemAdapter(getApplicationContext(), R.layout.list_view_item, songs));
                dialog.dismiss();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);
        listView = (ListView) findViewById(R.id.list_view);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = songs.get(position);//根据位置获得这个歌曲Song对象

                Intent intent = new Intent(MusicActivity.this, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
             /*   //intent.putExtra("listPosition", );
                intent.putExtra("currentTime", currentTime);
                intent.putExtra("repeatState", repeatState);
                intent.putExtra("shuffleState", isShuffle);*/
                bundle.putString("title", song.getTitle());
                bundle.putString("url", song.getFileUrl());
                bundle.putString("artist", song.getSinger());
                bundle.putString("album", song.getAlbum());
                //发此刻的位置信息。
                bundle.putInt("position", position);
                intent.putExtra("bundle", bundle);

                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onResume() {
        super.onResume();
        //等待框
        dialog = new ProgressDialog(MusicActivity.this);
        dialog.setTitle("扫描本地音乐中...");
        dialog.setProgress(0);
        dialog.setMax(20);
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                songs = AudioUtil.getAllSongs(getApplicationContext());
                Message msg = new Message();
                msg.what = 1;
                musicHandler.sendMessage(msg);

            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}

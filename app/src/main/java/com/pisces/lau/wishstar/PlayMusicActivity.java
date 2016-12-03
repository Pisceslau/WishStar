package com.pisces.lau.wishstar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pisces.lau.wishstar.bean.Song;
import com.pisces.lau.wishstar.service.MusicService;
import com.pisces.lau.wishstar.util.AudioUtil;
import com.pisces.lau.wishstar.util.Util;

import java.util.List;

/**
 * Created by E440 on 2016/5/16.
 * 该Activity还要接受PlayService的广播并进行页面UI的更新
 */
public class PlayMusicActivity extends Activity {
    String songSinger;
    String songFileUrl;
    String songAlbum;
    String songTitle;
    int currentPosition;//此刻位置
    int duration;//音乐时长
    List<Song> songs;
    //UI
    ImageButton buttonCurrent;
    ImageButton buttonNext;
    ImageButton buttonPre;
    AppCompatSeekBar progressSeekBar;
    TextView musicLength;
    TextView musicCurrentLength;
    //其他
    UpdateUIReceiver receiver;
    MusicService.MusicControlBinder binder;
    MusicServiceConnection connection = new MusicServiceConnection();
    boolean isPause = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music_layout);
        initViews();
        //注册BroadcastReceiver;
        receiver = new UpdateUIReceiver();
        IntentFilter filter = new IntentFilter(AppConstants.MUSIC_DURATION);
        filter.addAction(AppConstants.MUSIC_CURRENT);
        registerReceiver(receiver, filter);


    }

    private void initViews() {
        buttonCurrent = (ImageButton) findViewById(R.id.play_current);//播放音乐的按钮。
        progressSeekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);
        musicLength = (TextView) findViewById(R.id.music_total_length);
        musicCurrentLength = (TextView) findViewById(R.id.music_current_length);
        buttonNext = (ImageButton) findViewById(R.id.play_next);
        buttonPre = (ImageButton) findViewById(R.id.play_pre);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(PlayMusicActivity.this, MusicService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(PlayMusicActivity.this, MusicService.class));
        unbindService(connection);
        unregisterReceiver(receiver);
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        songs = AudioUtil.getAllSongs(PlayMusicActivity.this);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        songSinger = bundle.getString("artist");
        songFileUrl = bundle.getString("url");
        songTitle = bundle.getString("title");
        songAlbum = bundle.getString("album");
        currentPosition = bundle.getInt("position");


        buttonCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没有暂停直接播放


                   Intent intent = new Intent(PlayMusicActivity.this, MusicService.class);
                    intent.putExtra("path", songFileUrl);
                    bindService(intent, connection, BIND_AUTO_CREATE);
                    startService(intent);
                    isPause = true;

                /*  if (isPause) {
                    binder.pause();
                    isPause = false;
                }
*/


            }
        });
    if (!isPause) {
            buttonCurrent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause_white_48dp));
        }
        if (isPause) {
            buttonCurrent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_arrow_white_48dp));
        }
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                      /*
    * SeekBar滚动时候的回调函数
    */

                if (fromUser) {
                    binder.dragUpdate(progress);
                }
                //如果音乐时长等于本身时长，则自动播放下一首歌曲(循环模式)
                if (musicCurrentLength.getText().equals(Util.milliSecondsToTimer(duration))) {
                    playNext();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //下一首音乐
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        });
        //播放上一首歌曲
        buttonPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playPre();
            }
        });

    }

    //播放前一首歌曲
    private void playPre() {
        if (currentPosition > 0) {
            Song song = songs.get(currentPosition - 1);//上一首歌曲的ListView位置
            playMusicByService(song);
            currentPosition = currentPosition - 1;
        } else {
            Song song = songs.get(songs.size() - 1);
            playMusicByService(song);
            currentPosition = songs.size() - 1;
        }
    }

    //播放后一首歌曲
    private void playNext() {
        if (currentPosition < songs.size() - 1) {
            Song song = songs.get(currentPosition + 1);//下一首歌曲的ListView位置
            playMusicByService(song);
            currentPosition = currentPosition + 1;
        } else {
            Song song = songs.get(0);
            playMusicByService(song);
            currentPosition = 0;
        }
    }


    private void playMusicByService(Song song) {
        if (song != null) {
            Intent intent = new Intent();
            intent.putExtra("path", song.getFileUrl());
            intent.setClass(PlayMusicActivity.this, MusicService.class);
            startService(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //接收来自播放服务MusicService的广播获取歌曲进度和总时长并更新UI
    private class UpdateUIReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case AppConstants.MUSIC_DURATION:
                    duration = intent.getIntExtra("duration", -1);
                    progressSeekBar.setMax(duration);
                    musicLength.setText(Util.milliSecondsToTimer(duration));
                    break;
                case AppConstants.MUSIC_CURRENT:
                    int current = intent.getIntExtra("current", -1);
                    progressSeekBar.setProgress(current);
                    musicCurrentLength.setText(Util.milliSecondsToTimer(current));
                    break;
            }
        }
    }

    public class MusicServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.MusicControlBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}

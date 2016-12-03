package com.pisces.lau.wishstar.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.pisces.lau.wishstar.AppConstants;

import java.io.IOException;

public class MusicService extends Service {
    MusicControlBinder binder = new MusicControlBinder();
    int currentTime;
    int duration;
    public MediaPlayer mediaPlayer;
    String path;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((msg.what == 1) && (mediaPlayer != null)) {
                currentTime = mediaPlayer.getCurrentPosition();
                Intent intent = new Intent(AppConstants.MUSIC_CURRENT);
                intent.putExtra("current", currentTime);
                sendBroadcast(intent);
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music
                mediaPlayer.pause();
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
                mediaPlayer.start();
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                mediaPlayer.pause();
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };


    @Nullable
    public IBinder onBind(Intent paramIntent) {
        return binder;
    }

    public void onCreate() {
        super.onCreate();
        TelephonyManager localTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (localTelephonyManager != null) {
            localTelephonyManager.listen(phoneStateListener, 32);
        }
        mediaPlayer = new MediaPlayer();
    }

    public void onDestroy() {

        if (mediaPlayer != null) {
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        TelephonyManager localTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (localTelephonyManager != null) {
            localTelephonyManager.listen(phoneStateListener, 0);
        }
        super.onDestroy();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        path = intent.getStringExtra("path");
        binder.playMusic(0);
        return super.onStartCommand(intent, flags, startId);
    }

    public boolean onUnbind(Intent intent) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        return super.onUnbind(intent);
    }

    private class CompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    }

    public class MusicControlBinder extends Binder {
        private void playMusic(int currentTime) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));
                mediaPlayer.setOnCompletionListener(new CompletionListener());
                mediaPlayer.prepareAsync();
                handler.sendEmptyMessage(1);

            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }

        public void pause() {
            if (mediaPlayer != null && (mediaPlayer.isPlaying())) {
                currentTime = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            } else {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    mediaPlayer.seekTo(currentTime);
                }
            }
        }

        public void reStart() {
            if (mediaPlayer != null) {
                playMusic(0);
            }
        }

        public void dragUpdate(int progress) {
            if (mediaPlayer != null) {
                currentTime = progress;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    mediaPlayer.seekTo(currentTime);
                } else {
                    mediaPlayer.pause();
                }
            }
        }
    }

    private class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
            if (currentTime > 0) {
                mediaPlayer.seekTo(currentTime);
            }
            Intent intent = new Intent();
            intent.setAction(AppConstants.MUSIC_DURATION);
            duration = mediaPlayer.getDuration();
            intent.putExtra("duration", duration);
            sendBroadcast(intent);
        }
    }
}

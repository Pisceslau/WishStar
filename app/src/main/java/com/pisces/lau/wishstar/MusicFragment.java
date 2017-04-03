package com.pisces.lau.wishstar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pisces.lau.wishstar.adapter.MusicItemAdapter;
import com.pisces.lau.wishstar.bean.Song;
import com.pisces.lau.wishstar.util.AudioUtil;

import java.util.List;

/**
 * Created by E440 on 2016/5/30.
 */
public class MusicFragment extends Fragment {
    ListView listView;
    ProgressDialog dialog;
    Toolbar mToolbar;
    CoordinatorLayout mCoordinatorLayout;
    List<Song> songs;

    //最低滑动距离
    int mTouchSlop;
    float mFirstY;
    float mCurrentY;
    int direction;//标识方向
    boolean isShow;//标识toolbar是否显示的判断位

    Animator mAnimator;
    Handler musicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                MusicItemAdapter adapter = new MusicItemAdapter(getActivity(), R.layout.list_view_item, songs);
                listView.setAdapter(adapter);
                //实现ListView的动态修改
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_layout, container, false);
        listView = (ListView) v.findViewById(R.id.list_view);
        mCoordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.container);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int toolbarHeight = 0;
        mTouchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
        View header = new View(getActivity());
        AppCompatActivity ac = (AppCompatActivity) getActivity();
        if (ac.getSupportActionBar() != null) {
            mToolbar = (Toolbar) ac.findViewById(R.id.toolbar);
            if (mToolbar != null) {
                toolbarHeight = mToolbar.getHeight();
            }
        }
        header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, toolbarHeight));
        listView.addHeaderView(header);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = songs.get(position);//根据位置获得这个歌曲Song对象

                Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
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
        //给ListView设置onTouchListener
        listView.setOnTouchListener(myTouchListener);

        //等待框
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("扫描本地音乐中...");
        dialog.setProgress(0);
        dialog.setMax(20);
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                songs = AudioUtil.getAllSongs(getActivity());
                Message msg = new Message();
                msg.what = 1;
                musicHandler.sendMessage(msg);

            }
        }.start();

        //给ListView设置OnScrollListener
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滚动到了最后一行SnackBar提示功能
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    //SnackBar的提示

                    Snackbar.make(mCoordinatorLayout, "已经到底啦~", Snackbar.LENGTH_LONG).show();

                }
            }
        });
    }

    private void setSnackBarMessageTextColor(Snackbar snackBar, int color) {
        View view = snackBar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }

    View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurrentY = event.getY();
                    if (mCurrentY - mFirstY > mTouchSlop) {
                        direction = 0;//down
                    } else if (mFirstY - mCurrentY > mTouchSlop) {
                        direction = 1;//up
                    }
                    if (direction == 1) {
                        if (isShow) {
                            toolbarAnim(0);//hide
                            isShow = !isShow;
                        }
                    } else if (direction == 0) {
                        if (!isShow) {
                            toolbarAnim(1);//show
                            isShow = !isShow;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }
    };


    private void toolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        if (flag == 0) {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), 0);

        } else {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), -mToolbar.getHeight());
        }
        if (mAnimator != null) {
            mAnimator.start();
        }

    }
}

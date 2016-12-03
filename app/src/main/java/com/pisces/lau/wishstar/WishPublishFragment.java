package com.pisces.lau.wishstar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pisces.lau.wishstar.adapter.MessageAdapter;
import com.pisces.lau.wishstar.bean.Message;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by Liu Wenyue on 2015/7/20.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class WishPublishFragment extends Fragment {
    private static final String TAG = "WishPublishFragment";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    List<Message> queryResult;
    MessageAdapter mAdapter;
    public Handler myHandler  = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                queryResult =(List<Message>) msg.obj;
                //主线程更新UI
                mAdapter = new MessageAdapter(getActivity(), queryResult);
                mRecyclerView.setAdapter(mAdapter);

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new MessageAdapter(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        queryDataFromMessage(getActivity());//获取查询结果

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // 点击RecyclerView Item之后.获得ID跳转到下一个Fragment
                    /*    Bundle bundle = new Bundle();
                        bundle.putString("bookId", books.get(position).getId());
                        Log.v("bookId", books.get(position).getId() + books.get(position).getTitle());*/
                  /*      DetailedInfoFragment fragment = new DetailedInfoFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame, fragment).addToBackStack(null).commit();*/


                    }
                })
        );
    }
    //从Message 愿望表中查询每一个结果
    public void queryDataFromMessage(Context context) {
            //查询message和发次message的用户名字

            String sql = "SELECT message,username FROM Message";
            BmobQuery<Message> query = new BmobQuery<>();
            query.setSQL(sql);//设置SQL语句
            //判断本地是否有缓存数据
            boolean isCache = query.hasCachedResult(context, Message.class);
            if (isCache) {
                query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);

            } else {
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
            }
            //执行查询操作
            query.doSQLQuery(context, sql, new SQLQueryListener<Message>() {
                @Override
                public void done(BmobQueryResult<Message> result, BmobException e) {
                    if (e == null) {
                        Log.i(TAG, "查询到：" + result.getResults().size() + "符合条件的数据");
                        queryResult = result.getResults();
                        //使用Handler传递！
                        for (Message message : queryResult) {
                            Log.i(TAG, message.getUsername() + " " + message.getMessage());
                        }

                        android.os.Message msg = new android.os.Message();
                        msg.what = 1;
                        msg.obj = queryResult;
                        myHandler.sendMessage(msg);

                    } else {
                        Log.i(TAG, "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                    }
                }

            });
    }
}

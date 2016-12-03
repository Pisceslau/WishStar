package com.pisces.lau.wishstar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.pisces.lau.wishstar.adapter.CardAdapter;
import com.pisces.lau.wishstar.bean.Book;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Liu Wenyue on 2015/7/20.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class ShoppingFragment extends Fragment {
    public static final String TAG = "ShoppingFragment";
    //买卖,暂时解析豆瓣API
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Book> books;
    String bookName, doubanUrl;
    CardAdapter mAdapter;
    Context context;

    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                books = (ArrayList<Book>) msg.obj;
                //主线程更新UI
                mAdapter = new CardAdapter(getActivity(), books);
                mRecyclerView.setAdapter(mAdapter);

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.getArguments() == null) {
            Toast.makeText(this.getActivity(), "查询书目为空!", Toast.LENGTH_LONG).show();
        }
        if (this.getArguments() != null) {
            bookName = this.getArguments().getString("query");
            Log.v("bookName", bookName);
            if (bookName != null) {
                doubanUrl = "https://api.douban.com/v2/book/search?q=" + bookName + "&=50&fields=id,title";
            }
        }


        View view = inflater.inflate(R.layout.recycler_view, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new CardAdapter(getActivity()));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // 点击RecyclerView Item之后.获得ID跳转到下一个Fragment
                        Bundle bundle = new Bundle();
                        bundle.putString("bookId", books.get(position).getId());
                        Log.v("bookId", books.get(position).getId() + books.get(position).getTitle());
                        DetailedInfoFragment fragment = new DetailedInfoFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame, fragment).addToBackStack(null).commit();


                    }
                })
        );

        getData(doubanUrl);
      //  startDownload(getActivity(),doubanUrl);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    //快捷键:语句提取组成方法:Ctrl + Alt + M
    public void startDownload(Context context, String url) {
        //小而多的图片下载使用Volley下载

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                parseBookResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
        requestQueue.add(request);

    }
    public void getData(String url) {
        //根据书名获取相关书籍
        FinalHttp fh = new FinalHttp();
        if (url != null) {
            fh.get(url, new AjaxCallBack<String>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onLoading(long count, long current) {

                }

                @Override
                public void onSuccess(String result) {
                    parseBookResult(result);
                    Log.v("kkk", result);

                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                }
            });

        }
    }


    //解析书籍结果:Json格式数据,采用Gson解析的方式
    public void parseBookResult(String bookResult) {

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(bookResult).getAsJsonObject().getAsJsonArray("books");
        Type listType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        books = gson.fromJson(jsonArray, listType);
        mAdapter = new CardAdapter(getActivity(), books);
        mRecyclerView.setAdapter(mAdapter);

        android.os.Message msg = new android.os.Message();
        msg.what = 1;
        msg.obj = books;
        myHandler.sendMessage(msg);
        for (Book book : books) {

            Log.v("id", book.getId());
            Log.v("title", book.getTitle());


        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}


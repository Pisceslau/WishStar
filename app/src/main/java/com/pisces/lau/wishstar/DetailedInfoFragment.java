package com.pisces.lau.wishstar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pisces.lau.wishstar.bean.DetailedInfo;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by Liu Wenyue on 2015/8/17.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class DetailedInfoFragment extends Fragment implements View.OnClickListener {
    String bookId = "";
    ImageView imageView;
    Button button;
    String bookInfo;
    Bundle bundle;
    TextView textView, summaryShort, summaryLong, moreText;
    FrameLayout frameLayout;
    private boolean isShortText = true;

    private boolean isInit = false;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.v("info", msg.getData().toString());
                String[] infos = msg.getData().getStringArray("info");
                final String[] pressInfos = msg.getData().getStringArray("pressInfo");
                if (pressInfos != null) {
                    String author = pressInfos[0];
                    String publisher = pressInfos[1];
                    String pubDate = pressInfos[2];
                    StringBuilder builder = new StringBuilder(author).append("/").append(publisher).append("/").append(pubDate);
                    button.setText(builder);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), PressInfoActivity.class);
                            intent.putExtra("p", pressInfos);
                            startActivity(intent);

                        }
                    });


                }
                if (infos != null) {
                    String bookName = infos[0];
                    String imageUrl = infos[1];
                    String summary = infos[2];

                    StringBuilder builder = new StringBuilder("简介").append("\n").append(summary);
                    //更新UI
                    //  String imageUrl = msg.obj.toString();
                    //主线程更新UI(ImageView图片),使用Picasso开源库
                    textView.setText(bookName);
                    Picasso.with(getActivity()).load(imageUrl).placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder).into(imageView);
                    //更新Summary

                    summaryShort.setText(builder);
                    summaryLong.setText(builder);
                    ViewTreeObserver vto = frameLayout.getViewTreeObserver();
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            if (isInit)
                                return true;
                            if (measureDescription(summaryShort, summaryLong)) {
                                moreText.setVisibility(View.VISIBLE);
                            }
                            isInit = true;
                            return true;

                        }
                    });

                }


            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detailed_info_layout, container, false);
        //出版信息按钮
        button = (Button) view.findViewById(R.id.press_info);
        //图书封面图片按钮
        imageView = (ImageView) view.findViewById(R.id.image_view);
        //图书名字TextView
        textView = (TextView) view.findViewById(R.id.book_name);
        //FrameLayout框架布局
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        //summary TextView
        summaryShort = (TextView) view.findViewById(R.id.summary_short);
        summaryLong = (TextView) view.findViewById(R.id.summary_long);
        moreText = (TextView) view.findViewById(R.id.fold_unfold);
        //设置监听
        moreText.setOnClickListener(this);
        summaryLong.setOnClickListener(this);
        summaryShort.setOnClickListener(this);

        return view;
    }

    /*计算信息(summary)是否过长*/
    private boolean measureDescription(TextView shortView, TextView longView) {
        //得到短TextView的高度
        final int shortHeight = shortView.getHeight();
        //得到长TextView的高度
        final int longHeight = longView.getHeight();
        if (longHeight > shortHeight) {
            //如果过长则短的可见, 长的GONE
            shortView.setVisibility(View.VISIBLE);
            longView.setVisibility(View.GONE);
            return true;
        }
        shortView.setVisibility(View.GONE);
        longView.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //得到传过来的图书资源ID:
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            bookId = bundle.getString("bookId");
            Log.v("DetailedInfoFragment", bookId);
            bookInfo = "https://api.douban.com/v2/book/" + bookId;
            getBookInfo(bookInfo);


        }
    }

    private void getBookInfo(String url) {

        FinalHttp fh = new FinalHttp();
        fh.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onLoading(long count, long current) {

            }

            @Override
            public void onSuccess(String result) {

                Log.v("kkk", result);
                parseResult(result);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    //解析书籍详细信息包括图片和信息
    private void parseResult(String result) {
        Gson gson = new Gson();
        DetailedInfo detailedInfo = gson.fromJson(result, DetailedInfo.class);
        //书籍名字
        final String title = detailedInfo.getTitle();
        final String summary = detailedInfo.getSummary();
        //作者
        String authors = detailedInfo.getAuthor().toString();


        Log.v("gson", authors);

        //出版社
        String publisher = detailedInfo.getPublisher();
        Log.v("gson", publisher);
        //翻译者
        String translators = detailedInfo.getTranslator().toString();

        Log.v("gson", translators);

        //出版日期
        String pubDate = detailedInfo.getPubdate();
        Log.v("gson", pubDate);
        //页数
        String pages = detailedInfo.getPages();
        Log.v("gson", pages);
        //定价
        String price = detailedInfo.getPrice();
        Log.v("gson", price);
        //装帧
        String binding = detailedInfo.getBinding();
        //ISBN
        String isbn = detailedInfo.getIsbn10();

        Log.v("gson", binding);
        Log.v("gson", detailedInfo.getTitle());
        //书籍封面图片
        final DetailedInfo.ImagesEntity imagesEntity = detailedInfo.getImages();
        Log.v("gson", imagesEntity.getLarge());
        final String[] pressInfo = {authors, publisher, translators, pubDate, pages, price, binding, isbn};


        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;

                Bundle bundle = new Bundle();//存放数据
                bundle.putStringArray("info", new String[]{title, imagesEntity.getLarge(), summary});
                bundle.putStringArray("pressInfo", pressInfo);

                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detailed_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.detailed_share) {
            //点击该图标,会进行分享.社会化分享shareSDK相关

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fold_unfold:
                //如果是短文的话,点击更多按钮
                if (isShortText) {
                    summaryLong.setVisibility(View.VISIBLE);
                    summaryShort.setVisibility(View.GONE);

                } else {
                    summaryShort.setVisibility(View.VISIBLE);
                    summaryLong.setVisibility(View.GONE);

                }
                //"更多" 变为 "收起"
                toggleMoreText(moreText);
                //使之状态变为长文
                isShortText = !isShortText;
                break;
            //点击短文时候的TextView
            case R.id.summary_short:
                summaryLong.setVisibility(View.VISIBLE);
                summaryShort.setVisibility(View.GONE);
                //"更多" 变为 "收起"
                toggleMoreText(moreText);
                //使之状态变为长文
                isShortText = !isShortText;
                break;
            //点击长文时候的TextView
            case R.id.summary_long:
                summaryLong.setVisibility(View.GONE);
                summaryShort.setVisibility(View.VISIBLE);
                //"更多" 变为 "收起"
                toggleMoreText(moreText);
                //使之状态变为长文

                break;
            default:
                break;

        }
    }

    /*更改按钮更多的文本*/
    private void toggleMoreText(TextView textView) {
        String text = textView.getText().toString();
        String moreText = getString(R.string.label_more);
        String lessText = getString(R.string.label_less);
        if (moreText.equals(text)) {
            textView.setText(lessText);
        } else {
            textView.setText(moreText);
        }
    }
}

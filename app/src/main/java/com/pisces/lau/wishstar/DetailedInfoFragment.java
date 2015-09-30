package com.pisces.lau.wishstar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
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
public class DetailedInfoFragment extends Fragment {
    String bookId = "";
    ImageView imageView;
    Button button;
    String bookInfo;
    Bundle bundle;
    TextView textView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.v("info", msg.getData().toString());
                String[] infos = msg.getData().getStringArray("info");
                if (infos != null) {
                    String bookName = infos[0];
                    String imageUrl = infos[1];
                    //更新UI
                    //  String imageUrl = msg.obj.toString();
                    //主线程更新UI(ImageView图片),使用Picasso开源库
                    textView.setText(bookName);
                    Picasso.with(getActivity()).load(imageUrl).placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder).into(imageView);


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






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PressInfoActivity.class);
                intent.putExtra("pressInfo", bundle);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.preview_iamge);
                ImageView ivPreview=(ImageView)dialog.findViewById(R.id.iv_preview_image);
              //  ivPreview.setImageDrawable();*/


            }
        });


        return view;
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
            Log.v("xxx", bookInfo);
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
        String[] pressInfo = {authors, translators, pubDate, pages, price, binding, isbn};
        bundle = new Bundle();
        bundle.putStringArray("pressInfo", pressInfo);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;

                Bundle bundle = new Bundle();//存放数据
                bundle.putStringArray("info", new String[]{title, imagesEntity.getLarge()});

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
}

package com.pisces.lau.wishstar.db;

import android.content.Context;
import android.util.Log;

import com.pisces.lau.wishstar.bean.Message;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by E440 on 2016/6/9.
 */
public class DBOperation {
    static List<Message> queryResult;//查询结果
    //String sql = "SELECT * FROM Message
    // 查询用户发布的愿望信息
    public static final String TAG = "DBOperation";

    public static List<Message> queryMessage(Context context) {
        //查询message和发次message的用户名字
        String sql = "SELECT message,username FROM Message";
        final BmobQuery<Message> query = new BmobQuery<>();
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

                    for (Message message : queryResult) {
                        Log.i(TAG, message.getUsername() + " " + message.getMessage());
                    }

                } else {
                    Log.i(TAG, "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }

        });
        return queryResult ;
    }
}

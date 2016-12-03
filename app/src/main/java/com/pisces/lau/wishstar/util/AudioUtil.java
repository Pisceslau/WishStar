package com.pisces.lau.wishstar.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.pisces.lau.wishstar.bean.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E440 on 2016/5/16.
 * 获取手机歌曲和信息
 */
public class AudioUtil {


    public static List<Song> getAllSongs(Context context) {
        List<Song> songs;
        String[] media_music_info = new String[]{MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.MIME_TYPE,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA};
        String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 ";
        String sortOrder =MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                media_music_info,
                null,
                null,
                sortOrder
        );
        songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {

            Song song;

            do {
                song = new Song();
                // 文件名
                song.setFileName(cursor.getString(1));
                // 歌曲名
                song.setTitle(cursor.getString(2));
                // 时长
                song.setDuration(cursor.getInt(3));
                // 歌手名
                song.setSinger(cursor.getString(4));
                // 专辑名
                song.setAlbum(cursor.getString(5));
                // 年代
                if (cursor.getString(6) != null) {
                    song.setYear(cursor.getString(6));
                } else {
                    song.setYear("未知");
                }
                // 歌曲格式
                if ("audio/mpeg".equals(cursor.getString(7).trim())) {
                    song.setType("mp3");
                } else if ("audio/x-ms-wma".equals(cursor.getString(7).trim())) {
                    song.setType("wma");
                }
                // 文件大小
                if (cursor.getString(8) != null) {
                    float size = cursor.getInt(8) / 1024f / 1024f;
                    song.setSize((size + "").substring(0, 4) + "M");
                } else {
                    song.setSize("未知");
                }
                // 文件路径
                if (cursor.getString(9) != null) {
                    song.setFileUrl(cursor.getString(9));
                }
                songs.add(song);
            } while (cursor.moveToNext());

            cursor.close();

        }
        return songs;
    }
}

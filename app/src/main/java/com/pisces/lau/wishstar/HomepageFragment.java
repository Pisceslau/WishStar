package com.pisces.lau.wishstar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pisces.lau.wishstar.bean.User;
import com.pisces.lau.wishstar.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Liu Wenyue on 2015/8/17.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class HomepageFragment extends Fragment {
    private ImageView profileImage;

    private String[] items = new String[]{"选择本地照片", "拍照"};
    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    /*请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        profileImage = (ImageView) view.findViewById(R.id.profile_setting);
        //设置监听
        profileImage.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialog();
        }
    };

    private void showDialog() {
        new AlertDialog.Builder(getActivity()).setTitle("设置头像").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Intent intentFromGallery = new Intent();
                        intentFromGallery.setType("image/*");//设置文件类型
                        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                        break;
                    case 1:
                        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //判断存储卡是否可以用,可用进行储存
                        if (Util.hasSdCard()) {
                            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            File file = new File(path, IMAGE_FILE_NAME);
                            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        }
                        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                        break;

                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //结果码不等于取消时候
        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (Util.hasSdCard()) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getActivity(), "未找到存储卡,无法存储照片!", Toast.LENGTH_LONG).show();

                    }
                    break;
                case RESULT_REQUEST_CODE:
                    //图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*保存裁剪之后的图片数据*/
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            //此位置有改动5.15.2016
            profileImage.setImageBitmap(photo);

            saveBitmap(photo, "/crop_"
                    + System.currentTimeMillis() + ".png", Environment.getExternalStorageDirectory());

            /*上传至服务器代码*/
            User newUser = new User();
            newUser.setProfile(photo);
            BmobUser bmobUser = BmobUser.getCurrentUser(getContext());
            newUser.update(getContext(), bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "更新成功！", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int code, String msg) {
                    Toast.makeText(getContext(), "更新失败！", Toast.LENGTH_LONG).show();

                }
            });

        }
    }

    /*裁剪图片方法实现*/
    private void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //OutputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("OutputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }
/*保存图片*/

    public void saveBitmap(Bitmap bitmap, String fileName, File baseFile) {
        FileOutputStream bos = null;
        File imgFile = new File(baseFile, "/" + fileName);
        try {

            bos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
                Toast.makeText(this.getContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

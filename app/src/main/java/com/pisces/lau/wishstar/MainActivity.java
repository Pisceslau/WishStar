package com.pisces.lau.wishstar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.konifar.fab_transformation.FabTransformation;
import com.pisces.lau.wishstar.service.LoadService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    Bundle bundle;
    FrameLayout frame;
    NavigationView navigationView;
    Toolbar toolbar, toolbarFooter;
    private DrawerLayout drawerLayout;
    private Uri fileUri;
    //用户头像
    private CircleImageView circleImageView;
    private ScrollView scrollView;
    private FloatingActionButton floatingActionButton;
    private boolean isTransforming;//FAB是否转为了底边toolbar,java逻辑值默认是false,为没转换了
    private ImageView[] images;//ImageView数组

    boolean doubleBackToExitPressedOnce = false;//默认双按后退键离开:false

    /*  MusicFragment musicFragment;
      ShoppingFragment shoppingFragment;
      WishPublishFragment wishPublishFragment;
      ContentFragment contentFragment;
  */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // 为了安全, 应该检查SD卡是否挂载
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // 如果存储目录不存在,则创建它
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "失败创建目录");
                return null;
            }
        }

        // 创建媒体文件名字
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //使用singleTask启动模式退出整个应用
        super.onNewIntent(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);
        //因为drawerLayout在外侧所以先find它.
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frame = (FrameLayout) findViewById(R.id.frame);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        //因为circleImageView 在header布局所以先加载header布局！
        LayoutInflater inflater = LayoutInflater.from(this);
        View header = inflater.inflate(R.layout.header, navigationView, false);
        circleImageView = (CircleImageView) header.findViewById(R.id.profile_image);
        circleImageView.bringToFront();//避免隐藏imageView
        images = new ImageView[3];
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

        }

        initViews();
        normalGo();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击头像进入用户个人信息设置界面,即应用设置界面的二级界面
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);

            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void normalGo() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            Class fragmentClass = null;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;

            }
        });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = Fragment.class;
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmentClass = WishPublishFragment.class;

                break;
            case R.id.drawer_accepted:
                Intent intent = new Intent(MainActivity.this, AccelerometerActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_sales:

                fragmentClass = ShoppingFragment.class;

                break;
            case R.id.drawer_music:
                fragmentClass = MusicFragment.class;

                break;
            case R.id.drawer_setting:
                Intent intentSetting = new Intent(MainActivity.this, SettingActivity.class);

                startActivity(intentSetting);
                break;
            case R.id.drawer_logout:
                //登出
                BmobUser.logOut(getApplicationContext());   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(getApplicationContext()); // 现在的currentUser是null了

                break;
            default:
            /*    fragmentClass = WishPublishFragment.class;*/

        }

        // Insert the fragment by replacing any existing fragment
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment instanceof ShoppingFragment) {
            fragment.setArguments(bundle);

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();

    }

    private void initViews() {
        //ImageView初始化
        images[0] = (ImageView) findViewById(R.id.image_1);
        images[1] = (ImageView) findViewById(R.id.image_2);
        images[2] = (ImageView) findViewById(R.id.image_3);


        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        toolbarFooter = (Toolbar) findViewById(R.id.toolbar_footer);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //如果FAB不可见而且转换了
                if (floatingActionButton.getVisibility() != View.VISIBLE && !isTransforming) {
                    FabTransformation.with(floatingActionButton).setListener(new FabTransformation.OnTransformListener() {
                        @Override
                        public void onStartTransform() {
                            //抓换了
                            isTransforming = true;
                        }

                        @Override
                        public void onEndTransform() {
                            //没转
                            isTransforming = false;
                        }
                    }).transformFrom(toolbarFooter);//从底边栏转回到FAB
                }
            }
        });

        //ImageView的监听事件:
        for (ImageView image : images) {

            image.setOnClickListener(mOnClickListener);
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果FAB可见,转化为底边栏
                if (floatingActionButton.getVisibility() == View.VISIBLE) {
                    FabTransformation.with(floatingActionButton).transformTo(toolbarFooter);
                }
            }
        });
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            for (ImageView image : images) {

                switch (v.getId()) {
                    case R.id.image_1:
                        //点击
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, BasicMapActivity.class);
                        MainActivity.this.startActivity(intent1);
                        break;
                    case R.id.image_2:
                        //点击
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        //创建一个文件保存图片
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        //设置图片文件名
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        //启动拍照intent
                        if (intent2.resolveActivity(getPackageManager()) != null) {


                            if (hasPermissionInManifest(getApplicationContext(), "CAMERA")) {
                                startActivityForResult(intent2, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            }

                        }
                        break;
                    case R.id.image_3:
                        //点击
                        Intent intent3 = new Intent();
                        intent3.setClass(MainActivity.this, CollectionActivity.class);
                        MainActivity.this.startActivity(intent3);
                        break;
                    default:
                        break;

                }


            }

        }
    };
    public boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermissions = packageInfo.requestedPermissions;
            if (declaredPermissions != null && declaredPermissions.length > 0) {
                for (String p : declaredPermissions) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }
    @Override
    public void onBackPressed() {
        //如果FAB不可见
        if (floatingActionButton.getVisibility() != View.VISIBLE) {
            //如果不可见,从底边栏转化为按钮
            FabTransformation.with(floatingActionButton).transformFrom(toolbarFooter);

        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再按一次离开愿望星", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setIconifiedByDefault(true);
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String searchString) {

                    bundle = new Bundle();
                    bundle.putString("query", searchString);
                    replaceFragment(new ShoppingFragment());

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

        }


        return super.onCreateOptionsMenu(menu);
    }

    private void replaceFragment(Fragment fragment) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            //搜索
            case R.id.action_search:


                return true;
            //撰写愿望
            case R.id.action_compose:
                Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
                startActivity(intent);
                //Activity出现方向,出现了多重现象,待解决,滑动冲突？？？？？？
               /* overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);*/

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

 /*   public LoadBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (LoadBinder) service;
            //使用www.baidu.com测试
            myBinder.startDownload(getApplicationContext(), "http://www.baidu.com");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };*/

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, LoadService.class);
        startService(intent);
       // bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑和消除Service
        Intent intent = new Intent(this, LoadService.class);
        stopService(intent);
     //   unbindService(connection);

    }
}

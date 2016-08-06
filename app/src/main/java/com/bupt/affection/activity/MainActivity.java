package com.bupt.affection.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.bupt.affection.R;
import com.bupt.affection.common.BaseActivity;
import com.bupt.affection.common.CommonUtil;
import com.bupt.affection.common.PreferencesUtil;
import com.bupt.affection.common.UserConfig;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_username;
    private View headView;
    private ImageView iv_avatar;
    private Dialog tipDialog;
    private RadioButton rb_main_schedule;
    private RadioButton rb_main_gallery;
//    private RadioButton rb_main_message;
    private RadioButton rb_main_location;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.rb_main_schedule));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headView = navigationView.getHeaderView(0);
        initUI();


    }

    private void addLeanCloudData() {
        AVObject Parents = new AVObject("Parents");// 构建对象
//        String foods[] = {"西红柿炒鸡蛋","牛奶","油饼","凉拌三丝"};
//        String acts[] = {"打桥牌","跳广场舞","京剧演出","散步"};
//        String sleeps[] = {"睡眠状况良好","午睡时间适中","无瞌睡状况"};
//        scheduleShow.put("food", foods);
//        scheduleShow.put("act", acts);

        JSONObject foodObject = new JSONObject();
        foodObject.put("food_1", "炒菜花");
        foodObject.put("food_2", "豆腐");
        foodObject.put("food_3", "凉拌三丝");

        JSONObject actObject = new JSONObject();
        actObject.put("act_1", "散步");
        actObject.put("act_2", "跳广场舞");
        actObject.put("act_3", "京剧演出");

        JSONObject sleepObject = new JSONObject();
        sleepObject.put("sleep_1", "睡眠状况良好");
        sleepObject.put("sleep_2", "午睡时间适中");
        sleepObject.put("sleep_3", "无瞌睡状况");

        JSONObject picObject = new JSONObject();
        picObject.put("pic_1", "http://ww3.sinaimg.cn/mw690/49a565f3jw1f6ju4fx2vnj21jk13dn8w.jpg");
        picObject.put("pic_2", "http://ww2.sinaimg.cn/mw690/49a565f3jw1f6ju4hqrbej21jk10349g.jpg");
        picObject.put("pic_3", "http://ww4.sinaimg.cn/mw690/49a565f3jw1f6ju4jj6ixj21jk1127fl.jpg");

        List<String> msgList = new ArrayList<>();
        msgList.add("这是第一条消息");
        msgList.add("这是第二条消息");
        msgList.add("这是第三条消息");

        Parents.put("food", foodObject);
        Parents.put("act", actObject);
        Parents.put("sleep", sleepObject);
        Parents.put("pic", picObject);
        Parents.put("message", msgList);

        Parents.put("priority", 1);// 设置优先级
        Parents.put("children", "18801253526");// 设置子女账号
        Parents.put("nurse", "18801253526");// 设置护工账号
        Parents.put("name", "王大爷");//设置老人姓名
        Parents.saveInBackground();// 保存到服务端
    }

    private void initUI() {
        tv_username = (TextView) headView.findViewById(R.id.actv_username);
        iv_avatar = (ImageView) headView.findViewById(R.id.iv_avatar);

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE)) {
                    tv_username.setText(PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });
        rb_main_schedule = (RadioButton) findViewById(R.id.rb_main_schedule);
        rb_main_gallery = (RadioButton) findViewById(R.id.rb_main_gallery);
//        rb_main_message = (RadioButton) findViewById(R.id.rb_main_message);
        rb_main_location = (RadioButton) findViewById(R.id.rb_main_location);

        rb_main_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.loginStatus(getBaseContext())) {
                    setFragmentVisible(R.id.fg_main_schedule);
                    toolbar.setTitle(getString(R.string.rb_main_schedule));
                } else {

                }

            }
        });
        rb_main_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.loginStatus(getBaseContext())) {
                    setFragmentVisible(R.id.fg_main_gallery);
                    toolbar.setTitle(getString(R.string.rb_main_gallery));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

            }
        });
//        rb_main_message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonUtil.loginStatus(getBaseContext())) {
//                    setFragmentVisible(R.id.fg_main_message);
//                    toolbar.setTitle(getString(R.string.rb_main_message));
//                } else {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                }
//
//            }
//        });
        rb_main_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtil.loginStatus(getBaseContext())) {
                    setFragmentVisible(R.id.fg_main_location);
                    toolbar.setTitle(getString(R.string.rb_main_location));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

            }
        });

        setFragmentVisible(R.id.fg_main_schedule);
        Logger.init("logger");
    }

    private void setFragmentVisible(int selectedFragmentId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int[] ids = {R.id.fg_main_schedule, R.id.fg_main_gallery, R.id.fg_main_location};
        for (int id : ids) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(id);
            if (id == selectedFragmentId) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE)) {
            tv_username.setText(PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE));
        } else {
            tv_username.setText(getString(R.string.action_touchtologin));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //增加一个类，ScheduleShow，存储没有登录的时候日程的信息
            addLeanCloudData();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bindparent) {
            if (null != PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE)) {
                if (null != PreferencesUtil.getString(MainActivity.this, UserConfig.PRENTID)) {
                    Toast.makeText(MainActivity.this,"您已经关联过相关老人，无需再次关联",Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(MainActivity.this, BindParentActivity.class));
                }
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }



        } else if (id == R.id.nav_logout) {
            tipDialog = new AlertDialog.Builder(MainActivity.this)
                    .setMessage(getString(R.string.tip_logout))
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PreferencesUtil.putString(getBaseContext(), UserConfig.MOBILE, null);
                            PreferencesUtil.putString(getBaseContext(), UserConfig.PRENTID, null);

                            AVUser.logOut();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
            if (null != PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE)) {
                tipDialog.show();
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        } else if (id == R.id.nav_sendmsg) {

            if (null != PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE)) {
                if (null!= PreferencesUtil.getString(getBaseContext(),UserConfig.PRENTID)){
                    startActivity(new Intent(MainActivity.this,MessageActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this,BindParentActivity.class));
                }

            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_feedback) {
            if (null != PreferencesUtil.getString(getBaseContext(), UserConfig.MOBILE)) {
                FeedbackAgent agent = new FeedbackAgent(MainActivity.this);
                agent.startDefaultThreadActivity();
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

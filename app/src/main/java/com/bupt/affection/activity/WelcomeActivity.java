package com.bupt.affection.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.bupt.affection.R;
import com.bupt.affection.common.PreferencesUtil;
import com.bupt.affection.common.UserConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        AVOSCloud.initialize(this, "48X5CbKCMh8qdAutJpkFNTr7-gzGzoHsz", "kbhPFjfDTyXknMlD9MFuXsC0");

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addLeanCloudData();
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }

    private void addLeanCloudData() {
        AVObject Parents = new AVObject("Parents");// 构建对象
//        String foods[] = {"西红柿炒鸡蛋","牛奶","油饼","凉拌三丝"};
//        String acts[] = {"打桥牌","跳广场舞","京剧演出","散步"};
//        String sleeps[] = {"睡眠状况良好","午睡时间适中","无瞌睡状况"};
//        scheduleShow.put("food", foods);
//        scheduleShow.put("act", acts);
        List<String> foodList = new ArrayList<>();
        foodList.add("炒菜花");
        foodList.add("炒菜花0");
        foodList.add("炒菜花1");
        foodList.add("炒菜花2");

        List<String> actList = new ArrayList<>();
        actList.add("跳广场舞");
        actList.add("跳广场舞1");
        actList.add("跳广场舞3");
        actList.add("跳广场舞4");

        List<String> sleepList = new ArrayList<>();
        sleepList.add("睡眠状况良好");
        sleepList.add("睡眠状况良好0");
        sleepList.add("睡眠状况良好1");
        sleepList.add("睡眠状况良好2");
        sleepList.add("睡眠状况良好3");


        List<String> picList = new ArrayList<>();
        picList.add("http://ww3.sinaimg.cn/mw690/49a565f3jw1f6ju4fx2vnj21jk13dn8w.jpg");
        picList.add("http://ww2.sinaimg.cn/mw690/49a565f3jw1f6ju4hqrbej21jk10349g.jpg");
        picList.add("http://ww4.sinaimg.cn/mw690/49a565f3jw1f6ju4jj6ixj21jk1127fl.jpg");

        List<String> msgList = new ArrayList<>();
        msgList.add("这是第一条消息");
        msgList.add("这是第二条消息");
        msgList.add("这是第三条消息");

        Parents.put("food", foodList);
        Parents.put("act", actList);
        Parents.put("sleep", sleepList);
        Parents.put("pic", picList);
        Parents.put("message", msgList);

        Parents.put("priority", 1);// 设置优先级
        Parents.put("children", "18801253526");// 设置子女账号
        Parents.put("nurse", "18801253526");// 设置护工账号
        Parents.put("name", "王大爷");//设置老人姓名

        Parents.put("longitude", "116.355932");//设置经度
        Parents.put("latitude", "39.963201");//设置纬度

        Parents.saveInBackground();// 保存到服务端
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
        if (!mVisible) {
            if (PreferencesUtil.getString(WelcomeActivity.this, UserConfig.MOBILE)!=null){
                if (PreferencesUtil.getString(WelcomeActivity.this,UserConfig.PRENTID)!=null){
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this, BindParentActivity.class));
                }
            }else {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }

            finish();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}

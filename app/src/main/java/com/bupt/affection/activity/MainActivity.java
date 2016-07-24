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

import com.bupt.affection.R;
import com.bupt.affection.common.BaseActivity;
import com.bupt.affection.common.PreferencesUtil;
import com.bupt.affection.common.UserConfig;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView tv_username;
    private View headView;
    private ImageView iv_avatar;
    private Dialog tipDialog;
    private RadioButton rb_main_schedule;
    private RadioButton rb_main_gallery;
    private RadioButton rb_main_message;
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
        rb_main_message = (RadioButton) findViewById(R.id.rb_main_message);
        rb_main_location = (RadioButton) findViewById(R.id.rb_main_location);

        rb_main_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentVisible(R.id.fg_main_schedule);
                toolbar.setTitle(getString(R.string.rb_main_schedule));
            }
        });
        rb_main_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentVisible(R.id.fg_main_gallery);
                toolbar.setTitle(getString(R.string.rb_main_gallery));
            }
        });
        rb_main_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentVisible(R.id.fg_main_message);
                toolbar.setTitle(getString(R.string.rb_main_message));
            }
        });
        rb_main_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentVisible(R.id.fg_main_location);
                toolbar.setTitle(getString(R.string.rb_main_location));
            }
        });

        setFragmentVisible(R.id.fg_main_schedule);

    }

    private void setFragmentVisible(int selectedFragmentId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int[] ids = {R.id.fg_main_schedule, R.id.fg_main_gallery, R.id.fg_main_message};
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
        }else{
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_switchtoother) {
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
            tipDialog = new AlertDialog.Builder(MainActivity.this)
                    .setMessage(getString(R.string.tip_logout))
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PreferencesUtil.putString(getBaseContext(), UserConfig.MOBILE, null);
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
            tipDialog.show();

        } else if (id == R.id.nav_finishapp) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_set) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

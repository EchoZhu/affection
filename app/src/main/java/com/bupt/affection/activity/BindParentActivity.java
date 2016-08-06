package com.bupt.affection.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bupt.affection.R;
import com.bupt.affection.adapter.SearchAdapter;
import com.bupt.affection.common.BaseActivity;
import com.bupt.affection.common.PreferencesUtil;
import com.bupt.affection.common.UserConfig;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class BindParentActivity extends BaseActivity {

    private ImageView iv_common_back;
    private EditText et_search_content;
    private ImageView iv_search_clear;
    private ListView lv_search;

    private String searchContent;
    private ProgressDialog dialog;

    private List<String> dataList;
    private SearchAdapter adapter;
    private String parentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_parent);
        initUI();

        dataList = new ArrayList<>();
        adapter = new SearchAdapter(this, dataList);
        lv_search.setAdapter(adapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentID = dataList.get(position).split(":")[1];
                Logger.d(parentID);
                AlertDialog.Builder builder = new AlertDialog.Builder(BindParentActivity.this);
                builder.setMessage(getString(R.string.confirm));
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferencesUtil.putString(BindParentActivity.this, UserConfig.PRENTID, parentID);
                        finish();
                    }
                });
                builder.create().show();
            }
        });
        setSearchListener();
    }

    private void setSearchListener() {

        et_search_content.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    dialog.show();
                    searchContent = et_search_content.getText().toString();
                    if (!TextUtils.isEmpty(searchContent)) {
//                        Toast.makeText(BindParentActivity.this,"123",Toast.LENGTH_LONG).show();
                        AVQuery<AVObject> avQuery = new AVQuery<>("Parents");
                        avQuery.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                boolean exist = false;
                                for (AVObject parent : list) {
                                    String name = parent.get("name").toString();
                                    if (name.equals(searchContent)) {
//                                        Toast.makeText(BindParentActivity.this,searchContent,Toast.LENGTH_LONG).show();
                                        Logger.d(name);
                                        String data = name+":"+parent.getObjectId();
                                        dataList.add(data);
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                        exist = true;
                                    }
                                }
                                if (!exist){
                                    dialog.dismiss();
                                    Toast.makeText(BindParentActivity.this,"未查到相应老人",Toast.LENGTH_LONG).show();
                                }


                            }
                        });



                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.iv_search_clear:
                et_search_content.setText("");
                break;
        }
    }

    private void initUI() {
        iv_common_back = (ImageView) findViewById(R.id.iv_common_back);
        iv_search_clear = (ImageView) findViewById(R.id.iv_search_clear);
        et_search_content = (EditText) findViewById(R.id.et_search_content);
        lv_search = (ListView) findViewById(R.id.lv_search);
        setListener(iv_common_back, iv_search_clear);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.action_searching));
    }

}

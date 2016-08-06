package com.bupt.affection.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.bupt.affection.R;
import com.bupt.affection.adapter.MessageAdapter;
import com.bupt.affection.common.BaseActivity;
import com.bupt.affection.common.PreferencesUtil;
import com.bupt.affection.common.UserConfig;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {

    private ImageView iv_back;
    private ListView lv_feedback;
    private EditText et_feedback_input;
    private Button btn_feedback_send;
    private SwipeRefreshLayout refresh;
    private MessageAdapter adapter;
    private List<String> messageList;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initUI();

        messageList = new ArrayList<>();
//        messageList.add("1");
//        messageList.add("2");
//        messageList.add("3");
        adapter = new MessageAdapter(MessageActivity.this,messageList);
        lv_feedback.setAdapter(adapter);
        getMessageFromLeancloud();
    }

    private void getMessageFromLeancloud() {
        id = PreferencesUtil.getString(MessageActivity.this, UserConfig.PRENTID);
        AVQuery<AVObject> avQuery = new AVQuery<>("Parents");
        avQuery.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                List<String> dataList = new ArrayList<String>();
                dataList = (List<String>) avObject.get("message");
                messageList.clear();
                for (String data :dataList){
                    messageList.add(data);
                    Logger.d(data);
                }
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_feedback_send:
//                Toast.makeText(MessageActivity.this,et_feedback_input.getText(),Toast.LENGTH_LONG).show();

                // 第一参数是 className,第二个参数是 objectId
                AVObject parent = AVObject.createWithoutData("Parents", id);
                messageList.add(et_feedback_input.getText().toString().trim());
                parent.put("message", messageList);
                // 保存到云端
                parent.saveInBackground();
                break;
        }
    }

    private void initUI() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_feedback = (ListView) findViewById(R.id.lv_feedback);
        et_feedback_input = (EditText) findViewById(R.id.et_feedback_input);
        btn_feedback_send = (Button) findViewById(R.id.btn_feedback_send);


        refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessageFromLeancloud();
            }
        });
        setListener(iv_back,btn_feedback_send);
    }

}

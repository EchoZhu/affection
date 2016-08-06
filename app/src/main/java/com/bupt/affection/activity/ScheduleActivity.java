package com.bupt.affection.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bupt.affection.R;
import com.bupt.affection.common.BaseActivity;

public class ScheduleActivity extends BaseActivity {

    private ImageView iv_common_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        iv_common_back = (ImageView) findViewById(R.id.iv_common_back);
        setListener(iv_common_back);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_common_back:
                finish();
                break;
            default:
                break;
        }
    }
}

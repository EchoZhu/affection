package com.bupt.affection.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by zhuyikun on 7/23/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //initLeanCloud();
    }
    private void initLeanCloud() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"48X5CbKCMh8qdAutJpkFNTr7-gzGzoHsz","kbhPFjfDTyXknMlD9MFuXsC0");
    }
}

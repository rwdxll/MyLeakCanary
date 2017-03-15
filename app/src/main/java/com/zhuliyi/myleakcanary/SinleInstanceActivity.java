package com.zhuliyi.myleakcanary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhuliyi.myleakcanary.leak.SingleInstance;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 单例生命周期和应用一样的长，如果单例中存在短生命周期其他对象的引用，则无法释放该对象，造成了内存泄漏
 */
public class SinleInstanceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        ButterKnife.bind(this);
        SingleInstance instance = SingleInstance.getInstance(this);
        //正常单例
//        NormalSingleInstance instance = NormalSingleInstance.getInstance(this);
    }

    @OnClick(R.id.btn_return)
    public void onClick() {
        finish();
    }
}

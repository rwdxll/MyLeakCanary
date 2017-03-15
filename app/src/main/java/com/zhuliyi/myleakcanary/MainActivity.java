package com.zhuliyi.myleakcanary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sendBroad();
    }
    //5s发送一次广播
    private void sendBroad(){
        final Intent intent=new Intent(ResNoCloseActivity.ACTION_TEST);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(intent);
                handler.postDelayed(this,5000);
            }
        },5000);

    }
    @OnClick({R.id.btn_single_instance,R.id.btn_handler,R.id.btn_nostatic_subclass,R.id.btn_thread,R.id.btn_res_no_close})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_single_instance:
                startActivity(new Intent(this,SinleInstanceActivity.class));
                break;
            case R.id.btn_handler:
                startActivity(new Intent(this,HandlerActivity.class));
                break;
            case R.id.btn_nostatic_subclass:
                startActivity(new Intent(this,NoStaticSubClassActivity.class));
                break;
            case R.id.btn_thread:
                startActivity(new Intent(this,ThreadActivity.class));
                break;
            case R.id.btn_res_no_close:
                startActivity(new Intent(this,ResNoCloseActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler=null;
    }
}

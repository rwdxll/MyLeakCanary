package com.zhuliyi.myleakcanary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资源未关闭导致的内存泄漏
 * BraodcastReceiver，ContentObserver，File，Cursor，Stream，Bitmap等资源的代码，应该在Activity销毁时及时关闭或者注销，否则这些资源将不会被回收，造成内存泄漏。
 */
public class ResNoCloseActivity extends AppCompatActivity {

    public static final String ACTION_TEST = "action_test";
    @BindView(R.id.image)
    ImageView image;
    Bitmap bitmap;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_TEST)) {
                Toast.makeText(ResNoCloseActivity.this, "这是一条广播信息", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_no_close);
        ButterKnife.bind(this);
        initReceiver();
        initBitmap();

    }

    //这几个代码在本测试LeakCanary检测不到
    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TEST); //为BroadcastReceiver指定action，即要监听的消息名字。
        registerReceiver(receiver, intentFilter); //注册监听
    }

    private void initBitmap(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth=dm.widthPixels;
        bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.pic);
        //图片太大，要做一下处理才能显示
        if(bitmap.getWidth()<=screenWidth){
            image.setImageBitmap(bitmap);
        }else{
            Bitmap bmp=Bitmap.createScaledBitmap(bitmap, screenWidth, bitmap.getHeight()*screenWidth/bitmap.getWidth(), true);
            image.setImageBitmap(bmp);
        }


    }
    @OnClick({R.id.btn_close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                finish();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //正常代码
//        unregisterReceiver(receiver);
//        receiver=null;
        //图片很大，内存泄漏严重，必须回收内存
//        if(bitmap!=null){
//            bitmap.recycle();
//            bitmap=null;
//        }
        App.getRefWatcher(this);
    }
}

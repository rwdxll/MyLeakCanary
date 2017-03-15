package com.zhuliyi.myleakcanary;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * handler未处理消息或正在处理消息，当acitivity退出时无法释放内存，从而导致内存泄漏
 */
public class HandlerActivity extends AppCompatActivity {

    private int initTime = 10;
    Handler handler = new Handler();
    @BindView(R.id.txt_num)
    TextView txtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        countDownTime();
    }

    private void countDownTime() {
        txtNum.setText(initTime + "");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (initTime > 0) {
                    initTime -= 1;
                    showTime();
                    handler.postDelayed(this,1000);
                }
            }
        }, 1000);
    }

    private void showTime() {
        txtNum.setText(initTime + "");
    }

    @OnClick(R.id.btn_return)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //正常代码
//        handler.removeCallbacksAndMessages(null);
//        handler=null;
    }
}

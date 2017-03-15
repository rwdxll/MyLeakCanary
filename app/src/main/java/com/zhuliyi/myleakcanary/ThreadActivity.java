package com.zhuliyi.myleakcanary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 线程导致内存泄漏
 * activity退出时，线程里面仍然有activity的引用，会导致activity无法释放，从而导致内存泄漏
 */
public class ThreadActivity extends AppCompatActivity {
    @BindView(R.id.txt_msg)
    TextView txtMsg;
    private AsyncTask<Void, Void, String> asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);
        txtMsg.setText("线程执行中。。。");
        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "线程执行完成";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                txtMsg.setText(s);
            }
        };
        asyncTask.execute();
    }

    @OnClick(R.id.btn_return)
    public void onClick() {
        finish();
    }

    private void stopThread(){
        if(asyncTask!=null&&!asyncTask.isCancelled()){
            asyncTask.cancel(true);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //正常代码
//        stopThread();
    }
}

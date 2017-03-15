package com.zhuliyi.myleakcanary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 非静态内部类导致内存泄漏
 * 非静态的内部类默认会持有外部类的引用,如果使用非静态内部类创建了一个静态的实例，该静态实例一直会持有该Activity的引用,导致Activity不能正常回收
 */
public class NoStaticSubClassActivity extends AppCompatActivity {


    private static Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_static_sub_class);
        ButterKnife.bind(this);
        if (student == null) {
            student = new Student("leory", "123");
        }
    }

    @OnClick(R.id.btn_return)
    public void onClick() {
        finish();
    }


    //正常代码，改为静态内部类，不持有activity引用
    class Student {
        private String name;
        private String no;

        public Student(String name, String no) {
            this.name = name;
            this.no = no;
        }
    }
}

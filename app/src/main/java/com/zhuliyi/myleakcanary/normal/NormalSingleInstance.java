package com.zhuliyi.myleakcanary.normal;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 单例正常
 * Created by Leory on 2017/3/15.
 */

public class NormalSingleInstance {
    private WeakReference<Context> context;
    private static NormalSingleInstance instance;
    private NormalSingleInstance(Context context){
        this.context=new WeakReference<>(context);
    }
    public static NormalSingleInstance getInstance(Context contex){
        if(instance==null){
            synchronized (NormalSingleInstance.class) {
                if(instance==null) {
                    instance = new NormalSingleInstance(contex);
                }
            }
        }
        return instance;
    }
    private Context getContext(){
        if(context!=null){
            return context.get();
        }
        return null;
    }
}

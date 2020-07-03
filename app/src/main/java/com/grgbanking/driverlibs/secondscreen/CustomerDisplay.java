package com.grgbanking.driverlibs.secondscreen;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.grgbanking.driverlibs.R;

/**
 * Author: gongxiaobiao
 * Date: on 2020/4/26 8:38
 * Email: 904430803@qq.com
 * Description:
 */
public class CustomerDisplay extends Presentation {
    private Context mContext;
    private TextView tv;

    public CustomerDisplay(Context outerContext, Display display) {
        super(outerContext,display);
        mContext = outerContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_customer_display);
        tv = findViewById(R.id.tv);
    }

    /**
     * 设置左侧预购买商品信息
     */
    public void setProductList(String str){
        tv.setText(str);
    }

    /**
     * 设置右侧显示的广告
     */
    public void setAdvertisement(){
        //
    }
}
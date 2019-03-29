package com.zhy.MyKF;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paradigm.botkit.BotKitClient;
import com.paradigm.botkit.ChatActivity;
import com.paradigm.botlib.VisitorInfo;
import com.zhy.ccbCricleMenu.R;

public class KF2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置访客信息
        VisitorInfo visitorInfo = new VisitorInfo();
        visitorInfo.userName = "wyj";
        visitorInfo.nickName = "wyj";
        visitorInfo.mail = "1149691900@qq.com";
        visitorInfo.phone = "18106951237";
        BotKitClient.getInstance().setVisitor(visitorInfo);

        // 调出客服页面
        Intent intent = new Intent();
        intent.setClass(this, ChatActivity.class);
        startActivity(intent);
    }
}

package com.zhy.MyKF;

import android.app.Application;

import com.paradigm.botkit.BotKitClient;
import com.paradigm.botkit.ChatActivity;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        String accessKey = "NDk5MiMzZDU1Nzk5Yi05NGJlLTQxNzctOTUyNC00NDM0ZmI5Mjk4ODkjN2EyOWU2OWUtN2Y1MC00YjU5LTllNWEtYjQzYWEwNzk3NTdiIzY3NjUwMGVlNjBmNzU0ODhlOGZmYThkN2JkYjYxYTUz";
        BotKitClient.getInstance().init(this, accessKey);
    }
}

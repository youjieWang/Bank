package com.zhy.joh.qiyudemo;

/**
 * @author Joh_hz
 * @date 2019/1/29
 * @Description 七鱼用户信息
 */
public class YSFUser {
    String key;
    String value;

    public YSFUser(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

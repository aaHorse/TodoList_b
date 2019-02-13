package com.example.zexiger.todolist_b.LitePal_general;

import org.litepal.crud.DataSupport;

public class Contents extends DataSupport {

    private String id_string;//用户唯一身份id,G+数字是general注册的id，QQ+数字是QQ注册的id
    //用户的密码不在这里存储，用另一个加密的SharedPreferences存
    private String content_text;//user存进来的内容
    private String date;//content最后一次编辑的日期，年月日
    private String time;//content最后一次编辑的时间，精确到分钟
    private int level;//content的等级，有三级，1级优先处理，3级最后处理
    private boolean done;//content条目是否已经完成，已经完成true，未完成false

    public String getId_string() {
        return id_string;
    }

    public void setId_string(String id_string) {
        this.id_string = id_string;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

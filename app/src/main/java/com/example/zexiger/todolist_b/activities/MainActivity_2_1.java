package com.example.zexiger.todolist_b.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.zexiger.todolist_b.general.Contents;
import com.example.zexiger.todolist_b.R;

import static com.example.zexiger.todolist_b.activities.MainActivity.adapterobj;

public class MainActivity_2_1 extends BaseActivity {
    private String activity_str;//用于区分从不同活动过来的
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2_1);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        activity_str=intent.getStringExtra("activity");
    }

    public String getActivity_str() {
        return activity_str;
    }

    public void setActivity_str(String activity_str) {
        this.activity_str = activity_str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*
    * 按了取消键销毁当前的碎片和活动
    * */
    public void back(){
        finish();
    }
    /*
     * 点击手机返回键
     * */
    @Override
    public void onBackPressed() {
        Contents item=new Contents();
        item.setToDefault("Checked");
        item.updateAll("id_string=?",id);
        if(activity_str.equals("MainActivity")){
            adapterobj.refreshAll();
        }else{
            Log.d("ttttt","MainActivity_2_1,无匹配的activity");
        }
        super.onBackPressed();
    }
}

package com.example.zexiger.todolist_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.zexiger.todolist_b.LitePal_general.Contents;

import static com.example.zexiger.todolist_b.LitePal_general.Search_result.itemInit;
import static com.example.zexiger.todolist_b.MainActivity.adapterobj;

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
        Log.d("ttttt","activity:"+activity_str);

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
    * 按了取消键销毁当前的碎片
    * */
    public void back(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.activity_main_2_1_fragment));
        fragmentTransaction.commit();
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

package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zexiger.todolist_b.BaseActivity;
import com.example.zexiger.todolist_b.FirstActivity;
import com.example.zexiger.todolist_b.R;


public class Succeed extends BaseActivity {
    private String user_id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        //返回通过Toolbar设置返回键，在后面有重写点击响应函数
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        Intent intent=getIntent();
        name=intent.getStringExtra("user_name");
        user_id=intent.getStringExtra("user_id");

        TextView textView=(TextView) findViewById(R.id.succeedz_tv);
        TextView textView_2=(TextView)findViewById(R.id.textView7);
        textView.setText("hi,"+name);
        textView_2.setText(user_id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            jump();
        }
        return true;
    }

    /*
     * 点击手机返回键
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        jump();
    }

    /*
    * 在Succeed.java活动，跳转到登录界面FirstActivity.java
    * 使用Bundle传数据，传的数据为注册成功界面分配的账号，该数据将把登录界面的账号那里setText
    * */
    private void jump(){
        Bundle bundle=new Bundle();
        bundle.putString("user_id",user_id);
        Intent intent=new Intent();
        intent.setClass(Succeed.this,FirstActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

package com.example.zexiger.todolist_b;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zexiger.todolist_b.LitePal_general.Create_user;
import com.example.zexiger.todolist_b.LitePal_general.Sign_in_general;

import org.litepal.LitePal;

/*
* 登录界面，有碎片引入
* */
public class FirstActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        /*
        * 登录页面的注册按钮，点击后，跳转到注册页面
        * */
        button=(Button)findViewById(R.id.sign_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,Create_user.class);
                startActivity(intent);
            }
        });

       final View view = getWindow().getDecorView();
        final Context context=getApplicationContext();

        //创建LitePal_general数据库
        LitePal.getDatabase();
        Sign_in_general.sign_in(view,context);

    }
}

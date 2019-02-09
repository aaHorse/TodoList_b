package com.example.zexiger.todolist_b;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zexiger.todolist_b.LitePal_general.Sign_in_general;

import org.litepal.LitePal;

public class FirstActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

       final View view = getWindow().getDecorView();
        final Context context=getApplicationContext();

        //创建LitePal_general数据库
        LitePal.getDatabase();
        Sign_in_general.sign_in(view,context);

    }
}

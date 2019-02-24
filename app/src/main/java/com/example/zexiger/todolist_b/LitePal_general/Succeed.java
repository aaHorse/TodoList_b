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
        String user_id;
        String name;
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
            Intent intent=new Intent(Succeed.this,FirstActivity.class);
            startActivity(intent);
        }
        return true;
    }
}

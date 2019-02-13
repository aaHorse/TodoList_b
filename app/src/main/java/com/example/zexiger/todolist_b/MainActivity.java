package com.example.zexiger.todolist_b;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Add_content;
import com.example.zexiger.todolist_b.floating_action_button.Fab_add;

import org.litepal.LitePal;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    FloatingActionButton floatingActionButton;
    View cv;
    Context context;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingActionButton = findViewById(R.id.floating_action_button);

        cv = getWindow().getDecorView();
        context=getApplicationContext();

        //创建LitePal数据库
        //LitePal.getDatabase();

        com.example.zexiger.todolist_b.recyclerview.ItemInit.initItems(cv,context);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_content.class);
                intent.putExtra("id",id);
                startActivityForResult(intent,2);
            }
        });
        com.example.zexiger.todolist_b.recyclerview_show.ItemInit.initItems(cv,context,id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_1:
                Toast.makeText(this, "item_1", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        com.example.zexiger.todolist_b.recyclerview_show.ItemInit.initItems(cv,context,id);
    }
}

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Add_content;
import com.example.zexiger.todolist_b.SQLite_User.Query;
import com.example.zexiger.todolist_b.floating_action_button.Fab_add;

import org.litepal.LitePal;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    FloatingActionButton floatingActionButton;
    View cv;
    Context context;

    private TextView tv_name;

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
        tv_name=(TextView)findViewById(R.id.id_name);
        floatingActionButton = findViewById(R.id.floating_action_button);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        Log.d("ttttt","id"+id);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cv = getWindow().getDecorView();
        context=getApplicationContext();

        /*
         * 在侧滑菜单设置用户名
         * */
        String name=Query.get_name(id,context);
        tv_name.setText(name);

        com.example.zexiger.todolist_b.recyclerview.ItemInit.initItems(cv,context);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_content.class);
                intent.putExtra("id",id);
                startActivityForResult(intent,2);
            }
        });

        //显示list界面
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

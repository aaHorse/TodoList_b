package com.example.zexiger.todolist_b;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Add_content;
import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.SQLite_User.Query;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    FloatingActionButton floatingActionButton;
    View cv;
    Context context;

    private TextView tv_name;
    private TextView tv_id;

    private String id;

    public static ItemInit adapterobj;
    public static com.example.zexiger.todolist_b.recyclerview.ItemInit adapterobj_2;

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
        tv_name=(TextView)findViewById(R.id.name);
        floatingActionButton = findViewById(R.id.floating_action_button);
        tv_id=(TextView)findViewById(R.id.id);

        Intent intent=getIntent();
        //intent.putExtra("hhh",id);//该行代码
        id=intent.getStringExtra("id");

        Log.d("ttttt","id"+id);

/*        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        cv = getWindow().getDecorView();
        context=getApplicationContext();

        /*
         * 在侧滑菜单设置用户名
         * */
        String name=Query.get_name(id,context);
        tv_name.setText(name);
        tv_id.setText(id);

        adapterobj_2=new com.example.zexiger.todolist_b.recyclerview.ItemInit();
        adapterobj_2.initItems(cv,context);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Add_content.class);
                intent.putExtra("id",id);
                intent.putExtra("flag",1);
                startActivityForResult(intent,2);
            }
        });

        //显示list界面
        adapterobj=new com.example.zexiger.todolist_b.recyclerview_show.ItemInit();
        adapterobj.initItems(cv,context,id);
    }

/*    @Override
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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("ttttt","在这!!!");
        Contents contents=(Contents)data.getSerializableExtra("user");
        if(contents!=null){
            adapterobj.refresh();
        }else{
            Toast.makeText(MainActivity.this,"添加取消",Toast.LENGTH_SHORT).show();
        }
    }
}

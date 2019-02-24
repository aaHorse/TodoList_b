package com.example.zexiger.todolist_b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Add_content;
import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.LitePal_general.Search_result;
import com.example.zexiger.todolist_b.SQLite_User.Query;
import com.example.zexiger.todolist_b.recyclerview_show.ItemAdapter;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import km.lmy.searchview.SearchView;


public class MainActivity extends AppCompatActivity {
    private Toolbar activityToolbar;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private View cv;
    private Context context;
    private TextView tv_name;
    private TextView tv_id;
    private SearchView searchView;
    private String id;

    public static com.example.zexiger.todolist_b.recyclerview_show.ItemInit adapterobj;
    private static com.example.zexiger.todolist_b.recyclerview.ItemInit adapterobj_2;

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
        activityToolbar = findViewById(R.id.activity_toolbar);
        searchView = findViewById(R.id.searchView);

        cv = getWindow().getDecorView();
        context=getApplicationContext();

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        Log.d("ttttt","id"+id);

        /*
        * 设置Toolbar
        * */
        activityToolbar.setTitle(this.getTitle());
        activityToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(activityToolbar);

        /*
        * 搜索框
        * */
        searchView();

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
                startActivity(intent);
            }
        });

        //显示list界面
        adapterobj=new com.example.zexiger.todolist_b.recyclerview_show.ItemInit(cv,context,id);
        adapterobj.initItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);//加载menu文件到布局
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //自动打开关闭SearchView
                searchView.autoOpenOrClose();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * 搜索框
    * */
    private void searchView(){
        //设置搜索框默认值
        //searchView.setSearchEditText("test string");
        //设置历史记录点击事件
        searchView.setHistoryItemClickListener(new SearchView.OnHistoryItemClickListener() {
            @Override
            public void onClick(String historyStr, int position) {
                Toast.makeText(MainActivity.this, historyStr, Toast.LENGTH_SHORT).show();
            }
        });

        //设置软键盘搜索按钮点击事件
        searchView.setOnSearchActionListener(new SearchView.OnSearchActionListener() {
            @Override
            public void onSearchAction(String searchText) {
                searchView.addOneHistory(searchText);
                searchView.close();
                Intent intent=new Intent(MainActivity.this,Search_result.class);
                intent.putExtra("searchText",searchText);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}

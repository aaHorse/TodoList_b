package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.recyclerview_show.ItemAdapter;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.example.zexiger.todolist_b.MainActivity.adapterobj;

public class Search_result extends AppCompatActivity {
    private String searchText;
    private String id;
    private Toolbar toolbar;
    private View view;
    private Context context;
    public static ItemInit itemInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        toolbar=(Toolbar)findViewById(R.id.toolbar_3);
        toolbar.setTitle("搜索结果");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        view = getWindow().getDecorView();
        context=getApplicationContext();

        Intent intent=getIntent();
        searchText=intent.getStringExtra("searchText");
        id=intent.getStringExtra("id");

        Log.d("ttttt","搜索1");

        List<Contents> list;
        //list= DataSupport.where("content_text = ?",searchText).find(Contents.class);
       list= DataSupport.where("content_text like ? and id_string=?","%" + searchText + "%",id)
               .order("id_string").find(Contents.class);
        Log.d("ttttt","搜索2");

        itemInit=new ItemInit(view,context,id,"Search_result");
        itemInit.initItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            //点击返回按钮，刷新MainActivity
            case android.R.id.home:
                adapterobj.refreshAll();
                finish();
            default:
                Log.d("ttttt","搜索结果的ToolBar出问题");
        }
        return true;
    }
}

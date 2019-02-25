package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.zexiger.todolist_b.BaseActivity;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.zexiger.todolist_b.MainActivity.adapterobj;

public class Search_result extends BaseActivity {
    private static String searchText;
    private static String id;
    private Toolbar toolbar;
    private View view;
    private Context context;
    public static ItemInit itemInit;
    private static List<Contents> list=new ArrayList<>();

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
        Log.d("ttttt",searchText);
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
                break;
            default:
                Log.d("ttttt","搜索结果的ToolBar出问题");
        }
        return true;
    }

    public static List<Contents> getList(){
        list= DataSupport.where("id_string = ? and content_text like ?",id,searchText + "%")
                .find(Contents.class);
        Log.d("ttttt","查询到"+list.size()+"个");
        return list;
    }

    /*
     * 点击手机返回键
     * */
    @Override
    public void onBackPressed() {
        adapterobj.refreshAll();
        super.onBackPressed();
    }
}

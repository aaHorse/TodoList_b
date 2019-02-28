package com.example.zexiger.todolist_b;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import static com.example.zexiger.todolist_b.MainActivity.adapterobj;


public class About extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private int i=1;
    private SwipeFlingAdapterView flingContainer;
    private Toolbar activityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        flingContainer=findViewById(R.id.frame);
        activityToolbar = findViewById(R.id.toolbar_3);

        /*
         * 设置Toolbar
         * */
        activityToolbar.setTitle("About developer");
        activityToolbar.setTitleTextColor(Color.parseColor("#000000"));
        //activityToolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityToolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);

        list = new ArrayList<>();
        list.add("黎家泽");
        list.add("221701414");
        list.add("软件工程");
        list.add("QQ:782821705");
        list.add("GitHub:aaHorse");
        list.add("==");
        list.add("@#*&");
        list.add("#￥￥%￥#@￥#");
        list.add("感谢你的使用");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, list);
        flingContainer.setAdapter(arrayAdapter);

        //左右滑动
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            //如果被移动，就删除
            @Override
            public void removeFirstObjectInAdapter() {
                list.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            //如果左移，删除，和removeFirstObjectInAdapter方法会同时触发
            @Override
            public void onLeftCardExit(Object dataObject) {
                //Toast.makeText(About.this,"Left!",Toast.LENGTH_SHORT).show();
            }

            //如果右移，删除，和removeFirstObjectInAdapter方法会同时触发
            @Override
            public void onRightCardExit(Object dataObject) {
                //Toast.makeText(About.this,"Right!",Toast.LENGTH_SHORT).show();
            }

            //当adapter为空的时候，往里面添加item
            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                list.add(("哈哈哈×"+i));
                arrayAdapter.notifyDataSetChanged();
                i++;
            }

            /*
            * setAlpha设置透明度,即将一个item消失
            * */
            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // 点击
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(About.this,"左右滑动查看更多信息",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    * toolbar的返回键
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                Log.d("ttttt","ToolBar出问题");
        }
        return true;
    }
    /*
    * 手机的返回键
    * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

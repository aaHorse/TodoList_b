package com.example.zexiger.todolist_b;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.recyclerview_show.ItemAdapter_2;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit_2;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.zexiger.todolist_b.LitePal_general.Search_result.itemInit;
import static com.example.zexiger.todolist_b.MainActivity.adapterobj;

public class MainActivity_2 extends BaseActivity {

    private static boolean isAll=false;//是否全选
    private TextView checked_num;
    private ItemInit_2 itemInit_2;

    private Button button;//删除
    private Button button_2;//全选，取消全选
    private Button button_3;//取消
    private View view;
    private Context context;
    private String activity;//用于区分从不同活动过来的，方便返回

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        checked_num=(TextView)findViewById(R.id.tv);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        view = getWindow().getDecorView();
        context =getApplicationContext();

        itemInit_2=new ItemInit_2(view,context,id);
        itemInit_2.inits();

        button=findViewById(R.id.button_delete);
        button_2=findViewById(R.id.button_all);
        button_3=findViewById(R.id.button_cancle);

        //刪除
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_2.setText("全选");
                checked_num.setText("选择项目");
                //对于Boolean的删除查找以及修改，需要注意
                DataSupport.deleteAll(Contents.class,"Checked=?","1");
                itemInit_2.refreshAll();
            }
        });

        //全选，取消全选
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刚开始是默认没有全选，
                //这里实现全选，反全选

                //isAll在这里被反转！！！
                if(isAll==false){
                    isAll=true;
                    button_2.setText("取消全选");
                }else{
                    isAll=false;
                    button_2.setText("全选");
                }
                itemInit_2.itemSetChecked(isAll);
                itemInit_2.refreshAll();
            }
        });

        //取消
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contents item=new Contents();
                item.setToDefault("Checked");
                item.updateAll("id_string=?",id);
                if(activity.equals("MainActivity")){
                    adapterobj.refreshAll();
                }else if(activity.equals("Search_Result")){
                    itemInit.refreshAll();
                }
                finish();
            }
        });
    }
}

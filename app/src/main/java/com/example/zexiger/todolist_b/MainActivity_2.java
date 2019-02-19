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
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_2 extends AppCompatActivity {

    private List<Contents> list=new ArrayList<>();
    private SwipeRecyclerView swipeRecyclerView;
    private LinearLayoutManager layoutManager;
    private ItemAdapter_2 itemAdapter_2;
    private static boolean isAll=false;//是否全选
    private TextView checked_num;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        checked_num=(TextView)findViewById(R.id.tv);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        //将list翻转,主要是要以时间线显示
        list=ItemInit.get_list(id);

       final View cv = getWindow().getDecorView();
       final Context context =getApplicationContext();


        swipeRecyclerView=findViewById(R.id.srv_show_2);
        layoutManager=new LinearLayoutManager(this);
        itemAdapter_2=new ItemAdapter_2(cv,context,list);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setAdapter(itemAdapter_2);

        Button button=findViewById(R.id.button_delete);
        final Button button_2=findViewById(R.id.button_all);
        Button button_3=findViewById(R.id.button_cancle);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size=list.size();
                for(int i=size-1;i >=0;i--){
                    Contents item=list.get(i);
                    if(item.isChecked()==true){
                        list.remove(i);
                        String date_id=item.getDate();
                        DataSupport.deleteAll(Contents.class,"date = ?",date_id);
                    }
                }
                layoutManager=new LinearLayoutManager(MainActivity_2.this);
                itemAdapter_2=new ItemAdapter_2(cv,context,list);
                swipeRecyclerView.setLayoutManager(layoutManager);
                swipeRecyclerView.setAdapter(itemAdapter_2);

                button_2.setText("全选");
                checked_num.setText("选择项目");
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刚开始是默认没有全选，
                //这里实现全选，反全选
                if(isAll==false){
                    isAll=true;
                    button_2.setText("取消全选");
                }else{
                    isAll=false;
                    button_2.setText("全选");
                }
                int size=list.size();
                for(int i=0;i<size;i++){
                    Contents item=list.get(i);
                    item.setChecked(isAll);
                }
                /*
                * 主要是为了刷新标题的显示了多少项
                * */
                itemAdapter_2.notifyDataSetChanged();
            }
        });

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent intent=new Intent(MainActivity_2.this,MainActivity.class);
                startActivity(intent);*/
                for(int i=0;i<list.size();i++){
                    Contents item=list.get(i);
                    item.setChecked(false);
                }
                Intent intent_2=new Intent(MainActivity_2.this,MainActivity.class);
                intent_2.putExtra("id",id);
                startActivity(intent_2);
            }
        });
    }
}

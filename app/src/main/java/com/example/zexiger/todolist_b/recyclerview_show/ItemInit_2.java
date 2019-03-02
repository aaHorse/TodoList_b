package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.R;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.util.List;

public class ItemInit_2 {
    private View view;
    private Context context;
    private String id;

    private SwipeRecyclerView swipeRecyclerView;
    private LinearLayoutManager layoutManager;
    private ItemAdapter_2 adapter;
    private String activity_str;

    private List<Contents> list;

    public ItemInit_2(View view,Context context,String id,String activity_str){
        this.view=view;
        this.context=context;
        this.id=id;
        this.activity_str=activity_str;
    }

    public void inits(){
        Log.d("ttttt","错误1");
        swipeRecyclerView=view.findViewById(R.id.srv_show_2);
        Log.d("ttttt","错误2");
        adapter=new ItemAdapter_2(view,context,id,activity_str);
        Log.d("ttttt","错误3");
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        Log.d("ttttt","错误4");
        swipeRecyclerView.setLayoutManager(layoutManager);
        Log.d("ttttt","错误5");
        swipeRecyclerView.setAdapter(adapter);
        Log.d("ttttt","错误6");
    }

    public void itemSetChecked(boolean isAll){
        list=adapter.get_list(id);
        int size=list.size();
        for(int i=0;i<size;i++){
            Contents item=list.get(i);
            if(isAll){
                //true
                item.setChecked(true);
                item.updateAll("date=?",item.getDate());
            }else{
                //false
                item.setToDefault("Checked");
                item.updateAll("date=?",item.getDate());
            }
        }
        refreshAll();
    }

    public void refreshAll(){
        adapter.notifyAdapter();
    }

}

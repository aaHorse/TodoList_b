package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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

    private List<Contents> list;

    public ItemInit_2(View view,Context context,String id){
        this.view=view;
        this.context=context;
        this.id=id;
    }

    public void inits(){
        swipeRecyclerView=view.findViewById(R.id.srv_show_2);
        adapter=new ItemAdapter_2(view,context,id);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setAdapter(adapter);
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

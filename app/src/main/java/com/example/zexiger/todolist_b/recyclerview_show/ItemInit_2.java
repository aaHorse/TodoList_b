package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.MainActivity_2;
import com.example.zexiger.todolist_b.R;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ItemInit_2 {
    private View view;
    private Context context;
    private String id;

    private SwipeRecyclerView swipeRecyclerView;
    private LinearLayoutManager layoutManager;
    private ItemAdapter_2 itemAdapter_2;

    private List<Contents> list;

    public ItemInit_2(View view,Context context,String id){
        this.view=view;
        this.context=context;
        this.id=id;
    }

    public List<Contents> get_list(String id){
        List<Contents> list_1;
        List<Contents> list_2;
        List<Contents> list_3;
        List<Contents> list_all=new ArrayList<>();
        list_1 = DataSupport.where("id_string=?and level=?",id,"1").find(Contents.class);
        list_2 = DataSupport.where("id_string=?and level=?",id,"2").find(Contents.class);
        list_3 = DataSupport.where("id_string=?and level=?",id,"3").find(Contents.class);

        for(int i=list_3.size()-1;i>=0;i--){
            list_all.add(list_3.get(i));
        }
        for(int i=list_2.size()-1;i>=0;i--){
            list_all.add(list_2.get(i));
        }
        for(int i=list_1.size()-1;i>=0;i--){
            list_all.add(list_1.get(i));
        }
        return list_all;
    }

    public void itemSetChecked(boolean isAll){
        list=get_list(id);
        int size=list.size();
        for(int i=0;i<size;i++){
            if(isAll){
                //true
                Contents item=list.get(i);
                item.setChecked(true);
                item.updateAll("date=?",item.getDate());
            }else{
                //false
                Contents item=list.get(i);
                item.setToDefault("Checked");
                item.updateAll("date=?",item.getDate());
            }
        }
    }

    public void refresh(){
        list=get_list(id);
        swipeRecyclerView=view.findViewById(R.id.srv_show_2);
        layoutManager=new LinearLayoutManager(context);
        itemAdapter_2=new ItemAdapter_2(view,context,list);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setAdapter(itemAdapter_2);
    }

    public void toMainActivity(){
        list=get_list(id);
        for(int i=0;i<list.size();i++){
            Contents item=list.get(i);
            String date=item.getDate();
            item.setToDefault("Checked");
            item.updateAll("date=?",date);
        }
        Intent intent_2=new Intent(context,MainActivity.class);
        intent_2.putExtra("id",id);
        context.startActivity(intent_2);
    }
}

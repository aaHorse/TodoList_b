package com.example.zexiger.todolist_b.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.zexiger.todolist_b.R;;

public class ItemInit {
    private List<Item> list=new ArrayList<>();
    public void initItems(View view, final Context context){
        //初始化
        Item item=new Item();
        item.setName("哈哈哈哈哈");
        list.add(item);
        Item item_2=new Item();
        item_2.setName("设置");
        list.add(item_2);
        Item item_3=new Item();
        item_3.setName("关于");
        list.add(item_3);

        RecyclerView recyclerView= view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter=new ItemAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}

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
    private static List<Item> list=new ArrayList<>();
    public static void initItems(View view, Context context){
        for(int i=1;i<11;i++){
            Item item=new Item();
            item.setName(""+i);
            list.add(item);
        }
        RecyclerView recyclerView= view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter=new ItemAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
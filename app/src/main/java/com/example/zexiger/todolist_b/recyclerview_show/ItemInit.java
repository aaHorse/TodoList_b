package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    private static List<Contents> list = new ArrayList<>();

    /*
    * 进行数据库查找，按level顺序排到list中
    *
    * */
    public static void initItems(View view, final Context context) {
        //查询数据库中，Contents表的所有数据，这里先测试，待完善
        list=DataSupport.findAll(Contents.class);

        Log.d("ttt",list.size()+" ");

        RecyclerView recyclerView = view.findViewById(R.id.rv_show);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter = new ItemAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}

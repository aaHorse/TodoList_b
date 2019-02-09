package com.example.zexiger.todolist_b.All_qq_and_general;

import com.example.zexiger.todolist_b.LitePal_general.Contents;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Show_content {
    public void show_content(){
        List<Contents> content_list=DataSupport.findAll(Contents.class);
        for(Contents content:content_list){
            //recyclerView 实现显示功能
        }
    }
    private boolean id_content(String id){
        return true;
    }
}


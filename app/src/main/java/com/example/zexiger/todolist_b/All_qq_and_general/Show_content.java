package com.example.zexiger.todolist_b.All_qq_and_general;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Show_content {
    public void show_content(){
        List<Contents> content_list=DataSupport.findAll(Contents.class);
        for(Contents content:content_list){
            //
        }
    }
    private boolean id_content(String id){
        return true;
    }
}


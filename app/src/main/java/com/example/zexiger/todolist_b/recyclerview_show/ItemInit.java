package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.MainActivity_2;
import com.example.zexiger.todolist_b.R;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemLongClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.ListPopupWindow.MATCH_PARENT;
import static org.litepal.crud.DataSupport.findAll;
import static org.litepal.crud.DataSupport.findFirst;

public class ItemInit {
    private static ItemAdapter adapter;
    private static List<Contents>list;

    /*
    * 进行数据库查找，按level顺序排到list中
    *
    * */
    public static void initItems(final View view, final Context context, final String id) {
        //查询数据库中，Contents表的所有数据，这里先测试，待完善,
        //该id用于查找当前用户的content
        list=DataSupport.where("id_string = ?",id).find(Contents.class);

        /////
        SwipeRecyclerView swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv_show);

        if (true){
            /////////////////////////////////

//            DefaultItemDecoration borderItemDecoration=new DefaultItemDecoration(Color.parseColor("#000000"));
//            swipeRecyclerView.addItemDecoration(borderItemDecoration);

            swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Toast.makeText(context,"hhhhhhhhhhhh",Toast.LENGTH_SHORT).show();
                }
            });

            swipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                    SwipeMenuItem swipeMenuItem=new SwipeMenuItem(context);
                    swipeMenuItem.setText("delete");
                    swipeMenuItem.setTextSize(50);
                    swipeMenuItem.setBackgroundColor(Color.parseColor("#FF4500"));
                    swipeMenuItem.setTextColor(Color.parseColor("#000000"));
                    swipeMenuItem.setHeight(MATCH_PARENT);
                    swipeMenuItem.setWidth(500);
                    rightMenu.addMenuItem(swipeMenuItem);

                    SwipeMenuItem swipeMenuItem_2=new SwipeMenuItem(context)
                            .setText("add")
                            .setTextSize(50)
                            .setBackgroundColor(Color.parseColor("#664500"))
                            .setTextColor(Color.parseColor("#000000"))
                            .setHeight(MATCH_PARENT)
                            .setWidth(500);
                    rightMenu.addMenuItem(swipeMenuItem_2);
                }
            });
            swipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
                @Override
                public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                    menuBridge.closeMenu();
                    int menu_position=menuBridge.getPosition();
                    Toast.makeText(context,"哈哈哈"+menu_position,Toast.LENGTH_SHORT).show();
                }
            });

            swipeRecyclerView.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View itemView, int position) {
                    Log.d("ttttt","长按按钮触动");
                    Toast.makeText(context,"长按按钮触动",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainActivity_2.class);
                    intent.putExtra("hhh",id);
                    context.startActivity(intent);
                }
            });

            swipeRecyclerView.setLongPressDragEnabled(true);
            //swipeRecyclerView.setItemViewSwipeEnabled(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            adapter=new ItemAdapter(context,list);
            swipeRecyclerView.setLayoutManager(layoutManager);
            swipeRecyclerView.setAdapter(adapter);
        }
    }

    public static void add(Contents contents){
        list.add(contents);
        adapter.notifyDataSetChanged();
    }
}

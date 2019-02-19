package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Add_content;
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

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.ListPopupWindow.MATCH_PARENT;
import static org.litepal.crud.DataSupport.findAll;
import static org.litepal.crud.DataSupport.findFirst;

public class ItemInit {
    private ItemAdapter adapter;
    private List<Contents>list;
    private String id;
    private Context context;
    private SwipeRecyclerView swipeRecyclerView;

    /*
    * 进行数据库查找，按level顺序排到list中
    *
    * */
    public void initItems(final View view, final Context context, final String id) {
        //查询数据库中，Contents表的所有数据，这里先测试，待完善,
        //该id用于查找当前用户的content

        this.id=id;
        this.context=context;
        list= get_list(id);
        /////
       swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv_show);

        if (true){
            /////////////////////////////////

//            DefaultItemDecoration borderItemDecoration=new DefaultItemDecoration(Color.parseColor("#000000"));
//            swipeRecyclerView.addItemDecoration(borderItemDecoration);

            //点击
            swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    edit(position);
                }
            });

            //滑动
            swipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {

                    SwipeMenuItem swipeMenuItem=new SwipeMenuItem(context);
                    swipeMenuItem.setText("修改");
                    swipeMenuItem.setTextSize(20);
                    swipeMenuItem.setBackgroundColor(Color.parseColor("#FFFF00"));
                    swipeMenuItem.setTextColor(Color.parseColor("#000000"));
                    swipeMenuItem.setHeight(MATCH_PARENT);
                    swipeMenuItem.setWidth(300);
                    rightMenu.addMenuItem(swipeMenuItem);

                    SwipeMenuItem swipeMenuItem_2=new SwipeMenuItem(context)
                            .setText("删除")
                            .setTextSize(20)
                            .setBackgroundColor(Color.parseColor("#FF0000"))
                            .setTextColor(Color.parseColor("#000000"))
                            .setHeight(MATCH_PARENT)
                            .setWidth(300);
                    rightMenu.addMenuItem(swipeMenuItem_2);

                    SwipeMenuItem swipeMenuItem_3=new SwipeMenuItem(context)
                            .setText("已完成")
                            .setTextSize(20)
                            .setBackgroundColor(Color.parseColor("#FFFFE0"))
                            .setTextColor(Color.parseColor("#000000"))
                            .setHeight(MATCH_PARENT)
                            .setWidth(300);
                    leftMenu.addMenuItem(swipeMenuItem_3);

                    SwipeMenuItem swipeMenuItem_4=new SwipeMenuItem(context)
                            .setText("撤销")
                            .setTextSize(20)
                            .setBackgroundColor(Color.parseColor("#EE799F"))
                            .setTextColor(Color.parseColor("#000000"))
                            .setHeight(MATCH_PARENT)
                            .setWidth(300);
                    leftMenu.addMenuItem(swipeMenuItem_4);

                }
            });

            //滑动菜单点击
            swipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
                @Override
                public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                    menuBridge.closeMenu();
                    int direction=menuBridge.getDirection();
                    if(direction==SwipeRecyclerView.LEFT_DIRECTION){
                        //左侧菜单
                        int menu_position=menuBridge.getPosition();
                        Contents item=list.get(position);
                        String str=item.getDate();
                        switch (menu_position){
                            case 0:
                                Log.d("ttttt","左边case0");
                                item.setDone(true);
                                break;
                            case 1:
                                Log.d("ttttt","左边case1");
                                /*
                                * LitePal对默认值不予以更新
                                * */
                                item.setToDefault("done");
                                break;
                            default:
                                Log.d("tttttt","侧滑菜单有问题");
                        }
                        //修改数据库item对应的Done
                        item.updateAll("date=?",str);
                        refresh();
                        Toast.makeText(context,"点击了左菜单",Toast.LENGTH_SHORT).show();
                    }else if(direction==SwipeRecyclerView.RIGHT_DIRECTION){
                        //右侧菜单
                        int menu_position=menuBridge.getPosition();
                        switch (menu_position){
                            case 0:
                                edit(position);
                                break;
                            case 1:
                                Contents item=list.get(position);
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                String date_id=item.getDate();
                                DataSupport.deleteAll(Contents.class,"date = ?",date_id);
                                break;
                            default:
                                Log.d("tttttt","侧滑菜单有问题");
                        }
                    }else{
                        //其他
                        Log.d("ttttt","滑动菜单的左右出错");
                    }
                }
            });

            //长按
            swipeRecyclerView.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View itemView, int position) {
                    Log.d("ttttt","长按按钮触动");
                    Toast.makeText(context,"长按按钮触动",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainActivity_2.class);
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }
            });

            //swipeRecyclerView.setLongPressDragEnabled(true);
            //swipeRecyclerView.setItemViewSwipeEnabled(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            adapter=new ItemAdapter(context,list);
            swipeRecyclerView.setLayoutManager(layoutManager);
            swipeRecyclerView.setAdapter(adapter);
        }
    }

    public void refresh(){
        //list.refresh(contents);
        //重新刷新list
        Log.d("ttttt","在这哈哈哈哈1");
        list= get_list(id);
        Log.d("ttttt",list.size()+"");
        Log.d("ttttt","在这哈哈哈哈2");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        adapter=new ItemAdapter(context,list);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setAdapter(adapter);
        Log.d("ttttt","在这哈哈哈哈3");
//        adapter.notifyDataSetChanged();
    }

    /*
    * 将list翻转，主要是因为需要在显示的时候，按照距离最近的编辑时间优先显示
    * */
    public static List<Contents> get_list(String id){
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

    /*
    * 点击修改逻辑后，跳转到编辑菜单
    * */
    private void edit(int position){
        Intent intent=new Intent(context,Add_content.class);
        intent.putExtra("id",id);
        intent.putExtra("flag",0);
        intent.putExtra("context_id",list.get(position).getDate());
        context.startActivity(intent);
    }
}

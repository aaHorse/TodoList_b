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
import static com.example.zexiger.todolist_b.LitePal_general.Search_result.itemInit;
import static org.litepal.crud.DataSupport.findAll;
import static org.litepal.crud.DataSupport.findFirst;


/*
* 该类将被声明两个对象，一个是MainActivity里面的，一个是Search_Result里面的，在构造函数最后一个参数作为区分
*
* */
public class ItemInit {
    private View view;
    private Context context;
    private String id;
    private String activity;//用于区分
    private ItemAdapter adapter;
    private SwipeRecyclerView swipeRecyclerView;

    /*
    * 构造函数
    * */
    public ItemInit(View view,Context context,String id,String activity){
        this.view=view;
        this.id=id;
        this.context=context;
        this.activity=activity;
    }

    public void initItems() {
        /*
        * 获得当前id对应的item，在这个函数会进行排列
        * */
        if(activity.equals("MainActivity")){
            swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv_show);
        }else if(activity.equals("Search_result")){
            swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.srv_show_3);
        }

        if (true){
            //点击
            swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    edit(position,1);
                }
            });
            //滑动
            swipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                    setMenu(leftMenu,rightMenu);
                }
            });
            //滑动菜单点击
            swipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
                @Override
                public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                    mySetMenuItemListener(menuBridge,position);
                }
            });
            //长按
            swipeRecyclerView.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View itemView, int position) {
                    Toast.makeText(context,"长按按钮触动",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainActivity_2.class);
                    intent.putExtra("id",id);
                    if(activity.equals("MainActivity")){
                        intent.putExtra("activity","MainActivity");
                    }else if(activity.equals("Search_result")){
                        intent.putExtra("activity","Search_result");
                    }
                    context.startActivity(intent);
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            adapter=new ItemAdapter(context,id,activity);
            swipeRecyclerView.setLayoutManager(layoutManager);
            swipeRecyclerView.setAdapter(adapter);
        }
    }

    /*
    * 点击修改逻辑(包括点击修改，点击整个item)后，跳转到编辑菜单
    * flag用于区分：点击整个item为1，点击修改为2,点击＋为3
    * */
    private void edit(int position,int flag){
        Intent intent=new Intent(context,Add_content.class);
        intent.putExtra("id",id);
        intent.putExtra("flag",flag);
        if(activity.equals("MainActivity")){
            intent.putExtra("activity","MainActivity");
        }else if(activity.equals("Search_result")){
            intent.putExtra("activity","Search_result");
        }
        intent.putExtra("context_id",adapter.getList().get(position).getDate());
        context.startActivity(intent);
    }

    /*
    * item的侧滑菜单设置
    * */
    public void setMenu(SwipeMenu leftMenu, SwipeMenu rightMenu){
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

    /*
    * 滑动菜单的点击事件
    * */
    public void mySetMenuItemListener(SwipeMenuBridge menuBridge, int position){
        menuBridge.closeMenu();
        int direction=menuBridge.getDirection();
        if(direction==SwipeRecyclerView.LEFT_DIRECTION){
            //左侧菜单
            int menu_position=menuBridge.getPosition();
            Contents item=adapter.getList().get(position);
            String str=item.getDate();
            switch (menu_position){
                case 0:
                    item.setDone(true);
                    break;
                case 1:
                    /*
                     * LitePal对默认值不予以更新，需要采用这种方式
                     * */
                    item.setToDefault("done");
                    break;
                default:
                    Log.d("ttttt","侧滑菜单有问题");
            }
            //修改数据库item对应的Done
            item.updateAll("date=?",str);
            //刷新adapter,是否已完成
            refreshAll();
        }else if(direction==SwipeRecyclerView.RIGHT_DIRECTION){
            //右侧菜单
            int menu_position=menuBridge.getPosition();
            switch (menu_position){
                case 0:
                    edit(position,2);
                    break;
                case 1:
                    Contents item=adapter.getList().get(position);
                    String date_id=item.getDate();
                    DataSupport.deleteAll(Contents.class,"date = ?",date_id);
                    //刷新adapter,删除单个item
                    refreshAll();
                    break;
                default:
                    Log.d("ttttt","侧滑菜单有问题");
            }
        }else{
            //其他
            Log.d("ttttt","滑动菜单的左右出错");
        }
    }

    /*
    * 添加一个item,因为涉及到优先级的问题，所以直接重新从数据库中获取list
    * 刷新全部，因为编辑一个item后，会进行添加和删除
    * */
    public void refreshAll(){
        adapter.notifyAdapter(activity);
    }
}

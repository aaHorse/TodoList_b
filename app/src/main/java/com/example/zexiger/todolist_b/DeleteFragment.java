package com.example.zexiger.todolist_b;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.recyclerview_show.ItemInit_2;

import org.litepal.crud.DataSupport;

import static com.example.zexiger.todolist_b.LitePal_general.Search_result.itemInit;
import static com.example.zexiger.todolist_b.MainActivity.adapterobj;

public class DeleteFragment extends Fragment {
    private static boolean isAll=false;//是否全选
    private TextView checked_num;
    private ItemInit_2 itemInit_2;

    private Button button;//删除
    private Button button_2;//全选，取消全选
    private Button button_3;//取消
    private View view;//碎片是实例
    private Activity activity;//从碎片中获取Activity活动的实例
    private MainActivity_2_1 activity_2_1;
    private MainActivity_2_2 activity_2_2;

    private String activity_str;//用于区分从不同活动过来的
    private String id;//当前的用户

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment,container,false);
        checked_num=(TextView)view.findViewById(R.id.tv);
        button=view.findViewById(R.id.button_delete);
        button_2=view.findViewById(R.id.button_all);
        button_3=view.findViewById(R.id.button_cancle);

        //刪除
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_2.setText("全选");
                checked_num.setText("选择项目");
                //对于Boolean的删除查找以及修改，需要注意
                DataSupport.deleteAll(Contents.class,"Checked=?","1");
                itemInit_2.refreshAll();
            }
        });

        //全选，取消全选
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刚开始是默认没有全选，
                //这里实现全选，反全选

                //isAll在这里被反转！！！
                if(isAll==false){
                    isAll=true;
                    button_2.setText("取消全选");
                }else{
                    isAll=false;
                    button_2.setText("全选");
                }
                itemInit_2.itemSetChecked(isAll);
                itemInit_2.refreshAll();
            }
        });

        //取消
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contents item=new Contents();
                item.setToDefault("Checked");
                item.updateAll("id_string=?",id);
                if(activity_str.equals("MainActivity")){
                    adapterobj.refreshAll();
                    activity_2_1.back();
                }else if(activity_str.equals("Search_result")){
                    itemInit.refreshAll();
                    activity_2_2.back();
                }else{
                    Log.d("ttttt","无匹配的activity");
                }
                /*
                * 通过调用父类活动中的方法来销毁当前的碎片
                * 解决跳转闪屏问题解决，原因是activity忘记赋值，比较没有else，导致找不到匹配的refresh
                * */
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        if(activity.findViewById(R.id.activity_main_2_1_fragment)!=null){
            activity_2_1=(MainActivity_2_1)activity;
            activity_str=activity_2_1.getActivity_str();
            id=activity_2_1.getId();
        }else if(activity.findViewById(R.id.activity_main_2_2_fragment)!=null){
            activity_2_2=(MainActivity_2_2)activity;
            activity_str=activity_2_2.getActivity_str();
            id=activity_2_2.getId();
        }else{
            Toast.makeText(activity,"比较出错",Toast.LENGTH_SHORT).show();
        }
        Log.d("ttttt","hhhhh1");
        itemInit_2=new ItemInit_2(view,activity,id,activity_str);
        Log.d("ttttt","hhhhh2");
        itemInit_2.inits();
        Log.d("ttttt","hhhhh3");
    }
}

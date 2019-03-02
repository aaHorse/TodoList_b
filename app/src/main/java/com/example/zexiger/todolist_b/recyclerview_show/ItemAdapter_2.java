package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.LitePal_general.Search_result;
import com.example.zexiger.todolist_b.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter_2 extends RecyclerView.Adapter<ItemAdapter_2.ViewHolder> {
    private List<Contents> list=new ArrayList<>();
    private Context context;
    private TextView checked_num;
    private View view;
    private String id;
    /*
    * 调用该adapter会有两种情况：
    * 第一种：在MainActivity长按item
    * 第二种：在搜索结果中，长按item
    * str用于区分这两种情况
    * */
    private String activity_str;


    public ItemAdapter_2(View view,Context context, String id,String str){
        this.id=id;
        Log.d("ttttt","error1");
        this.activity_str=str;
        Log.d("ttttt","error2");
        this.context=context;
        Log.d("ttttt","error4");
        this.view=view;
        Log.d("ttttt","|||activity:"+activity_str);
        this.list=get_list(this.id);
        Log.d("ttttt","error3");
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView_2;
        CheckBox checkBox;
        RatingBar ratingBar;

        View listView;
        public ViewHolder(View view){
            super(view);
            listView=view;
            textView=(TextView)view.findViewById(R.id.tv_content_2);
            textView_2=(TextView)view.findViewById(R.id.tv_date_2);
            checkBox=(CheckBox)view.findViewById(R.id.checkBox_2);
            ratingBar=(RatingBar)view.findViewById(R.id.ratingbar_show_2);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_item_layout_2,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Contents item=list.get(i);
        viewHolder.textView.setText(item.getContent_text());
        viewHolder.textView_2.setText(item.getDate());
        viewHolder.checkBox.setChecked(item.isChecked());
        checked_num=(TextView)view.findViewById(R.id.tv);
        viewHolder.ratingBar.setRating(item.getLevel());

        /*
        * 设置划线
        * */
        if (item.isDone()){
            viewHolder.textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if(item.isChecked()){
            viewHolder.checkBox.setChecked(true);
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //true
                    item.setChecked(isChecked);
                    item.updateAll("date=?",item.getDate());
                }else{
                    //false
                    item.setToDefault("Checked");
                    item.updateAll("date=?",item.getDate());
                }
                int num=getCheckedNum();
                if(num==0){
                    checked_num.setText("选择项目");
                }else if(num>0){
                    checked_num.setText("选择了"+num+"项");
                }
            }
        });
    }

    private int getCheckedNum(){
        int num=0;
        list=get_list(id);
        for(int i=0;i<list.size();i++){
            Contents item=list.get(i);
            if(item.isChecked()){
                num++;
            }
        }
        return num;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Contents> getList() {
        return list;
    }

    public void setList(List<Contents> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /*
     * 以下代码实现批量删除操作
     * @parameter flag 是否进行了删除操作
     * */
    public void notifyAdapter() {
        list=get_list(id);
        notifyDataSetChanged();
    }

    /*
     * 将list翻转，主要是因为需要在显示的时候，按照距离最近的编辑时间优先显示
     * */
    public List<Contents> get_list(String id){
        Log.d("ttttt",activity_str);
        if(activity_str.equals("MainActivity")){
            Log.d("ttttt","进入了MainActivity条件里面，id为："+id);
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
            Log.d("ttttt","hhhhhhhhhhh1");
            return list_all;
        }else if(activity_str.equals("Search_result")){
            List<Contents> list=Search_result.getList();
            Log.d("ttttt","hhhhhhhhhhh2");
            return list;
        }else{
            Log.d("ttttt","ItemAdapter_2得到的list为空");
            List<Contents> list=new ArrayList<>();
            return list;
        }
    }
}

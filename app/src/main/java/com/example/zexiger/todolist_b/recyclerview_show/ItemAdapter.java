package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Contents> list;
    private Context context;
    private String id;

    public ItemAdapter(Context context,String id){
        this.id=id;
        this.list=get_list(id);
        this.context=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView_2;
        View listView;
        RatingBar ratingBar;
        public ViewHolder(View view){
            super(view);
            listView=view;
            textView=(TextView)view.findViewById(R.id.tv_content);
            textView_2=(TextView)view.findViewById(R.id.tv_date);
            ratingBar=(RatingBar)view.findViewById(R.id.ratingbar_show);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_item_layout,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Contents item=list.get(i);
        viewHolder.textView.setText(item.getContent_text());
        viewHolder.textView_2.setText(item.getDate());
        viewHolder.ratingBar.setRating(item.getLevel());

        if (item.isDone()){
            viewHolder.textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textView.getPaint().setAntiAlias(true);
        }else{
            Log.d("ttttt","item.isDone=false");
            viewHolder.textView.getPaint().setFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*
    * 以下代码实现批量删除操作
    * @parameter flag 是否进行了删除操作
    * */
    public void notifyAdapter(boolean flag) {
        //在这里还没有体现出差别，等到代码实现后进行完善
        if (flag) {
            //如果进行了
            this.list=get_list(id);
        } else {
            //如果list没有发生改变
            this.list=get_list(id);
        }
        notifyDataSetChanged();
    }

    /*
     * 将list翻转，主要是因为需要在显示的时候，按照距离最近的编辑时间优先显示
     * */
    public List<Contents> get_list(String id){
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
    * 获取当前正在显示的list
    * */
    public List<Contents> getList(){
        return this.list;
    }

}

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

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Contents> list;
    private Context context;

    public ItemAdapter(Context context, List<Contents>list){
        this.list=list;
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
}

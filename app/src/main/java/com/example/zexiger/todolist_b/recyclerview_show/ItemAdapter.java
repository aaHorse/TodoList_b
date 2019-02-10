package com.example.zexiger.todolist_b.recyclerview_show;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Contents> itemList;

    public ItemAdapter(List<Contents>list){
        this.itemList=list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView_2;
        View listView;
        public ViewHolder(View view){
            super(view);
            listView=view;
            textView=(TextView)view.findViewById(R.id.tv_content);
            textView_2=(TextView)view.findViewById(R.id.tv_date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_item_layout,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                switch(position){
                    default:
                        Toast.makeText(v.getContext(),"hahahahaha",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Contents item=itemList.get(i);
        viewHolder.textView.setText(item.getContent());
        viewHolder.textView_2.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

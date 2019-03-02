package com.example.zexiger.todolist_b.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.activities.About;
import com.example.zexiger.todolist_b.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private List<Item>itemList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        View listView;
        public ViewHolder(View view){
            super(view);
            listView=view;
            textView=(TextView)view.findViewById(R.id.item_name);
        }
    }

    public ItemAdapter(Context context,List<Item> list){
        this.context=context;
        this.itemList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                switch(position){
                    case 0:
                        Toast.makeText(v.getContext(),"(=^@^=)",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(v.getContext(),"该功能没有实现",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent=new Intent(context,About.class);
                        context.startActivity(intent);
                        break;
                    default:
                        Log.d("ttttt","侧滑菜单的position出问题了");
                        break;
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Item item=itemList.get(i);
        viewHolder.textView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

package com.example.zexiger.todolist_b.recyclerview_show;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.R;

import java.util.List;

public class ItemAdapter_2 extends RecyclerView.Adapter<ItemAdapter_2.ViewHolder> {
    private List<Contents> list;
    private Context context;
    private TextView checked_num;
    private View view;


    public ItemAdapter_2(View view,Context context, List<Contents>list){
        this.list=list;
        this.context=context;
        this.view=view;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView_2;
        CheckBox checkBox;

        View listView;
        public ViewHolder(View view){
            super(view);
            listView=view;
            textView=(TextView)view.findViewById(R.id.tv_content_2);
            textView_2=(TextView)view.findViewById(R.id.tv_date_2);
            checkBox=(CheckBox)view.findViewById(R.id.checkBox_2);
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

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);

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

}

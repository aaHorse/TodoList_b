package com.example.zexiger.todolist_b.LitePal_general;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_content {
    private String id=Sign_in_general.right_id;
    private EditText editText;
    private Button button;
    public void add_content(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content_text=editText.getText().toString();
                Contents user=new Contents();
                user.setId(id);
                user.setContent(content_text);
                user.setDate("2019年2月8日");
                user.setTime("22时10分");
                user.setLevel(1);
                user.setDone(false);
                user.save();
            }
        });
    }
}

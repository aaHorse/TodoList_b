package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;

public class Add_content extends AppCompatActivity {
    private String id=Sign_in_general.right_id;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_content);

        editText=(EditText)findViewById(R.id.et_add);
        button=(Button)findViewById(R.id.bt_add);

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

                //完成编辑之后，跳回主界面
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}

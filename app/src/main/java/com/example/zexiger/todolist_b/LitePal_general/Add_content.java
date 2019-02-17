package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.classichu.lineseditview.LinesEditView;
import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.SQLite_User.Users;

import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.crud.DataSupport.find;
import static org.litepal.crud.DataSupport.findAll;
import static org.litepal.crud.DataSupport.findFirst;

public class Add_content extends AppCompatActivity {
    private String id;
    /*
    * 使用第三方控件
    * */
    private LinesEditView editText;

    private Button button;//界面的添加按钮
    private Button button_2;//界面的返回按钮
    private ImageButton button_3;//界面的垃圾桶按钮
    private ImageButton button_4;//界面的勾按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_content);

        editText=(LinesEditView)findViewById(R.id.et_add);
        button=(Button)findViewById(R.id.bt_add);
        button_2=(Button)findViewById(R.id.back) ;
        button_3=(ImageButton)findViewById(R.id.delete_input);
        button_4=(ImageButton)findViewById(R.id.input_done);

        editText.setHintText("input here ...");
        editText.setContentTextSize(80);
        editText.setContentTextColor(Color.parseColor("#000000"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成编辑之后，跳回主界面
                Intent intent_2=new Intent();
                setResult(RESULT_OK,intent_2);
                finish();//按了返回键，不保存数据，直接销毁当前的界面
            }
        });
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setContentText("");
            }
        });
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save(){
        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        String content_text=editText.getContentText().toString();

        Contents user=new Contents();

        user.setId_string(id);
        user.setContent_text(content_text);
        user.setDate("2019年2月8日");
        user.setLevel(1);
        user.setDone(false);
        user.setChecked(false);

        user.save();

        //完成编辑之后，跳回主界面
        Intent intent_2=new Intent();
        intent_2.putExtra("user",user);
        setResult(RESULT_OK,intent_2);
        finish();
    }
}

package com.example.zexiger.todolist_b.LitePal_general;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.SQLite_User.Users;

public class Create_user extends AppCompatActivity {

    private static int id=1;//用户的id，采用顺序编排

    private EditText editText;//name
    private EditText editText_2;//password
    private EditText editText_3;//再次确认密码
    private Button button;//注册按钮

    private Users user;//数据库

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_general);

        editText=(EditText)findViewById(R.id.name);
        editText_2=(EditText)findViewById(R.id.password);
        editText_3=(EditText)findViewById(R.id.password_2);
        button=(Button)findViewById(R.id.create_user_general);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_string=editText.getText().toString();
                String password_string=editText_2.getText().toString();
                String password_string_2=editText_3.getText().toString();
                if(password_string.equals(password_string_2)){
                    save_user(name_string,password_string);//成功注册，进行保存
                    Intent intent=new Intent(Create_user.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Create_user.this,"两次密码不符",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 使用SQLite数据库存储用户的账号和密码，注：对于每一条item，使用的是LitePal存储
     * */
    private void save_user(String name,String password){
        user=new Users(this,"Users.db",null,1);
        SQLiteDatabase db=user.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name_id","GG"+id);
        id++;
        values.put("name",name);
        values.put("password",password);
        db.insert("User",null,values);
    }
}

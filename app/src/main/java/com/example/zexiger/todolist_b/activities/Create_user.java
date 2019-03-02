package com.example.zexiger.todolist_b.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zexiger.todolist_b.something.DBUtils;
import com.example.zexiger.todolist_b.something.EditTextShakeHelper;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.SQLite_user.Users;

public class Create_user extends BaseActivity {

    /*
     * 用于给QQ登录的用户分配id，
     * 使用MySQL上面的数据进行初始化
     * 和general登录共用同一个MySQL数据库上面的表
     * id在这里为负数，在后面会被初始化为正数
     * */
    private static int id=-100;


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
                }else{
                    new EditTextShakeHelper(Create_user.this).shake(editText_2,editText_3);
                    Toast.makeText(Create_user.this,"两次密码不符",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 使用SQLite数据库存储用户的账号和密码，注：对于每一条item，使用的是LitePal存储
     * */
    private void save_user(final String name, final String password){
        //初始化id
        new Thread(new Runnable(){
            public void run(){
                id=DBUtils.get_mysql_id();
                user=new Users(Create_user.this,"users.db",null,1);
                /*
                 * 用户填完信息，成功注册后，系统将分配一个id字符串，将把这个字符串传到用户完成注册后的反馈页面
                 * */
                String user_id;
                SQLiteDatabase db=user.getWritableDatabase();
                ContentValues values=new ContentValues();
                user_id="GG"+id;
                values.put("user_id",user_id);
                values.put("name",name);
                values.put("password",password);
                db.insert("User",null,values);

                //跳转到注册成功界面
                Intent intent=new Intent(Create_user.this,Succeed.class);
                intent.putExtra("user_name",name);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        }).start();
    }
}

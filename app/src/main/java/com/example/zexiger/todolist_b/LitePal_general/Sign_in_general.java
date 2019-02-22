package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.SQLite_User.Users;

public class Sign_in_general {
    private static Button sign_in;//登录按钮
    private static CheckBox checkBox;//是否选择“记住密码”
    private static EditText text_id;//用户输入的id
    private static EditText text_password;//用户输入的密码
    public static  String right_id;//正确登录后，改id设置为public，访问或存单个content时，通过该id获得对应的数据

    /**
     * Litepal数据库的general登录功能实现，检查id和password是否对应，如果对应，跳转到main页面
     * */
    public static void sign_in(final View view, final Context context){
        sign_in=(Button)view.findViewById(R.id.btn_login);
        checkBox=(CheckBox)view.findViewById(R.id.cb_checkbox);
        text_id=(EditText) view.findViewById(R.id.et_userName);
        text_password=(EditText)view.findViewById(R.id.et_password);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果勾选了记住密码，在这里将信息进行存储
                boolean isChecked=checkBox.isChecked();
                String id=text_id.getText().toString();
                String password=text_password.getText().toString();
                if(checkInput(id,password,context)){
                    if (isChecked){
                        save(view,context);
                    }
                    right_id=id;
                    Intent intent=new Intent(context,MainActivity.class);
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "账号或密码输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 检查用户的登录输入
     * 用户的id，password输入正确，则返回true
     * */
    private static boolean checkInput(String id,String password,Context context){
        Users user=new Users(context,"users.db",null,1);
        SQLiteDatabase db=user.getWritableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String id_db=cursor.getString(cursor.getColumnIndex("user_id"));
                String password_db=cursor.getString(cursor.getColumnIndex("password"));
                if(id_db.equals(id)&&password_db.equals(password)){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        return false;
    }

    /**
     * 如果选择了记住密码，
     * 这里将界面显示出来
     * */
    public static void checkout_show(View view,Context context){
        checkBox=(CheckBox)view.findViewById(R.id.cb_checkbox);
        text_id=(EditText) view.findViewById(R.id.et_userName);
        text_password=(EditText)view.findViewById(R.id.et_password);

        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        String user_id=preferences.getString("user_id","404");
        String user_name=preferences.getString("user_password","null");
        text_id.setText(user_id);
        text_password.setText(user_name);
        checkBox.setChecked(true);
    }

    /*
    * 对勾选了记住密码的用户进行信息存储
    * */
    public static void save(final View view,Context context){
        text_id=(EditText) view.findViewById(R.id.et_userName);
        text_password=(EditText)view.findViewById(R.id.et_password);

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.clear();

        editor.putBoolean("remember_password",true);
        editor.putString("user_id",text_id.getText().toString());
        editor.putString("user_password",text_password.getText().toString());

        editor.apply();
    }
}

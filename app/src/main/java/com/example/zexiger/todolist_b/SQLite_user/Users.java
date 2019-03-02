package com.example.zexiger.todolist_b.SQLite_user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Users extends SQLiteOpenHelper {
    //在建表时，id作为主键，不需要程序员初始化，否则违反对用户无意义原则
    public static final String User_String="create table User ("
            +"id integer primary key autoincrement,"
            +"user_id text,"
            +"name text,"
            +"password text )";

    private Context context;

    public Users(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        db.execSQL(User_String);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}

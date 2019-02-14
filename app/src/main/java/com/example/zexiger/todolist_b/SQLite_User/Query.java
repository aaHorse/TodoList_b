package com.example.zexiger.todolist_b.SQLite_User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Query {

    /*
    * 查询通过QQ登录的用户中，该用户对应的id是否存在
    * 注：general注册登录的用户，不需要查询数据库，因为其只能通过账号登录
    * */
    public static boolean query_have(String name_query, Context context){
        Users user=new Users(context,"users.db",null,1);
        SQLiteDatabase db=user.getWritableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                if(name_query.equals(name)){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    /*
    * 通过name,获得user_id，并且返回user_id
    * */
    public static String get_id(String name_query,Context context){
        Users user=new Users(context,"users.db",null,1);
        SQLiteDatabase db=user.getWritableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String id=cursor.getString(cursor.getColumnIndex("user_id"));
                String name=cursor.getString(cursor.getColumnIndex("name"));
                if(name_query.equals(name)){
                    return id;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return "通过name查询id，但是无法找到对应的id";
    }

    /*
    * 通过id查询对应的name，并返回name
    * */
    public static String get_name(String id_query,Context context){
        Users user=new Users(context,"users.db",null,1);
        SQLiteDatabase db=user.getWritableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String id=cursor.getString(cursor.getColumnIndex("user_id"));
                String name=cursor.getString(cursor.getColumnIndex("name"));
                if(id_query.equals(id)){
                    return name;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return "通过id查询name，但是无法找到对应的name";
    }

    /*
    * 打印所有用户的数据
    * */
    public static void print_all(Context context){
        String id="hhhhhhhhhhhh";
        String name="ooooooooooooooooooo";
        Users user=new Users(context,"users.db",null,1);
        SQLiteDatabase db=user.getWritableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                id=cursor.getString(cursor.getColumnIndex("user_id"));
                name=cursor.getString(cursor.getColumnIndex("name"));
                Log.d("ttttt",id);
                Log.d("ttttt",name);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}

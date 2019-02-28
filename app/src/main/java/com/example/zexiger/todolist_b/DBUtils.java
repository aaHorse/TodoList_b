package com.example.zexiger.todolist_b;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    /*
     * MySQL在linux服务器上面的数据库名字，
     * 以及和要操作的表的名字
     * */
    private static final String mydatabase="mydatabase";
    private static final String mytable_2="mytable_2";

    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://112.74.35.126:3306/"+mydatabase;
    private static final String DBUSER = "root";
    private static final String DBPASSWORD = "Horse123+";


    /*
    * 打开MySQL上面的数据库，读取上面的user_id,然后将上面的user_id加1
    *
    * */
    public static int get_mysql_id(){
        int id=0;
        Connection conn=null;
        PreparedStatement stmt=null;
        try {
            Class.forName(DBDRIVER);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ttttt","出问题了1");
        }
        try{
            conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
            //
            String sql = "SELECT * from "+mytable_2;
            stmt= conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id= rs.getInt("user_id");
                Log.d("ttttt", "hhh"+id+"hhh");
            }
            //UPDATE cs_user SET gender = '男' WHERE id = 4
            try {
                int id_2=id;
                id_2++;
                String sq2="UPDATE "+mytable_2+" SET user_id = \'"+id_2+"\' WHERE user_id = "+id;
                Statement st=conn.createStatement();//创建用于执行静态的statement对象
                int count=st.executeUpdate(sq2);//执行sql插入（删除、更新）语句，返回插入的行数
                Log.d("ttttt","修改了第"+count+"行");
            } catch (SQLException e) {
                Log.d("ttttt","出问题了2");
                e.printStackTrace();
            }
            //
            rs.close();
            stmt.close();
        } catch (Exception e) {
            Log.d("ttttt","出问题了3");
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                try {
                    conn.close();
                }catch (Exception e){
                    Log.d("ttttt","出问题了4");
                    e.printStackTrace();
                }
            }
        }
        return id;
    }
}
